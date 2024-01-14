package com.example.a2048mult.game.logic;

import android.util.Log;

import com.example.a2048mult.game.states.GameTile;
import com.example.a2048mult.game.states.GameTileImpl;
import com.example.a2048mult.game.states.MoveType;
import com.example.a2048mult.game.states.Player;
import com.example.a2048mult.game.states.PlayfieldState;
import com.example.a2048mult.game.states.PlayfieldTurn;

import java.util.Random;

public class GameRules {

    // merge two tiles in memory and queue all necessary animations
    private static void internalMerge(GameTile tile_view, int x_new, int y_new, Player player) {
//        player.getPlayfieldTurn().removeLastMoveAnimation();
        // TODO seems to not worked

        GameTile tile_merge_old;
        GameTile tile_merge_new;
        PlayfieldState field_state;

        field_state = player.getPlayfieldState();

        // tile we pulled
        tile_view.updateCoordinates(x_new, y_new);

        // merge in memory
        field_state.setTile(tile_view.getOldX(), tile_view.getOldY(), 0);
        field_state.setTile(x_new, y_new, tile_view.getLevel() + 1);

        // tile we merge our with
        tile_merge_old = new GameTileImpl(x_new, y_new, tile_view.getLevel());
        tile_merge_old.changeNewToOld();

        // new tile that will spawn
        tile_merge_new = new GameTileImpl(x_new, y_new, tile_view.getLevel() + 1);

        // add turn to queue
        player.getPlayfieldTurn().addNewMerged(tile_view, tile_merge_old, tile_merge_new);

        Log.e("!", "MERGED");
    }

    private static void internalMoveUp(PlayfieldState field_state, Player player) {
        int y;
        int x;
        int yy;
        int tile;
        GameTile tile_view;
        PlayfieldTurn playfield_turn;

        // move: iterate from top to bottom and from left to right
        for (y = 0; y < field_state.getFieldSizeY(); y++) {
            for (x = 0; x < field_state.getFieldSizeX(); x++) {

                // field is already at the upper walls, do nothing
                if (y == 0) {
                    continue;
                }

                // get tile, if its 0 there is no tile
                tile = field_state.getTile(x, y);
                if (tile == 0) {
                    continue;
                }

                // tile has space to move (ok i pull up)
                yy = y;
                while (field_state.getTile(x, yy - 1) == 0) {
                    if (--yy == 0) {
                        break;
                    }
                }

                // create a tile with the old x and y
                tile_view = new GameTileImpl(x, y, tile);

                // check if we are at the end of our long journey through the
                // play field or if we can merge our knowledge and the friends
                // we made on our way with another tile
                if (yy != 0 && field_state.getTile(x, yy - 1) == tile) {
                    GameRules.internalMerge(tile_view, x, yy - 1, player);
                    continue;
                }

                // if we haven't moved at all continue lol
                if (y == yy) {
                    continue;
                }

                // move tile in data
                field_state.setTile(x, yy, tile);
                field_state.setTile(x, y, 0);

                // update tile view
                tile_view.updateCoordinates(x, yy);

                // move animation (please let it end finally)
                playfield_turn = player.getPlayfieldTurn();
                playfield_turn.addNewMove(tile_view);

                Log.e("!", "MOVED ("
                        + Integer.toString(x) + " | "
                        + Integer.toString(y) + ") to ("
                        + Integer.toString(x) + " | "
                        + Integer.toString(yy) + ")"
                );
            }
        }
    }

    public static void moveUp(Player player) {
        PlayfieldState field_state;

        // obtain field state
        field_state = player.getPlayfieldState();

        //field_state.printField();

        // move or merge
        GameRules.internalMoveUp(field_state, player);

        //field_state.printField();
    }

