package com.tudor.Exceptions.TypeExceptions;

public class ConditionNotBoolType extends RuntimeException{
    @Override
    public String toString(){
        return "The condition evaluated to not BoolType";
    }
}
