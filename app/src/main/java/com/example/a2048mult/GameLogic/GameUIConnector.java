package com.example.a2048mult.GameLogic;

/**
 * This interface allows to communicate between UI and Gamelogic
 * only events that the player can trigger are here
 */
public interface GameUIConnector {

    /**
     * returns GameTurn when moving left
     * @return GameTurn , so it can be processed by the UI
     */
    GameState moveLeft();

    /**
     * returns GameTurn when moving right
     * @return GameTurn , so it can be processed by the UI
     */
    GameState moveRight();
    /**
     * returns GameTurn when moving up
     * @return GameTurn , so it can be processed by the UI
     */
    GameState moveUp();
    /**
     * returns GameTurn when moving down
     * @return GameTurn , so it can be processed by the UI
     */
    GameState moveDown();

}
