package com.example.a2048mult.game.logic;

import android.util.Log;

import com.example.a2048mult.game.states.GameTile;
import com.example.a2048mult.game.states.GameTileImpl;
import com.example.a2048mult.game.states.MoveType;
import com.example.a2048mult.game.states.Player;
import com.example.a2048mult.game.states.PlayfieldState;

import java.util.Random;

public class GameRules {
    public static void moveUp(Player player) {
        int[][] beforeField = copyFieldFromPlayer(player);
        rotateAntiClockwise(player.getPlayfieldState());
        rotateAntiClockwise(player.getPlayfieldState());
        rotateAntiClockwise(player.getPlayfieldState());
        move(player);
        rotateAntiClockwise(player.getPlayfieldState());
        calculateMove(player, beforeField, copyFieldFromPlayer(player), MoveType.UP);
    }

    public static void moveDown(Player player) {
        Log.e("!", "rules");
        int[][] beforeField = copyFieldFromPlayer(player);
        rotateAntiClockwise(player.getPlayfieldState());
        move(player);
        rotateAntiClockwise(player.getPlayfieldState());
        rotateAntiClockwise(player.getPlayfieldState());
        rotateAntiClockwise(player.getPlayfieldState());
        calculateMove(player, beforeField, copyFieldFromPlayer(player), MoveType.DOWN);
    }

    public static void moveLeft(Player player) {
        int[][] beforeField = copyFieldFromPlayer(player);
        move(player);
        calculateMove(player, beforeField, copyFieldFromPlayer(player), MoveType.LEFT);
    }

    public static void moveRight(Player player) {
        int[][] beforeField = copyFieldFromPlayer(player);
        rotateAntiClockwise(player.getPlayfieldState());
        rotateAntiClockwise(player.getPlayfieldState());
        move(player);
        rotateAntiClockwise(player.getPlayfieldState());
        rotateAntiClockwise(player.getPlayfieldState());
        calculateMove(player, beforeField, copyFieldFromPlayer(player), MoveType.RIGHT);
    }

    public static boolean spawnTile(Player player) {
        int[][] field = player.getPlayfieldState().getField();

        boolean freeSpaceForNewTile = false;
        int[][] freeSpaces = new int[field.length * field[0].length][2];
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
            //TODO PLayer Variable verloren dafuer Methode wird void
            return false; // spiel verloren
        }

        Random rd = new Random();
        int rmdPos = rd.nextInt(freeSpacesIndex);

