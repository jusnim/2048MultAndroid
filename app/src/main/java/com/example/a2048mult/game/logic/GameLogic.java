package com.example.a2048mult.game.logic;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.a2048mult.game.currentlyNotUsed.ReceiveListener;
import com.example.a2048mult.game.states.GameState;
import com.example.a2048mult.game.states.GameStateImpl;
import com.example.a2048mult.game.states.LobbySettings;
import com.example.a2048mult.game.states.MoveType;
import com.example.a2048mult.game.states.Player;
import com.example.a2048mult.ui.game.GameFragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * singleton pattern
 */
public class GameLogic implements InGameControl, ReceiveListener, GameControlMenu, GameDraw, Serializable {
    private static final GameLogic gameLogic = new GameLogic();
    boolean gameStarted = false;
    private LobbySettings lobbySettings;
    private GameState gameState;

    private GameLogic() {
    }

    ;

    public static GameLogic getInstance() {
        return gameLogic;
    }

    @Override
    public void swipe(MoveType move) {
        switch (move) {
            case UP:
                for (Player player : gameState.getAllPlayer()) {
                    GameRules.moveUp(player);
                }
                break;
            case DOWN:
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
        drawGameState();
    }

    @Override
    public void leaveGame() {
        // TODO
    }

    @Override
    public GameState getGameState() {
        return this.gameState;
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

        // serialize gamelogic object to byte Array
        byte[] gamestateContent;
        try {
            ByteArrayOutputStream osB = new ByteArrayOutputStream();
            ObjectOutputStream osO = new ObjectOutputStream(osB);
            osO.writeObject(this);
            osB.flush();
            gamestateContent = osB.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Bundle bundle = new Bundle();
        bundle.putByteArray(GameFragment.GAMELOGIC, gamestateContent);

        // navigate to GameView
        NavHostFragment.findNavController(fragment).navigate(resID, bundle);
    }

    @Override
    public void setLobbySettings(LobbySettings lobbySettings) {
        this.lobbySettings = lobbySettings;
    }

    @Override
    public void onReceivedPaket() {
        // TODO
    }

    @Override
    public void drawGameState() {
        // TODO
    }
}
