package com.example.a2048mult.GameLogic;

/**
 * represents the data structure of a item on a playfield
 */
public interface GameBlock {
    /**
     * updates coordinates, the new coordinates are saved as newX and newY and the ones before are saved in oldX and oldY
     * @param newX
     * @param newY
     */
    default void updateCoordinates(int newX, int newY){
        changeNewToOld();
        setNewX(newX);
        setNewY(newY);
    }

    /**
     * overrites the old coordinates with the newer ones
     */
    default void changeNewToOld(){
        setOldX(getNewX());
        setOldY(getNewY());
    }

    /**
     * setter for old x coordinate
     * @param x
     */
    void setOldX(int x);
    /**
     * setter for old y coordinate
     * @param y
     */
    void setOldY(int y);
    /**
     * setter for new x coordinate
     * @param x
     */
    void setNewX(int x);
    /**
     * setter for new y coordinate
     * @param y
     */
    void setNewY(int y);

    /**
     * getter for old x coordinate
     */
    int getOldX();
    /**
     * getter for old y coordinate
     */
    int getOldY();
    /**
     * getter for new x coordinate
     */
    int getNewX();
    /**
     * getter for new y coordinate
     */
    int getNewY();

    /**
     * getter for level
     */
    int getLevel();
    /**
     * setter for level
     */
    int setLevel();
}
