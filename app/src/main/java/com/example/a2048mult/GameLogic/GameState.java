package com.example.a2048mult.GameLogic;

/**
 * represents the data structure of a GameState
 * one GameState can be one or more playfields at once
 */
public interface GameState {
    /**
     * getter for GameState
     * @return GameState
     */
    GameState getGameState();
    /**
     * setter for GameState
     */
    void setGameState();

    /**
     * gets the current Turn --> which player can do a move
     * @return
     */
    PlayfieldState getTurn();

    /**
     * called after a turn --> next player can do a move
     * if only one player (so one PlayfieldState) --> nextTurn changes on itself, so doing nothing
     */
    void nextTurn();
}
