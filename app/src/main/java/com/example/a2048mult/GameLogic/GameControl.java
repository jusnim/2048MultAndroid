package com.example.a2048mult.GameLogic;

/**
 * This interface allows to communicate between UI and Gamelogic
 * only events that the player can trigger are here
 */
public interface GameControl{

    /**
     * returns Gamestate when moving left
     * @return Gamestate , so it can be processed by the UI
     */
    GameState moveLeft();

    /**
     * returns Gamestate when moving right
     * @return Gamestate , so it can be processed by the UI
     */
    GameState moveRight();
    /**
     * returns Gamestate when moving up
     * @return Gamestate , so it can be processed by the UI
     */
    GameState moveUp();
    /**
     * returns Gamestate when moving down
     * @return Gamestate , so it can be processed by the UI
     */
    GameState moveDown();

}
