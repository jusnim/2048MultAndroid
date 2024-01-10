package com.example.a2048mult.game.states;

import com.example.a2048mult.game.states.PlayfieldState;
import com.example.a2048mult.game.states.PlayfieldTurn;

import java.io.Serializable;

/**
 * represents a player in the game
 */
public interface Player extends Serializable {
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
     * getter for Playfield
     * @return
     */
    PlayfieldState getPlayfieldState();


    /**
     * setter for PlayfieldState
     */
    void setPlayfieldSize(int size);

    /**
     * getter for PlayfieldTurn representing the change on Field - used for animation
     * @return
     */
    PlayfieldTurn getPlayfieldTurn();

    /**
     * setter for PlayfieldTurn
     * @param playfieldTurn
     */
    void setPlayfieldTurn(PlayfieldTurn playfieldTurn);

}
