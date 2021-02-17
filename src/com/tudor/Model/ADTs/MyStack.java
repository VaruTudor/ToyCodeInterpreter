package com.tudor.Model.ADTs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class MyStack<T> implements IStack<T> {
    private final Stack<T> stack;

    public MyStack() {
        this.stack = new Stack<>();
    }

    @Override
    public T pop() {
        return stack.pop();
    }
    @Override
    public void push(T newElement) {
        stack.push(newElement);
    }
    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public Stack<T> getStack(){
        return this.stack;
    }

    @Override
    public String toString(){
        StringBuilder output = new StringBuilder();

        List<T> tList = new ArrayList<>(stack);
        Collections.reverse(tList);

        for(T element : tList){
            output.append(element.toString());
        }
        return output.toString();
    }
}
