package com.example.a2048mult;

import com.example.a2048mult.Control.GameStatePDU;
import com.example.a2048mult.Control.LobbyInfo;
import com.example.a2048mult.Control.LobbyInfoPDU;
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

        LobbyInfoPDU lip = new LobbyInfoPDU();
        lip.setData(new LobbyInfo(23, "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAH")); //TODO Update when LobbyInfo is implemented
        Serializer.serializePDU(lip, outputStream);
        byte[] data = outputStream.toByteArray();
        LobbyInfoPDU copy = (LobbyInfoPDU) Serializer.deserializePDU(new ByteArrayInputStream(data));
        assertEquals(copy.getData().getAnzahl(), lip.getData().getAnzahl());
        System.out.println(lip.getData().getNachricht());
        System.out.println(copy.getData().getNachricht());
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
