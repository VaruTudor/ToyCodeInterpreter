package com.tudor.Exceptions;

public class NoStatementsInProgramState extends RuntimeException{
    @Override
    public String toString(){
        return "There are no more statements in program state";
    }
}
