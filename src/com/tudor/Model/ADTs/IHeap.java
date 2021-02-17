package com.tudor.Model.ADTs;

import java.util.Map;

public interface IHeap<T1,T2>{
    void add(T1 v1, T2 v2);
    void update(T1 v1, T2 v2);
    void remove(T1 v1);
    T2 lookup(T1 id);
    boolean isDefined(T1 id);
    int getNextFreeLocation();
    void setContent(Map<T1,T2> newDict);
    Map<T1,T2> getContent();
}
