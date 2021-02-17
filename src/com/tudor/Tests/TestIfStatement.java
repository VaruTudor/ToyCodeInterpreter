package com.tudor.Tests;

import com.tudor.Exceptions.TypeExceptions.ConditionNotBoolType;
import com.tudor.Model.ADTs.*;
import com.tudor.Model.Expressions.Expression;
import com.tudor.Model.Expressions.LogicExpression;
import com.tudor.Model.Expressions.ValueExpression;
import com.tudor.Model.ProgramState;
import com.tudor.Model.Statements.IfStatement;
import com.tudor.Model.Statements.Statement;
import com.tudor.Model.Statements.VariableDeclarationStatement;
import com.tudor.Model.Types.BoolType;
import com.tudor.Model.Types.IntType;
import com.tudor.Model.Values.BoolValue;
import com.tudor.Model.Values.IntValue;
import com.tudor.Model.Values.Value;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestIfStatement {

    @Test
    public void shouldDoThenOnIfStatement() {

        Statement thenStatement = new VariableDeclarationStatement("then",new IntType());

        Statement ifStatement =
                new IfStatement(
                        new LogicExpression(
                                new ValueExpression(new BoolValue(true)),
                                new ValueExpression(new BoolValue(true)),
                                1),
                        thenStatement,
                        new VariableDeclarationStatement("else", new BoolType()) ) ;

        IStack<Statement> executionStack = new MyStack<>();
        IDict<String, Value> symbolTable = new MyDict<>();
        IList<Value> out = new MyList<>();

        executionStack.push(ifStatement);

        ProgramState myProgramState = new ProgramState(executionStack, symbolTable, out);

        ifStatement.execute(myProgramState);

        Assertions.assertEquals(thenStatement,executionStack.pop());
    }

    @Test
    public void shouldDoElseOnIfStatement() {

        Statement thenStatement = new VariableDeclarationStatement("then",new IntType());
        Statement elseStatement = new VariableDeclarationStatement("else",new IntType());

        Statement ifStatement =
                new IfStatement(
                        new LogicExpression(
                                new ValueExpression(new BoolValue(true)),
                                new ValueExpression(new BoolValue(false)),
                                1),
                        thenStatement,
                        elseStatement ) ;

        IStack<Statement> executionStack = new MyStack<>();
        IDict<String, Value> symbolTable = new MyDict<>();
        IList<Value> out = new MyList<>();

        executionStack.push(ifStatement);

        ProgramState myProgramState = new ProgramState(executionStack, symbolTable, out);

        ifStatement.execute(myProgramState);

        Assertions.assertEquals(elseStatement,executionStack.pop());
    }

    @Test
    public void shouldFailIfStatement__ConditionNotBoolean() {

        Statement thenStatement = new VariableDeclarationStatement("then",new IntType());
        Statement elseStatement = new VariableDeclarationStatement("else",new IntType());
        Expression notBoolean = new ValueExpression(new IntValue(5));

        Statement ifStatement =
                new IfStatement(
                        notBoolean,
                        thenStatement,
                        elseStatement ) ;

        IStack<Statement> executionStack = new MyStack<>();
        IDict<String, Value> symbolTable = new MyDict<>();
        IList<Value> out = new MyList<>();

        executionStack.push(ifStatement);

        ProgramState myProgramState = new ProgramState(executionStack, symbolTable, out);

        Assertions.assertThrows(ConditionNotBoolType.class, () -> ifStatement.execute(myProgramState), "ConditionNotBoolean not thrown");
    }
}
