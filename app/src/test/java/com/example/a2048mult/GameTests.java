package com.example.a2048mult;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.a2048mult.game.logic.GameRules;
import com.example.a2048mult.game.states.GameTile;
import com.example.a2048mult.game.states.Player;
import com.example.a2048mult.game.states.PlayerImpl;
import com.example.a2048mult.game.states.PlayfieldStateImpl;
import com.example.a2048mult.game.states.PlayfieldTurnAnimTuple;
import com.example.a2048mult.game.states.PlayfieldTurnAnimationType;

public class GameTests {

    @Test
    public void initGameTest(){
        Player player = new PlayerImpl("Max Mustermann", 0, new PlayfieldStateImpl());
        GameRules.initGame(player);
        int counter = 0;
        for (int[] row :player.getPlayfieldState().getField()) {
            for (int value: row) {
                assertTrue(value>=0 && value <=2);
                if(value>0){
                    counter++;
                }
            }
        }
        assertEquals(2, counter);
    }

    @Test
    public void calculateAndMoveTest(){
        Player player = new PlayerImpl("Max Mustermann", 0, new PlayfieldStateImpl());
        player.getPlayfieldState().setField(new int[][]{{1,1,0,0},
                                                        {0,1,0,0},
                                                        {0,0,1,0},
                                                        {0,0,0,0}});
        int[][] compare ={  {0,0,0,0},
                            {0,0,0,0},
                            {0,0,0,0},
                            {1,2,1,0}};//probable outcome without new spawned tiles
        GameRules.moveDown(player);
        PlayfieldTurnAnimTuple<PlayfieldTurnAnimationType, GameTile> pt = player.getPlayfieldTurn().pollNextAnimation();      //gets all registered changes from calculateMove and similar methods and prints them for manual debugging
        while(pt != null){
            System.out.println(((PlayfieldTurnAnimationType)pt.type).name() + " X:" + ((GameTile)pt.tile).getNewX() + " Y:" + ((GameTile)pt.tile).getNewY() + " Level" + ((GameTile)pt.tile).getLevel());
            pt = player.getPlayfieldTurn().pollNextAnimation();
        }
        assertEquals(2,player.getPlayfieldState().getTile(3,1));
    }
}
