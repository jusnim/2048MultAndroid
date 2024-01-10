package com.example.a2048mult.game.states;

import java.io.Serializable;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PlayfieldTurnImpl implements PlayfieldTurn, Serializable {

    private Queue<PlayfieldTurnAnimTuple<PlayfieldTurnAnimationType, GameTile>> animationQueue = new ConcurrentLinkedQueue<>();
    @Override

    public void addNewSpawned(GameTile tile) {
        animationQueue.add(new PlayfieldTurnAnimTuple<>(PlayfieldTurnAnimationType.SPAWN, tile));
    }

    @Override
    public void addNewMove(GameTile tile) {
        animationQueue.add(new PlayfieldTurnAnimTuple<>(PlayfieldTurnAnimationType.MOVE, tile));
    }

    @Override
    public void addNewMerged(GameTile tile1, GameTile tile2, GameTile mergedTile) {
        PlayfieldTurn.super.addNewMerged(tile1, tile2, mergedTile);
    }

    @Override
    public void addRemoved(GameTile tile) {
        animationQueue.add(new PlayfieldTurnAnimTuple<>(PlayfieldTurnAnimationType.REMOVE, tile));
    }

    @Override
    public PlayfieldTurnAnimTuple<PlayfieldTurnAnimationType, GameTile> pollNextAnimation() {
        return animationQueue.poll();
    }




}
