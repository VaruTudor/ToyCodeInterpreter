package com.tudor.Model.Statements;

import com.tudor.Exceptions.FilenameAlreadyDeclared;
import com.tudor.Exceptions.FilenameNotDeclared;
import com.tudor.Exceptions.OpenFileException;
import com.tudor.Exceptions.TypeExceptions.ValueNotStringType;
import com.tudor.Model.ADTs.IDict;
import com.tudor.Model.ADTs.IFileTable;
import com.tudor.Model.ADTs.IHeap;
import com.tudor.Model.Expressions.Expression;
import com.tudor.Model.ProgramState;
import com.tudor.Model.Types.StringType;
import com.tudor.Model.Types.Type;
import com.tudor.Model.Values.StringValue;
import com.tudor.Model.Values.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class openRFile implements Statement{
    Expression expression;

    public openRFile(Expression expression) {
        this.expression = expression;
    }

    /**
     * Opens the file with name returned by expression.evaluate
     * and adds it to the state's fileTable
     * @param state (type ProgramState)
     * @return null
     * @throws ValueNotStringType expression.evaluate is not StringValue(which has StringType)
     * @throws FilenameNotDeclared expression.evaluate not found as key in state's fileTable
     * @throws OpenFileException cannot open bufferReader
     */
    @Override
    public ProgramState execute(ProgramState state) {
        IDict<String, Value> symbolTable = state.getSymTable();
        IFileTable<StringValue, BufferedReader> fileTable = state.getFileTable();
        IHeap<Integer, Value> heap = state.getHeap();

        Value evaluatedExpression = expression.evaluate(symbolTable,heap);

        if (!evaluatedExpression.getType().equals(new StringType())){
            // check if the expression evaluates to StringType
            throw new ValueNotStringType();
        }
        if (fileTable.isDefined((StringValue) evaluatedExpression))
        {
            // check if the expression evaluated is declared in the file table
            throw new FilenameAlreadyDeclared();
        }


        BufferedReader bufferedReader;
        try{
            bufferedReader = new BufferedReader(new FileReader(((StringValue) evaluatedExpression).getValue()));
            fileTable.add((StringValue) evaluatedExpression,bufferedReader);
        } catch (FileNotFoundException e) {
            // we used the try catch just to throw a runtime exception
            throw new OpenFileException();
        }

        return  null;
    }

    @Override
    public String toStringAsCode() {
        return "you should do toStringAsCode for openRFile";
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnvironment) {
        expression.typecheck(typeEnvironment);
        return typeEnvironment;
    }
}
