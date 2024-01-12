package com.example.a2048mult.game.currentlyNotUsed;

import com.example.a2048mult.Control.PDU;

/**
 * Interface zum bemerken von neuen Paketen in der 2048-Komponente
 */
public interface ReceiveListener {
    /**
     * Methode zum Bemerkbarmachen von neuen Informationene in der 2048-Komponente
     */
    void onReceivedPaket(PDU pdu);
}
