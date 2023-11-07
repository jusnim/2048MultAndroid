package com.example.a2048mult.GameLogic;

/**
 * Represents the data structure of one Playfield
 */
public interface PlayfieldState extends GameRules{

    /**
     * getter for a Playfield
     * @return PlayfieldState
     */
    PlayfieldState getPlayfieldState();

    /**
     * setter for a Playfield
     */
    void setPlayfieldState();

    /**
     * spawning a block on the playfield
     */
    void spawnBlock();

}
