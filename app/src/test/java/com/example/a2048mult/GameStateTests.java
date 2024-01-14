package com.example.a2048mult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.a2048mult.game.states.ChangeLobbyConfigurations;
import com.example.a2048mult.game.states.LobbySettings;
import com.example.a2048mult.game.states.Player;
import com.example.a2048mult.game.states.PlayerImpl;
import com.example.a2048mult.game.states.PlayfieldStateImpl;

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
}
