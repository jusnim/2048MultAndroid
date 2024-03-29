package com.example.a2048mult.Control;

import com.example.a2048mult.game.currentlyNotUsed.HandlePackets;
import com.example.a2048mult.game.currentlyNotUsed.ReceiveListener;
import com.example.a2048mult.game.logic.GameLogic;
import com.example.a2048mult.game.states.GameState;
import com.example.a2048mult.game.states.LobbySettings;
import com.example.a2048mult.ui.menu.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ProtocolWrapper implements HandlePackets {

    ReceiveListener receiveListener;
    BluetoothManager bluetoothManager;
    OutputStream outputStream;
    public ProtocolWrapper(){
        this.receiveListener = GameLogic.getInstance();
        this.bluetoothManager = MainActivity.getBTManagerInstance();

        BtMessageSender btMessageSender;
        if(bluetoothManager.getConnectionType() == ConnectionType.Server){
            btMessageSender = new BtMessageSender(bluetoothManager, bluetoothManager.getServerSocketThread().getBtSocket());
        }else {
            btMessageSender = new BtMessageSender(bluetoothManager, bluetoothManager.getConnectClientSocketThread().getBtSocket());
        }
        outputStream = btMessageSender.btOStream;
    }
    @Override
    public void recievedataPackets(InputStream inputStream) {
        PDU pdu;
        try {
             pdu = Serializer.deserializePDU(inputStream);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        receiveListener.onReceivedPaket(pdu);
    }

    @Override
    public void senddataPackets(GameState gameState) {
        GameStatePDU gameStatePDU = new GameStatePDU();
        gameStatePDU.setData(gameState);
        senddataPackets(gameStatePDU);
    }

    @Override
    public void senddataPackets(LobbySettings lobbySettings) {
        LobbySettingsPDU lobbySettingsPDU = new LobbySettingsPDU();
        lobbySettingsPDU.setData(lobbySettings);
        senddataPackets(lobbySettingsPDU);
    }

    private void senddataPackets(PDU pdu){
        try {
            Serializer.serializePDU(pdu, outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
