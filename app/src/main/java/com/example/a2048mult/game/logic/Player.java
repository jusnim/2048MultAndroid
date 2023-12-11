package com.example.a2048mult.game.logic;

/**
 * represents a player in the game
 */
public interface Player {
    /**
     * getter for the username of the player
     * @return
     */
    String getName();

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