    private static void internalMoveDown(PlayfieldState field_state, Player player) {
        int y;
        int x;
        int yy;
        int tile;
        GameTile tile_view;
        PlayfieldTurn playfield_turn;

        // move: iterate from bottom to top and from left to right
        for (y = field_state.getFieldSizeY() - 1; y > -1; y--) {
            for (x = 0; x < field_state.getFieldSizeX(); x++) {

                // field is already at the bottom walls, do nothing
                if (y == (field_state.getFieldSizeY() - 1)) {
                    continue;
                }

                // get tile, if its 0 there is no tile
                tile = field_state.getTile(x, y);
                if (tile == 0) {
                    continue;
                }

                // tile has space to move (pull it down)
                yy = y;
                while (field_state.getTile(x, yy + 1) == 0) {
                    if (++yy == field_state.getFieldSizeY() - 1) {
                        break;
                    }
                }

                // create a tile with the old x and y
                tile_view = new GameTileImpl(x, y, tile);

                // check if we are able to merge
                if (yy != field_state.getFieldSizeY() - 1 && field_state.getTile(x, yy + 1) == tile) {
                    GameRules.internalMerge(tile_view, x, yy + 1, player);
                    continue;
                }

                // if we haven't moved at all continue lol
                if (y == yy) {
                    continue;
                }

                // move tile in data
                field_state.setTile(x, yy, tile);
                field_state.setTile(x, y, 0);

                // update tile view
                tile_view.updateCoordinates(x, yy);

                // move animation (please let it end finally)
                playfield_turn = player.getPlayfieldTurn();
                playfield_turn.addNewMove(tile_view);

                Log.e("!", "MOVED ("
                        + Integer.toString(x) + " | "
                        + Integer.toString(y) + ") to ("
                        + Integer.toString(x) + " | "
                        + Integer.toString(yy) + ")"
                );
            }
        }
    }

    public static void moveDown(Player player) {
        PlayfieldState field_state;

        // obtain field state
        field_state = player.getPlayfieldState();

        //field_state.printField();

        // move or merge
        GameRules.internalMoveDown(field_state, player);

        //field_state.printField();
    }

    private static void internalMoveLeft(PlayfieldState field_state, Player player) {
        int y;
        int x;
        int xx;
        int tile;
        GameTile tile_view;
        PlayfieldTurn playfield_turn;

        // move: iterate from top to bottom and from left to right
        for (x = 0; x < field_state.getFieldSizeX(); x++) {
            for (y = 0; y < field_state.getFieldSizeY(); y++) {

                // field is already at the left wall, do nothing
                if (x == 0) {
                    continue;
                }

                // get tile, if its 0 there is no tile
                tile = field_state.getTile(x, y);
                if (tile == 0) {
                    continue;
                }

                // tile has space to move (pull it to the left)
                xx = x;
                while (field_state.getTile(xx - 1, y) == 0) {
                    if (--xx == 0) {
                        break;
                    }
                }

                // create a tile with the old x and y
                tile_view = new GameTileImpl(x, y, tile);

                // check if we are able to merge
                if (xx != 0 && field_state.getTile(xx - 1, y) == tile) {
                    GameRules.internalMerge(tile_view, xx - 1, y, player);
                    continue;
                }

                // if we haven't moved at all continue lol
                if (x == xx) {
                    continue;
                }

                // move tile in data
                field_state.setTile(xx, y, tile);
                field_state.setTile(x, y, 0);

                // update tile view
                tile_view.updateCoordinates(xx, y);

                // move animation (please let it end finally)
                playfield_turn = player.getPlayfieldTurn();
                playfield_turn.addNewMove(tile_view);
            }

        }
    }

    public static void moveLeft(Player player) {
        PlayfieldState field_state;

        // obtain field state
        field_state = player.getPlayfieldState();

        //field_state.printField();

        // move or merge
        GameRules.internalMoveLeft(field_state, player);

        //field_state.printField();
    }

