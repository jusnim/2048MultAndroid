package com.example.a2048mult.ui.menu.multiplayer;

import com.example.a2048mult.game.states.ChangeLobbyConfigurations;
import com.example.a2048mult.game.states.Player;

public interface MultiplayerUI {

    /**
     * draws current available lobbies
     */
    void drawCurrentAvailableLobbies(ChangeLobbyConfigurations[] lobbies);

    /**
     * draws an incoming invite
     */
    void drawNewInvite(ChangeLobbyConfigurations lobbySettings);

    /**
     * draws a joined player
     */
    void drawJoinedPlayer(Player player);
}
