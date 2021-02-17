package com.tudor.Tests;

import com.tudor.Model.ADTs.*;
import com.tudor.Model.ProgramState;
import com.tudor.Model.Statements.ForkStatement;
import com.tudor.Model.Statements.Statement;
import com.tudor.Model.Statements.VariableDeclarationStatement;
import com.tudor.Model.Types.IntType;
import com.tudor.Model.Values.Value;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestForkStatement {

    @Test
    public void shouldExecuteForkStatement__ProgramStateReturnedByForkStatementHasInTheStackTheStatementFromForkStatement() {

        Statement declareA = new VariableDeclarationStatement("a",new IntType());
        Statement declareC = new VariableDeclarationStatement("c",new IntType());
        Statement forkC = new ForkStatement(declareC);

        IStack<Statement> executionStack = new MyStack<>();
        IDict<String, Value> symbolTable = new MyDict<>();
        IList<Value> out = new MyList<>();
        ProgramState myProgramState = new ProgramState(executionStack, symbolTable, out);

        declareA.execute(myProgramState);
        ProgramState newProgramState = forkC.execute(myProgramState);

        Assertions.assertEquals(newProgramState.getStack().pop(),declareC);
    }

    @Test
    public void shouldExecuteForkStatement__ProgramStateReturnedByForkStatementHasACopyOfTheSymbolTable() {

        Statement declareA = new VariableDeclarationStatement("a",new IntType());
        Statement declareC = new VariableDeclarationStatement("c",new IntType());
        Statement forkC = new ForkStatement(declareC);

        IStack<Statement> executionStack = new MyStack<>();
        IDict<String, Value> symbolTable = new MyDict<>();
        IList<Value> out = new MyList<>();
        ProgramState myProgramState = new ProgramState(executionStack, symbolTable, out);

        declareA.execute(myProgramState);
        ProgramState newProgramState = forkC.execute(myProgramState);

        Assertions.assertNotEquals(newProgramState.getSymTable(),symbolTable);
        Assertions.assertTrue(newProgramState.getSymTable().isDefined("a"));
    }

}
