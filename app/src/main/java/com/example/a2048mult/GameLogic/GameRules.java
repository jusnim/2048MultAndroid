package com.example.a2048mult.GameLogic;

public interface GameRules {

    /**
     * applies the rules when moving left
     */
    PlayfieldState applyMoveLeft(PlayfieldState playfieldState);
    /**
     * applies the rules when moving right
     */
    PlayfieldState applyMoveRight(PlayfieldState playfieldState);
    /**
     * applies the rules when moving up
     */
    PlayfieldState applyMoveUp(PlayfieldState playfieldState);
    /**
     * applies the rules when moving down
     */
    PlayfieldState applyMoveDown(PlayfieldState playfieldState);
}
