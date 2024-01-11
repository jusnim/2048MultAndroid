package com.example.a2048mult.game.states;

import java.io.Serializable;

// https://stackoverflow.com/questions/2670982/using-pairs-or-2-tuples-in-java
public class PlayfieldTurnAnimTuple<Type, Tiles> implements Serializable {
    public final Type type;
    public final Tiles tiles;
    public PlayfieldTurnAnimTuple(Type type, Tiles tiles) {
        this.type = type;
        this.tiles = tiles;
    }
}