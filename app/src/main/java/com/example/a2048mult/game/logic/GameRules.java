package com.example.a2048mult.game.logic;

/**
 * specification for rules for the game
 */
public interface GameRules {

    /**
     * applies the rules when moving left
     */
    void applyMoveLeft();
    /**
     * applies the rules when moving right
     */
    void applyMoveRight();
    /**
     * applies the rules when moving up
     */
    void applyMoveUp();
    /**
     * applies the rules when moving down
     */
    void applyMoveDown();
}
