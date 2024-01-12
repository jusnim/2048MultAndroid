package com.example.a2048mult.game.currentlyNotUsed;

import com.example.a2048mult.game.states.GameState;
import com.example.a2048mult.game.states.LobbySettings;

import java.io.InputStream;

public interface HandlePackets {

    /**
     * recieve the data give by game logic
     */
    void recievedataPackets(InputStream inputStream);

    /**
     * sends stored data when asked for 
     */

    void senddataPackets(GameState gameState);

    void senddataPackets(LobbySettings lobbySettings);
}
