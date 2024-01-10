package com.example.a2048mult.game.logic;

public interface GameDraw {
//    /**
//     *  inits a GameState
//     *  used when starting a new game
//     *  important for setting playfields etc once
//     */
//    void initGameState();

    /**
     * draw current GameState
     * used when a new turn was done
     * also used when going out of a game and going in again
     * basiaclly refreshes current GameState
     */
    void drawGameState();
}
