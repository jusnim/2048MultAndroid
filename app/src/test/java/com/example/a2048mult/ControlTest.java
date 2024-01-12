package com.example.a2048mult;

import com.example.a2048mult.Control.GameStatePDU;
import com.example.a2048mult.game.states.LobbySettings;
import com.example.a2048mult.Control.LobbySettingsPDU;
import com.example.a2048mult.Control.Serializer;
import com.example.a2048mult.game.states.GameState;
import com.example.a2048mult.game.states.Player;
import com.example.a2048mult.game.states.PlayerImpl;
import com.example.a2048mult.game.states.PlayfieldStateImpl;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ControlTest {
    @Test
    public void serializerTest() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        LobbySettingsPDU lip = new LobbySettingsPDU();
        LobbySettings lobbySettings =new LobbySettings();
        lobbySettings.addPlayer(new PlayerImpl("max", 0, new PlayfieldStateImpl()));
        lip.setData(lobbySettings);
        Serializer.serializePDU(lip, outputStream);
        byte[] data = outputStream.toByteArray();
        LobbySettingsPDU copy = (LobbySettingsPDU) Serializer.deserializePDU(new ByteArrayInputStream(data));
        assertEquals(copy.getData().getPlayerNum(), lip.getData().getPlayerNum());
        System.out.println(lip.getData().getPlayerNum());
        System.out.println(copy.getData().getPlayerNum());
        outputStream.reset();

        Player player = new PlayerImpl("Max Mustermann", 69, new PlayfieldStateImpl());
        GameState gameState = new GameState(new Player[]{player});
        GameStatePDU gsp = new GameStatePDU();
        gsp.setData(gameState);
        Serializer.serializePDU(gsp, outputStream);
        data = outputStream.toByteArray();
        GameStatePDU copyToo = (GameStatePDU) Serializer.deserializePDU(new ByteArrayInputStream(data));
        assertEquals(gsp.getData().getAllPlayer()[0].getName(),copyToo.getData().getAllPlayer()[0].getName());
        System.out.println(copyToo.getData().getAllPlayer()[0].getName());
    }
}
