package com.example.a2048mult.game.logic;

import android.util.Log;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class PlayfieldTurnImpl implements PlayfieldTurn, Serializable {

    private Queue<PlayfieldTurnAnimTuple<PlayfieldTurnAnimationTypes, GameTile>> animationQueue = new PriorityQueue<>();
    @Override

    public void addNewSpawned(GameTile tile) {
        animationQueue.add(new PlayfieldTurnAnimTuple<>(PlayfieldTurnAnimationTypes.SPAWN, tile));
    }

    @Override
    public void addNewMove(GameTile tile) {
        animationQueue.add(new PlayfieldTurnAnimTuple<>(PlayfieldTurnAnimationTypes.MOVE, tile));
    }

    @Override
    public void addNewMerged(GameTile tile1, GameTile tile2, GameTile mergedTile) {
        PlayfieldTurn.super.addNewMerged(tile1, tile2, mergedTile);
    }

    @Override
    public void addRemoved(GameTile tile) {
        animationQueue.add(new PlayfieldTurnAnimTuple<>(PlayfieldTurnAnimationTypes.REMOVE, tile));
    }

    @Override
    public PlayfieldTurnAnimTuple<PlayfieldTurnAnimationTypes, GameTile> pollNextAnimation() {
        return animationQueue.poll();
    }




}
