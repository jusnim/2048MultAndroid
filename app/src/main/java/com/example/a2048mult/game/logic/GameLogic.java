package com.example.a2048mult.game.logic;

import android.os.Bundle;

import androidx.navigation.fragment.NavHostFragment;

import com.example.a2048mult.R;
import com.example.a2048mult.game.GameState;
import com.example.a2048mult.game.GameStateImpl;
import com.example.a2048mult.game.currentlyNotUsed.GameInputs;
import com.example.a2048mult.game.currentlyNotUsed.LobbySettings;
import com.example.a2048mult.game.currentlyNotUsed.ReceiveListener;
import com.example.a2048mult.ui.game.GameFragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;

public class GameLogic implements GameInputs, LobbySettings, ReceiveListener {
    Player leader;
    boolean gameStarted = false;
    private GameState gameState;
    private Player[] playerForStarting = new Player[0];
    private int sizeForStarting = 0;

    private Bundle bundle;

    @Override
    public void swipe(Move move) {
        switch (move) {
            case UP:
                break;
            case DOWN:
                break;
            case LEFT:
                break;
            case RIGHT:
                break;
        }
    }

    @Override
    public void leaveGame() {

    }

    @Override
    public boolean getGameStarted() {
        return this.gameStarted;
    }

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

    @Override
    public void startGame() {
        // TODO create GameState, sendGameState to other Devices

        // for updating all Players on given Playfieldsize
        this.setPlayFieldSize(this.getPlayFieldSize());
        this.gameState = new GameStateImpl(this.getAllPlayer());
        this.gameStarted = true;


        byte[] gamestateContent;
        try {
            // serialize object to byte Array
            ByteArrayOutputStream osB = new ByteArrayOutputStream();
            ObjectOutputStream osO = new ObjectOutputStream(osB);
            osO.writeObject(this.gameState);
            osB.flush();
            gamestateContent = osB.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.bundle = new Bundle();
        this.bundle.putByteArray(GameFragment.GAMESTATE, gamestateContent);
    }

    @Override
    public Bundle getBundle() {
        return this.bundle;
    }


    @Override
    public void onReceivedPaket() {

    }
}
