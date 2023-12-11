package com.example.a2048mult.game.logic;

/**
 * Represents the data structure of one Playfield
 */
public interface PlayfieldState{

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
     * spawning a Tile on the playfield
     */
    void spawnTile();

    /**
     * setter for FieldSizeX
     */
    void setFieldSizeX();

    /**
     * setter for FieldSizeY
     */
    void setFieldSizeY();

}
