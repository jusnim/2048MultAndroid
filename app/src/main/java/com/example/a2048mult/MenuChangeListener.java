package com.example.a2048mult;

public interface MenuChangeListener {
    /**
     * sends a signal, so MenuView can retrieve new Changes In Lobby
     */
    public void notifyChangeInLobby();
}