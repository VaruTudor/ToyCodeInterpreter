package com.tudor.Tests;

import com.tudor.Exceptions.DivisionByZero;
import com.tudor.Exceptions.OperationNonExistent;
import com.tudor.Exceptions.TypeExceptions.TypeMismatch;
import com.tudor.Exceptions.VariableNotDeclared;
import com.tudor.Model.ADTs.*;
import com.tudor.Model.Expressions.*;
import com.tudor.Model.ProgramState;
import com.tudor.Model.Statements.AllocateStatement;
import com.tudor.Model.Statements.Statement;
import com.tudor.Model.Statements.VariableDeclarationStatement;
import com.tudor.Model.Types.IntType;
import com.tudor.Model.Types.RefType;
import com.tudor.Model.Values.BoolValue;
import com.tudor.Model.Values.IntValue;
import com.tudor.Model.Values.StringValue;
import com.tudor.Model.Values.Value;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;

public class TestExpressionsEvaluate {

    @Test
    public void shouldEvaluateValueExpression() {
        IDict<String, Value> notImportantDict = new MyDict<>();
        IHeap<Integer, Value> notImportantHeap = new MyHeap<>();

        Expression expression = new ValueExpression(new IntValue(7));

        Assertions.assertEquals(expression.evaluate(notImportantDict,notImportantHeap), new IntValue(7));
    }

    @Test
    public void shouldEvaluateVarExpression() {
        IDict<String, Value> symbolTable = new MyDict<>();
        IHeap<Integer, Value> notImportantHeap = new MyHeap<>();
        symbolTable.add("a",new IntValue(7));

        Expression expression = new VarExpression("a");

        Assertions.assertEquals(expression.evaluate(symbolTable,notImportantHeap), new IntValue(7));
    }

    @Test
    public void shouldEvaluateAdditionArithExpression() {
        IDict<String, Value> notImportantDict = new MyDict<>();
        IHeap<Integer, Value> notImportantHeap = new MyHeap<>();

        Expression value1 = new ValueExpression(new IntValue(12));
        Expression value2 = new ValueExpression(new IntValue(6));

        // +
        Expression expressionAddition = new ArithExpression(value1,value2,1);

        Assertions.assertEquals(expressionAddition.evaluate(notImportantDict,notImportantHeap), new IntValue(18));

    }

    @Test
    public void shouldEvaluateSubtractionArithExpression() {
        IDict<String, Value> notImportantDict = new MyDict<>();
        IHeap<Integer, Value> notImportantHeap = new MyHeap<>();

        Expression value1 = new ValueExpression(new IntValue(12));
        Expression value2 = new ValueExpression(new IntValue(6));

        // -
        Expression expressionSubtraction = new ArithExpression(value1,value2,2);

        Assertions.assertEquals(expressionSubtraction.evaluate(notImportantDict,notImportantHeap), new IntValue(6));
    }

    @Test
    public void shouldEvaluateMultiplicationArithExpression() {
        IDict<String, Value> notImportantDict = new MyDict<>();
        IHeap<Integer, Value> notImportantHeap = new MyHeap<>();

        Expression value1 = new ValueExpression(new IntValue(12));
        Expression value2 = new ValueExpression(new IntValue(6));

        // *
        Expression expressionMultiplication = new ArithExpression(value1,value2,3);

        Assertions.assertEquals(expressionMultiplication.evaluate(notImportantDict,notImportantHeap), new IntValue(72));
    }

    @Test
    public void shouldEvaluateDivisionArithExpression() {
        IDict<String, Value> notImportantDict = new MyDict<>();
        IHeap<Integer, Value> notImportantHeap = new MyHeap<>();

        Expression value1 = new ValueExpression(new IntValue(12));
        Expression value2 = new ValueExpression(new IntValue(6));

        // /
        Expression expressionDivision = new ArithExpression(value1,value2,4);

        Assertions.assertEquals(expressionDivision.evaluate(notImportantDict,notImportantHeap), new IntValue(2));
    }

