package com.tudor.Exceptions.TypeExceptions;

public class TypeMismatch extends RuntimeException{
    @Override
    public String toString(){
        return "Tha types don't match";
    }
}
