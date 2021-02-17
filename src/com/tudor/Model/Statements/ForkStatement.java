package com.tudor.Model.Statements;

import com.tudor.Exceptions.TypeExceptions.TypeMismatch;
import com.tudor.Model.ADTs.*;
import com.tudor.Model.ProgramState;
import com.tudor.Model.Types.Type;
import com.tudor.Model.Values.StringValue;
import com.tudor.Model.Values.Value;

import java.io.BufferedReader;

public class ForkStatement implements Statement{
    Statement statement;

    public ForkStatement(Statement statement) {
        this.statement = statement;
    }

    /**
     * Creates a new ProgramState which shares the outputList, fileTable and heap with state
     * and has a copy of the symbolTable and a brand new stack. The id field is set to the next available one.
     * @param state (type ProgramState)
     * @return the newly created ProgramState
     * @throws TypeMismatch if the symbolTable is not type MyDict (cast will fail)
     * @throws RuntimeException if the copy constructor for MyDict throws a RuntimeException
     */
    @Override
    public ProgramState execute(ProgramState state) {
        IDict<String, Value> symbolTable                = state.getSymTable();
        IList<Value> outputList                         = state.getList();
        IFileTable<StringValue, BufferedReader> fileTable    = state.getFileTable();
        IHeap<Integer, Value> heap                      = state.getHeap();

        // new stack which contains only the statement to be executed within fork
        IStack<Statement> newStack = new MyStack<>();
        newStack.push(statement);

        // clone object of symbol table so we have all variables but we cannot change the ones from the outer programState
        if(symbolTable instanceof MyDict){
            IDict<String, Value> newSymbolTable = new MyDict<>((MyDict<String, Value>) symbolTable);
            ProgramState newProgramState = new ProgramState(newStack,newSymbolTable,outputList,fileTable,heap);
            newProgramState.setId(ProgramState.nextId());

            return newProgramState;
        }else {
            throw new TypeMismatch();
        }

    }

    @Override
    public String toStringAsCode() {
        return "fork(" + statement.toString() + ");";
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnvironment) {
        // every "scope has a new type environment"
        statement.typecheck(new MyDict<>((MyDict<String, Type>) typeEnvironment));
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "fork(" + statement.toString() + ")  ";
    }
}
