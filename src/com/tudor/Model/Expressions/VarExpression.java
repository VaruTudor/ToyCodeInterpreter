package com.tudor.Model.Expressions;

import com.tudor.Model.ADTs.IDict;
import com.tudor.Model.ADTs.IHeap;
import com.tudor.Model.Types.Type;
import com.tudor.Model.Values.Value;

public class VarExpression implements Expression{
    private final String id;

    public VarExpression(String id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return "@" + id;
    }

    @Override
    public Value evaluate(IDict<String, Value> symTable, IHeap<Integer, Value> heap) {
        return symTable.lookup(id);
    }

    @Override
    public Type typecheck(IDict<String, Type> typeEnvironment) {
        return typeEnvironment.lookup(id);
    }
}
