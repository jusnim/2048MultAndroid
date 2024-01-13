package com.example.a2048mult.game.states;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;

public class PlayfieldTurnImpl implements PlayfieldTurn, Serializable {

    private Queue<PlayfieldTurnAnimTuple> animationQueue = new PriorityQueue<>();
    private Queue<GameTile[]> mergeQueue = new ArrayDeque<>();

    @Override

    public void addNewSpawned(GameTile tile) {
        animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.SPAWN, tile));
    }

    @Override
    public void addNewMove(GameTile tile) {
//        animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.SAVE_REFERENCE, tile));
        animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.MOVE, tile));
//        animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.REMOVE,tile));
    }

    @Override
    public void addNewMerged(GameTile tile1, GameTile tile2, GameTile mergedTile) {
        mergeQueue.add(new GameTile[]{tile1, tile2, mergedTile});
    }

    @Override
    public void addRemoved(GameTile tile) {
        animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.REMOVE, tile));
    }

    @Override
    public PlayfieldTurnAnimTuple pollNextAnimation() {

//        PlayfieldTurnAnimTuple anim = animationQueue.poll();
//        Log.e("!","cfsd");
//        Log.e("!", String.valueOf(anim.type));
//        if (anim != null && anim.type == PlayfieldTurnAnimationType.MERGE) {
//            Log.e("!","cfsd");
//            GameTile[] tiles = mergeQueue.poll();
//            animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.SAVE_REFERENCE, tiles[0]));
//            animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.SAVE_REFERENCE, tiles[1]));
//            animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.MOVE, tiles[0]));
//            animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.MOVE, tiles[1]));
//            animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.REMOVE, tiles[0]));
//            animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.REMOVE, tiles[1]));
//            animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.SPAWN, tiles[2]));
//
//            return animationQueue.poll();
//        }

        return animationQueue.poll();
    }

    @Override
    public void removeLastAnimation() {
        if (animationQueue.size() > 0) {
            Queue<PlayfieldTurnAnimTuple> newAnimationQueue = new PriorityQueue<>();
            animationQueue.stream()
                    .limit(animationQueue.size() - 1)
                    .forEach(newAnimationQueue::add);
            this.animationQueue = newAnimationQueue;
        }
    }


}