        int tileValue = 1;
        if (Math.random() > 0.8)
            tileValue = 2;
//        player.getPlayfieldTurn().addNewSpawned(new GameTileImpl(freeSpaces[rmdPos][0], freeSpaces[rmdPos][1], tileValue));
        // TODO change

//        player.getPlayfieldTurn().addNewSpawned(new GameTileImpl(0, 0, tileValue));
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

//        PlayfieldTurn playfieldTurn = new PlayfieldTurnImpl();
//        GameTile gameTile = new GameTileImpl(0,0,2,0,1);
//        playfieldTurn.addNewMove(gameTile);
//        player.setPlayfieldTurn(playfieldTurn);
        // spielfeld einfügen
        playfieldState.setField(neueListe);
        GameRules.spawnTile(player);
    }

    private static void rotateAntiClockwise(PlayfieldState playfieldState) {
        int[][] field = playfieldState.getField();
        int[][] output = new int[playfieldState.getFieldSizeX()][playfieldState.getFieldSizeY()];
        for (int y = 0; y < playfieldState.getFieldSizeY(); y++) {
            for (int x = 0; x < playfieldState.getFieldSizeX(); x++) {
                output[x][y] = field[playfieldState.getFieldSizeY() - y - 1][x];
            }
        }
        playfieldState.setField(output);
    }


    public static void initGame(Player player) {
        GameRules.spawnTile(player);
        GameRules.spawnTile(player);
    }


    private static void calculateMove(Player player, int[][] before, int[][] after, MoveType moveType) {
        switch (moveType) {
            case UP:
                for (int x = 0; x < player.getPlayfieldState().getFieldSizeX(); x++) {
                    int offset = 0;
                    for (int y = 0; y < player.getPlayfieldState().getFieldSizeY(); y++) {
                        if (after[x][y] != 0 && before[x][y + offset] != after[x][y] && y + offset < player.getPlayfieldState().getFieldSizeY()) {   //find changed Tile
                            int old1 = y + offset;                                               //find old coordinate from first tile
                            while (before[x][old1] == 0) {
                                old1++;
                                offset++;
                            }
                            before[x][old1] = 0;                                        //remove first coordinate to find second
                            int old2 = old1 + 1;                                        //find old coordinate from second tile
                            while (before[x][old2] == 0) {
                                old2++;
                                offset++;
                            }
                            offset++;
                            int oldValue = before[x][old2];
                            before[x][old2] = 0;                                        //remove second coordinate to find possible next
                            player.getPlayfieldTurn().addNewMerged(new GameTileImpl(x, old1, x, y, oldValue), new GameTileImpl(x, old2, x, y, oldValue), new GameTileImpl(x, y, after[x][y]));
                            after[x][y] = 0;
                        }
                    }
                }
                break;
            case DOWN:
                for (int x = 0; x < player.getPlayfieldState().getFieldSizeX(); x++) {
                    int offset = 0;                                                              //offset is always positive
                    for (int y = player.getPlayfieldState().getFieldSizeY() - 1; y >= 0; y--) {
                        if (after[x][y] != 0 && before[x][y - offset] != after[x][y] && y - offset >= 0) {            //find changed Tile
                            int old1 = y;                                               //find old coordinate from first tile
                            while (before[x][old1] == 0) {
                                old1--;
                                offset++;
                            }
                            before[x][old1] = 0;                                        //remove first coordinate to find second
                            int old2 = old1 - 1;                                        //find old coordinate from second tile
                            while (before[x][old2] == 0) {
                                old2--;
                                offset++;
                            }
                            offset++;
                            int oldValue = before[x][old2];
                            before[x][old2] = 0;                                        //remove second coordinate to find possible next
                            player.getPlayfieldTurn().addNewMerged(new GameTileImpl(x, old1, x, y, oldValue), new GameTileImpl(x, old2, x, y, oldValue), new GameTileImpl(x, y, after[x][y]));
                            after[x][y] = 0;
                        }
                    }
                }
                break;
            case LEFT:
                for (int y = 0; y < player.getPlayfieldState().getFieldSizeY(); y++) {
                    int offset = 0;
                    for (int x = 0; x < player.getPlayfieldState().getFieldSizeX(); x++) {
                        if (after[x][y] != 0 && before[x + offset][y] != after[x][y] && x + offset < player.getPlayfieldState().getFieldSizeX()) {            //find changed Tile
                            int old1 = x;                                               //find old coordinate from first tile
                            while (before[old1][y] == 0) {
                                old1++;
                                offset++;
                            }
                            before[old1][y] = 0;                                        //remove first coordinate to find second
                            int old2 = old1 + 1;                                        //find old coordinate from second tile
                            while (before[old2][y] == 0) {
                                old2++;
                                offset++;
                            }
                            offset++;
                            int oldValue = before[old2][y];
                            before[old2][y] = 0;                                        //remove second coordinate to find possible next
                            player.getPlayfieldTurn().addNewMerged(new GameTileImpl(old1, y, x, y, oldValue), new GameTileImpl(old2, y, x, y, oldValue), new GameTileImpl(x, y, after[x][y]));
                            after[x][y] = 0;
                        }
                    }
                }
                break;
            case RIGHT:
                for (int y = 0; y < player.getPlayfieldState().getFieldSizeY(); y++) {
                    int offset = 0;
                    for (int x = player.getPlayfieldState().getFieldSizeX(); x >= 0; x--) {
                        if (after[x][y] != 0 && before[x - offset][y] != after[x][y] && x - offset >= 0) {            //find changed Tile
                            int old1 = x;                                               //find old coordinate from first tile
                            while (before[old1][y] == 0) {
                                old1--;
                                offset++;
                            }
                            before[old1][y] = 0;                                        //remove first coordinate to find second
                            int old2 = old1 - 1;                                        //find old coordinate from second tile
                            while (before[old2][y] == 0) {
                                old2--;
                                offset++;
                            }
                            offset++;
                            int oldValue = before[old2][y];
                            before[old2][y] = 0;                                        //remove second coordinate to find possible next
                            player.getPlayfieldTurn().addNewMerged(new GameTileImpl(old1, y, x, y, oldValue), new GameTileImpl(old2, y, x, y, oldValue), new GameTileImpl(x, y, after[x][y]));
                            after[x][y] = 0;
                        }
                    }
                }
                break;
        }
    }

    private static int[][] copyFieldFromPlayer(Player player) {
        int[][] field = player.getPlayfieldState().getField();
        int[][] output = new int[player.getPlayfieldState().getFieldSizeX()][player.getPlayfieldState().getFieldSizeY()];
        for (int x = 0; x < player.getPlayfieldState().getFieldSizeX(); x++) {
            if (player.getPlayfieldState().getFieldSizeY() >= 0)
                System.arraycopy(field[x], 0, output[x], 0, player.getPlayfieldState().getFieldSizeY());
        }
        return output;
    }

}
