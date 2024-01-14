package com.example.a2048mult;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.a2048mult.game.logic.GameRules;
import com.example.a2048mult.game.states.GameTileImpl;
import com.example.a2048mult.game.states.MoveType;
import com.example.a2048mult.game.states.Player;
import com.example.a2048mult.game.states.PlayerImpl;
import com.example.a2048mult.game.states.PlayfieldState;
import com.example.a2048mult.game.states.PlayfieldStateImpl;
import com.example.a2048mult.game.states.PlayfieldTurnAnimTuple;

import org.junit.Test;
import org.junit.jupiter.api.Nested;

public class GameLogicTests {

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

    private int[][] execMoveSwitch(MoveType moveType, int[][] inputArray) {
        PlayfieldState playfieldstate = new PlayfieldStateImpl();
        playfieldstate.setField(inputArray);
        Player player = new PlayerImpl("", 0, playfieldstate);

        switch (moveType) {
            case UP:
                GameRules.moveUp(player);
                break;
            case DOWN:
                GameRules.moveDown(player);
                break;
            case LEFT:
                GameRules.moveLeft(player);
                break;
            case RIGHT:
                GameRules.moveRight(player);
                break;
        }
        return playfieldstate.getField();
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
            for (int level : line) {
                System.out.print(level + " ");
//                Log.e("!", String.valueOf(level));
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
//        GameRules.moveDown(player);

        player.getPlayfieldTurn().addNewMove(new GameTileImpl(0, 0, 1, 0, 1));
        player.getPlayfieldTurn().addNewMove(new GameTileImpl(1, 0, 2, 0, 1));

        //gets all registered changes from calculateMove and similar methods and prints them for manual debugging
        PlayfieldTurnAnimTuple pt =
                player.getPlayfieldTurn().pollNextAnimation();

        int i = 0;
        while (pt != null) {
            System.out.println(
                    pt.tile.getOldX() + ", "
                            + pt.tile.getOldY() + ", "
                            + pt.tile.getNewX() + ", "
                            + pt.tile.getNewY());
            pt = player.getPlayfieldTurn().pollNextAnimation();
            i++;
        }
        System.out.println(i);
    }

    @Test
    public void moveDown1() {
        int[][] inputArray = {
                {1, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        int[][] expectedArrau = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {1, 0, 0, 0},
        };
        int[][] processedArray;
        processedArray = execMoveSwitch(MoveType.DOWN, inputArray);

        assertArrayEquals(processedArray, expectedArrau);
    }

    @Test
    public void moveDown2() {
        int[][] inputArray = {
                {1, 1, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 0},
        };

        int[][] expectedArrau = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {1, 1, 1, 0},
        };
        int[][] processedArray;
        processedArray = execMoveSwitch(MoveType.DOWN, inputArray);

        assertArrayEquals(processedArray, expectedArrau);
    }

    @Test
    public void moveDown3() {
        int[][] inputArray = {
                {1, 1, 1, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        int[][] expectedArrau = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {1, 1, 1, 1},
        };
        int[][] processedArray;
        processedArray = execMoveSwitch(MoveType.DOWN, inputArray);

        assertArrayEquals(processedArray, expectedArrau);
    }

    @Test
    public void moveDown4() {
        int[][] inputArray = {
                {1, 1, 1, 1},
                {0, 0, 0, 1},
                {0, 1, 0, 0},
                {0, 0, 0, 0},
        };

        int[][] expectedArrau = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {1, 2, 1, 2},
        };
        int[][] processedArray;
        processedArray = execMoveSwitch(MoveType.DOWN, inputArray);

        assertArrayEquals(processedArray, expectedArrau);
    }

    @Test
    public void moveDown5() {
        int[][] inputArray = {
                {1, 1, 1, 1},
                {0, 0, 0, 1},
                {0, 1, 0, 1},
                {1, 0, 0, 0},
        };

        int[][] expectedArrau = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 1},
                {2, 2, 1, 2},
        };
        int[][] processedArray;
        processedArray = execMoveSwitch(MoveType.DOWN, inputArray);

