package com.tudor.Exceptions;

public class CloseFileException extends RuntimeException{
    @Override
    public String toString() {
            return "cannot close file\n";
        }
}
