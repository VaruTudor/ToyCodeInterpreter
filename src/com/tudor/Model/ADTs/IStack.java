package com.tudor.Model.ADTs;

import java.util.Stack;

public interface IStack<T> {
    T pop();
    void push(T newElement);

    boolean isEmpty();
    Stack<T> getStack();

}
