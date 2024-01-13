package com.example.a2048mult.game.states;

import java.io.Serializable;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class PlayfieldTurnImpl implements PlayfieldTurn, Serializable {

    private Queue<PlayfieldTurnAnimTuple> animationQueue = new PriorityQueue<>();
    @Override

    public void addNewSpawned(GameTile tile) {
        animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.SPAWN, tile));
    }

    @Override
    public void addNewMove(GameTile tile) {
        animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.MOVE, tile));
    }

    @Override
    public void addNewMerged(GameTile tile1, GameTile tile2, GameTile mergedTile) {
        animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.MOVE, tile1));
        animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.MOVE, tile2));
//        animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.REMOVE,tile1));
//        animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.REMOVE, tile2));
        animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.SPAWN, mergedTile));
//        animationQueue.add(new PlayfieldTurnAnimTuple<>(PlayfieldTurnAnimationType.MERGE, new GameTile[]{tile1,tile2,mergedTile}));
    }

    @Override
    public void addRemoved(GameTile tile) {
        animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.REMOVE, tile));
    }

    @Override
    public PlayfieldTurnAnimTuple pollNextAnimation() {
        return animationQueue.poll();
    }




}
