package com.example.a2048mult;

import com.example.a2048mult.Control.LobbyInfo;
import com.example.a2048mult.Control.LobbyInfoPDU;
import com.example.a2048mult.Control.PDU;
import com.example.a2048mult.Control.Serializer;

import org.junit.Test;
import static org.junit.Assert.*;

import androidx.annotation.NonNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
    }
}
