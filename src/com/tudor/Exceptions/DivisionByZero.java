package com.tudor.Exceptions;

public class DivisionByZero extends RuntimeException{
    @Override
    public String toString(){
        return "Division by zero";
    }
}
