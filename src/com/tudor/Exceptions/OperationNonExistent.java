package com.tudor.Exceptions;

public class OperationNonExistent extends RuntimeException{
    @Override
    public String toString() {
        return "operation does not exits\n";
    }
}
