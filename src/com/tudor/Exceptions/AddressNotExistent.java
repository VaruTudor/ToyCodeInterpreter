package com.tudor.Exceptions;

public class AddressNotExistent extends RuntimeException{
    @Override
    public String toString() {
        return "address not existent in Heap\n";
    }
}
