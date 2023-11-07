package com.example.a2048mult.GameLogic;

/**
 * This manages the general GameState
 * and round/rurn logic --> Random events can be triggered, based on round and turn counts
 */
public interface GameManagment {

    /**
     * getter for TurnCount
     * @return int, total TurnCount e.g. 46
     */
    int getTurnCount();

    /**
     * increments TurnCount, so 46 --> 47
     */
    void incrementTurnCount();

    /**
     * applies move left on the GameState / allPlayfields and triggers events after each turn
     * @return GameState
     */
    void onMoveLeft();
    /**
     * applies move right on the GameState / allPlayfields and triggers events after each turn
     * @return GameState
     */
    void onMoveRight();
    /**
     * applies move up on the GameState / allPlayfields and triggers events after each turn
     * @return GameState
     */
    void onMoveUp();
    /**
     * applies move down on the GameState / allPlayfields and triggers events after each turn
     * @return GameState
     */
    void onMoveDown();

}
