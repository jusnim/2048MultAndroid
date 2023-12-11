package com.example.a2048mult.game.logic;

public interface HandlePackets {

    /**
     * recieve the data give by game logic
     */
    void recievedataPackets();

    /**
     * sends stored data when asked for 
     */

    void senddataPackets();
}
