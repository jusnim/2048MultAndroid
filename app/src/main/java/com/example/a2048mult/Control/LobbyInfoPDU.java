package com.example.a2048mult.Control;

public class LobbyInfoPDU extends PDU{
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
