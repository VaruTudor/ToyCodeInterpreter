package com.tudor.Model.ADTs;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements IList<T> {
    private final ArrayList<T> list;

    public MyList() {
        this.list = new ArrayList<>();
    }

    @Override
    public void add(T v) {
        list.add(v);
    }

    @Override
    public List<T> getContent() {
        return list;
    }

    @Override
    public T pop() {
        return list.remove(list.size()-1);
    }

    @Override
    public String toString(){
        StringBuilder output = new StringBuilder();
        for( T value : list){
            output.append(value.toString());
            output.append("  ");
        }
        return output.toString();
    }
}
