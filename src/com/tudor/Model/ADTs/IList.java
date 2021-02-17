package com.tudor.Model.ADTs;

import java.util.List;

public interface IList<T> {
    void add(T v);
    List<T> getContent();
    T pop();
}
