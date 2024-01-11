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
        currentPlayerIndex++;
        if (!onlineStatusPlayer[currentPlayerIndex] || !playingPlayer[currentPlayerIndex] ) {
            nextPlayer();
        }
    }

    @Override
    public Player[] getQuittedPlayer() {
        Player[] disconnectedPlayer = new Player[playerList.length];

        for (int i = 0; i < onlineStatusPlayer.length; i++){
            if (! onlineStatusPlayer[i]){
                disconnectedPlayer[i] = playerList[i];
            }
        }
        // trim array, so no null values are there
        disconnectedPlayer = (Player[]) Arrays.asList(disconnectedPlayer).stream().filter(Objects::nonNull).toArray();
        return disconnectedPlayer;
    }

    @Override
    public Player[] getAllPlayer() {
        return playerList;
    }

    @Override
    public Player[] getPlayerLost() {
        Player[] lostPlayer = new Player[playerList.length];

        for (int i = 0; i < playingPlayer.length; i++){
            if (! playingPlayer[i]){
                lostPlayer[i] = playerList[i];
            }
        }
        // trim array, so no null values are there
        lostPlayer = (Player[]) Arrays.asList(lostPlayer).stream().filter(Objects::nonNull).toArray();
        return lostPlayer;
    }

    @Override
    public Player[] getPlayerPlaying() {
        Player[] playingPlayerx = new Player[playerList.length];

        for (int i = 0; i < playingPlayer.length; i++){
            if (playingPlayer[i]){
                playingPlayerx[i] = playerList[i];
            }
        }
        // trim array, so no null values are there
        playingPlayerx = (Player[]) Arrays.asList(playingPlayerx).stream().filter(Objects::nonNull).toArray();
        return playingPlayerx;
    }
}
