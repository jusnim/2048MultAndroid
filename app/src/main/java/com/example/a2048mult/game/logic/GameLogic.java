package com.example.a2048mult.game.logic;

import android.os.Handler;
import android.os.HandlerThread;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.a2048mult.Control.GameStatePDU;
import com.example.a2048mult.Control.LobbySettingsPDU;
import com.example.a2048mult.Control.PDU;
import com.example.a2048mult.Control.PDUType;
import com.example.a2048mult.game.currentlyNotUsed.ReceiveListener;
import com.example.a2048mult.game.states.ChangeLobbyConfigurations;
import com.example.a2048mult.game.states.GameState;
import com.example.a2048mult.game.states.GameTileImpl;
import com.example.a2048mult.game.states.MoveType;
import com.example.a2048mult.game.states.OperateOnGameState;
import com.example.a2048mult.game.states.Player;
import com.example.a2048mult.ui.game.DrawGameUI;

import java.io.Serializable;

/**
 * singleton pattern
 */
public class GameLogic implements InGameControl, ReceiveListener, GameMenuControl, Serializable {
    private static final GameLogic gameLogic = new GameLogic();
    boolean gameStarted = false;
    private ChangeLobbyConfigurations lobbySettings;
    private OperateOnGameState gameState;
    private DrawGameUI gameUI;

    private GameLogic() {
    }

    ;

    public static GameLogic getInstance() {
        return gameLogic;
    }

    @Override
    public void swipe(MoveType move) {
        Runnable r = () -> {
            for (Player player : gameState.getAllPlayer()) {
                switch (move) {
                    case UP:
                        GameRules.moveUp(player);
                        break;
                    case DOWN:
                        GameRules.moveDown(player);
                        break;
                    case LEFT:
                        GameRules.moveLeft(player);

                        break;
                    case RIGHT:

                        GameRules.moveRight(player);
                        break;
                }
                GameRules.spawnTile(player);
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
    public void initDrawGameUI(DrawGameUI gameUI) {
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
//        this.lobbySettings.setPlayFieldSize(this.lobbySettings.getPlayFieldSize());
        this.gameState = new GameState(this.lobbySettings.getAllPlayer());
        this.gameStarted = true;

        NavHostFragment.findNavController(fragment).navigate(resID);

        Runnable r = () -> {
            for (Player player : gameState.getAllPlayer()) {
//            GameRules.initGame(player);
            }
//
//
        };
        HandlerThread handlerThread = new HandlerThread("spawnFirstTiles", 0);
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        handler.post(r);
    }

    @Override
    public void setLobbySettings(ChangeLobbyConfigurations lobbySettings) {
        this.lobbySettings = lobbySettings;
    }

    @Override
    public void onReceivedPaket(PDU pdu) {
        if (pdu.getPDUType() == null) {
            return;
        }
        switch (pdu.getPDUType()) {
            case LobbySettingsPDU:
                GameLogic.getInstance().setLobbySettings(((LobbySettingsPDU) pdu).getData());
                break;
            case GameStatePDU:
                this.gameState = ((GameStatePDU) pdu).getData();
                break;
        }

    }
}
