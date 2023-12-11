package com.example.a2048mult.game.logic;

import com.example.a2048mult.game.Move;

/**
 * Interface zum Verarbeiten der GameView-Inputs
 */
public interface GameInputs {
    /**
     * Methode um Spielzüge auszuwerten
     * @param move Richtung in die sich die Tiles bewegen sollen
     */
    void swipe(Move move);

    /**
     * Methode um das Spiel vorzeitig zu verlassen
     */
    void leaveGame();
}
