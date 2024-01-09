package com.example.a2048mult.Control;

public abstract class PDU {
    public static final String MAGIC_NUMBER = "2048Mult";
    public static final String VERSION = "1.0";
    public abstract PDUType getPDUType();

}
