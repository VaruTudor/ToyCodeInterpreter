package com.tudor.Model.Values;

import com.tudor.Model.Types.BoolType;
import com.tudor.Model.Types.Type;

public class BoolValue implements Value{
    private final boolean value;

    public BoolValue(boolean newValue) {
        value = newValue;
    }

    public boolean getValue(){
        return this.value;
    }

    public Type getType(){
        return new BoolType();
    }

    @Override
    public boolean equals(Object another){
        // if compared to itself return true
        if (another == this){
            return  true;
        }

        // return false if different type
        if ( !(another instanceof BoolValue)){
            return false;
        }

        // cast Object to actual type
        BoolValue anotherBool = (BoolValue) another;

        // perform equality check
        return (this.value == anotherBool.value);
    }

    @Override
    public String toString(){
        return Boolean.toString(this.value);
    }
}
