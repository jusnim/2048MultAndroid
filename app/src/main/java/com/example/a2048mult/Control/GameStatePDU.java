package com.example.a2048mult.Control;

import com.example.a2048mult.game.states.OperateOnGameState;

import java.io.Serializable;

public class GameStatePDU extends PDU implements Serializable {
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
