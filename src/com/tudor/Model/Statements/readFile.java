package com.tudor.Model.Statements;

import com.tudor.Exceptions.FilenameNotDeclared;
import com.tudor.Exceptions.ReadLineException;
import com.tudor.Exceptions.TypeExceptions.ValueNotStringType;
import com.tudor.Model.ADTs.IDict;
import com.tudor.Model.ADTs.IFileTable;
import com.tudor.Model.ADTs.IHeap;
import com.tudor.Model.Expressions.Expression;
import com.tudor.Model.ProgramState;
import com.tudor.Model.Types.StringType;
import com.tudor.Model.Types.Type;
import com.tudor.Model.Values.IntValue;
import com.tudor.Model.Values.StringValue;
import com.tudor.Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class readFile implements Statement{
    Expression expression;
    String variableName;

    public readFile(Expression expression, String variableName) {
        this.expression = expression;
        this.variableName = variableName;
    }

    /**
     * Reads a line from the file named as the return of expression.evaluate
     * and updates the key variableName from state's symbolTable with it (the read line)
     * @param state (type ProgramState)
     * @return null
     * @throws ValueNotStringType expression.evaluate is not StringValue(which has StringType)
     * @throws FilenameNotDeclared expression.evaluate not found as key in state's fileTable
     * @throws ReadLineException cannot readLine with bufferReader
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
        if (!fileTable.isDefined((StringValue) evaluatedExpression)){
            // check if the file name is defined in the file table
            throw new FilenameNotDeclared();
        }

        BufferedReader bufferedReader = fileTable.lookup((StringValue) evaluatedExpression);
        try {
            String valueAsString = bufferedReader.readLine();
            int value;
            if (valueAsString.equals("")){
                value = 0;
            } else {
                value = Integer.parseInt(valueAsString);
            }

            symbolTable.add(variableName,new IntValue(value));
        } catch (IOException e) {
            throw new ReadLineException();
        }

        return null;
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
