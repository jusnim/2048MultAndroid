package com.example.a2048mult.game.logic;

import com.example.a2048mult.game.states.MoveType;
import com.example.a2048mult.ui.game.DrawGameUI;

/**
 * Interface zum Verarbeiten der GameView-Inputs
 */
public interface InGameControl {
    /**
     * Methode um Spielzüge auszuwerten
     * @param move Richtung in die sich die Tiles bewegen sollen
     */
    void swipe(MoveType move);

    /**
     * Methode um das Spiel vorzeitig zu verlassen
     */
    void leaveGame();


    /**
     * setter for GameUI
     */
    void initDrawGameUI(DrawGameUI gameUI);
}
