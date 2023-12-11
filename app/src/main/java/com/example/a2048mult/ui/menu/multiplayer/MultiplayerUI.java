package com.example.a2048mult.ui.menu.multiplayer;

import com.example.a2048mult.game.logic.Lobby;
import com.example.a2048mult.game.logic.Player;

public interface MultiplayerUI {

    /**
     * draws current available lobbies
     */
    void drawCurrentAvailableLobbies(Lobby[] lobbies);

    /**
     * draws an incoming invite
     */
    void drawNewInvite(Lobby lobby);

    /**
     * draws a joined player
     */
    void drawJoinedPlayer(Player player);
}