        assertArrayEquals(processedArray, expectedArrau);
    }
    @Nested
    class moveDown {

    }
    @Test
    public void moveUp1() {
        int[][] inputArray = {
                {1, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        int[][] expectedArrau = {
                {1, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] processedArray;
        processedArray = execMoveSwitch(MoveType.UP, inputArray);

        assertArrayEquals(processedArray, expectedArrau);
    }

    @Test
    public void moveUp2() {
        int[][] inputArray = {
                {1, 1, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 0},
        };

        int[][] expectedArrau = {
                {1, 1, 1, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] processedArray;
        processedArray = execMoveSwitch(MoveType.UP, inputArray);

        assertArrayEquals(processedArray, expectedArrau);
    }

    @Test
    public void moveUp3() {
        int[][] inputArray = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {1, 1, 1, 1},
        };

        int[][] expectedArrau = {
                {1, 1, 1, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] processedArray;
        processedArray = execMoveSwitch(MoveType.UP, inputArray);

        assertArrayEquals(processedArray, expectedArrau);
    }

    @Test
    public void moveUp4() {
        int[][] inputArray = {
                {1, 1, 1, 1},
                {0, 0, 0, 1},
                {0, 1, 0, 0},
                {0, 0, 0, 0},
        };

        int[][] expectedArrau = {
                {1, 2, 1, 2},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] processedArray;
        processedArray = execMoveSwitch(MoveType.UP, inputArray);

        assertArrayEquals(processedArray, expectedArrau);
    }

    @Test
    public void moveUp5() {
        int[][] inputArray = {
                {1, 1, 1, 1},
                {0, 0, 0, 1},
                {0, 1, 0, 1},
                {1, 0, 0, 0},
        };

        int[][] expectedArrau = {
                {2, 2, 1, 2},
                {0, 0, 0, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] processedArray;
        processedArray = execMoveSwitch(MoveType.UP, inputArray);

        assertArrayEquals(processedArray, expectedArrau);
    }
    @Test
    public void moveLeft1() {
        int[][] inputArray = {
                {1, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        int[][] expectedArrau = {
                {1, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] processedArray;
        processedArray = execMoveSwitch(MoveType.LEFT, inputArray);

        assertArrayEquals(processedArray, expectedArrau);
    }

    @Test
    public void moveLeft2() {
        int[][] inputArray = {
                {1, 1, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 0},
        };

        int[][] expectedArrau = {
                {2, 0, 0, 0},
                {0, 0, 0, 0},
                {1, 0, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] processedArray;
        processedArray = execMoveSwitch(MoveType.LEFT, inputArray);

        assertArrayEquals(processedArray, expectedArrau);
    }

    @Test
    public void moveLeft3() {
        int[][] inputArray = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {1, 1, 1, 1},
        };

        int[][] expectedArrau = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 2, 0, 0},
        };
        int[][] processedArray;
        processedArray = execMoveSwitch(MoveType.LEFT, inputArray);

        assertArrayEquals(processedArray, expectedArrau);
    }

    @Test
    public void moveLeft4() {
        int[][] inputArray = {
                {1, 1, 1, 1},
                {0, 0, 0, 1},
                {0, 1, 0, 0},
                {0, 0, 0, 0},
        };

        int[][] expectedArrau = {
                {2, 2, 0, 0},
                {1, 0, 0, 0},
                {1, 0, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] processedArray;
        processedArray = execMoveSwitch(MoveType.LEFT, inputArray);

        assertArrayEquals(processedArray, expectedArrau);
    }

    @Test
    public void moveLeft5() {
        int[][] inputArray = {
                {1, 1, 1, 1},
                {0, 0, 0, 1},
                {0, 1, 0, 1},
                {1, 0, 0, 0},
        };

        int[][] expectedArrau = {
                {2, 2, 0, 0},
                {1, 0, 0, 0},
                {2, 0, 0, 0},
                {1, 0, 0, 0},
        };
        int[][] processedArray;
        processedArray = execMoveSwitch(MoveType.LEFT, inputArray);

        assertArrayEquals(processedArray, expectedArrau);
    }
    @Test
    public void moveRight1() {
        int[][] inputArray = {
                {1, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        int[][] expectedArrau = {
                {0, 0, 0, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] processedArray;
        processedArray = execMoveSwitch(MoveType.RIGHT, inputArray);

        assertArrayEquals(processedArray, expectedArrau);
    }

    @Test
    public void moveRight2() {
        int[][] inputArray = {
                {1, 1, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 0},
        };

        int[][] expectedArrau = {
                {0, 0, 0, 2},
                {0, 0, 0, 0},
                {0, 0, 0, 1},
                {0, 0, 0, 0},
        };
        int[][] processedArray;
        processedArray = execMoveSwitch(MoveType.RIGHT, inputArray);

        assertArrayEquals(processedArray, expectedArrau);
    }

    @Test
    public void moveRight3() {
        int[][] inputArray = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {1, 1, 1, 1},
        };

        int[][] expectedArrau = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 2, 2},
        };
        int[][] processedArray;
        processedArray = execMoveSwitch(MoveType.RIGHT, inputArray);

        assertArrayEquals(processedArray, expectedArrau);
    }

    @Test
    public void moveRight4() {
        int[][] inputArray = {
                {1, 1, 1, 1},
                {0, 0, 0, 1},
                {0, 1, 0, 0},
                {0, 0, 0, 0},
        };

        int[][] expectedArrau = {
                {0, 0, 2, 2},
                {0, 0, 0, 1},
                {0, 0, 0, 1},
                {0, 0, 0, 0},
        };
        int[][] processedArray;
        processedArray = execMoveSwitch(MoveType.RIGHT, inputArray);

        assertArrayEquals(processedArray, expectedArrau);
    }

    @Test
    public void moveRight5() {
        int[][] inputArray = {
                {1, 1, 1, 1},
                {0, 0, 0, 1},
                {0, 1, 0, 1},
                {1, 0, 0, 0},
        };

        int[][] expectedArrau = {
                {0, 0, 2, 2},
                {0, 0, 0, 1},
                {0, 0, 0, 2},
                {0, 0, 0, 1},
        };
        int[][] processedArray;
        processedArray = execMoveSwitch(MoveType.RIGHT, inputArray);

        assertArrayEquals(processedArray, expectedArrau);
    }

}

