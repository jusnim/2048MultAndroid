package com.example.a2048mult.ui.game.playfield;

import com.example.a2048mult.game.states.Player;
import com.example.a2048mult.game.states.PlayfieldTurn;

public interface PlayfieldUI  {
    /**
     *  inits a Player (also Playfield)
     *  used when starting a new game
     *  important for setting playfield once
     */
    public void initPlayer(Player player);

    /**
     * draw current Player (also Playfield)
     * used when a new turn was done
     * also used when going out of a game and going in again
     * basically refreshes current playfield
     */
    public void drawPlayer(Player player);

    void drawPlayfieldBackground(int width, int height);

    void drawPlayfieldTurn(PlayfieldTurn playfieldTurn);
}
