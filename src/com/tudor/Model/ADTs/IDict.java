package com.tudor.Model.ADTs;

import java.util.Map;

public interface IDict<T1,T2> {
    void add(T1 v1, T2 v2);
    void update(T1 v1, T2 v2);
    T2 lookup(T1 id);
    boolean isDefined(T1 id);
    Map<T1,T2> getContent();
}
