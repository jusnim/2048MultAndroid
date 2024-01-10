package com.example.a2048mult.ui.game.playfield;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.a2048mult.databinding.ViewPlayfieldWithPlayerBinding;
import com.example.a2048mult.game.states.Player;
import com.example.a2048mult.game.states.PlayfieldTurn;

public class PlayfieldWithPlayerView extends ConstraintLayout implements PlayfieldUI {
    private ViewPlayfieldWithPlayerBinding binding;

    private PlayfieldView playfieldView;

    public PlayfieldWithPlayerView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public PlayfieldWithPlayerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayfieldWithPlayerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public PlayfieldWithPlayerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    // TODO score and username better xml
    private void init(@NonNull Context context) {
        binding = ViewPlayfieldWithPlayerBinding.inflate(LayoutInflater.from(context), this, true);

        ViewGroup.LayoutParams playfieldParams = this.binding.playfield.getLayoutParams();
        this.binding.getRoot().removeView(binding.playfield);
        PlayfieldView playfield = new PlayfieldView(context);
        playfield.setLayoutParams(playfieldParams);
        binding.getRoot().addView(playfield);
        this.playfieldView = playfield;
    }

    @Override
    public void initPlayer(Player player) {
        this.playfieldView.initPlayer(player);
        binding.name.setText(player.getName().toString());
    }

    private void updateScore(long score) {
        binding.score.setText(Long.toString(score));
    }

    public void drawPlayfieldBackground(int width, int height) {
        this.playfieldView.drawPlayfieldBackground(width, height);
    }

    @Override
    public void drawPlayfieldTurn(PlayfieldTurn playfieldTurn) {
        this.playfieldView.drawPlayfieldTurn(playfieldTurn);
    }
}
