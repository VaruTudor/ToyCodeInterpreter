package com.tudor.Model.Expressions;

import com.tudor.Model.ADTs.IDict;
import com.tudor.Model.ADTs.IHeap;
import com.tudor.Model.Types.Type;
import com.tudor.Model.Values.Value;

public interface Expression {
    Value evaluate(IDict<String, Value> symTable, IHeap<Integer, Value> heap);
    Type typecheck(IDict<String, Type> typeEnvironment);
}
