package com.tudor.Model.Expressions;

import com.tudor.Model.ADTs.IDict;
import com.tudor.Model.ADTs.IHeap;
import com.tudor.Model.Types.Type;
import com.tudor.Model.Values.Value;

public class ValueExpression implements Expression{
    private final Value value;

    public ValueExpression(Value value) {
        this.value = value;
    }

    @Override
    public String toString(){
        return value.toString();
    }

    @Override
    public Value evaluate(IDict<String, Value> symTable, IHeap<Integer, Value> heap) {
        return value;
    }

    @Override
    public Type typecheck(IDict<String, Type> typeEnvironment) {
        return value.getType();
    }
}