    @Test
    public void shouldFailEvaluatingDivisionArithExpression__DivisionByZero() {
        IDict<String, Value> notImportantDict = new MyDict<>();
        IHeap<Integer, Value> notImportantHeap = new MyHeap<>();

        Expression value1 = new ValueExpression(new IntValue(12));
        Expression value2 = new ValueExpression(new IntValue(0));

        // /
        Expression expressionDivision = new ArithExpression(value1,value2,4);

        Assertions.assertThrows(DivisionByZero.class, () -> expressionDivision.evaluate(notImportantDict,notImportantHeap), "DivisionByZero not thrown");
    }

    @Test
    public void shouldFailEvaluatingDivisionArithExpression__TypeMismatch() {
        IDict<String, Value> notImportantDict = new MyDict<>();
        IHeap<Integer, Value> notImportantHeap = new MyHeap<>();

        Expression value1 = new ValueExpression(new IntValue(12));
        Expression value2 = new ValueExpression(new BoolValue(false));

        Expression expressionDivision = new ArithExpression(value1,value2,4);

        Assertions.assertThrows(TypeMismatch.class, () -> expressionDivision.evaluate(notImportantDict,notImportantHeap), "TypeMismatch not thrown");
    }

    @Test
    public void shouldFailEvaluatingDivisionArithExpression__OperationNonExistent() {
        IDict<String, Value> notImportantDict = new MyDict<>();
        IHeap<Integer, Value> notImportantHeap = new MyHeap<>();

        Expression value1 = new ValueExpression(new IntValue(12));
        Expression value2 = new ValueExpression(new IntValue(5));

        Expression expressionDivision = new ArithExpression(value1,value2,7);

        Assertions.assertThrows(OperationNonExistent.class, () -> expressionDivision.evaluate(notImportantDict,notImportantHeap), "OperationNonExistent not thrown");
    }

    @Test
    public void shouldEvaluateORLogicExpression() {
        IDict<String, Value> notImportantDict = new MyDict<>();
        IHeap<Integer, Value> notImportantHeap = new MyHeap<>();

        Expression value1 = new ValueExpression(new BoolValue(true));
        Expression value2 = new ValueExpression(new BoolValue(false));

        Expression expression = new LogicExpression(value1,value2,2);

        Assertions.assertEquals(expression.evaluate(notImportantDict,notImportantHeap), new BoolValue(true));
    }

    @Test
    public void shouldEvaluateANDLogicExpression() {
        IDict<String, Value> notImportantDict = new MyDict<>();
        IHeap<Integer, Value> notImportantHeap = new MyHeap<>();

        Expression value1 = new ValueExpression(new BoolValue(true));
        Expression value2 = new ValueExpression(new BoolValue(false));

        Expression expression = new LogicExpression(value1,value2,1);

        Assertions.assertEquals(expression.evaluate(notImportantDict,notImportantHeap), new BoolValue(false));
    }

    @Test
    public void shouldFailEvaluatingLogicExpression__TypeMismatch() {
        IDict<String, Value> notImportantDict = new MyDict<>();
        IHeap<Integer, Value> notImportantHeap = new MyHeap<>();

        Expression value1 = new ValueExpression(new IntValue(12));
        Expression value2 = new ValueExpression(new BoolValue(false));

        Expression expression = new LogicExpression(value1,value2,1);

        Assertions.assertThrows(TypeMismatch.class, () -> expression.evaluate(notImportantDict,notImportantHeap), "TypeMismatch not thrown");
    }

    @Test
    public void shouldFailEvaluatingLogicExpression__OperationNonExistent() {
        IDict<String, Value> notImportantDict = new MyDict<>();
        IHeap<Integer, Value> notImportantHeap = new MyHeap<>();

        Expression value1 = new ValueExpression(new BoolValue(true));
        Expression value2 = new ValueExpression(new BoolValue(false));

        Expression expression = new LogicExpression(value1,value2,5);

        Assertions.assertThrows(OperationNonExistent.class, () -> expression.evaluate(notImportantDict,notImportantHeap), "OperationNonExistent not thrown");
    }

