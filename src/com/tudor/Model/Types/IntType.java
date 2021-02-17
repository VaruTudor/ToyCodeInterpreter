package com.tudor.Model.Types;

import com.tudor.Model.Values.IntValue;
import com.tudor.Model.Values.Value;

public class IntType implements Type{
    @Override
    public boolean equals(Object obj) {
        return  (obj instanceof IntType);
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public Value defaultValue() {
        return new IntValue(0);
    }
}
