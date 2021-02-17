package com.tudor.Exceptions;

public class ReadLineException extends RuntimeException{
    @Override
    public String toString() {
        return "cannot read line\n";
    }
}
