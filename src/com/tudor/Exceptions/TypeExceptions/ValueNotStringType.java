package com.tudor.Exceptions.TypeExceptions;

public class ValueNotStringType extends RuntimeException{
    @Override
    public String toString() {
        return "the statement does not evaluate to a StringType\n";
    }
}
