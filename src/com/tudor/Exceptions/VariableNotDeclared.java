package com.tudor.Exceptions;

public class VariableNotDeclared extends RuntimeException{
    @Override
    public String toString(){
        return "Tha variable has not been declared";
    }
}
