package com.example.a2048mult.GameLogic;

/**
 * represents the data structure that saves the change of a PlayfieldState after each turn
 * it gives also instruction for animations
 */
public interface PlayfieldTurn {

    /**
     * adds a spawned block to the object
     * @param block
     */
    void addNewSpawned(GameBlock block);

    /**
     * adds a blockMove to the object
     * @param block
     */
    void addNewMove(GameBlock block);

    /**
     * adds a merging of two block to the object
     * @param block1
     * @param block2
     * @param mergedBlock
     */
    default void addNewMerged(GameBlock block1, GameBlock block2, GameBlock mergedBlock){
        addNewMove(block1);
        addNewMove(block2);
        addRemoved(block1);
        addRemoved(block2);
        addNewSpawned(mergedBlock);
    };

    /**
     * adds a remove to the object
     * @param block
     */
    void addRemoved(GameBlock block);

    /**
     * getter for the TurnInstructions, so the UI can animate properly
     */
    PlayfieldTurn getTurnInstructions();

}
