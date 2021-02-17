package com.tudor.Tests;

import com.tudor.Controller.Controller;
import com.tudor.Model.ADTs.*;
import com.tudor.Model.Expressions.ArithExpression;
import com.tudor.Model.Expressions.ReadHeap;
import com.tudor.Model.Expressions.ValueExpression;
import com.tudor.Model.Expressions.VarExpression;
import com.tudor.Model.ProgramState;
import com.tudor.Model.Statements.*;
import com.tudor.Model.Types.IntType;
import com.tudor.Model.Types.RefType;
import com.tudor.Model.Values.BoolValue;
import com.tudor.Model.Values.IntValue;
import com.tudor.Model.Values.Value;
import com.tudor.Repository.MultiThreadRepo;
import com.tudor.Repository.Repo;
import org.junit.Assert;
import org.junit.Test;

public class TestController {

    @Test
    public void shouldUpdateOutTable() {
        Repo repository = new MultiThreadRepo();
        Controller controller = new Controller(repository);
        controller.setMuteLogProgramStateExecution(true);
        controller.setMutePrintProgramStateExecution(true);
        Statement originalProgram =
                new PrintStatement(new ValueExpression(new IntValue(50)));

        IStack<Statement> executionStack = new MyStack<>();
        IDict<String, Value> symbolTable = new MyDict<>();
        IList<Value> out = new MyList<>();

        executionStack.push(originalProgram);

        ProgramState myProgramState = new ProgramState(executionStack, symbolTable, out);

        controller.addProgram(myProgramState);
        controller.allStep();

        Assert.assertEquals(out.pop(),new IntValue(50));
    }

    @Test
    public void shouldUpdateSymbolTable() {
        Repo repository = new MultiThreadRepo();
        Controller controller = new Controller(repository);
        controller.setMuteLogProgramStateExecution(true);
        controller.setMutePrintProgramStateExecution(true);
        Statement originalProgram =
            new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new IfStatement(
                    new ValueExpression(new BoolValue(true)),
                    new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntValue(5))),
                        new PrintStatement(
                                new ArithExpression(
                                    new VarExpression("v"),
                                    new ValueExpression(new IntValue(3)),
                                    1
                        ) ) ) ,
                    new PrintStatement(new ValueExpression(new IntValue(100) ) ) )
            );

        IStack<Statement> executionStack = new MyStack<>();
        executionStack.push(originalProgram);

        IDict<String, Value> symbolTable = new MyDict<>();
        IList<Value> out = new MyList<>();

        ProgramState myProgramState = new ProgramState(executionStack, symbolTable, out);

        controller.addProgram(myProgramState);

        controller.allStep();

        Assert.assertEquals(symbolTable.lookup("v"), new IntValue(5));

    }


    @Test
    public void shouldUpdateStack() {
        Repo repository = new MultiThreadRepo();
        Controller controller = new Controller(repository);
        controller.setMuteLogProgramStateExecution(true);
        controller.setMutePrintProgramStateExecution(true);
        Statement originalProgram =
                new CompoundStatement(
                        new VariableDeclarationStatement("v", new IntType()),
                        new IfStatement(
                                new ValueExpression(new BoolValue(true)),
                                new CompoundStatement(
                                        new AssignmentStatement("v", new ValueExpression(new IntValue(5))),
                                        new PrintStatement(
                                                new ArithExpression(
                                                        new VarExpression("v"),
                                                        new ValueExpression(new IntValue(3)),
                                                        1
                                                ) ) ) ,
                                new PrintStatement(new ValueExpression(new IntValue(100) ) ) )
                );

        IStack<Statement> executionStack = new MyStack<>();
        executionStack.push(originalProgram);

        IDict<String, Value> symbolTable = new MyDict<>();
        IList<Value> out = new MyList<>();

        ProgramState myProgramState = new ProgramState(executionStack, symbolTable, out);

        controller.addProgram(myProgramState);

        controller.allStep();

        Assert.assertTrue(executionStack.isEmpty());

    }

    @Test
    public void shouldUpdateHeap() {
        Repo repository = new MultiThreadRepo();
        Controller controller = new Controller(repository);
        controller.setMuteLogProgramStateExecution(true);
        controller.setMutePrintProgramStateExecution(true);
        Statement program1 =
                new CompoundStatement(
                        new VariableDeclarationStatement("v", new RefType(new IntType())),
                        new CompoundStatement(
                                new AllocateStatement("v",new ValueExpression(new IntValue(20))),
                                new CompoundStatement(
                                        new VariableDeclarationStatement("a",new RefType(new RefType(new IntType()))),
                                        new CompoundStatement(
                                                new AllocateStatement("a",new VarExpression("v")),
                                                new CompoundStatement(
                                                        new AllocateStatement("v",new ValueExpression(new IntValue(30))),
                                                        new PrintStatement(new ReadHeap(new ReadHeap(new VarExpression("a"))))
                                                )

                                        )
                                )
                        )
                );

        IStack<Statement> executionStack = new MyStack<>();
        executionStack.push(program1);

        IDict<String, Value> symbolTable = new MyDict<>();
        IList<Value> out = new MyList<>();

        ProgramState myProgramState = new ProgramState(executionStack, symbolTable, out);

        controller.addProgram(myProgramState);
        controller.allStep();
    }
}
