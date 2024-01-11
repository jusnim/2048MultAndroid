package com.example.a2048mult;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.util.Log;

import com.example.a2048mult.game.logic.GameRules;
import com.example.a2048mult.game.states.GameTile;
import com.example.a2048mult.game.states.Player;
import com.example.a2048mult.game.states.PlayerImpl;
import com.example.a2048mult.game.states.PlayfieldStateImpl;
import com.example.a2048mult.game.states.PlayfieldTurnAnimTuple;
import com.example.a2048mult.game.states.PlayfieldTurnAnimationType;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GameTests {

    @Test
    public void initGameTest() {
        Player player = new PlayerImpl("Max Mustermann", 0, new PlayfieldStateImpl());
        GameRules.initGame(player);
        int counter = 0;
        for (int[] row : player.getPlayfieldState().getField()) {
            for (int value : row) {
                assertTrue(value >= 0 && value <= 2);
                if (value > 0) {
                    counter++;
                }
            }
        }
        assertEquals(2, counter);
    }

    @Test
    public void calculateAndMoveTest() {
        Player player = new PlayerImpl("Max Mustermann", 0, new PlayfieldStateImpl());
        player.getPlayfieldState().setField(new int[][]{
                {1, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 0}});
        int[][] compare = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {1, 2, 1, 0}};//probable outcome without new spawned tiles

        for (int[] line : player.getPlayfieldState().getField()) {
            for (int level: line) {
                System.out.print(level + " ");
//                Log.e("!", String.valueOf(level));
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();


        GameRules.moveDown(player);


        List<PlayfieldTurnAnimTuple<PlayfieldTurnAnimationType, GameTile[]>> listPts = new ArrayList<>();
        //gets all registered changes from calculateMove and similar methods and prints them for manual debugging
        PlayfieldTurnAnimTuple<PlayfieldTurnAnimationType, GameTile[]> pt =
                player.getPlayfieldTurn().pollNextAnimation();

        listPts.add(pt);
        while (pt != null) {
            pt = player.getPlayfieldTurn().pollNextAnimation();
            listPts.add(pt);
        }

        for (int[] line : player.getPlayfieldState().getField()) {
            for (int level: line) {
                System.out.print(level + " ");
//                Log.e("!", String.valueOf(level));
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();


        for (int[] line : compare) {
            for (int level: line) {
                System.out.print(level + " ");
//                Log.e("!", String.valueOf(level));
            }
            System.out.println();
        }
//        assertArrayEquals(compare, player.getPlayfieldState().getField());
    }
}
