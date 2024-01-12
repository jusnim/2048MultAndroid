package com.example.a2048mult.Control;

import com.example.a2048mult.game.states.LobbySettings;

import java.io.Serializable;

public class LobbySettingsPDU extends PDU implements Serializable {
    private static final PDUType pduType = PDUType.LobbyInfoPDU;
    private LobbySettings data;
    public LobbySettings getData() {
        return data;
    }

    public void setData(LobbySettings data) {
        this.data = data;
    }

    @Override
    public PDUType getPDUType() {
        return pduType;
    }
}
