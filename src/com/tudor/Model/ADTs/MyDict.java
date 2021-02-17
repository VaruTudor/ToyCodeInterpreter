package com.tudor.Model.ADTs;

import java.util.HashMap;
import java.util.Map;

public class MyDict<T1,T2> implements IDict<T1,T2>{
    protected HashMap<T1,T2> dict;

    public MyDict() {
        this.dict = new HashMap<>();
    }

    //copy constructor
    public MyDict(MyDict<T1,T2> anotherDict){
        try{
            dict = (HashMap<T1, T2>) anotherDict.dict.clone();
        } catch (Exception e) {
            throw new RuntimeException("copy constructor error");
        }
    }

    @Override
    public void add(T1 v1, T2 v2) {
        dict.put(v1,v2);
    }

    @Override
    public void update(T1 v1, T2 v2) {
        dict.replace(v1, v2);
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
    public Map<T1, T2> getContent() {
        return dict;
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
