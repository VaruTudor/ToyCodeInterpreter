package com.tudor.View;

import com.tudor.Controller.Controller;
import com.tudor.Model.ADTs.*;
import com.tudor.Model.Expressions.*;
import com.tudor.Model.ProgramState;
import com.tudor.Model.Statements.*;
import com.tudor.Model.Types.BoolType;
import com.tudor.Model.Types.IntType;
import com.tudor.Model.Types.RefType;
import com.tudor.Model.Types.Type;
import com.tudor.Model.Values.BoolValue;
import com.tudor.Model.Values.IntValue;
import com.tudor.Model.Values.StringValue;
import com.tudor.Model.Values.Value;
import com.tudor.Repository.MultiThreadRepo;
import com.tudor.Repository.Repo;
import com.tudor.View.Commands.ExitCommand;
import com.tudor.View.Commands.RunExampleCommand;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Interpreter extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{

        //setting up the first window
        FXMLLoader mainLoader = new FXMLLoader();
        mainLoader.setLocation(getClass().getResource("mainWindow.fxml"));
        Parent mainWindow = mainLoader.load();

        //creating a MainWindowController to pass it to the other stage
        MainWindowController mainWindowController = mainLoader.getController();
        primaryStage.setScene(new Scene(mainWindow));
        primaryStage.show();

        //setting up the second window
        FXMLLoader secondaryLoader = new FXMLLoader();
        secondaryLoader.setLocation(getClass().getResource("programSelectWindow.fxml"));
        Parent secondaryWindow = secondaryLoader.load();

        ProgramsListViewController selectProgramStateController = secondaryLoader.getController();
        selectProgramStateController.setMainWindowController(mainWindowController);

        Stage secondaryStage = new Stage();
        secondaryStage.setScene(new Scene(secondaryWindow));
        secondaryStage.show();




//        Parent root = FXMLLoader.load(getClass().getResource("programSelectWindow.fxml"));
//        primaryStage.setScene(new Scene(root, 480, 480));
//        primaryStage.show();
//
//        Parent secondRoot = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
//        Stage secondStage = new Stage();
//        secondStage.setScene(new Scene(secondRoot, 650,700));
//        secondStage.show();
    }


    public static void main(String[] args) {
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0","exit"));
        menu.addCommand(new RunExampleCommand("1","int v; v=2; Print(v)",example1()) );
        menu.addCommand(new RunExampleCommand("2","int a; int b; a=2+3*5; b=a+1; Print(b)",example2()) );
        menu.addCommand(new RunExampleCommand("3","bool a; int v; a=true; If(a)Then(v=2)Else(v=3); Print(v)",example3()) );
        menu.addCommand(new RunExampleCommand("4","int v; v=4; while(v>0) print(v);v=v-1",example4()));
        menu.addCommand(new RunExampleCommand("5","Ref int v; new(v,20); Ref Ref int a; new(a,v); new(v,30); printf(rH(rH(a)))",example5()));
        menu.addCommand(new RunExampleCommand("6","int v; Ref int a; v=10; new(a,22); fork(wH(a,30);v=32;printf(v);print(rH(a)) ); printf(v); print(rH(a))",example6()));

//        menu.show();
        launch(args);
    }

        //int v; v=2; Print(v)
        private static Controller example1() {
            Repo repository = new MultiThreadRepo();
            Controller controller = new Controller(repository);
            controller.setMuteLogProgramStateExecution(true);
            IDict<String, Type> typeEnvironment = new MyDict<>();
            Statement program1 =
                    new CompoundStatement(
                            new VariableDeclarationStatement("v", new IntType()),
                            new CompoundStatement(
                                    new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                                    new PrintStatement(new VarExpression("v"))
                            )
                    );

            program1.typecheck(typeEnvironment);

            IStack<Statement> executionStack = new MyStack<>();
            executionStack.push(program1);

            IDict<String, Value> symbolTable = new MyDict<>();
            IList<Value> out = new MyList<>();

            ProgramState myProgramState = new ProgramState(executionStack, symbolTable, out);

            controller.addProgram(myProgramState);

            return controller;
        }

        //int a; int b; a=2+3*5; b=a+1; Print(b)
        private static Controller example2() {
            Repo repository = new MultiThreadRepo();
            Controller controller = new Controller(repository);
            controller.setMuteLogProgramStateExecution(true);
            IDict<String, Type> typeEnvironment = new MyDict<>();
            Statement program1 =
                    new CompoundStatement(
                            new VariableDeclarationStatement("a", new IntType()),
                            new CompoundStatement(
                                    new VariableDeclarationStatement("b",new IntType()),
                                    new CompoundStatement(
                                            new AssignmentStatement("a",
                                                    new ArithExpression(
                                                            new ValueExpression(new IntValue(2)),
                                                            new ArithExpression(
                                                                    new ValueExpression(new IntValue(3)),
                                                                    new ValueExpression(new IntValue(5)),
                                                                    3),
                                                            1)
                                            ),
                                            new CompoundStatement(
                                                    new AssignmentStatement("b",
                                                            new ArithExpression(
                                                                    new VarExpression("a"),
                                                                    new ValueExpression(new IntValue(1)),
                                                                    1
                                                            )
                                                    ),
                                                    new PrintStatement(new VarExpression("b"))
                                            )
                                    )
                            )
                    );

            program1.typecheck(typeEnvironment);

            IStack<Statement> executionStack = new MyStack<>();
            executionStack.push(program1);

            IDict<String, Value> symbolTable = new MyDict<>();
            IList<Value> out = new MyList<>();

            ProgramState myProgramState = new ProgramState(executionStack, symbolTable, out);

            controller.addProgram(myProgramState);
            return controller;
        }

        //bool a; int v; a=true; If(a)Then(v=2)Else(v=3); Print(v)
        private static Controller example3() {
            Repo repository = new MultiThreadRepo();
            Controller controller = new Controller(repository);
            controller.setMuteLogProgramStateExecution(true);
            IDict<String, Type> typeEnvironment = new MyDict<>();
            Statement program1 =
                    new CompoundStatement(
                            new VariableDeclarationStatement("a", new BoolType()),
                            new CompoundStatement(
                                    new VariableDeclarationStatement("v",new IntType()),
                                    new CompoundStatement(
                                            new AssignmentStatement("a",new ValueExpression(new BoolValue(true))),
                                            new CompoundStatement(
                                                    new IfStatement(
                                                            new VarExpression("a"),
                                                            new AssignmentStatement("v",new ValueExpression(new IntValue(2))),
                                                            new AssignmentStatement("v",new ValueExpression(new IntValue(3)))
                                                    ),
                                                    new PrintStatement(new VarExpression("v"))
                                            )
                                    )
                            )
                    );

            program1.typecheck(typeEnvironment);

            IStack<Statement> executionStack = new MyStack<>();
            executionStack.push(program1);

            IDict<String, Value> symbolTable = new MyDict<>();
            IList<Value> out = new MyList<>();

            ProgramState myProgramState = new ProgramState(executionStack, symbolTable, out);

            controller.addProgram(myProgramState);
            return controller;
        }

        //int v; v=4; while(v>0) print(v);v=v-1;
        private static Controller example4() {
            Repo repository = new MultiThreadRepo();
            Controller controller = new Controller(repository);
            controller.setMuteLogProgramStateExecution(true);
            IDict<String, Type> typeEnvironment = new MyDict<>();
            Statement program1 =
                    new CompoundStatement(
                            new VariableDeclarationStatement("v",new IntType()),
                            new CompoundStatement(
                                    new AssignmentStatement("v",new ValueExpression(new IntValue(4))),
                                    new WhileStatement(
                                            new RelationalExpression(
                                                    new VarExpression("v"),
                                                    new ValueExpression(new IntValue(0)),
                                                    new StringValue(">")
                                            ),
                                            new CompoundStatement(
                                                    new PrintStatement(new VarExpression("v")),
                                                    new AssignmentStatement(
                                                            "v",
                                                            new ArithExpression(
                                                                    new VarExpression("v"),
                                                                    new ValueExpression(new IntValue(1)),
                                                                    2
                                                            )
                                                    )
                                            )
                                    )
                            )
                    );

            program1.typecheck(typeEnvironment);

            IStack<Statement> executionStack = new MyStack<>();
            executionStack.push(program1);

            IDict<String, Value> symbolTable = new MyDict<>();
            IList<Value> out = new MyList<>();

            ProgramState myProgramState = new ProgramState(executionStack, symbolTable, out);

            controller.addProgram(myProgramState);
            return controller;
        }

        //Ref int v; new(v,20); Ref Ref int a; new(a,v); new(v,30); printf(rH(rH(a)))
        private static Controller example5() {
            Repo repository = new MultiThreadRepo();
            Controller controller = new Controller(repository);
            controller.setMuteLogProgramStateExecution(true);
            IDict<String, Type> typeEnvironment = new MyDict<>();
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

            program1.typecheck(typeEnvironment);

            IStack<Statement> executionStack = new MyStack<>();
            executionStack.push(program1);

            IDict<String, Value> symbolTable = new MyDict<>();
            IList<Value> out = new MyList<>();

            ProgramState myProgramState = new ProgramState(executionStack, symbolTable, out);

            controller.addProgram(myProgramState);
            return controller;
        }

        //int v; Ref int a; v=10; new(a,22); fork(wH(a,30);v=32;printf(v);print(rH(a)) ); printf(v); print(rH(a))
        private static Controller example6() {
            Repo repository = new MultiThreadRepo();
            Controller controller = new Controller(repository);
            controller.setMuteLogProgramStateExecution(true);
            IDict<String, Type> typeEnvironment = new MyDict<>();
            Statement program = new CompoundStatement(
                    new VariableDeclarationStatement("v",new IntType()),
                    new CompoundStatement(
                            new VariableDeclarationStatement("a",new RefType(new IntType())),
                            new CompoundStatement(
                                    new AssignmentStatement("v",new ValueExpression(new IntValue(10))),
                                    new CompoundStatement(
                                            new AllocateStatement("a",new ValueExpression(new IntValue(10))),
                                            new CompoundStatement(
                                                    new ForkStatement(
                                                            new CompoundStatement(
                                                                    new WriteToHeapStatement("a",new ValueExpression(new IntValue(30))),
                                                                    new CompoundStatement(
                                                                            new AssignmentStatement("v",new ValueExpression(new IntValue(32))),
                                                                            new CompoundStatement(
                                                                                    new PrintStatement(new VarExpression("v")),
                                                                                    new PrintStatement(new ReadHeap(new VarExpression("a")))
                                                                            )
                                                                    )
                                                            )
                                                    ),
                                                    new CompoundStatement(
                                                            new PrintStatement(new VarExpression("v")),
                                                            new PrintStatement(new ReadHeap(new VarExpression("a")))
                                                    )
                                            )
                                    )
                            )
                    )
            );

            program.typecheck(typeEnvironment);

            IStack<Statement> executionStack = new MyStack<>();
            IDict<String, Value> symbolTable = new MyDict<>();
            IList<Value> out = new MyList<>();

            executionStack.push(program);
            ProgramState myProgramState = new ProgramState(executionStack, symbolTable, out);

            controller.addProgram(myProgramState);
            return controller;
        }

}


