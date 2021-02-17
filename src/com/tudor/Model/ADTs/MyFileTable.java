package com.tudor.Model.ADTs;

import java.util.HashMap;

public class MyFileTable<T1,T2> implements IFileTable<T1,T2> {
    private final HashMap<T1,T2> dict;

    public MyFileTable() {
        this.dict = new HashMap<>();
    }

    @Override
    public void add(T1 v1, T2 v2) {
        dict.put(v1,v2);
    }

    @Override
    public void remove(T1 v1){
        dict.remove(v1);
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
    public HashMap<T1, T2> getHashMap() {
        return dict;
    }

}
