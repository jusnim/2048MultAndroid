package com.example.a2048mult.game.states;

import java.util.Arrays;

public class LobbySettingsImpl implements LobbySettings {

    private Player[] playerForStarting = new Player[0];
    private int sizeForStarting = 0;
    Player leader;

    @Override
    public int getPlayerNum() {
        return playerForStarting.length;
    }

    @Override
    public Player[] getAllPlayer() {
        return playerForStarting;
    }

    @Override
    public void addPlayer(Player player) {
        this.playerForStarting = Arrays.copyOf(this.playerForStarting, playerForStarting.length + 1);
        this.playerForStarting[this.playerForStarting.length - 1] = player;
    }

    @Override
    public Player getLeader() {
        return this.leader;
    }

    @Override
    public void setLeader(Player player) {
        this.leader = player;
    }

    @Override
    public int getPlayFieldSize() {
        return sizeForStarting;
    }

    @Override
    public void setPlayFieldSize(int size) {
        for (int i = 0; i < this.getPlayerNum(); i++) {
            this.getAllPlayer()[i].setPlayfieldSize(size);
        }
        this.sizeForStarting = size;
    }
}
