package com.tudor.Exceptions;

public class VariableAlreadyDeclared extends RuntimeException{
    @Override
    public String toString(){
        return "Tha variable has already been declared";
    }
}
