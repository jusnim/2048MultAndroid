package com.example.a2048mult.ui.game;
import com.example.a2048mult.game.logic.GameState;

public interface GameUI {
    /**
     *  inits a GameState
     *  used when starting a new game
     *  important for setting playfields etc once
     */
    public void initGameState(GameState gameState);

    /**
     * draw current GameState
     * used when a new turn was done
     * also used when going out of a game and going in again
     * basiaclly refreshes current GameState
     */
    public void drawGameState(GameState gameState);
}
