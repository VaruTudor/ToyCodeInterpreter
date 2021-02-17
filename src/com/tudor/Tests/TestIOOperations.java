package com.tudor.Tests;

import com.tudor.Model.ADTs.*;
import com.tudor.Model.Expressions.Expression;
import com.tudor.Model.Expressions.ValueExpression;
import com.tudor.Model.ProgramState;
import com.tudor.Model.Statements.*;
import com.tudor.Model.Values.IntValue;
import com.tudor.Model.Values.StringValue;
import com.tudor.Model.Values.Value;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;

public class TestIOOperations {
    @Test
    public void shouldUseTest1__VariablesWillBeDeclaredInSymbolTable(){
        IStack<Statement> stackOfStatements = new MyStack<>();
        IDict<String, Value>symbolTable = new MyDict<>();
        IList<Value> outputList = new MyList<>();
        IFileTable<StringValue, BufferedReader>fileTable = new MyFileTable<>();


        String fileName = "C:\\Users\\Tudor\\Desktop\\D\\faculta\\SemIII\\MAP\\Labs\\week 8(16 -20 November)\\Lab8_A5\\test1.in";
        Expression stringAsExpression = new ValueExpression(new StringValue(fileName));

        Statement openFile = new openRFile(stringAsExpression);
        Statement setValue1 = new readFile(stringAsExpression,"variable");
        Statement setValue2 = new readFile(stringAsExpression,"variable");
        Statement closeFile = new closeRFile(stringAsExpression);
        ProgramState myProgramState = new ProgramState(stackOfStatements,symbolTable,outputList,fileTable);

        stackOfStatements.push(openFile);
        stackOfStatements.push(setValue1);
        stackOfStatements.push(setValue2);

        openFile.execute(myProgramState);
        setValue1.execute(myProgramState);
        Assertions.assertEquals(myProgramState.getSymTable().lookup("variable"),new IntValue(15));
        setValue2.execute(myProgramState);
        Assertions.assertEquals(myProgramState.getSymTable().lookup("variable"),new IntValue(50));
        closeFile.execute(myProgramState);

    }
}