    private static void internalMoveRight(PlayfieldState field_state, Player player) {
        int y;
        int x;
        int xx;
        int tile;
        GameTile tile_view;
        PlayfieldTurn playfield_turn;

        // move: iterate from top to bottom and from right to left
        for (x = field_state.getFieldSizeX() - 1; x >= 0; x--) {
            for (y = 0; y < field_state.getFieldSizeY(); y++) {

                // field is already at the right wall, do nothing
                if (x == field_state.getFieldSizeX() - 1) {
                    continue;
                }

                // get tile, if its 0 there is no tile
                tile = field_state.getTile(x, y);
                if (tile == 0) {
                    continue;
                }

                // tile has space to move (pull it to the right)
                xx = x;
                while (field_state.getTile(xx + 1, y) == 0) {
                    if (++xx == field_state.getFieldSizeX() - 1) {
                        break;
                    }
                }

                Log.e("!", "Tile could be moved to " + xx + " " + y);

                // create a tile with the old x and y
                tile_view = new GameTileImpl(x, y, tile);

                // check if we are able to merge
                if (xx != field_state.getFieldSizeX() - 1 && field_state.getTile(xx + 1, y) == tile) {
                    GameRules.internalMerge(tile_view, xx + 1, y, player);
                    continue;
                }

                // if we haven't moved at all continue lol
                if (x == xx) {
                    continue;
                }

                // move tile in data
                field_state.setTile(xx, y, tile);
                field_state.setTile(x, y, 0);

                // update tile view
                tile_view.updateCoordinates(xx, y);

                // move animation (please let it end finally)
                playfield_turn = player.getPlayfieldTurn();
                playfield_turn.addNewMove(tile_view);
            }

        }
    }

    public static void moveRight(Player player) {
        PlayfieldState field_state;

        // obtain field state
        field_state = player.getPlayfieldState();

        //field_state.printField();

        // move or merge
        GameRules.internalMoveRight(field_state, player);

        //field_state.printField();
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
            //TODO Player Variable verloren dafuer Methode wird void
            return false; // spiel verloren
        }

        Random rd = new Random();
        int rmdPos = rd.nextInt(freeSpacesIndex);

        int tileValue = 1;
        if (Math.random() > 0.8)
            tileValue = 2;

//        player.getPlayfieldTurn().addNewSpawned(new GameTileImpl(freeSpaces[rmdPos][0], freeSpaces[rmdPos][1], tileValue));
        // TODO change

