package com.example.a2048mult.game.states;

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
//        animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.SAVE_REFERENCE, tile));
        animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.MOVE, tile));
//        animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.REMOVE, tile));
//        animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.SPAWN, tile));
//        animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.REMOVE,tile));
    }

    @Override
    public void addNewMerged(GameTile tile1, GameTile tile2, GameTile mergedTile) {
//        animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.SAVE_REFERENCE, tile1));
//        animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.SAVE_REFERENCE, tile2));
//        animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.MOVE, tile1));
//        animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.MOVE, tile2));
//        animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.REMOVE,tile1));
//        animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.REMOVE, tile2));
//        animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.SPAWN, mergedTile));
        GameTile placeholder = tile1;
        animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.MERGE, placeholder));
        mergeQueue.add(new GameTile[]{tile1, tile2, mergedTile});
    }

    @Override
    public void addRemoved(GameTile tile) {
        animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.REMOVE, tile));
    }

    @Override
    public PlayfieldTurnAnimTuple pollNextAnimation() {

        PlayfieldTurnAnimTuple anim = animationQueue.poll();
        if (anim != null && anim.type == PlayfieldTurnAnimationType.MERGE) {
            GameTile[] tiles = mergeQueue.poll();
            animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.SAVE_REFERENCE, tiles[0]));
            animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.SAVE_REFERENCE, tiles[1]));
            animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.MOVE, tiles[0]));
            animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.MOVE, tiles[1]));
            animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.REMOVE, tiles[0]));
            animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.REMOVE, tiles[1]));
            animationQueue.add(new PlayfieldTurnAnimTuple(PlayfieldTurnAnimationType.SPAWN, tiles[2]));

            return animationQueue.poll();
        }

        return anim;
    }

    @Override
    public void removeLastMoveAnimation() {
        if (animationQueue.size() > 0
                && ((PlayfieldTurnAnimTuple) (animationQueue.stream()
                .skip(animationQueue.size() - 1)
                .toArray()[0])).type == PlayfieldTurnAnimationType.MOVE) {

//            Queue<PlayfieldTurnAnimTuple> newAnimationQueue = new PriorityQueue<>();
//            animationQueue.stream()
//                    .limit(animationQueue.size() - 1)
//                    .forEach(newAnimationQueue::add);
//            this.animationQueue = newAnimationQueue;
        }
    }


}
