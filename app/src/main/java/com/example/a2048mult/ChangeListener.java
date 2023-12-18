package com.example.a2048mult;

public interface ChangeListener {
        /**
         * sends a signal, so GameView can retrieve new Changes from GameState
         */
        public void notifyChangeInGameState();
}
