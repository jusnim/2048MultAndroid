package com.example.a2048mult.game.states;

import java.io.Serializable;

/**
 * Represents the data structure of one Playfield
 */
public interface PlayfieldState extends Serializable {

    void printField();

    /**
     * getter for FieldSizeX
     */
    int getFieldSizeX();

    /**
     * getter for FieldSizeY
     */
    int getFieldSizeY();

    /**
     * getter for one tile
     */
    int getTile(int x, int y);

    /**
     * setter for one tile
     */
    boolean setTile(int x, int y, int value);

    /**
     * getter for field
     */
    int[][] getField();

    /**
     * setter for field
     */
    void setField(int[][] field);

}
