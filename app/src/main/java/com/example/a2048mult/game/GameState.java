package com.example.a2048mult.game;

import com.example.a2048mult.game.logic.Player;
import com.example.a2048mult.game.logic.PlayfieldTurn;

/**
 * represents the data structure of a GameState
 * one GameState can be one or more PlayfieldStates at once
 */
public interface GameState {

    /**
     * getter for TurnCount
     * @return int, total TurnCount e.g. 46
     */
    int getTurnCount();

    /**
     * increments TurnCount, so 46 --> 47
     */
    void incrementTurnCount();

    /**
     * gets the current GameTurn
     * @return
     */
    PlayfieldTurn[] getGameTurn();

    /**
     * getter for the current User
     * @return
     */
    Player getCurrentPlayer();

    /**
     * called after a turn --> next player can do a move
     * if only one player (so one PlayfieldState) --> nextTurn changes on itself, so doing nothing
     */
    void nextPlayer();

    /**
     * get the list of quitted User
     * @return
     */
    Player[] getQuittedPlayer();
    /**
     * get all player
     * @return
     */
    Player[] getAllPlayer();
}
