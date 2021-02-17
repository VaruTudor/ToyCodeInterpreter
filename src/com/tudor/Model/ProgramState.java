package com.tudor.Model;

import com.tudor.Exceptions.NoStatementsInProgramState;
import com.tudor.Model.ADTs.*;
import com.tudor.Model.Statements.NopStatement;
import com.tudor.Model.Statements.Statement;
import com.tudor.Model.Values.StringValue;
import com.tudor.Model.Values.Value;

import java.io.BufferedReader;
import java.util.Stack;

public class ProgramState {
    private int id = 0;
    public int getId() {
        return id;
    }


    private static int threadCount = 0;
    public synchronized static int nextId(){
        threadCount++;
        return threadCount;
    }
    public void setId(int newId){id = newId;}


    private final IStack<Statement> stackOfStatements;
    private final IDict<String, Value> symbolTable;
    private final IList<Value> outputList;
    private final IFileTable<StringValue, BufferedReader> fileTable;
    private final IHeap<Integer,Value> heap;
    Statement originalProgram; // don't really use this

    //constructor with 3 params
    //no file table
    //no heap
    public ProgramState(IStack<Statement> stack, IDict<String, Value> dict, IList<Value> list) {
        stackOfStatements = stack;
        symbolTable = dict;
        outputList = list;
        fileTable = new MyFileTable<>();
        heap = new MyHeap<>();
    }

    //constructor with 4 params
    //no heap
    public ProgramState(IStack<Statement> stack, IDict<String, Value> dict, IList<Value> list, IFileTable<StringValue, BufferedReader> fileDict) {
        stackOfStatements = stack;
        symbolTable = dict;
        outputList = list;
        fileTable = fileDict;
        heap = new MyHeap<>();
    }

    //constructor with 5 params
    public ProgramState(IStack<Statement> stack, IDict<String, Value> dict, IList<Value> list, IFileTable<StringValue, BufferedReader> fileDict, IHeap<Integer,Value> heap) {
        stackOfStatements = stack;
        symbolTable = dict;
        outputList = list;
        fileTable = fileDict;
        this.heap = heap;
    }

    //constructor with 0 params
    public ProgramState() {
        stackOfStatements = new MyStack<>();
        symbolTable = new MyDict<>();
        outputList = new MyList<>();
        fileTable = new MyFileTable<>();
        heap = new MyHeap<>();
        originalProgram = new NopStatement();
    }

    public IFileTable<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public IStack<Statement> getStack() { return stackOfStatements; }

    public IDict<String, Value> getSymTable() {
        return symbolTable;
    }

    public IList<Value> getList() {
        return outputList;
    }

    public IHeap<Integer, Value> getHeap() {
        return heap;
    }

    /**
     * get all Collections which are part of the ProgramState
     * and return a String of their contents (like a detailed debug)
     * @return output (type String)
     */
    public String toStringAsCode(){
        StringBuilder output = new StringBuilder();
        Stack<Statement> stack = stackOfStatements.getStack();
        for(Statement statement : stack){
            output.append(statement.toStringAsCode());
        }
        return output.toString();
    }

    public boolean isNotCompleted(){
        return !(stackOfStatements.isEmpty() );
    }

    public ProgramState oneStep() {
        if(stackOfStatements.isEmpty()){
            throw new NoStatementsInProgramState();
        }
        Statement currentStatement = stackOfStatements.pop();
        return currentStatement.execute(this);
    }

    @Override
    public String toString(){
        String output = "--------------------------------------------------------";

        output += "\nid = ";
        output += id;

        output += "\n[stackOfStatements]\n";
        output += stackOfStatements.toString();

        output += "\n[symbolTable]\n";
        output += symbolTable.toString();

        output += "\n[outputList]\n";
        output += outputList.toString();

        output += "\n[heap]\n";
        output += heap.toString();

        output += "\n--------------------------------------------------------";
        return  output;

    }
}
