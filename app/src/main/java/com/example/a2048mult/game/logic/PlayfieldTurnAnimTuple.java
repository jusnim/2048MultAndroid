package com.example.a2048mult.game.logic;

import java.io.Serializable;

// https://stackoverflow.com/questions/2670982/using-pairs-or-2-tuples-in-java
public class PlayfieldTurnAnimTuple<Type, Tile> implements Serializable {
    public final Type type;
    public final Tile tile;
    public PlayfieldTurnAnimTuple(Type type, Tile tile) {
        this.type = type;
        this.tile = tile;
    }
}