package com.example.a2048mult.ui.menu;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.a2048mult.databinding.ViewChooseBinding;
import com.example.a2048mult.ui.game.playfield.PlayfieldView;

import java.util.Random;

public class ChooseView extends ConstraintLayout {
    private final int[] playfieldsSizes = new int[]{2,3, 4, 5, 6, 8};
    private final View[] playfields = new View[playfieldsSizes.length];
    private ViewChooseBinding binding;
    private ViewGroup.LayoutParams playfieldParams;
    private int selectedPlayfieldIndex = 0;
    private View currentPlayfield;

    public ChooseView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ChooseView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ChooseView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ChooseView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(@NonNull Context context) {
        binding = ViewChooseBinding.inflate(LayoutInflater.from(context), this, true);
        playfieldParams = binding.playfieldView1.getLayoutParams();
        this.currentPlayfield = binding.playfieldView1;

        initPreview();
        updatePlayfieldSelection();
        binding.beforeButton.setOnClickListener(
                v -> beforeSize()
        );
        binding.nextButton.setOnClickListener(
                v -> nextSize()
        );
    }

    private void nextSize() {
        if (selectedPlayfieldIndex == playfieldsSizes.length - 1) {
            selectedPlayfieldIndex = 0;
        } else {
            selectedPlayfieldIndex++;
        }
        updatePlayfieldSelection();
    }

    private void beforeSize() {
        if (selectedPlayfieldIndex - 1 < 0) {
            selectedPlayfieldIndex = playfieldsSizes.length - 1;
        } else {
            selectedPlayfieldIndex--;
        }
        updatePlayfieldSelection();
    }

    private void updatePlayfieldSelection() {
        if (playfields[selectedPlayfieldIndex] == null) {
            playfields[selectedPlayfieldIndex] = createPlayfieldPreview(playfieldsSizes[selectedPlayfieldIndex]);
        }

        binding.getRoot().removeView(this.currentPlayfield);
        this.currentPlayfield =  playfields[selectedPlayfieldIndex];
        this.currentPlayfield.setLayoutParams(this.playfieldParams);
        binding.getRoot().addView(this.currentPlayfield);
        binding.previewText.setText(getSelectedPlayfieldSize() + "x" + getSelectedPlayfieldSize());
    }

    public int getSelectedPlayfieldSize() {
        return playfieldsSizes[selectedPlayfieldIndex];
    }

    private void initPreview() {
        playfields[0] = createPlayfieldPreview(playfieldsSizes[0]);
    }


    private View createPlayfieldPreview(int size) {
        PlayfieldView[] playfield = new PlayfieldView[1];
        playfield[0] = new PlayfieldView(binding.getRoot().getContext());
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Random rd = new Random();

                playfield[0].drawPlayfieldBackground(size, size);
                int[][] levels = new int[size][size];
                for (int y = 0; y < size; y++) {
                    for (int x = 0; x < size; x++) {
                        levels[y][x] = rd.nextInt(12);
                    }
                }
                playfield[0].drawPlayfieldState(levels);
            }
        };

        HandlerThread handlerThread = new HandlerThread("createChoose",0);
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        handler.post(r);

        return playfield[0];
    }
}