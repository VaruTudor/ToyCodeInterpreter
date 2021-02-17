package com.tudor.Tests;

import com.tudor.Exceptions.VariableAlreadyDeclared;
import com.tudor.Exceptions.TypeExceptions.TypeMismatch;
import com.tudor.Exceptions.VariableNotDeclared;
import com.tudor.Model.ADTs.*;
import com.tudor.Model.Expressions.ValueExpression;
import com.tudor.Model.ProgramState;
import com.tudor.Model.Statements.AssignmentStatement;
import com.tudor.Model.Statements.Statement;
import com.tudor.Model.Statements.VariableDeclarationStatement;
import com.tudor.Model.Types.BoolType;
import com.tudor.Model.Types.IntType;
import com.tudor.Model.Values.IntValue;
import com.tudor.Model.Values.Value;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestDeclarationAndAssignmentStatement {

    @Test
    public void shouldDeclareVariable() {

        Statement originalProgram =
                new VariableDeclarationStatement("a", new IntType());

        IStack<Statement> executionStack = new MyStack<>();
        IDict<String, Value> symbolTable = new MyDict<>();
        IList<Value> out = new MyList<>();

        executionStack.push(originalProgram);

        ProgramState myProgramState = new ProgramState(executionStack, symbolTable, out);

        originalProgram.execute(myProgramState);

        Assertions.assertTrue(myProgramState.getSymTable().isDefined("a"));
    }

    @Test
    public void shouldFailDeclaration__AlreadyDeclared() {
        Statement statementThatShouldPassWhenExecuted =
                new VariableDeclarationStatement("a", new IntType());

        Statement statementThatShouldFailWhenExecuted =
                new VariableDeclarationStatement("a", new IntType());

        IStack<Statement> executionStack = new MyStack<>();
        IDict<String, Value> symbolTable = new MyDict<>();
        IList<Value> out = new MyList<>();

        executionStack.push(statementThatShouldPassWhenExecuted);
        executionStack.push(statementThatShouldFailWhenExecuted);

        ProgramState myProgramState = new ProgramState(executionStack, symbolTable, out);

        statementThatShouldPassWhenExecuted.execute(myProgramState);

        // lambda is used
        Assertions.assertThrows(VariableAlreadyDeclared.class, () -> statementThatShouldFailWhenExecuted.execute(myProgramState),"AlreadyDeclared not thrown");
    }

    @Test
    public void shouldAssignValueToAlreadyDeclared() {
        Statement statementOfDeclaration =
                new VariableDeclarationStatement("a", new IntType());

        Statement statementOfAssertion =
                new AssignmentStatement("a",new ValueExpression(new IntValue(5)));

        IStack<Statement> executionStack = new MyStack<>();
        IDict<String, Value> symbolTable = new MyDict<>();
        IList<Value> out = new MyList<>();

        executionStack.push(statementOfDeclaration);
        executionStack.push(statementOfAssertion);

        ProgramState myProgramState = new ProgramState(executionStack, symbolTable, out);

        statementOfDeclaration.execute(myProgramState);
        statementOfAssertion.execute(myProgramState);

        Assertions.assertEquals(myProgramState.getSymTable().lookup("a"),new IntValue(5));
    }

    @Test
    public void shouldFailAssignment__ValueNonDeclared() {

        Statement statementOfAssertion =
                new AssignmentStatement("a",new ValueExpression(new IntValue(5)));

        IStack<Statement> executionStack = new MyStack<>();
        IDict<String, Value> symbolTable = new MyDict<>();
        IList<Value> out = new MyList<>();

        executionStack.push(statementOfAssertion);

        ProgramState myProgramState = new ProgramState(executionStack, symbolTable, out);

        // lambda is used
        Assertions.assertThrows(VariableNotDeclared.class, () -> statementOfAssertion.execute(myProgramState),"ValueNonDeclared not thrown");
    }

    @Test
    public void shouldFailAssignment__TypeMismatch() {
        Statement statementOfDeclaration =
                new VariableDeclarationStatement("a", new BoolType());

        Statement statementOfAssertion =
                new AssignmentStatement("a",new ValueExpression(new IntValue(5)));

        IStack<Statement> executionStack = new MyStack<>();
        IDict<String, Value> symbolTable = new MyDict<>();
        IList<Value> out = new MyList<>();

        executionStack.push(statementOfDeclaration);
        executionStack.push(statementOfAssertion);

        ProgramState myProgramState = new ProgramState(executionStack, symbolTable, out);

        statementOfDeclaration.execute(myProgramState);

        // lambda is used
        Assertions.assertThrows(TypeMismatch.class, () -> statementOfAssertion.execute(myProgramState),"TypeMismatch not thrown");
    }
}
