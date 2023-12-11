package com.example.a2048mult;

public interface GameDraw {
    /**
     * notifies GameView, that something change in GameState, so Gameview can retrieve it
     */
    void notifyChangeInGameState();
}
