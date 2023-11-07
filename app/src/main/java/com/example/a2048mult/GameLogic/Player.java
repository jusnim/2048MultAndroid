package com.example.a2048mult.GameLogic;

/**
 * represents a player in the game
 */
public interface Player {

    /**
     * getter for score
     * @return score
     */
    long getScore();

    /**
     * adds points to the current Score
     */
    void addScore(long points);

    /**
     * get the Playfield associated with the Player
     * @return
     */
    PlayfieldState getPlayfieldState();

}
