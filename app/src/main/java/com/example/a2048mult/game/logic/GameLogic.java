package com.example.a2048mult.game.logic;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.a2048mult.game.currentlyNotUsed.ReceiveListener;
import com.example.a2048mult.game.states.GameState;
import com.example.a2048mult.game.states.GameStateImpl;
import com.example.a2048mult.game.states.LobbySettings;
import com.example.a2048mult.game.states.MoveType;
import com.example.a2048mult.game.states.Player;
import com.example.a2048mult.ui.game.GameFragment;
import com.example.a2048mult.ui.game.GameUI;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * singleton pattern
 */
public class GameLogic implements InGameControl, ReceiveListener, GameControlMenu, Serializable {
    private static final GameLogic gameLogic = new GameLogic();
    boolean gameStarted = false;
    private LobbySettings lobbySettings;
    private GameState gameState;
    private GameUI gameUI;

    private GameLogic() {
    }

    ;

    public static GameLogic getInstance() {
        return gameLogic;
    }

    @Override
    public void swipe(MoveType move) {
        Runnable r = () -> {
            switch (move) {
                case UP:
                    for (Player player : gameState.getAllPlayer()) {
                        GameRules.moveUp(player);
                    }
                    break;
                case DOWN:
                    Log.e("!","logic");
                    for (Player player : gameState.getAllPlayer()) {
                        GameRules.moveDown(player);
                    }
                    break;
                case LEFT:
                    for (Player player : gameState.getAllPlayer()) {
                        GameRules.moveLeft(player);
                    }
                    break;
                case RIGHT:
                    for (Player player : gameState.getAllPlayer()) {
                        GameRules.moveRight(player);
                    }
                    break;
            }
            gameUI.drawGameState(GameLogic.this.gameState);
        };


        HandlerThread handlerThread = new HandlerThread("handleMove",0);
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        handler.post(r);

    }

    @Override
    public void leaveGame() {
        // TODO
    }

    @Override
    public void initDrawGameUI(GameUI gameUI) {
        this.gameUI = gameUI;
        this.gameUI.initDrawGameState(this.gameState);
    }

    @Override
    public void startGame(Fragment fragment, int resID) throws IllegalStateException {
        // TODO create GameState, sendGameState to other Devices
        if (this.lobbySettings == null) {
            throw new IllegalStateException("LobbySettings isnt set");
        }

        // for updating all Players on given Playfieldsize
        this.lobbySettings.setPlayFieldSize(this.lobbySettings.getPlayFieldSize());
        this.gameState = new GameStateImpl(this.lobbySettings.getAllPlayer());
        this.gameStarted = true;

        NavHostFragment.findNavController(fragment).navigate(resID);

        Runnable r = () -> {
            for (Player player : gameState.getAllPlayer()) {
                GameRules.spawnTile(player);
                GameRules.spawnTile(player);
            }
        };
        HandlerThread handlerThread = new HandlerThread("spawnFirstTiles",0);
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        handler.post(r);
    }

    @Override
    public void setLobbySettings(LobbySettings lobbySettings) {
        this.lobbySettings = lobbySettings;
    }

    @Override
    public void onReceivedPaket() {
        // TODO
    }
}
