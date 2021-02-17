package com.tudor.Model.Values;

import com.tudor.Model.Types.RefType;
import com.tudor.Model.Types.Type;

public class RefValue implements Value{
    private final int address;
    private final Type locationType;

    public RefValue(int address, Type type) {
        this.address = address;
        this.locationType = type;
    }

    public int getAddress() {
        return address;
    }

    public Type getLocationType() {
        return locationType;
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    @Override
    public String toString() {
        return "RefValue(" +
                address +
                "," + locationType.toString() + ')';
    }
}
