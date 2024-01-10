package com.example.a2048mult.game.logic;

import android.util.Log;

import com.example.a2048mult.game.states.GameTile;
import com.example.a2048mult.game.states.GameTileImpl;
import com.example.a2048mult.game.states.Player;
import com.example.a2048mult.game.states.PlayfieldState;
import com.example.a2048mult.game.states.PlayfieldTurn;
import com.example.a2048mult.game.states.PlayfieldTurnImpl;

import java.util.Random;

public class GameRules {
    public static void moveUp(Player player) {
        rotateAntiClockwise(player.getPlayfieldState());
        rotateAntiClockwise(player.getPlayfieldState());
        rotateAntiClockwise(player.getPlayfieldState());
        move(player);
        rotateAntiClockwise(player.getPlayfieldState());
    }

    public static void moveDown(Player player) {
        Log.e("!","rules");
        rotateAntiClockwise(player.getPlayfieldState());
        move(player);
        rotateAntiClockwise(player.getPlayfieldState());
        rotateAntiClockwise(player.getPlayfieldState());
        rotateAntiClockwise(player.getPlayfieldState());
    }

    public static void moveLeft(Player player) {
        move(player);
    }

    public static void moveRight(Player player) {
        rotateAntiClockwise(player.getPlayfieldState());
        rotateAntiClockwise(player.getPlayfieldState());
        move(player);
        rotateAntiClockwise(player.getPlayfieldState());
        rotateAntiClockwise(player.getPlayfieldState());
    }

    public static boolean spawnTile(Player player) {
        // TODO add PLayfieldTurn Spawn
        int[][] field = player.getPlayfieldState().getField();
        Log.e("!", String.valueOf(field[0][0]));

        boolean freeSpaceForNewTile = false;
        int[][] freeSpaces = new int[field.length* field[0].length][2];
        int freeSpacesIndex = 0;
        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field[0].length; y++) {
                if (field[x][y] == 0) {
                    freeSpaceForNewTile = true;
                    freeSpaces[freeSpacesIndex] = new int[]{x, y};
                    freeSpacesIndex++;
                }
            }
        }

        if (!freeSpaceForNewTile) {
            return false; // spiel verloren
        }

        Random rd = new Random();
        int rmdPos = rd.nextInt(freeSpacesIndex + 1);

        int tileValue = 1;
        if (Math.random() > 0.8)
            tileValue = 2;

        player.getPlayfieldState().setTile(freeSpaces[rmdPos][0], freeSpaces[rmdPos][1], tileValue);
        return true;
    }

//    public static boolean spawnTile2(Player player) {
//        //TODO: random
//        double[][] random = new double[game.getFieldSizeX()][game.getFieldSizeY()];
//        int[] highest = new int[2];
//        double highestValue = 0;
//        for (int i = 0; i < game.getFieldSizeX(); i++) {
//            for (int j = 0; j < game.getFieldSizeY(); j++) {
//                random[i][j] = Math.random();
//                if (random[i][j] > highestValue) {
//                    highestValue = random[i][j];
//                    highest[0] = i;
//                    highest[1] = j;
//                }
//            }
//        }
//
//        while (game.getTile(highest[0], highest[1]) != 0) {
//            random[highest[0]][highest[1]] = 0;
//            highestValue = 0;
//            for (int i = 0; i < game.getFieldSizeX(); i++) {
//                for (int j = 0; j < game.getFieldSizeY(); j++) {
//                    if (random[i][j] > highestValue) {
//                        highestValue = random[i][j];
//                        highest[0] = i;
//                        highest[1] = j;
//                    }
//                }
//            }
//        }
//        if (highestValue == 0)
//            return false; //spiel verloren
//        int tileValue = 1;
//        if (Math.random() > 0.8)
//            tileValue = 2;
//        game.setTile(highest[0], highest[1], tileValue);
//        return true;
//    }

    private static void move(Player player) {    //move links als standard
        // TODO add PLayfieldTurn Move
        int[][] neueListe = new int[player.getPlayfieldState().getFieldSizeX()][player.getPlayfieldState().getFieldSizeY()];

        //spielfeld nach move erstellen
        PlayfieldState playfieldState = player.getPlayfieldState();
        for (int x = 0; x < playfieldState.getFieldSizeX(); x++) {
            int positionNeueListe = 0;
            int zahl = playfieldState.getTile(x, 0);
            for (int y = 1; y < playfieldState.getFieldSizeY(); y++) {
                if (zahl == 0) {
                    zahl = playfieldState.getTile(x, y);
                } else if (zahl == playfieldState.getTile(x, y)) {
                    neueListe[x][positionNeueListe] = zahl + 1;
                    zahl = 0;
                    positionNeueListe++;
                } else if (playfieldState.getTile(x, y) != 0) {
                    neueListe[x][positionNeueListe] = zahl;
                    zahl = playfieldState.getTile(x, y);
                    positionNeueListe++;
                }
            }
            neueListe[x][positionNeueListe] = zahl;
        }

        PlayfieldTurn playfieldTurn = new PlayfieldTurnImpl();
        GameTile gameTile = new GameTileImpl(0,0,1);
        playfieldTurn.addNewSpawned(gameTile);
        player.setPlayfieldTurn(playfieldTurn);
        // spielfeld einfügen
        playfieldState.setField(neueListe);
    }

    public static void rotateAntiClockwise(PlayfieldState playfieldState) {
        int[][] field = playfieldState.getField();
        int[][] output = new int[playfieldState.getFieldSizeX()][playfieldState.getFieldSizeY()];
        for (int y = 0; y < playfieldState.getFieldSizeY(); y++) {
            for (int x = 0; x < playfieldState.getFieldSizeX(); x++) {
                output[x][y] = field[playfieldState.getFieldSizeY() - y - 1][x];
            }
        }
        playfieldState.setField(output);
    }

}
