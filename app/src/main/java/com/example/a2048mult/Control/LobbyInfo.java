package com.example.a2048mult.Control;

import java.io.Serializable;

public class LobbyInfo implements Serializable {
    //TODO: LobbyInfoObject




    //Testabschnitt
    //
    //
    //
    int anzahl;
    String nachricht;
    public LobbyInfo(int anzahl, String nachricht){
        this.anzahl = anzahl;
        this.nachricht = nachricht;
    }

    public String getNachricht(){
        return nachricht;
    }
    public int getAnzahl() {
        return anzahl;
    }
    //
    //
    //
    //Testabschnitt Ende



}
