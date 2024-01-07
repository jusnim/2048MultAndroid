package com.example.a2048mult.ui.game.playfield;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.a2048mult.databinding.ViewPlayfieldBinding;
import com.example.a2048mult.databinding.ViewPlayfieldWithPlayerBinding;

public class PlayfieldWithPlayerView extends PlayfieldView {
    private ViewPlayfieldWithPlayerBinding binding;

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

    private void init(@NonNull Context context) {
        binding = ViewPlayfieldWithPlayerBinding.inflate(LayoutInflater.from(context));

        ViewGroup.LayoutParams playfieldparams = this.binding.playfield.getLayoutParams();
        this.binding.getRoot().removeView(binding.playfield);

        super.getBinding().getRoot().setLayoutParams(playfieldparams);
//
//        ConstraintLayout playfield = super.getBinding().playfield;
//        super.getBinding().getRoot().removeView(playfield);
//
//        playfield.setLayoutParams(playfieldparams);
//        binding.getRoot().addView(playfield);
    }
}
