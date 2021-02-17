package com.tudor.Model.Statements;

import com.tudor.Exceptions.CloseFileException;
import com.tudor.Exceptions.FilenameNotDeclared;
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
import java.io.IOException;

public class closeRFile implements Statement{
    Expression expression;

    public closeRFile(Expression expression) {
        this.expression = expression;
    }

    /**
     * Closes the file with name returned by expression.evaluate
     * and removes it from the state's fileTable
     * @param state (type ProgramState)
     * @return null
     * @throws ValueNotStringType expression.evaluate is not StringValue(which has StringType)
     * @throws FilenameNotDeclared expression.evaluate not found as key in state's fileTable
     * @throws CloseFileException cannot close bufferReader
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
            bufferedReader.close();
            fileTable.remove((StringValue) evaluatedExpression);
        } catch (IOException e) {
            throw new CloseFileException();
        }



        return null;
    }

    @Override
    public String toStringAsCode() {
        return "should do toStringAsCode for closeRFile";
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnvironment) {
        expression.typecheck(typeEnvironment);
        return typeEnvironment;
    }
}
