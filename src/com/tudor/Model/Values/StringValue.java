package com.tudor.Model.Values;

import com.tudor.Model.Types.StringType;
import com.tudor.Model.Types.Type;

public class StringValue implements Value{
    private final String value;

    public StringValue(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public boolean equals(Object another){
        // if compared to itself return true
        if (another == this){
            return  true;
        }

        // return false if different type
        if ( !(another instanceof StringValue)){
            return false;
        }

        // cast Object to actual type
        StringValue anotherStringValue = (StringValue) another;

        // perform equality check
        return (this.value.equals(anotherStringValue.value));
    }
}