        player.getPlayfieldTurn().addNewSpawned(new GameTileImpl(0, 0, tileValue));
        player.getPlayfieldState().setTile(freeSpaces[rmdPos][0], freeSpaces[rmdPos][1], tileValue);
        player.getPlayfieldState().printField();
        return true;
    }

    public static boolean spawnTile2(Player player) {
        double[][] random = new double[player.getPlayfieldState().getFieldSizeX()][player.getPlayfieldState().getFieldSizeY()];
        int[] highest = new int[2];
        double highestValue = 0;
        for (int i = 0; i < player.getPlayfieldState().getFieldSizeX(); i++) {

            for (int j = 0; j < player.getPlayfieldState().getFieldSizeY(); j++) {
                random[i][j] = Math.random();
                if (random[i][j] > highestValue) {
                    highestValue = random[i][j];
                    highest[0] = i;
                    highest[1] = j;
                }
            }
        }

        while (player.getPlayfieldState().getTile(highest[0], highest[1]) != 0) {
            random[highest[0]][highest[1]] = 0;
            highestValue = 0;
            for (int i = 0; i < player.getPlayfieldState().getFieldSizeX(); i++) {
                for (int j = 0; j < player.getPlayfieldState().getFieldSizeY(); j++) {
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
        player.getPlayfieldState().setTile(highest[0], highest[1], tileValue);
        player.getPlayfieldTurn().addNewSpawned(new GameTileImpl(highest[0], highest[1], tileValue));
        return true;
    }

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

                    //move
//                    player.getPlayfieldTurn().addNewMove(new GameTileImpl(x, y, x, positionNeueListe, zahl + 1));

                    positionNeueListe++;
                } else if (playfieldState.getTile(x, y) != 0) {
                    neueListe[x][positionNeueListe] = zahl;
                    zahl = playfieldState.getTile(x, y);

                    // Merge
//                    player.getPlayfieldTurn().addNewMerged(new GameTileImpl(x, y, x, positionNeueListe, zahl + 1));

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
                calculateUpMove(player, before, after);
                break;
            case DOWN:
                calculateDownMove(player, before, after);
                break;
            case LEFT:
                calculateLeftMove(player, before, after);
                break;
            case RIGHT:
                calculateRightMove(player, before, after);
                break;
        }
    }

    private static void calculateUpMove(Player player, int[][] before, int[][] after) {
        for (int y = 0; y < player.getPlayfieldState().getFieldSizeY(); y++) {
            int offset = 0;
            for (int x = 0; x < player.getPlayfieldState().getFieldSizeX(); x++) {
                if (after[x][y] != 0 && x + offset < player.getPlayfieldState().getFieldSizeX()) {            //find changed Tile
                    int old1 = x + offset;                                               //find old coordinate from first tile
                    while (before[old1][y] == 0) {
                        old1++;
                        offset++;
                    }
                    if (before[old1][y] == after[x][y]) {
                        before[old1][y] = 0;                                        //remove first coordinate to find second
                        player.getPlayfieldTurn().addNewMove(new GameTileImpl(old1, y, x, y, after[x][y]));
                    } else {
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
                    }
                    after[x][y] = 0;
                }
            }
        }
    }

    private static void calculateDownMove(Player player, int[][] before, int[][] after) {
        for (int y = 0; y < player.getPlayfieldState().getFieldSizeY(); y++) {
            int offset = 0;
            for (int x = player.getPlayfieldState().getFieldSizeX() - 1; x >= 0; x--) {
                if (after[x][y] != 0 && x - offset >= 0) {            //find changed Tile
                    int old1 = x - offset;                                               //find old coordinate from first tile
                    while (before[old1][y] == 0) {
                        old1--;
                        offset++;
                    }
                    if (before[old1][y] == after[x][y]) {
                        before[old1][y] = 0;                                        //remove first coordinate to find second
                        player.getPlayfieldTurn().addNewMove(new GameTileImpl(old1, y, x, y, after[x][y]));
                    } else {
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
                    }
                    after[x][y] = 0;
                }
            }
        }
    }

    private static void calculateLeftMove(Player player, int[][] before, int[][] after) {
        for (int x = 0; x < player.getPlayfieldState().getFieldSizeX(); x++) {
            int offset = 0;
            for (int y = 0; y < player.getPlayfieldState().getFieldSizeY(); y++) {
                if (after[x][y] != 0 && y + offset < player.getPlayfieldState().getFieldSizeY()) {            //find changed Tile
                    int old1 = y + offset;                                               //find old coordinate from first tile
                    while (before[x][old1] == 0) {
                        old1++;
                        offset++;
                    }
                    if (before[x][old1] == after[x][y]) {
                        before[x][old1] = 0;                                        //remove first coordinate to find second
                        player.getPlayfieldTurn().addNewMove(new GameTileImpl(x, old1, x, y, after[x][y]));
                    } else {
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
                    }
                    after[x][y] = 0;
                }
            }
        }
    }

    private static void calculateRightMove(Player player, int[][] before, int[][] after) {
        for (int x = 0; x < player.getPlayfieldState().getFieldSizeX(); x++) {
            int offset = 0;                                                              //offset is always positive
            for (int y = player.getPlayfieldState().getFieldSizeY() - 1; y >= 0; y--) {
                if (after[x][y] != 0 && y - offset >= 0) {            //find changed Tile
                    int old1 = y - offset;                                               //find old coordinate from first tile
                    while (before[x][old1] == 0) {
                        old1--;
                        offset++;
                    }
                    if (before[x][old1] == after[x][y]) {
                        before[x][old1] = 0;                                        //remove first coordinate to find second
                        player.getPlayfieldTurn().addNewMove(new GameTileImpl(x, old1, x, y, after[x][y]));
                    } else {
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
                    }
                    after[x][y] = 0;
                }
            }
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
