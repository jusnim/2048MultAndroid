package com.example.a2048mult.Control;

import com.example.a2048mult.game.states.OperateOnGameState;

public class GameStatePDU extends PDU{
    private static final PDUType pduType = PDUType.GameStatePDU;
    private OperateOnGameState data;
    public OperateOnGameState getData() {
        return data;
    }

    public void setData(OperateOnGameState data) {
        this.data = data;
    }

    @Override
    public PDUType getPDUType() {
        return pduType;
    }

}