    @Test
    public void shouldEvaluateReadHeap(){
        IStack<Statement> executionStack = new MyStack<>();
        IDict<String, Value> symbolTable = new MyDict<>();
        IList<Value> out = new MyList<>();
        IFileTable<StringValue, BufferedReader>fileTable = new MyFileTable<>();
        IHeap<Integer,Value> heap = new MyHeap<>();
        ProgramState myProgramState = new ProgramState(executionStack, symbolTable, out, fileTable, heap);

        Statement declareA = new VariableDeclarationStatement("A",new RefType(new IntType()));
        Statement allocateA = new AllocateStatement("A",new ValueExpression(new IntValue(15)));

        executionStack.push(declareA);
        declareA.execute(myProgramState);
        allocateA.execute(myProgramState);

        Expression findContentOfAddressLocation1 = new ReadHeap(new VarExpression("A"));

        Value result = findContentOfAddressLocation1.evaluate(symbolTable,heap);

        Assertions.assertEquals(result,new IntValue(15));
    }


    @Test
    public void shouldFailEvaluatingReadHeap__TypeMismatch(){
        IStack<Statement> executionStack = new MyStack<>();
        IDict<String, Value> symbolTable = new MyDict<>();
        IList<Value> out = new MyList<>();
        IFileTable<StringValue, BufferedReader>fileTable = new MyFileTable<>();
        IHeap<Integer,Value> heap = new MyHeap<>();
        ProgramState myProgramState = new ProgramState(executionStack, symbolTable, out, fileTable, heap);

        Statement declareA = new VariableDeclarationStatement("A",new RefType(new IntType()));
        Statement allocateA = new AllocateStatement("A",new ValueExpression(new IntValue(15)));

        executionStack.push(declareA);
        declareA.execute(myProgramState);
        allocateA.execute(myProgramState);

        Expression findContentOfAddressLocation1 = new ReadHeap(new ValueExpression(new IntValue(5)));

        Assertions.assertThrows(TypeMismatch.class,()-> findContentOfAddressLocation1.evaluate(symbolTable,heap),"TypeMismatch not thrown");
    }

    @Test
    public void shouldFailEvaluatingReadHeap__VariableNotDefined(){
        IStack<Statement> executionStack = new MyStack<>();
        IDict<String, Value> symbolTable = new MyDict<>();
        IList<Value> out = new MyList<>();
        IFileTable<StringValue, BufferedReader>fileTable = new MyFileTable<>();
        IHeap<Integer,Value> heap = new MyHeap<>();
        ProgramState myProgramState = new ProgramState(executionStack, symbolTable, out, fileTable, heap);

        Statement declareA = new VariableDeclarationStatement("A",new RefType(new IntType()));

        executionStack.push(declareA);
        declareA.execute(myProgramState);

        Expression findContentOfAddressLocation1 = new ReadHeap(new VarExpression("A"));

        Assertions.assertThrows(VariableNotDeclared.class,()-> findContentOfAddressLocation1.evaluate(symbolTable,heap),"VariableNotDeclared not thrown");
    }

    @Test
    public void shouldEvaluateSmallerRelationalExpressionAsTrue(){
        IDict<String, Value> notImportantDict = new MyDict<>();
        IHeap<Integer, Value> notImportantHeap = new MyHeap<>();

        Expression value1 = new ValueExpression(new IntValue(6));
        Expression value2 = new ValueExpression(new IntValue(12));

        // +
        Expression expressionAddition = new RelationalExpression(value1,value2,new StringValue("<"));

        Assertions.assertEquals(expressionAddition.evaluate(notImportantDict,notImportantHeap), new BoolValue(true));
    }

    @Test
    public void shouldEvaluateSmallerRelationalExpressionAsFalse(){
        IDict<String, Value> notImportantDict = new MyDict<>();
        IHeap<Integer, Value> notImportantHeap = new MyHeap<>();

        Expression value1 = new ValueExpression(new IntValue(15));
        Expression value2 = new ValueExpression(new IntValue(6));

        // +
        Expression expressionAddition = new RelationalExpression(value1,value2,new StringValue("<"));

        Assertions.assertEquals(expressionAddition.evaluate(notImportantDict,notImportantHeap), new BoolValue(false));
    }

