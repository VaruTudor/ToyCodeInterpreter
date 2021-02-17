package com.tudor.Model.Values;

import com.tudor.Model.Types.IntType;
import com.tudor.Model.Types.Type;

public class IntValue implements Value{
    private final int value;

    public IntValue(int newValue) {
        value = newValue;
    }

    public int getValue(){
        return this.value;
    }

    public Type getType(){
        return new IntType();
    }

    @Override
    public boolean equals(Object another){
        // if compared to itself return true
        if (another == this){
            return  true;
        }

        // return false if different type
        if ( !(another instanceof IntValue)){
            return false;
        }

        // cast Object to actual type
        IntValue anotherInt = (IntValue) another;

        // perform equality check
        return (this.value == anotherInt.value);
    }

    @Override
    public String toString(){
        return Integer.toString(this.value);
    }
}
