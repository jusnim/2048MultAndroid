package com.example.a2048mult.ui.game.playfield;

import com.example.a2048mult.game.states.Player;
import com.example.a2048mult.game.states.PlayfieldTurn;

public interface DrawPlayfieldUI {
    /**
     *  inits a Player (also Playfield)
     *  used when starting a new game
     *  important for setting playfield once
     */
    public void initPlayer(Player player);


    /**
     * draws animations, after that UI represents PlayfieldState
     * basically refreshes current playfield
     */
    void drawPlayfieldTurn(PlayfieldTurn playfieldTurn);
}
