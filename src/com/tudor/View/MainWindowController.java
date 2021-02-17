package com.tudor.View;

import com.tudor.Controller.Controller;
import com.tudor.Model.ADTs.IStack;
import com.tudor.Model.ProgramState;
import com.tudor.Model.Statements.Statement;
import com.tudor.Model.Values.StringValue;
import com.tudor.Model.Values.Value;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class MainWindowController implements Initializable {

    @FXML
    private TextField numberOfProgramStatesTextField;

    // address (int/Integer) and Value
    @FXML
    private TableView<Map.Entry<Integer, Value>> heapTableView;

    @FXML
    private TableColumn<Map.Entry<Integer, Value>,Integer> addressColumnHeapTableView;

    @FXML
    private TableColumn<Map.Entry<Integer, Value>,String> valueColumnHeapTableView;

    @FXML
    private ListView<Value> outputListView;

    @FXML
    private ListView<String> fileListView;

    @FXML
    private ListView<Integer> idsOfProgramStatesListView;

    // variable name (String) and Value
    @FXML
    private TableView<Map.Entry<String, Value>> selectedProgramStateSymbolTableView;

    @FXML
    private TableColumn<Map.Entry<String, Value>,String> variableColumnSelectedProgramStateSymbolTableView;

    @FXML
    private TableColumn<Map.Entry<String, Value>,String> valueColumnSelectedProgramStateSymbolTableView;

    @FXML
    private ListView<String> SelectedProgramStateExecutionStackListView;

    @FXML
    private Button runOneStepButton;

    private Controller currentMainWindowController;

    public void setController(Controller controller){
        this.currentMainWindowController = controller;
        populateNumberOfProgramStatesTextField();
        populateIdsOfProgramStatesListView();
        runOneStepButton.setDisable(false);
    }

    private void populateNumberOfProgramStatesTextField(){
        int numberOfProgramStates = currentMainWindowController.getNumberOfProgramStates();
        numberOfProgramStatesTextField.setText("There are " + numberOfProgramStates + " ProgramStates");
    }

    private void populateIdsOfProgramStatesListView(){
        idsOfProgramStatesListView.setItems(FXCollections.observableArrayList(currentMainWindowController.getIdsOfProgramStatesAsList()));
    }

    private void updateInterface(ProgramState currentProgramState){
        if(currentProgramState == null){
            return;
        }
        try{
            populateNumberOfProgramStatesTextField();
            populateIdsOfProgramStatesListView();
            populateHeapTableView(currentProgramState);
            populateOutputListView(currentProgramState);
            populateFileListView(currentProgramState);
            populateSelectedProgramStateSymbolTableView(currentProgramState);
            SelectedProgramStateExecutionStackListView(currentProgramState);
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void SelectedProgramStateExecutionStackListView(ProgramState currentProgramState) {
        IStack<Statement> currentProgramStateStack = currentProgramState.getStack();
        List<String> listOfStatementsToString = new ArrayList<>();
        for(Statement statement : currentProgramStateStack.getStack()){
            listOfStatementsToString.add(statement.toStringAsCode());
        }

        Collections.reverse(listOfStatementsToString);
        SelectedProgramStateExecutionStackListView.setItems(FXCollections.observableArrayList(listOfStatementsToString));
    }

    private void populateSelectedProgramStateSymbolTableView(ProgramState currentProgramState) {
        selectedProgramStateSymbolTableView.setItems(FXCollections.observableArrayList(new ArrayList<>(currentProgramState.getSymTable().getContent().entrySet())));
        selectedProgramStateSymbolTableView.refresh();
    }

    private void populateFileListView(ProgramState currentProgramState) {
        fileListView.setItems(FXCollections.observableArrayList(currentProgramState.getFileTable().getHashMap().keySet()
                .stream()
                .map(
                        StringValue::getValue
                )
                .collect(Collectors.toList())
        ));
    }

    private void populateOutputListView(ProgramState currentProgramState) {
        outputListView.setItems(FXCollections.observableArrayList(currentProgramState.getList().getContent()));
    }

    private void populateHeapTableView(ProgramState currentProgramState) {
        heapTableView.setItems(FXCollections.observableArrayList(new ArrayList<>(currentProgramState.getHeap().getContent().entrySet())));
        heapTableView.refresh();
    }

    public void runOneStepButtonHandler(ActionEvent actionEvent){
        if(currentMainWindowController == null){
            new Alert(Alert.AlertType.ERROR, "No statement selected").show();
            runOneStepButton.setDisable(true);
            return;
        }

        ProgramState selectedProgramState = getSelectedProgramState();
        if(selectedProgramState != null && !selectedProgramState.isNotCompleted()){
            new Alert(Alert.AlertType.ERROR, "No steps left").show();
            return;
        }

        try{
            currentMainWindowController.oneStep();
            updateInterface(selectedProgramState);
            if(currentMainWindowController.getNumberOfProgramStates() == 0)
            {
                new Alert(Alert.AlertType.INFORMATION,"Program finished !").show();
                runOneStepButton.setDisable(true);
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    private ProgramState getSelectedProgramState(){
        if(idsOfProgramStatesListView.getSelectionModel().getSelectedIndex() == -1) {
            return null;
        }
        else {
            int selectedItemOfProgramState = idsOfProgramStatesListView.getSelectionModel().getSelectedItem();
            return currentMainWindowController.getProgramStateHavingId(selectedItemOfProgramState);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.currentMainWindowController = null;

        //setting up the heap table
        addressColumnHeapTableView.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        valueColumnHeapTableView.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue() + " "));

        //setting up the symbol table
        variableColumnSelectedProgramStateSymbolTableView.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey() + " "));
        valueColumnSelectedProgramStateSymbolTableView.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue() + " "));


        idsOfProgramStatesListView.setOnMouseClicked(mouseEvent -> updateInterface(getSelectedProgramState()));

        runOneStepButton.setDisable(true);
    }
}
