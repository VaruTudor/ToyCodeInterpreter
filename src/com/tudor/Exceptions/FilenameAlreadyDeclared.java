package com.tudor.Exceptions;

public class FilenameAlreadyDeclared extends RuntimeException{
    @Override
    public String toString() {
        return "the file name already been declared\n";
    }
}
