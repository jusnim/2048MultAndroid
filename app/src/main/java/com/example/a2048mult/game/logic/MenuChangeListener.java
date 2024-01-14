package com.example.a2048mult.game.logic;

public interface MenuChangeListener {
    /**
     * sends a signal, so MenuView can retrieve new Changes In Lobby
     */
    void notifyChangeInLobby();
}
