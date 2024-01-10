package com.example.a2048mult.ui.game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import com.example.a2048mult.databinding.FragmentGameBinding;
import com.example.a2048mult.game.states.GameState;
import com.example.a2048mult.game.logic.InGameControl;
import com.example.a2048mult.game.states.Player;
import com.example.a2048mult.ui.game.playfield.PlayfieldUI;
import com.example.a2048mult.ui.game.playfield.PlayfieldWithPlayerView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


public class GameFragment extends Fragment implements GameUI {

    public static final String GAMELOGIC = "gamelogic";

    private FragmentGameBinding binding;

    private PlayfieldUI[] playfieldUIs;
    private Player[] players;

    private InGameControl inGameControl;

    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.inGameControl = byteArrayToGameState(getArguments().getByteArray(GAMELOGIC));
        }
    }

    private InGameControl byteArrayToGameState(byte[] byteArray) {
        try {
            ByteArrayInputStream isB = new ByteArrayInputStream(byteArray);
            ObjectInputStream isO = new ObjectInputStream(isB);
            return (InGameControl) isO.readObject();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGameBinding.inflate(getLayoutInflater());
        initDrawGameState(this.inGameControl.getGameState());

        binding.getRoot().setOnTouchListener(new InputListener(binding.getRoot().getContext()) {
            @Override
            public void onLeft() {
            }

            @Override
            public void onDown() {
//                playfield.mergeTile(0,0,0,2,6);
            }

            @Override
            public void onUp() {
//                protoSpawnTilesPlayfield(4,4);
            }

            @Override
            public void onRight() {
//                protoRemovePlayfield(4,4);
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
        this.players = gameState.getAllPlayer();
        this.playfieldUIs = new PlayfieldUI[players.length];


        for (int i = 0; i < this.players.length; i++) {
            playfieldUIs[i] = new PlayfieldWithPlayerView(getContext());
            playfieldUIs[i].initPlayer(players[i]);
        }

        switch (this.players.length) {
            case 1:
                drawSingleplayer();
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

    private void drawSingleplayer() {
        View playfield = (View) playfieldUIs[0];
        ((PlayfieldUI) playfield).drawPlayer(players[0]);

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

    }

}