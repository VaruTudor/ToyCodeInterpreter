package com.tudor.Tests;

import com.tudor.Exceptions.TypeExceptions.ConditionNotBoolType;
import com.tudor.Model.ADTs.*;
import com.tudor.Model.Expressions.RelationalExpression;
import com.tudor.Model.Expressions.ValueExpression;
import com.tudor.Model.ProgramState;
import com.tudor.Model.Statements.Statement;
import com.tudor.Model.Statements.VariableDeclarationStatement;
import com.tudor.Model.Statements.WhileStatement;
import com.tudor.Model.Types.IntType;
import com.tudor.Model.Values.IntValue;
import com.tudor.Model.Values.StringValue;
import com.tudor.Model.Values.Value;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestWhileStatement {

    @Test
    public void shouldExecuteWhileStatement__EnteringWhile(){
        IStack<Statement> executionStack = new MyStack<>();
        IDict<String, Value> symbolTable = new MyDict<>();
        IList<Value> out = new MyList<>();
        ProgramState myProgramState = new ProgramState(executionStack, symbolTable, out);

        Statement declareA = new VariableDeclarationStatement("A",new IntType());
        Statement whileStatement =
                new WhileStatement(
                        new RelationalExpression(
                                new ValueExpression(new IntValue(3)),
                                new ValueExpression(new IntValue(0)),
                                new StringValue(">")
                        ),
                        declareA
                );


        executionStack.push(whileStatement);
        whileStatement.execute(myProgramState);

        Assertions.assertEquals(declareA,executionStack.pop());
        Assertions.assertTrue(executionStack.pop() instanceof WhileStatement);
    }

    @Test
    public void shouldExecuteWhileStatement__NotEnteringWhile(){
        IStack<Statement> executionStack = new MyStack<>();
        IDict<String, Value> symbolTable = new MyDict<>();
        IList<Value> out = new MyList<>();
        ProgramState myProgramState = new ProgramState(executionStack, symbolTable, out);

        Statement declareA = new VariableDeclarationStatement("A",new IntType());
        Statement whileStatement =
                new WhileStatement(
                        new RelationalExpression(
                                new ValueExpression(new IntValue(3)),
                                new ValueExpression(new IntValue(5)),
                                new StringValue(">")
                        ),
                        declareA
                );


        executionStack.push(whileStatement);
        whileStatement.execute(myProgramState);

        Assertions.assertEquals(executionStack.pop(),whileStatement);
    }

    @Test
    public void shouldFailExecutingWhileStatement__NotBoolType(){
        IStack<Statement> executionStack = new MyStack<>();
        IDict<String, Value> symbolTable = new MyDict<>();
        IList<Value> out = new MyList<>();
        ProgramState myProgramState = new ProgramState(executionStack, symbolTable, out);

        Statement declareA = new VariableDeclarationStatement("A",new IntType());
        Statement whileStatement =
                new WhileStatement(
                        new ValueExpression(new IntValue(3)),
                        declareA
                );


        executionStack.push(whileStatement);

        Assertions.assertThrows(ConditionNotBoolType.class, () -> whileStatement.execute(myProgramState), "ConditionNotBoolean not thrown");
    }

}
