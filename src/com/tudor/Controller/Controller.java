package com.tudor.Controller;

import com.tudor.Model.ADTs.IHeap;
import com.tudor.Model.ProgramState;
import com.tudor.Model.Values.RefValue;
import com.tudor.Model.Values.Value;
import com.tudor.Repository.Repo;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private final Repo repository;
    private ExecutorService executorService = Executors.newFixedThreadPool(2);
    private boolean muteLogProgramStateExecution;
    private boolean mutePrintProgramStateExecution;

    public Controller(Repo repository) {
        this.repository = repository;
        muteLogProgramStateExecution = false;
        mutePrintProgramStateExecution = false;
    }

    public void setMuteLogProgramStateExecution(boolean muteLogProgramStateExecution) {
        this.muteLogProgramStateExecution = muteLogProgramStateExecution;
    }

    public void setMutePrintProgramStateExecution(boolean mutePrintProgramStateExecution) {
        this.mutePrintProgramStateExecution = mutePrintProgramStateExecution;
    }

    public void addProgram(ProgramState newProgramState) {
        repository.addProgramState(newProgramState);
    }

    /**
     *  Filters the input by keeping only the address field
     *  from the Values in symbol table which are RefValue.
     * @param symbolTableValues all entries in a symbol table (type Collection<Value>)
     * @return the update symbolTableValues (type List<int>)
     */
    private List<Integer> getAddressesFromSymbolTable(Collection<Value> symbolTableValues){
        return  symbolTableValues.stream()
                .filter(value -> value instanceof RefValue)
                .map(value -> {RefValue valueFromSymbolTable = (RefValue)value; return valueFromSymbolTable.getAddress();})
                .collect(Collectors.toList());
    }

    /**
     * Filters the input by keeping only the address field
     * from the Values in heap which are RefValue.
     * @param heapValues all entries in a heap(type Collection<Value>)
     * @return the update heapValues (type List<int>)
     */
    private List<Integer> getAddressesFromHeap(Collection<Value> heapValues){
        return heapValues.stream()
                .filter(value -> value instanceof RefValue)
                .map(value -> {RefValue valueFromHeap = (RefValue)value; return valueFromHeap.getAddress();})
                .collect(Collectors.toList());
    }

    /**
     * It removes all entries in heap which don't have the key in addressesFromSymbolTable or addressesFromHeapValues.
     * @param addressesFromSymbolTable (type List<Integer>) all keys from a ProgramState's symbolTable
     * @param addressesFromHeapValues (type List<Integer>) all keys from a ProgramState's heap (or found in RefValues's address)
     * @param heap (type Map<Integer, Value>)
     * @return the modified heap
     */
    private Map<Integer, Value> garbageCollector(List<Integer> addressesFromSymbolTable, List<Integer> addressesFromHeapValues, Map<Integer,Value> heap){
        return heap.entrySet().stream()
                .filter(entry -> addressesFromSymbolTable.contains(entry.getKey()) || addressesFromHeapValues.contains(entry.getKey()) )
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * It filters all ProgramStates by removing the ones which are "completed" (isNotCompleted returns false)
     * @param programList an initial list of ProgramState (type List<ProgramState>)
     * @return the modified programList
     */
    List<ProgramState> removeCompletedPrograms(List<ProgramState> programList){
        return programList.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public void oneStep(){
        executorService = Executors.newFixedThreadPool(2);
        List<ProgramState> programStateList = removeCompletedPrograms(getProgramStates());
        if(programStateList.size() > 0){
            //call garbage collector
            IHeap<Integer, Value> heap = programStateList.get(0).getHeap();
            for (ProgramState programState: programStateList) {
                // for the symbol table we must take it for each due to it being a local state
                programState.getHeap().setContent(garbageCollector(
                        getAddressesFromSymbolTable(programState.getSymTable().getContent().values()),
                        getAddressesFromHeap(heap.getContent().values()),
                        heap.getContent()
                        )
                );
            }
            try{
                oneStepForAllProgramStates(programStateList);
            }catch (Exception e){
                throw new RuntimeException(e.getMessage());
            }

        }
        executorService.shutdownNow();
        repository.setProgramStateList(removeCompletedPrograms(repository.getProgramStateList()));
    }

    /**
     * <p>
     *     Firstly, it creates a List of Callables from programStateList. Then, the executorService creates a List
     * of Futures which used as a stream will give update programStateList by executing one step for each ProgramState
     * (which should be in a separate thread). The programStateList is updated by adding ProgramStates for which one step returned
     * not null (one step executed a ForkStatement).
     * </p>
     * <p>
     *     Basically for each ForkStatement executed, there will be a new ProgramState in programStateList.
     * </p>
     * <p>
     *      ProgramStates are logged into a file if "not muted"(muteLogProgramStateExecution is false).
     * </p>
     * @param programStateList (type List<ProgramState>) repository's programStates
     */
    public void oneStepForAllProgramStates(List<ProgramState> programStateList) {
        if(!muteLogProgramStateExecution)
            programStateList.forEach(repository::logProgramStateExecution);

        // now we want to execute one step for each program states
        List<Callable<ProgramState>> callableProgramStateList = programStateList
                .stream()
                .map(
                        // we change each programState into a Callable<ProgramState>
                        (ProgramState programState) -> (Callable<ProgramState>)(programState::oneStep)
                )
                .collect(Collectors.toList());

        try {
            List<ProgramState> newProgramStateList = executorService.invokeAll(callableProgramStateList)
                    // in the stream we will have a Future for each program state containing it's execution result
                    .stream()
                    .map(programStateFuture -> {
                        try {
                            return programStateFuture.get();
                        } catch (InterruptedException e) {
                            throw new RuntimeException("InterruptedException()\n");
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                            throw new RuntimeException("ExecutionException()\n");
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            // add the new ProgramStates
            programStateList.addAll(newProgramStateList);
            if(!muteLogProgramStateExecution)
            programStateList.forEach(repository::logProgramStateExecution);

            // save the modifications in the repository
            repository.setProgramStateList(programStateList);
        } catch (InterruptedException e) {
            throw new RuntimeException("InterruptedException\n");
        }
    }

    /**
     *  <p>
     *      It executes oneStepForAllProgramStates until programStateList becomes empty.
     *      Also clears the heap for each ProgramState using garbageCollector method.
     *  </p>
     *  <p>
     *      ProgramStates are logged into a file if "not muted"(muteLogProgramStateExecution is false).
     *      ProgramStates are printed on the screen if "not muted"(mutePrintProgramStateExecution is false).
     *  </p>
     */
    public void allStep() {
        repository.setLogFilePath("C:\\Users\\Tudor\\Desktop\\D\\faculta\\SemIII\\MAP\\Labs\\week 8(16 -20 November)\\Lab8_A5\\output.txt");
        executorService = Executors.newFixedThreadPool(2);

        // remove completed
        List<ProgramState> programStateList = removeCompletedPrograms(repository.getProgramStateList());
        if (!mutePrintProgramStateExecution)
            System.out.println(programStateList.get(0).toStringAsCode());

        while(programStateList.size() > 0){
            // use garbageCollector
            // we can take the heap from the first programState since it's shared
            IHeap<Integer, Value> heap = programStateList.get(0).getHeap();
            for (ProgramState programState: programStateList) {
                // for the symbol table we must take it for each due to it being a local state
                programState.getHeap().setContent(garbageCollector(
                        getAddressesFromSymbolTable(programState.getSymTable().getContent().values()),
                        getAddressesFromHeap(heap.getContent().values()),
                        heap.getContent()
                    )
                );
            }
            
            oneStepForAllProgramStates(programStateList);
            if (!mutePrintProgramStateExecution)
                for (ProgramState programState: programStateList) {
                    System.out.println(programState.toString());
                }

            programStateList = removeCompletedPrograms(repository.getProgramStateList());
        }
        executorService.shutdown();

        repository.setProgramStateList(programStateList);
    }

    public int getNumberOfProgramStates(){
        return this.repository.getProgramStateList().size();
    }

    public List<Integer> getIdsOfProgramStatesAsList(){
        return this.repository.getProgramStateList()
                .stream()
                .map(ProgramState::getId)
                .collect(Collectors.toList());
    }

    public ProgramState getProgramStateHavingId(int id){
        return this.repository.getProgramStateHavingId(id);
    }

    public List<ProgramState> getProgramStates(){
        return this.repository.getProgramStateList();
    }

}
