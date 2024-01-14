package com.example.a2048mult.game.states;

import java.util.Arrays;
import java.util.Objects;

public class GameState implements OperateOnGameState {

    private int turnCount = 0;
    private Player[] playerList;

    // if value false --> the player with this index  quitted
    private boolean[] onlineStatusPlayer;

    // if value false --> the player with this index lost
    private boolean[] playingPlayer;

    public GameState(Player[] playerList){
        this.playerList = playerList;
        onlineStatusPlayer = new boolean[playerList.length];
        playingPlayer = new boolean[playerList.length];
        for (int i = 0; i < playerList.length; i++) {
            onlineStatusPlayer[i] = true;
            playingPlayer[i] = true;
        }
    }

    private int currentPlayerIndex = 0;

    @Override
    public int getTurnCount() {
        return this.turnCount;
    }

    @Override
    public void incrementTurnCount() {
        this.turnCount++;
    }

    @Override
    public PlayfieldTurn[] getGameTurn() {
        return new PlayfieldTurn[0];
    }

    @Override
    public Player getCurrentPlayer() {
        return playerList[currentPlayerIndex];
    }

    @Override
    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % playerList.length;
        if (!onlineStatusPlayer[currentPlayerIndex] || !playingPlayer[currentPlayerIndex] ) {
            nextPlayer();
        }
    }

    @Override
    public Player[] getQuittedPlayer() {
        Player[] disconnectedPlayer = new Player[playerList.length];
        int counter = 0;
        for (int i = 0; i < onlineStatusPlayer.length; i++){
            if (! onlineStatusPlayer[i]){
                disconnectedPlayer[i] = playerList[i];
                counter++;
            }
        }
        // trim array, so no null values are there
        Player[] output = new Player[counter];
        for(int i = 0; i < counter; i++){
            output[i] = disconnectedPlayer[i];
        }
        return output;
    }

    @Override
    public Player[] getAllPlayer() {
        return playerList;
    }

    @Override
    public Player[] getPlayerLost() {
        Player[] lostPlayer = new Player[playerList.length];
        int counter = 0;
        for (int i = 0; i < playingPlayer.length; i++){
            if (! playingPlayer[i]){
                lostPlayer[i] = playerList[i];
                counter++;
            }
        }
        // trim array, so no null values are there
        Player[] output = new Player[counter];
        for(int i = 0; i < counter; i++){
            output[i] = lostPlayer[i];
        }
        return output;
    }

    @Override
    public Player[] getPlayerPlaying() {
        Player[] playingPlayerx = new Player[playerList.length];
        int counter = 0;
        for (int i = 0; i < playingPlayer.length; i++){
            if (playingPlayer[i]){
                playingPlayerx[i] = playerList[i];
                counter++;
            }
        }
        // trim array, so no null values are there
        Player[] output = new Player[counter];
        for(int i = 0; i < counter; i++){
            output[i] = playingPlayerx[i];
        }
        return output;
    }
}
