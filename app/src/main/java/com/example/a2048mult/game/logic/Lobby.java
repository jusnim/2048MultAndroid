package com.example.a2048mult.game.logic;

public interface Lobby {
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
}
