package com.tudor.Model.Types;

import com.tudor.Model.Values.BoolValue;
import com.tudor.Model.Values.Value;

public class BoolType implements Type{
    @Override
    public boolean equals(Object obj) {
        return  (obj instanceof BoolType);
    }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }
}
