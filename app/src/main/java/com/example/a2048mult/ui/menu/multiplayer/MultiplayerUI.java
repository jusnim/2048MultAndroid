package com.example.a2048mult.ui.menu.multiplayer;

import com.example.a2048mult.game.logic.LobbySettings;
import com.example.a2048mult.game.logic.Player;

public interface MultiplayerUI {

    /**
     * draws current available lobbies
     */
    void drawCurrentAvailableLobbies(LobbySettings[] lobbies);

    /**
     * draws an incoming invite
     */
    void drawNewInvite(LobbySettings lobbySettings);

    /**
     * draws a joined player
     */
    void drawJoinedPlayer(Player player);
}
