package com.example.a2048mult.game.currentlyNotUsed;

import android.os.Bundle;

import com.example.a2048mult.game.logic.Player;

/**
 * gives and recieves information about the current lobby and exchanges them with MenuView
 */
public interface LobbySettings {

    /**
     * @return true - if the game was started by the leader
     */
    boolean getGameStarted();

    /**
     * returns the number of Players, already joined in the Lobby
     * @return playernum
     */
    int getPlayerNum();

    /**
     * returns the list of player already joined the lobby
     */
    Player[] getAllPlayer();

    /**
     * gets the leader of the current Lobby
     * @return Lobbyleader
     */
    Player getLeader();

    /**
     * setter for leader
     * @param player
     */
    void setLeader(Player player);

    /**
     * add Player to Game
     * @param player
     */
    public void addPlayer(Player player);
    /**
     * gets current playfield size
     * @return playfieldsize
     */
    int getPlayFieldSize();

    /**
     * sets size of playfield
     * @param size is the new size
     */
    void setPlayFieldSize(int size);

    /**
     * starts the game and locks all settings and devices/players
     */
    void startGame();

    /**
     * getter for the Bundle, that holds the ByteRepresentation of the GameState
     * @return
     */
    Bundle getBundle();

}
