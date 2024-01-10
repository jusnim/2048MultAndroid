package com.example.a2048mult.ui.game;

import com.example.a2048mult.game.states.GameState;

/**
 * when initialized, should save into Gamelogic with:
 * GameLogic.getInstance().setgameUI(this);
 */
public interface GameUI {
    /**
     *  inits a Game
     *  used when starting a new game
     *  important for setting game once
     */
    void initDrawGameState(GameState gameState);

    /**
     * draw current Gamestate
     * used when a new turn was done
     * also used when going out of a game and going in again
     * basically refreshes current game
     */
    void drawGameState(GameState gameState);
}
