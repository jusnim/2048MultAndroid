package com.example.a2048mult.game.logic;

/**
 * gives and recieves information about the current lobby and exchanges them with MenuView
 */
public interface LobbySettings {
    /**
     * returns the number of Players, already joined in the Lobby
     * @return playernum
     */
    int getPlayerNum();

    /**
     * returns the list of player already joined the lobby
     */
    Player[] player();

    /**
     * gets the leader of the current Lobby
     * @return Lobbyleader
     */
    Player getLeader();

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

}
