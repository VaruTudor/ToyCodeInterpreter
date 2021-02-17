package com.tudor.Model.Types;


import com.tudor.Model.Values.Value;

public class StringType implements Type{

    @Override
    public boolean equals(Object obj) {
        return  (obj instanceof StringType);
    }

    @Override
    public String toString() {
        return "string";
    }

    @Override
    public Value defaultValue() {
        return null;
    }
}
