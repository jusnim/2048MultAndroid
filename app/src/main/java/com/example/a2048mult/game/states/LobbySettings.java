package com.example.a2048mult.game.states;

import java.io.Serializable;

/**
 * gives and recieves information about the current lobby and exchanges them with MenuView
 */
public interface LobbySettings extends Serializable {

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
    void addPlayer(Player player);
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
}
