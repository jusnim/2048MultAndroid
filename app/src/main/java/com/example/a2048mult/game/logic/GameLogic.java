package com.example.a2048mult.game.logic;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.a2048mult.game.currentlyNotUsed.ReceiveListener;
import com.example.a2048mult.game.states.GameState;
import com.example.a2048mult.game.states.GameStateImpl;
import com.example.a2048mult.game.states.GameTileImpl;
import com.example.a2048mult.game.states.LobbySettings;
import com.example.a2048mult.game.states.MoveType;
import com.example.a2048mult.game.states.Player;
import com.example.a2048mult.ui.game.GameUI;

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
                    this.gameState.getAllPlayer()[0].getPlayfieldTurn().addNewSpawned(new GameTileImpl(0, 0, 1));
//                    for (Player player : gameState.getAllPlayer()) {
//                        GameRules.moveUp(player);
//                    }
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
            gameUI.drawGameState(GameLogic.this.gameState);
        };

        HandlerThread handlerThread = new HandlerThread("handleMove", 0);
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
        // TODO sendGameState to other Devices
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
                GameRules.initGame(player);
            }
        };
        HandlerThread handlerThread = new HandlerThread("spawnFirstTiles", 0);
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
