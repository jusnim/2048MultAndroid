package com.example.a2048mult.game.logic;

import androidx.fragment.app.Fragment;

import com.example.a2048mult.game.states.ChangeLobbyConfigurations;

public interface GameMenuControl {

    /**
     * requires set LobbySettings
     * initlize GameState and open GameUI of GameView
     * @param fragment - fragment
     * @param resID - representation from navigation resID e.g. from singleplayerToGame
     * @throws IllegalStateException - when LobbySettings isn't set
     */
    void startGame(Fragment fragment, int resID) throws IllegalStateException;

    /**
     * setter for LobbySettings
     * @param lobbySettings
     */
    void setLobbySettings(ChangeLobbyConfigurations lobbySettings);
}