    @Test
    public void shouldEvaluateSmallerOrEqualRelationalExpressionAsTrue__IntValuesAreEqual(){
        IDict<String, Value> notImportantDict = new MyDict<>();
        IHeap<Integer, Value> notImportantHeap = new MyHeap<>();

        Expression value1 = new ValueExpression(new IntValue(6));
        Expression value2 = new ValueExpression(new IntValue(6));

        // +
        Expression expressionAddition = new RelationalExpression(value1,value2,new StringValue("<="));

        Assertions.assertEquals(expressionAddition.evaluate(notImportantDict,notImportantHeap), new BoolValue(true));
    }

    @Test
    public void shouldEvaluateSmallerOrEqualRelationalExpressionAsTrue__IntValuesAreNotEqual(){
        IDict<String, Value> notImportantDict = new MyDict<>();
        IHeap<Integer, Value> notImportantHeap = new MyHeap<>();

        Expression value1 = new ValueExpression(new IntValue(6));
        Expression value2 = new ValueExpression(new IntValue(8));

        // +
        Expression expressionAddition = new RelationalExpression(value1,value2,new StringValue("<="));

        Assertions.assertEquals(expressionAddition.evaluate(notImportantDict,notImportantHeap), new BoolValue(true));
    }

    @Test
    public void shouldEvaluateSmallerOrEqualRelationalExpressionAsFalse(){
        IDict<String, Value> notImportantDict = new MyDict<>();
        IHeap<Integer, Value> notImportantHeap = new MyHeap<>();

        Expression value1 = new ValueExpression(new IntValue(6));
        Expression value2 = new ValueExpression(new IntValue(3));

        // +
        Expression expressionAddition = new RelationalExpression(value1,value2,new StringValue("<="));

        Assertions.assertEquals(expressionAddition.evaluate(notImportantDict,notImportantHeap), new BoolValue(false));
    }

    @Test
    public void shouldEvaluateEqualRelationalExpressionAsFalse(){
        IDict<String, Value> notImportantDict = new MyDict<>();
        IHeap<Integer, Value> notImportantHeap = new MyHeap<>();

        Expression value1 = new ValueExpression(new IntValue(6));
        Expression value2 = new ValueExpression(new IntValue(3));

        // +
        Expression expressionAddition = new RelationalExpression(value1,value2,new StringValue("=="));

        Assertions.assertEquals(expressionAddition.evaluate(notImportantDict,notImportantHeap), new BoolValue(false));
    }

    @Test
    public void shouldEvaluateEqualRelationalExpressionAsTrue(){
        IDict<String, Value> notImportantDict = new MyDict<>();
        IHeap<Integer, Value> notImportantHeap = new MyHeap<>();

        Expression value1 = new ValueExpression(new IntValue(3));
        Expression value2 = new ValueExpression(new IntValue(3));

        // +
        Expression expressionAddition = new RelationalExpression(value1,value2,new StringValue("=="));

        Assertions.assertEquals(expressionAddition.evaluate(notImportantDict,notImportantHeap), new BoolValue(true));
    }

    @Test
    public void shouldEvaluateNotEqualRelationalExpressionAsFalse(){
        IDict<String, Value> notImportantDict = new MyDict<>();
        IHeap<Integer, Value> notImportantHeap = new MyHeap<>();

        Expression value1 = new ValueExpression(new IntValue(6));
        Expression value2 = new ValueExpression(new IntValue(6));

        // +
        Expression expressionAddition = new RelationalExpression(value1,value2,new StringValue("!="));

        Assertions.assertEquals(expressionAddition.evaluate(notImportantDict,notImportantHeap), new BoolValue(false));
    }

    @Test
    public void shouldEvaluateNotEqualRelationalExpressionAsTrue(){
        IDict<String, Value> notImportantDict = new MyDict<>();
        IHeap<Integer, Value> notImportantHeap = new MyHeap<>();

        Expression value1 = new ValueExpression(new IntValue(6));
        Expression value2 = new ValueExpression(new IntValue(3));

        // +
        Expression expressionAddition = new RelationalExpression(value1,value2,new StringValue("!="));

        Assertions.assertEquals(expressionAddition.evaluate(notImportantDict,notImportantHeap), new BoolValue(true));
    }

