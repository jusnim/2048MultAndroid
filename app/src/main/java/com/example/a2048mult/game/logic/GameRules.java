package com.example.a2048mult.game.logic;

import android.util.Log;

import java.util.Random;

public class GameRules {
    public static void moveUp(PlayfieldState game) {
        rotateAntiClockwise(game);
        rotateAntiClockwise(game);
        rotateAntiClockwise(game);
        move(game);
        rotateAntiClockwise(game);
    }

    public static void moveDown(PlayfieldState game) {
        rotateAntiClockwise(game);
        move(game);
        rotateAntiClockwise(game);
        rotateAntiClockwise(game);
        rotateAntiClockwise(game);
    }

    public static void moveLeft(PlayfieldState game) {
        move(game);
    }

    public static void moveRight(PlayfieldState game) {
        rotateAntiClockwise(game);
        rotateAntiClockwise(game);
        move(game);
        rotateAntiClockwise(game);
        rotateAntiClockwise(game);
    }

    public static boolean spawnTile(PlayfieldState game) {
        int[][] field = game.getField();
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

        game.setTile(freeSpaces[rmdPos][0], freeSpaces[rmdPos][1], tileValue);
        return true;
    }

    public static boolean spawnTile2(PlayfieldState game) {
        //TODO: random
        double[][] random = new double[game.getFieldSizeX()][game.getFieldSizeY()];
        int[] highest = new int[2];
        double highestValue = 0;
        for (int i = 0; i < game.getFieldSizeX(); i++) {
            for (int j = 0; j < game.getFieldSizeY(); j++) {
                random[i][j] = Math.random();
                if (random[i][j] > highestValue) {
                    highestValue = random[i][j];
                    highest[0] = i;
                    highest[1] = j;
                }
            }
        }

        while (game.getTile(highest[0], highest[1]) != 0) {
            random[highest[0]][highest[1]] = 0;
            highestValue = 0;
            for (int i = 0; i < game.getFieldSizeX(); i++) {
                for (int j = 0; j < game.getFieldSizeY(); j++) {
                    if (random[i][j] > highestValue) {
                        highestValue = random[i][j];
                        highest[0] = i;
                        highest[1] = j;
                    }
                }
            }
        }
        if (highestValue == 0)
            return false; //spiel verloren
        int tileValue = 1;
        if (Math.random() > 0.8)
            tileValue = 2;
        game.setTile(highest[0], highest[1], tileValue);
        return true;
    }

    public static void move(PlayfieldState game) {    //move links als standard
        int[][] neueListe = new int[game.getFieldSizeX()][game.getFieldSizeY()];

        //spielfeld nach move erstellen
        for (int x = 0; x < game.getFieldSizeX(); x++) {
            int positionNeueListe = 0;
            int zahl = game.getTile(x, 0);
            for (int y = 1; y < game.getFieldSizeY(); y++) {
                if (zahl == 0) {
                    zahl = game.getTile(x, y);
                } else if (zahl == game.getTile(x, y)) {
                    neueListe[x][positionNeueListe] = zahl + 1;
                    zahl = 0;
                    positionNeueListe++;
                } else if (game.getTile(x, y) != 0) {
                    neueListe[x][positionNeueListe] = zahl;
                    zahl = game.getTile(x, y);
                    positionNeueListe++;
                }
            }
            neueListe[x][positionNeueListe] = zahl;
        }

        //spielfeld einfügen
        game.setField(neueListe);
    }

    public static void rotateAntiClockwise(PlayfieldState game) {
        int[][] field = game.getField();
        int[][] output = new int[game.getFieldSizeX()][game.getFieldSizeY()];
        for (int y = 0; y < game.getFieldSizeY(); y++) {
            for (int x = 0; x < game.getFieldSizeX(); x++) {
                output[x][y] = field[game.getFieldSizeY() - y - 1][x];
            }
        }
        game.setField(output);
    }

}
