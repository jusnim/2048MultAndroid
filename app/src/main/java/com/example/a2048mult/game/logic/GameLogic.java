package com.example.a2048mult.game.logic;

import android.os.Handler;
import android.os.HandlerThread;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

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
            switch (move) {
                case UP:
                    this.gameState.getAllPlayer()[0].getPlayfieldTurn().addNewMerged(
                            new GameTileImpl(2, 0, 3, 0, 1),
                            new GameTileImpl(3, 0, 3, 0, 1),
                            new GameTileImpl(3, 0, 2));

//                    this.gameState.getAllPlayer()[0].getPlayfieldTurn().addNewMove(
//                            new GameTileImpl(0, 0, 2, 0, 1));

//                    this.gameState.getAllPlayer()[0].getPlayfieldTurn().addNewSpawned(new GameTileImpl(0,0,1));
                    /////////////////////

//                    this.gameState.getAllPlayer()[0].getPlayfieldTurn().addNewMerged(
//                            new GameTileImpl(2, 1, 3, 1, 1),
//                            new GameTileImpl(3, 1, 3, 1, 1),
//                            new GameTileImpl(3, 1, 2));
//
//                    this.gameState.getAllPlayer()[0].getPlayfieldTurn().addNewMove(
//                            new GameTileImpl(1, 1, 2, 1, 1));
//
//                    ///////////////////////////
//
//                    this.gameState.getAllPlayer()[0].getPlayfieldTurn().addNewMerged(
//                            new GameTileImpl(2, 2, 3, 2, 1),
//                            new GameTileImpl(3, 2, 3, 2, 1),
//                            new GameTileImpl(3, 2, 2));
//
//                    this.gameState.getAllPlayer()[0].getPlayfieldTurn().addNewMerged(
//                            new GameTileImpl(0, 2, 2, 2, 1),
//                            new GameTileImpl(1, 2, 2, 2, 1),
//                            new GameTileImpl( 2, 2, 2));


//                    this.gameState.getAllPlayer()[0].getPlayfieldTurn().addNewMove(new GameTileImpl(1,1,3, 1, 1));
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
        // TODO
        PDU received = new PDU() {@Override public PDUType getPDUType() {return null;}}; //TODO als argument uebergeben anstatt erstellen
        if(received.getPDUType() == null){
            throw new RuntimeException("Wrong PDU Type"); //TODO ExceptionType
        }
        switch (received.getPDUType()){
            case LobbySettingsPDU:
                GameLogic.getInstance().setLobbySettings(((LobbySettingsPDU) received).getData());
                break;
            case GameStatePDU:
                break;
            default:
                throw new RuntimeException("Wrong PDU Type");//TODO ExceptionType
        }

    }
}
