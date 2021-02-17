package com.tudor.Model.ADTs;

import java.util.HashMap;
import java.util.Map;

public class MyHeap<T1,T2> implements IHeap<T1,T2>{
    private final HashMap<T1,T2> dict;
    private int nextFreeLocation;

    public MyHeap() {
        this.dict = new HashMap<>();
        nextFreeLocation = 1;
    }

    public int getNextFreeLocation() {
        return nextFreeLocation;
    }

    @Override
    public void setContent(Map<T1,T2> newDict) {
        dict.clear();
        for ( Map.Entry<T1,T2> entry : newDict.entrySet()){
            dict.put(entry.getKey(),entry.getValue());
        }
    }

    @Override
    public Map<T1, T2> getContent() {
        return dict;
    }

    @Override
    public void add(T1 v1, T2 v2) {
        dict.put(v1,v2);
        nextFreeLocation++;
    }

    @Override
    public void update(T1 v1, T2 v2) {
        dict.replace(v1,v2);
    }

    @Override
    public void remove(T1 v1) {
        dict.remove(v1);
        nextFreeLocation--;
    }

    @Override
    public T2 lookup(T1 id) {
        return dict.get(id);
    }

    @Override
    public boolean isDefined(T1 id) {
        return dict.containsKey(id);
    }

    @Override
    public String toString(){
        StringBuilder output = new StringBuilder();
        for (Map.Entry<T1, T2> entry : dict.entrySet()) {
            T1 key = entry.getKey();
            output.append(key.toString());
            output.append("=");
            T2 value = entry.getValue();
            output.append(value.toString());
            output.append("  ");
        }
        return output.toString();
    }

}
