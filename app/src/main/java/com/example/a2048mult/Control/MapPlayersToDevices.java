package com.example.a2048mult.Control;

import com.example.a2048mult.game.states.PlayerImpl;

/**
 * connects players with their mac addresses
 */
public interface MapPlayersToDevices {
    /**
     * gives player information from Mac-Address
     * @param macAddress Mac-Address for searched player
     * @return Player object which has this Mac-Address
     */
    PlayerImpl givePlayerFromMac(String macAddress);
}
