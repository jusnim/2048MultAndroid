package com.example.a2048mult.ui.game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import com.example.a2048mult.databinding.FragmentGameBinding;
import com.example.a2048mult.game.logic.GameLogic;
import com.example.a2048mult.game.logic.InGameControl;
import com.example.a2048mult.game.states.GameState;
import com.example.a2048mult.game.states.MoveType;
import com.example.a2048mult.game.states.Player;
import com.example.a2048mult.ui.game.playfield.PlayfieldUI;
import com.example.a2048mult.ui.game.playfield.PlayfieldWithPlayerView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


public class GameFragment extends Fragment implements GameUI {

    public static final String GAMELOGIC = "gamelogic";

    private FragmentGameBinding binding;


    public GameFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGameBinding.inflate(getLayoutInflater());
        GameLogic.getInstance().initDrawGameUI(this);
        binding.getRoot().setOnTouchListener(new InputListener(binding.getRoot().getContext()) {
            @Override
            public void onLeft() {
                GameLogic.getInstance().swipe(MoveType.LEFT);
            }

            @Override
            public void onDown() {
                GameLogic.getInstance().swipe(MoveType.DOWN);
            }

            @Override
            public void onUp() {
                GameLogic.getInstance().swipe(MoveType.UP);
            }

            @Override
            public void onRight() {
                GameLogic.getInstance().swipe(MoveType.RIGHT);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void initDrawGameState(GameState gameState) throws IllegalArgumentException {
        Player[] players = gameState.getAllPlayer();
        PlayfieldUI[] playfieldUIs = new PlayfieldUI[players.length];


        for (int i = 0; i < players.length; i++) {
            playfieldUIs[i] = new PlayfieldWithPlayerView(getContext());
            playfieldUIs[i].initPlayer(players[i]);
        }

        switch (players.length) {
            case 1:
                drawSingleplayer(playfieldUIs[0],players[0]);
                break;
            case 2:
                drawMultiplayer2();
                break;
            case 3:
                drawMultiplayer3();
                break;
            case 4:
                drawMultiplayer4();
                break;
            default:
                throw new IllegalArgumentException("Invalid amount of players. It should be > 0 && < 4");
        }
    }

    private void drawSingleplayer(PlayfieldUI playfieldUI, Player player) {
        View playfield = (View) playfieldUI;
        playfieldUI.drawPlayer(player);

        playfield.setId(ConstraintLayout.generateViewId());
        binding.playfieldContainer.addView(playfield);
        playfield.setLayoutParams(new ConstraintLayout.LayoutParams(0, 0));

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(binding.playfieldContainer);
        constraintSet.connect(playfield.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
        constraintSet.connect(playfield.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
        constraintSet.connect(playfield.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
        constraintSet.connect(playfield.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
        constraintSet.applyTo(binding.playfieldContainer);
    }

    private void drawMultiplayer2() {
    }

    private void drawMultiplayer3() {
    }

    private void drawMultiplayer4() {
    }

    @Override
    public void drawGameState(GameState gameState) {
        for (Player player:gameState.getAllPlayer()) {

            player.getPlayfieldTurn();
        }
    }

}