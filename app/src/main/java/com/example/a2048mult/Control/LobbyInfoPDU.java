package com.example.a2048mult.Control;

import java.io.Serializable;

public class LobbyInfoPDU extends PDU implements Serializable {
    private static final PDUType pduType = PDUType.LobbyInfoPDU;
    private LobbyInfo data;
    public LobbyInfo getData() {
        return data;
    }

    public void setData(LobbyInfo data) {
        this.data = data;
    }

    @Override
    public PDUType getPDUType() {
        return pduType;
    }
}
