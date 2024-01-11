package com.example.a2048mult.ui.game;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import com.example.a2048mult.databinding.FragmentGameBinding;
import com.example.a2048mult.game.logic.GameLogic;
import com.example.a2048mult.game.states.OperateOnGameState;
import com.example.a2048mult.game.states.MoveType;
import com.example.a2048mult.game.states.Player;
import com.example.a2048mult.ui.game.playfield.DrawPlayfieldUI;
import com.example.a2048mult.ui.game.playfield.PlayfieldWithPlayerView;


public class GameFragment extends Fragment implements DrawGameUI {
    private FragmentGameBinding binding;
    private DrawPlayfieldUI[] playfieldUIs;

    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGameBinding.inflate(getLayoutInflater());
        GameLogic.getInstance().initDrawGameUI(this);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void initDrawGameState(OperateOnGameState gameState) throws IllegalArgumentException {
//        Runnable r = () -> {
            Player[] players = gameState.getAllPlayer();
            this.playfieldUIs = new DrawPlayfieldUI[players.length];


            for (int i = 0; i < players.length; i++) {
                this.playfieldUIs[i] = new PlayfieldWithPlayerView(getContext());
                this.playfieldUIs[i].initPlayer(players[i]);
            }

            switch (players.length) {
                case 1:
                    drawSingleplayer(this.playfieldUIs[0], players[0]);
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
//        };
//        HandlerThread handlerThread = new HandlerThread("createChoose", 0);
//        handlerThread.start();
//        Handler handler = new Handler(handlerThread.getLooper());
//        handler.post(r);


        binding.inputObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("!", "cllick");
            }
        });
        binding.inputObject.setOnTouchListener(new InputListener(binding.getRoot().getContext()) {
            @Override
            public void onLeft() {
                GameLogic.getInstance().swipe(MoveType.LEFT);
            }

            @Override
            public void onDown() {
                Log.e("!", "Fragment");
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
    }


    private void drawSingleplayer(DrawPlayfieldUI playfieldUI, Player player) {
        View playfield = (View) playfieldUI;


        playfieldUI.initPlayer(player);
        playfield.setId(ConstraintLayout.generateViewId());

//        ((Activity) this.binding.getRoot().getContext()).runOnUiThread(() -> {
            binding.playfieldContainer.addView(playfield);

//        });
        playfield.setLayoutParams(new ConstraintLayout.LayoutParams(0, 0));
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(binding.playfieldContainer);
        constraintSet.connect(playfield.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
        constraintSet.connect(playfield.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
        constraintSet.connect(playfield.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
        constraintSet.connect(playfield.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);

//        ((Activity) this.binding.getRoot().getContext()).runOnUiThread(() -> {
            constraintSet.applyTo(binding.playfieldContainer);
//        });
    }

    private void drawMultiplayer2() {
    }

    private void drawMultiplayer3() {
    }

    private void drawMultiplayer4() {
    }

    @Override
    public void drawGameState(OperateOnGameState gameState) {
        for (int i = 0; i < gameState.getAllPlayer().length; i++) {
            this.playfieldUIs[i].drawPlayfieldTurn(
                    gameState.getAllPlayer()[i].getPlayfieldTurn()
            );
        }
    }
}