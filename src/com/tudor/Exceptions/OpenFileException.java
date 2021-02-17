package com.tudor.Exceptions;

public class OpenFileException extends RuntimeException{
    @Override
    public String toString() {
        return "cannot open file\n";
    }
}
