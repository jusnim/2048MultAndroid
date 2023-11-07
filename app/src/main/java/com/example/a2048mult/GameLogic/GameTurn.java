package com.example.a2048mult.GameLogic;

/**
 * represents the data structure that saves the change of GameState after each turn
 */
public interface GameTurn {
    GameTurn getGameTurn();
//    default void setGameTurn(){
//        clearTurn();
//        addNewSpawned();
//        addNewMove();
//        addNewMerged();
//    }

    void clearTurn();
    // int x, int y
    void addNewSpawned();

    // item.oldX, item.oldY item.newX, item.newY
    // item
    void addNewMove();

    default void addNewMerged(GameBlock block1, GameBlock block2){
        addNewMove();
        addNewMove();
        addRemoved();
        addRemoved();
        addNewSpawned();
    };
    void addRemoved();

}