    @Test
    public void shouldEvaluateBiggerOrEqualRelationalExpressionAsTrue__IntValuesAreEqual(){
        IDict<String, Value> notImportantDict = new MyDict<>();
        IHeap<Integer, Value> notImportantHeap = new MyHeap<>();

        Expression value1 = new ValueExpression(new IntValue(8));
        Expression value2 = new ValueExpression(new IntValue(8));

        // +
        Expression expressionAddition = new RelationalExpression(value1,value2,new StringValue(">="));

        Assertions.assertEquals(expressionAddition.evaluate(notImportantDict,notImportantHeap), new BoolValue(true));
    }

    @Test
    public void shouldEvaluateBiggerOrEqualRelationalExpressionAsTrue__IntValuesAreNotEqual(){
        IDict<String, Value> notImportantDict = new MyDict<>();
        IHeap<Integer, Value> notImportantHeap = new MyHeap<>();

        Expression value1 = new ValueExpression(new IntValue(6));
        Expression value2 = new ValueExpression(new IntValue(3));

        // +
        Expression expressionAddition = new RelationalExpression(value1,value2,new StringValue(">="));

        Assertions.assertEquals(expressionAddition.evaluate(notImportantDict,notImportantHeap), new BoolValue(true));
    }

    @Test
    public void shouldEvaluateBiggerOrEqualRelationalExpressionAsFalse(){
        IDict<String, Value> notImportantDict = new MyDict<>();
        IHeap<Integer, Value> notImportantHeap = new MyHeap<>();

        Expression value1 = new ValueExpression(new IntValue(6));
        Expression value2 = new ValueExpression(new IntValue(8));

        // +
        Expression expressionAddition = new RelationalExpression(value1,value2,new StringValue(">="));

        Assertions.assertEquals(expressionAddition.evaluate(notImportantDict,notImportantHeap), new BoolValue(false));
    }

    @Test
    public void shouldEvaluateBiggerRelationalExpressionAsTrue(){
        IDict<String, Value> notImportantDict = new MyDict<>();
        IHeap<Integer, Value> notImportantHeap = new MyHeap<>();

        Expression value1 = new ValueExpression(new IntValue(6));
        Expression value2 = new ValueExpression(new IntValue(3));

        // +
        Expression expressionAddition = new RelationalExpression(value1,value2,new StringValue(">"));

        Assertions.assertEquals(expressionAddition.evaluate(notImportantDict,notImportantHeap), new BoolValue(true));
    }

    @Test
    public void shouldEvaluateBiggerEqualRelationalExpressionAsFalse(){
        IDict<String, Value> notImportantDict = new MyDict<>();
        IHeap<Integer, Value> notImportantHeap = new MyHeap<>();

        Expression value1 = new ValueExpression(new IntValue(6));
        Expression value2 = new ValueExpression(new IntValue(9));

        // +
        Expression expressionAddition = new RelationalExpression(value1,value2,new StringValue(">"));

        Assertions.assertEquals(expressionAddition.evaluate(notImportantDict,notImportantHeap), new BoolValue(false));
    }

    @Test
    public void shouldFailEvaluatingRelationalExpression__OperationNonExistent(){
        IDict<String, Value> notImportantDict = new MyDict<>();
        IHeap<Integer, Value> notImportantHeap = new MyHeap<>();

        Expression value1 = new ValueExpression(new IntValue(6));
        Expression value2 = new ValueExpression(new IntValue(9));

        // +
        Expression expressionAddition = new RelationalExpression(value1,value2,new StringValue(";"));

        Assertions.assertThrows(OperationNonExistent.class, () -> expressionAddition.evaluate(notImportantDict,notImportantHeap), "OperationNonExistent not thrown");
    }

    @Test
    public void shouldFailEvaluatingRelationalExpression__TypeMismatch(){
        IDict<String, Value> notImportantDict = new MyDict<>();
        IHeap<Integer, Value> notImportantHeap = new MyHeap<>();

        Expression value1 = new ValueExpression(new IntValue(6));
        Expression value2 = new ValueExpression(new BoolValue(false));

        // +
        Expression expressionAddition = new RelationalExpression(value1,value2,new StringValue("=="));

        Assertions.assertThrows(TypeMismatch.class, () -> expressionAddition.evaluate(notImportantDict,notImportantHeap), "TypeMismatch not thrown");
    }

}
