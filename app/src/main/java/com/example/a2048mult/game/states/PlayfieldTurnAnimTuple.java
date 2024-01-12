package com.example.a2048mult.game.states;

import java.io.Serializable;

// https://stackoverflow.com/questions/2670982/using-pairs-or-2-tuples-in-java
public class PlayfieldTurnAnimTuple implements Serializable, Comparable<PlayfieldTurnAnimTuple>{
    public final PlayfieldTurnAnimationType type;
    public final GameTile tile;
    public PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType type, GameTile tile) {
        this.type = type;
        this.tile = tile;
    }

    @Override
    public int compareTo(PlayfieldTurnAnimTuple o) {
        if(this.type.ordinal() > o.type.ordinal()){
            return 1;
        }
        return -1;
    }
}