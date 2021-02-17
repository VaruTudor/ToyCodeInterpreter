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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ProgramsListViewController implements Initializable {
    @FXML
    private ListView<Statement> statementListView;

    private MainWindowController mainWindowController;

    public void setMainWindowController(MainWindowController mainWindowController){this.mainWindowController = mainWindowController;}

    @FXML
    public void initialize(){
        statementListView.setItems(getPrograms());

        //here we perform an action when selecting a new Statement from the ListView statementListView
        statementListView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldSelectionStatement, newSelectionStatement) -> {
            System.out.println(newSelectionStatement);

            //now that we have a selection we can create a Controller
            Repo repository = new MultiThreadRepo();
            repository.setLogFilePath("C:\\Users\\Tudor\\Desktop\\D\\faculta\\SemIII\\MAP\\Labs\\week 10 (30November-4December)\\l10\\output.txt");
            Controller controller = new Controller(repository);
            controller.setMuteLogProgramStateExecution(true);
            IDict<String, Type> typeEnvironment = new MyDict<>();

            newSelectionStatement.typecheck(typeEnvironment);

            IStack<Statement> executionStack = new MyStack<>();
            IDict<String, Value> symbolTable = new MyDict<>();
            IList<Value> out = new MyList<>();

            executionStack.push(newSelectionStatement);
            ProgramState myProgramState = new ProgramState(executionStack, symbolTable, out);

            controller.addProgram(myProgramState);
            //lastly we add the controller to the main window
            mainWindowController.setController(controller);

        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialize();
    }




    private ObservableList<Statement> getPrograms(){
        Statement program1 =
            new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VarExpression("v"))
                        )
                );

        Statement program2 =
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

        Statement program3 =
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

        Statement program4 =
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

        Statement program5 =
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

        Statement program6 = new CompoundStatement(
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


        return FXCollections.observableArrayList(
                Arrays.asList(
                        program1,
                        program2,
                        program3,
                        program4,
                        program5,
                        program6
                )
        );
    }


}
