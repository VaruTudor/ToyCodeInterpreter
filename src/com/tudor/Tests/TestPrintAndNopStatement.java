package com.tudor.Tests;

import com.tudor.Model.ADTs.*;
import com.tudor.Model.Expressions.ValueExpression;
import com.tudor.Model.ProgramState;
import com.tudor.Model.Statements.PrintStatement;
import com.tudor.Model.Statements.Statement;
import com.tudor.Model.Values.IntValue;
import com.tudor.Model.Values.Value;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestPrintAndNopStatement {

    @Test
    public void shouldUsePrintStatement__OutIsUpdated() {
        Statement printStatement5 =
                new PrintStatement(new ValueExpression(new IntValue(5)));

        IStack<Statement> executionStack = new MyStack<>();
        IDict<String, Value> symbolTable = new MyDict<>();
        IList<Value> out = new MyList<>();

        executionStack.push(printStatement5);

        ProgramState myProgramState = new ProgramState(executionStack, symbolTable, out);

        printStatement5.execute(myProgramState);

        Assertions.assertEquals(new IntValue(5), out.pop());
    }
}
