package com.tudor.Exceptions;

public class FilenameNotDeclared extends RuntimeException{
    @Override
    public String toString() {
        return "the file name has not been declared\n";
    }
}
