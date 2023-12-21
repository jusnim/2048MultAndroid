package com.example.a2048mult.ui.menu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.a2048mult.databinding.ViewChooseBinding;
import com.example.a2048mult.ui.game.playfield.PlayfieldViewImpl;

import java.util.Random;

public class ChooseView extends ConstraintLayout {
    private final int[] playfieldsSizes = new int[]{3, 4, 5, 6, 8};
    private final View[] playfields = new View[playfieldsSizes.length];
    private ViewChooseBinding binding;
    private ViewGroup.LayoutParams playfieldParams;
    private int selectedPlayfieldIndex = 0;
    private PlayfieldViewImpl currentPlayfield;

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
        this.currentPlayfield = (PlayfieldViewImpl) playfields[selectedPlayfieldIndex];
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
        Random rd = new Random();
        PlayfieldViewImpl playfield = new PlayfieldViewImpl(binding.getRoot().getContext());
        playfield.drawPlayfieldBackground(size, size);
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                playfield.spawnTileAt(x, y, rd.nextInt(12));
            }
        }
        return playfield;
    }
}