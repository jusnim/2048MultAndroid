package com.example.a2048mult.GameAppearance.Playfield;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Guideline;
import androidx.fragment.app.Fragment;

import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Space;

import com.example.a2048mult.R;
import com.example.a2048mult.databinding.ViewPlayfieldBinding;

public class PlayfieldView extends LinearLayout {
    private ViewPlayfieldBinding binding;
    private LinearLayout container;

    public PlayfieldView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public PlayfieldView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayfieldView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public PlayfieldView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(@NonNull Context context) {
        binding = ViewPlayfieldBinding.inflate(LayoutInflater.from(context), this, true);


        //  creating margin for playfield border
        float margin = 0.10f;
        ConstraintLayout.LayoutParams vertical = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT);
        vertical.orientation = LinearLayout.VERTICAL;

        ConstraintLayout.LayoutParams vertical2 = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT);
        vertical2.orientation = LinearLayout.VERTICAL;

        ConstraintLayout.LayoutParams horizontal = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT);
        horizontal.orientation = LinearLayout.HORIZONTAL;

        ConstraintLayout.LayoutParams horizontal2 = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT);
        horizontal2.orientation = LinearLayout.HORIZONTAL;


        Guideline guidelineTop = new Guideline(binding.getRoot().getContext());
        Guideline guidelineBottom = new Guideline(binding.getRoot().getContext());
        Guideline guidelineLeft = new Guideline(binding.getRoot().getContext());
        Guideline guidelineRight = new Guideline(binding.getRoot().getContext());

        guidelineTop.setId(Guideline.generateViewId());
        guidelineBottom.setId(Guideline.generateViewId());
        guidelineLeft.setId(Guideline.generateViewId());
        guidelineRight.setId(Guideline.generateViewId());

        guidelineTop.setLayoutParams(horizontal);
        guidelineBottom.setLayoutParams(horizontal2);
        guidelineLeft.setLayoutParams(vertical);
        guidelineRight.setLayoutParams(vertical2);


        binding.getRoot().addView(guidelineTop);
        binding.getRoot().addView(guidelineBottom);
        binding.getRoot().addView(guidelineLeft);
        binding.getRoot().addView(guidelineRight);

        guidelineTop.setGuidelinePercent(margin);
        guidelineBottom.setGuidelinePercent(1f - margin);
        guidelineLeft.setGuidelinePercent(margin);
        guidelineRight.setGuidelinePercent(1f - margin);


        this.container = new LinearLayout(binding.getRoot().getContext());
        binding.getRoot().addView(container);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setId(LinearLayout.generateViewId());

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(binding.getRoot());

        constraintSet.connect(container.getId(), ConstraintSet.TOP, guidelineTop.getId(), ConstraintSet.TOP, 0);
        constraintSet.connect(container.getId(), ConstraintSet.BOTTOM, guidelineBottom.getId(), ConstraintSet.BOTTOM, 0);
        constraintSet.connect(container.getId(), ConstraintSet.START, guidelineRight.getId(), ConstraintSet.START, 0);
        constraintSet.connect(container.getId(), ConstraintSet.END, guidelineLeft.getId(), ConstraintSet.END, 0);

        constraintSet.applyTo(binding.getRoot());
    }


    void setPlayfieldSizing(int width, int height) {


//        float marginX = 100/width;
//        float marginY = 100/height;

        float marginX = (100 - PlayfieldConfig.marginInPercentage) / width;
//        float marginX = (100 - PlayfieldConfig.marginInPercentage)/width;

        for (int y = 0; y < height; y++) {
            LinearLayout row = new LinearLayout(container.getContext());
            row.setBackgroundColor(12);
            container.addView(row);



            LinearLayout linearWrapper;
            LinearLayout.LayoutParams linearWrParams;

            for (int x = 0; x < width; x++) {
                // TODO change tile level --> take level from GameState Object
                addNewSpace(row);

                linearWrapper = new LinearLayout(row.getContext());
                linearWrParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                linearWrParams.weight = 1;
                linearWrapper.setLayoutParams(linearWrParams);

                // wrap into linearLayout
                PlayfieldTileView tile = new PlayfieldTileView(row.getContext());
                tile.setLevel(2);
                linearWrapper.addView(tile);
                row.addView(linearWrapper);


                addNewSpace(row);
            }
        }

    }
    private void addNewSpace(LinearLayout row){
        Space space = new Space(container.getContext());
        LinearLayout.LayoutParams spaceParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        spaceParams.weight = 1;
        space.setLayoutParams(spaceParams);
        row.addView(space);
    }
}