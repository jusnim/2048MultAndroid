package com.example.a2048mult.ui.game.playfield;

import com.example.a2048mult.game.logic.PlayfieldState;

public interface PlayfieldUI {
    /**
     *  inits a Playfield
     *  used when starting a new game
     *  important for setting playfield once
     */
    public void initGameState(PlayfieldState playfieldState);

    /**
     * draw current Playfield
     * used when a new turn was done
     * also used when going out of a game and going in again
     * basically refreshes current playfield
     */
    public void drawGameState(PlayfieldState playfieldState);
}
