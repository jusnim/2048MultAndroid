package com.example.a2048mult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.a2048mult.game.states.ChangeLobbyConfigurations;
import com.example.a2048mult.game.states.GameState;
import com.example.a2048mult.game.states.GameTile;
import com.example.a2048mult.game.states.GameTileImpl;
import com.example.a2048mult.game.states.LobbySettings;
import com.example.a2048mult.game.states.OperateOnGameState;
import com.example.a2048mult.game.states.Player;
import com.example.a2048mult.game.states.PlayerImpl;
import com.example.a2048mult.game.states.PlayfieldState;
import com.example.a2048mult.game.states.PlayfieldStateImpl;
import com.example.a2048mult.game.states.PlayfieldTurn;
import com.example.a2048mult.game.states.PlayfieldTurnAnimTuple;
import com.example.a2048mult.game.states.PlayfieldTurnAnimationType;
import com.example.a2048mult.game.states.PlayfieldTurnImpl;

import org.junit.Test;



public class GameStateTests {

    @Test
    public void lobbyTest(){
        ChangeLobbyConfigurations config = new LobbySettings();
        Player p1 = new PlayerImpl("00", 0, new PlayfieldStateImpl());
        Player p2 = new PlayerImpl("01", 0, new PlayfieldStateImpl());

        assertEquals(0, config.getPlayerNum());

        config.addPlayer(p1);
        assertEquals(1, config.getPlayerNum());

        config.addPlayer(p2);
        assertEquals(2, config.getPlayerNum());

        config.setLeader(p1);
        assertEquals(p1, config.getLeader());

        config.setPlayFieldSize(69);
        assertEquals(69, p1.getPlayfieldState().getFieldSizeX());
        assertEquals(69, p1.getPlayfieldState().getFieldSizeY());
        assertEquals(69, p2.getPlayfieldState().getFieldSizeX());
        assertEquals(69, p2.getPlayfieldState().getFieldSizeY());

        assertTrue((config.getAllPlayer()[0] == p1 && config.getAllPlayer()[1] == p2)
                        || (config.getAllPlayer()[1] == p1 && config.getAllPlayer()[0] == p2));
    }

    @Test
    public void tileTest(){
        GameTile tile = new GameTileImpl(1,1,2);

        assertEquals(1, tile.getNewX());
        assertEquals(1, tile.getNewY());
        assertEquals(2, tile.getLevel());

        tile.updateCoordinates(4,3);

        assertEquals(4, tile.getNewX());
        assertEquals(3, tile.getNewY());
        assertEquals(1, tile.getOldX());
        assertEquals(1, tile.getOldY());

        tile.updateCoordinates(76,87);

        assertEquals(76, tile.getNewX());
        assertEquals(87, tile.getNewY());
        assertEquals(4, tile.getOldX());
        assertEquals(3, tile.getOldY());

        tile.setLevel(17);

        assertEquals(17, tile.getLevel());

        tile.setNewX(1);
        tile.setNewY(2);
        tile.setOldX(3);
        tile.setOldY(4);

        assertEquals(1, tile.getNewX());
        assertEquals(2, tile.getNewY());
        assertEquals(3, tile.getOldX());
        assertEquals(4, tile.getOldY());

        tile.changeNewToOld();

        assertEquals(1, tile.getNewX());
        assertEquals(2, tile.getNewY());
        assertEquals(1, tile.getOldX());
        assertEquals(2, tile.getOldY());

        tile = new GameTileImpl(1,2,3,4,5);

        assertEquals(3, tile.getNewX());
        assertEquals(4, tile.getNewY());
        assertEquals(1, tile.getOldX());
        assertEquals(2, tile.getOldY());
        assertEquals(5, tile.getLevel());
    }

    @Test
    public void gameStateTest(){
        ChangeLobbyConfigurations config = new LobbySettings();
        Player p1 = new PlayerImpl("00", 0, new PlayfieldStateImpl());
        Player p2 = new PlayerImpl("01", 0, new PlayfieldStateImpl());

        config.addPlayer(p1);
        config.addPlayer(p2);

        OperateOnGameState state = new GameState(config.getAllPlayer());

        assertEquals(0, state.getTurnCount());
        state.incrementTurnCount();
        assertEquals(1, state.getTurnCount());

        assertEquals(p1, state.getCurrentPlayer());
        state.nextPlayer();
        assertEquals(p2, state.getCurrentPlayer());
        state.nextPlayer();
        assertEquals(p1, state.getCurrentPlayer());

        assertEquals(p1, state.getAllPlayer()[0]);
        assertEquals(p2, state.getAllPlayer()[1]);

        assertEquals(0, state.getQuittedPlayer().length);
        assertEquals(0, state.getPlayerLost().length);

        assertEquals(p1, state.getPlayerPlaying()[0]);
        assertEquals(p2, state.getPlayerPlaying()[1]);
    }

    @Test
    public void playerTest(){
        PlayfieldState pfs = new PlayfieldStateImpl();
        Player p1 = new PlayerImpl("00", 42000, pfs);

        assertEquals("00", p1.getName());
        assertEquals(42000, p1.getScore());

        p1.addScore(69);

        assertEquals(42069, p1.getScore());
        assertEquals(pfs, p1.getPlayfieldState());

        p1.setPlayfieldSize(42);

        assertEquals(42, p1.getPlayfieldState().getFieldSizeX());
        assertEquals(42, p1.getPlayfieldState().getFieldSizeY());

        PlayfieldTurn pft = new PlayfieldTurnImpl();
        p1.setPlayfieldTurn(pft);

        assertEquals(pft, p1.getPlayfieldTurn());
    }

    @Test
    public void playfieldTest(){
        PlayfieldState pfs = new PlayfieldStateImpl(187);

        assertEquals(187, pfs.getFieldSizeX());
        assertEquals(187, pfs.getFieldSizeY());

        pfs.setTile(0,0,42);

        assertEquals(42, pfs.getTile(0,0));

        pfs.setTile(0,0,43);

        assertEquals(43, pfs.getTile(0,0));

        assertEquals(43, pfs.getField()[0][0]);

        int[][] field = new int[187][187];
        pfs.setField(field);

        assertEquals(field[123][45], pfs.getField()[123][45]);
    }

    @Test
    public void playfieldTurnTest(){
        PlayfieldTurn pft = new PlayfieldTurnImpl();

        GameTile tile = new GameTileImpl(0,0,42);

        pft.addNewMove(tile);

        PlayfieldTurnAnimTuple pftat = pft.pollNextAnimation();

        assertEquals(PlayfieldTurnAnimationType.MOVE, pftat.type);
        assertEquals(tile, pftat.tile);

        assertNull(pft.pollNextAnimation());

        pft.addNewSpawned(tile);
        assertEquals(PlayfieldTurnAnimationType.SPAWN, pft.pollNextAnimation().type);

        pft.addRemoved(tile);
        assertEquals(PlayfieldTurnAnimationType.REMOVE, pft.pollNextAnimation().type);
    }
}
