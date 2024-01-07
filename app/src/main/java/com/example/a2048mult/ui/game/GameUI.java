package com.example.a2048mult.ui.game;

import com.example.a2048mult.game.GameState;
import com.example.a2048mult.game.logic.PlayfieldState;

public interface GameUI {
    /**
     *  inits a Game
     *  used when starting a new game
     *  important for setting game once
     */
    public void initGameState(GameState gameState) throws IllegalAccessException;

    /**
     * draw current Gamestate
     * used when a new turn was done
     * also used when going out of a game and going in again
     * basically refreshes current game
     */
    public void drawGameState(GameState gameState);
}
