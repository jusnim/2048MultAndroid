package com.example.a2048mult.Control;

import com.example.a2048mult.game.logic.Player;

/**
 * connects players with their mac addresses
 */
public interface MapPlayersToDevices {
    /**
     * gives player information from Mac-Address
     * @param macAddress Mac-Address for searched player
     * @return Player object which has this Mac-Address
     */
    Player givePlayerFromMac(String macAddress);
}