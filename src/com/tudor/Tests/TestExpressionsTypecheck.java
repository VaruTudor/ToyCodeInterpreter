package com.tudor.Tests;

import com.tudor.Exceptions.TypeExceptions.TypeMismatch;
import com.tudor.Exceptions.TypeExceptions.ValueNotRefType;
import com.tudor.Model.ADTs.IDict;
import com.tudor.Model.ADTs.MyDict;
import com.tudor.Model.Expressions.*;
import com.tudor.Model.Types.BoolType;
import com.tudor.Model.Types.IntType;
import com.tudor.Model.Types.RefType;
import com.tudor.Model.Types.Type;
import com.tudor.Model.Values.BoolValue;
import com.tudor.Model.Values.IntValue;
import com.tudor.Model.Values.StringValue;
import com.tudor.Model.Values.Value;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestExpressionsTypecheck {

    @Test
    public void shouldTypecheckValueExpression() {
        IDict<String, Type> typeEnvironment = new MyDict<>();
        Expression expression = new ValueExpression(new IntValue(7));

        Assertions.assertEquals(expression.typecheck(typeEnvironment), new IntType());
    }

    @Test
    public void shouldTypecheckVarExpression() {
        IDict<String, Value> symbolTable = new MyDict<>();
        IDict<String, Type> typeEnvironment = new MyDict<>();
        symbolTable.add("a",new IntValue(7));
        typeEnvironment.add("a",new IntType());

        Expression expression = new VarExpression("a");

        Assertions.assertEquals(expression.typecheck(typeEnvironment), new IntType());
    }

    @Test
    public void shouldTypecheckArithExpression() {
        IDict<String, Type> typeEnvironment = new MyDict<>();
        Expression value1 = new ValueExpression(new IntValue(12));
        Expression value2 = new ValueExpression(new IntValue(6));

        // +
        Expression expressionAddition = new ArithExpression(value1,value2,1);

        Assertions.assertEquals(expressionAddition.typecheck(typeEnvironment), new IntType());

    }

    @Test
    public void shouldFailTypecheckArithExpression__TypeMismatch() {
        IDict<String, Type> typeEnvironment = new MyDict<>();
        Expression value1 = new ValueExpression(new IntValue(12));
        Expression value2 = new ValueExpression(new BoolValue(false));

        Expression expressionDivision = new ArithExpression(value1,value2,1);

        Assertions.assertThrows(TypeMismatch.class, () -> expressionDivision.typecheck(typeEnvironment), "TypeMismatch not thrown");
    }

    @Test
    public void shouldTypecheckLogicExpression() {
        IDict<String, Type> typeEnvironment = new MyDict<>();

        Expression value1 = new ValueExpression(new BoolValue(true));
        Expression value2 = new ValueExpression(new BoolValue(false));

        Expression expression = new LogicExpression(value1,value2,2);

        Assertions.assertEquals(expression.typecheck(typeEnvironment), new BoolType());
    }

    @Test
    public void shouldFailTypecheckLogicExpression__TypeMismatch() {
        IDict<String, Type> typeEnvironment = new MyDict<>();

        Expression value1 = new ValueExpression(new BoolValue(true));
        Expression value2 = new ValueExpression(new IntValue(5));

        Expression expression = new LogicExpression(value1,value2,2);

        Assertions.assertThrows(TypeMismatch.class, () -> expression.typecheck(typeEnvironment), "TypeMismatch not thrown");
    }

    @Test
    public void shouldTypecheckReadHeap(){
        IDict<String, Type> typeEnvironment = new MyDict<>();

        typeEnvironment.add("A",new RefType(new IntType()));
        Expression expression = new ReadHeap(new VarExpression("A"));

        Assertions.assertEquals(expression.typecheck(typeEnvironment), new IntType());
    }


    @Test
    public void shouldFailTypecheckReadHeap__ValueNotRefType(){
        IDict<String, Type> typeEnvironment = new MyDict<>();

        typeEnvironment.add("A",new IntType());
        Expression expression = new ReadHeap(new VarExpression("A"));

        Assertions.assertThrows(ValueNotRefType.class,()-> expression.typecheck(typeEnvironment),"ValueNotRefType not thrown");
    }

    @Test
    public void shouldTypecheckRelationalExpression(){
        IDict<String, Type> typeEnvironment = new MyDict<>();
        Expression value1 = new ValueExpression(new IntValue(6));
        Expression value2 = new ValueExpression(new IntValue(6));

        Expression expressionAddition = new RelationalExpression(value1,value2,new StringValue("!="));

        Assertions.assertEquals(expressionAddition.typecheck(typeEnvironment), new BoolType());
    }

    @Test
    public void shouldFailTypecheckRelationalExpression(){
        IDict<String, Type> typeEnvironment = new MyDict<>();
        Expression value1 = new ValueExpression(new IntValue(6));
        Expression value2 = new ValueExpression(new BoolValue(false));

        Expression expression = new RelationalExpression(value1,value2,new StringValue("!="));

        Assertions.assertThrows(TypeMismatch.class,()-> expression.typecheck(typeEnvironment),"TypeMismatch not thrown");
    }

}
