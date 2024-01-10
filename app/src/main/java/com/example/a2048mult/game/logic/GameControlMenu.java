package com.example.a2048mult.game.logic;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.a2048mult.game.states.LobbySettings;

public interface GameControlMenu {

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
    void setLobbySettings(LobbySettings lobbySettings);
}
