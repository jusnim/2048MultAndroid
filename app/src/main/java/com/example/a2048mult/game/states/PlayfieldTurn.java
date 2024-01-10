package com.example.a2048mult.game.states;

/**
 * represents the data structure that saves the change of a PlayfieldState after each turn
 * it gives also instruction for animations
 */
public interface PlayfieldTurn {

    /**
     * adds a spawned tile to the object
     * @param tile
     */
    void addNewSpawned(GameTile tile);

    /**
     * adds a tileMove to the object
     * @param tile
     */
    void addNewMove(GameTile tile);

    /**
     * adds a merging of two tile to the object
     * @param tile1
     * @param tile2
     * @param mergedTile
     */
    default void addNewMerged(GameTile tile1, GameTile tile2, GameTile mergedTile){
        addNewMove(tile1);
        addNewMove(tile2);
        addRemoved(tile1);
        addRemoved(tile2);
        addNewSpawned(mergedTile);
    };

    /**
     * adds a remove to the object
     * @param tile
     */
    void addRemoved(GameTile tile);

    /**
     * getter for next animation that shall be done
     *
     * @return
     */
    PlayfieldTurnAnimTuple<PlayfieldTurnAnimationType, GameTile> pollNextAnimation();

}
