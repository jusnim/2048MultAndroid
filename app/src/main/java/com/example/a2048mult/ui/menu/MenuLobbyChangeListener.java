package com.example.a2048mult.ui.menu;

public interface MenuLobbyChangeListener {
    /**
     * sends a signal, so MenuView can retrieve new Changes In Lobby
     */
    public void notifyChangeInLobby();
}