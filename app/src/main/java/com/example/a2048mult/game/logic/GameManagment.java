package com.example.a2048mult.game.logic;

/**
 * This manages the general GameState
 * and round/rurn logic --> Random events can be triggered, based on round and turn counts
 */
public interface GameManagment {

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
