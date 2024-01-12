package com.example.a2048mult;

import java.io.Serializable;

public class Tuple<T, U> implements Serializable {
    public T t;
    public U u;

    public Tuple(T t, U u) {
        this.t = t;
        this.u = u;
    }
}