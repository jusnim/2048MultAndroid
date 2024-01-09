package com.example.a2048mult.Control;

import com.example.a2048mult.game.GameState;

public class GameStatePDU extends PDU{
    private static final PDUType pduType = PDUType.GameStatePDU;
    private GameState data;
    public GameState getData() {
        return data;
    }

    public void setData(GameState data) {
        this.data = data;
    }

    @Override
    public PDUType getPDUType() {
        return pduType;
    }

}
