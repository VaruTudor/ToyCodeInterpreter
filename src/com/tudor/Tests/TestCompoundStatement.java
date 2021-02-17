package com.tudor.Tests;

import com.tudor.Model.ADTs.*;
import com.tudor.Model.ProgramState;
import com.tudor.Model.Statements.CompoundStatement;
import com.tudor.Model.Statements.Statement;
import com.tudor.Model.Statements.VariableDeclarationStatement;
import com.tudor.Model.Types.IntType;
import com.tudor.Model.Values.Value;
import org.junit.Assert;
import org.junit.Test;

public class TestCompoundStatement {

    @Test
    public void shouldDoCompoundStatement__StatementsArePushedOntoStack() {
        Statement statement1 = new VariableDeclarationStatement("1",new IntType());
        Statement statement2 = new VariableDeclarationStatement("2",new IntType());

        Statement compoundStatement =
                new CompoundStatement(
                        statement1,
                        statement2 ) ;

        IStack<Statement> executionStack = new MyStack<>();
        IDict<String, Value> symbolTable = new MyDict<>();
        IList<Value> out = new MyList<>();

        executionStack.push(compoundStatement);
        ProgramState myProgramState = new ProgramState(executionStack, symbolTable, out);

        compoundStatement.execute(myProgramState);

        Assert.assertEquals(statement1,executionStack.pop());
        Assert.assertEquals(statement2,executionStack.pop());
    }

}
