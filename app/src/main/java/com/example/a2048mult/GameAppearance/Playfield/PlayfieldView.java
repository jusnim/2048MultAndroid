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

import java.util.Arrays;

public class PlayfieldView extends ConstraintLayout {
    private ViewPlayfieldBinding binding;

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

        // changing margin to borders
        binding.testTop.setGuidelinePercent(PlayfieldConfig.marginBorderFloat);
        binding.testBot.setGuidelinePercent(1f - PlayfieldConfig.marginBorderFloat);
        binding.testRight.setGuidelinePercent(1f - PlayfieldConfig.marginBorderFloat);
        binding.testLeft.setGuidelinePercent(PlayfieldConfig.marginBorderFloat);

    }


    void setPlayfieldSizing(int width, int height) {

        View[] containerContent = new View[2 * height - 1];
        ConstraintSet containerConstraintSet = new ConstraintSet();
        int containerViewCount = 0;

        for (int y = 0; y < height; y++) {

            // add line
            containerContent = addNewRow(containerConstraintSet, containerContent, containerViewCount);
            containerViewCount++;

            // adding space
            if (containerViewCount < 2 * height - 1) {
                containerContent = addNewLineSpace(containerConstraintSet, containerContent, containerViewCount);
                containerViewCount++;
            }

            ConstraintLayout line = (ConstraintLayout) containerContent[y*2];
            View[] lineContent = new View[2 * width - 1];
            ConstraintSet lineConstraintSet = new ConstraintSet();
            int lineViewCount = 0;

            for (int x = 0; x < width; x++) {
                // TODO change tile level --> take level from GameState Object
                // add tile

                lineContent = addNewTile(lineConstraintSet, line,lineContent, lineViewCount,5);
                lineViewCount++;

                // add space
                if (lineViewCount < 2 * width - 1) {
                    Log.e("!","call");
                    lineContent = addNewSpace(lineConstraintSet, line, lineContent, lineViewCount);
                    lineViewCount++;
                }
            }

            lineConstraintSet.clone(line);
            lineConstraintSet.connect(lineContent[lineContent.length - 1].getId(), lineConstraintSet.END, lineConstraintSet.PARENT_ID, lineConstraintSet.END, 0);
            lineConstraintSet.applyTo(line);
        }

        containerConstraintSet.clone(binding.playfieldContainer);
        containerConstraintSet.connect(containerContent[containerContent.length - 1].getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
        containerConstraintSet.applyTo(binding.playfieldContainer);

    }

    private View[] addNewRow(ConstraintSet constraintSet, View[] containerContent, int containerViewCount) {
        ConstraintLayout row = new ConstraintLayout(binding.playfieldContainer.getContext());
        row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0));
        return addNewLine(constraintSet, containerContent, containerViewCount, row);
    }

    private View[] addNewTile(ConstraintSet constraintSet, ConstraintLayout line, View[] lineContent, int lineViewCount, int level) {
        PlayfieldTileView tile = new PlayfieldTileView(line.getContext());
        tile.setLevel(level);
        tile.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT));
        return addNewItemInLine(constraintSet,line,lineContent,lineViewCount,tile);
    }

    private View[] addNewLineSpace(ConstraintSet constraintSet, View[] containerContent, int containerViewCount) {
        Space space = new Space(binding.playfieldContainer.getContext());
        space.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, PlayfieldConfig.marginTileInDP));
        return addNewLine(constraintSet, containerContent, containerViewCount, space);
    }

    private View[] addNewSpace(ConstraintSet constraintSet, ConstraintLayout line, View[] lineContent, int lineViewCount) {
        Space space = new Space(binding.playfieldContainer.getContext());
        space.setLayoutParams(new LayoutParams(PlayfieldConfig.marginTileInDP, LayoutParams.MATCH_PARENT));
        return addNewItemInLine(constraintSet,line,lineContent,lineViewCount,space);
    }

    private View[] addNewLine(ConstraintSet constraintSet, View[] containerContent, int containerViewCount, View itemToAdd) {
        itemToAdd.setId(ConstraintLayout.generateViewId());

        binding.playfieldContainer.addView(itemToAdd);
        containerContent[containerViewCount] = itemToAdd;

        constraintSet.clone(binding.playfieldContainer);

        if (containerViewCount < 1) {
            addConstraintsLine(constraintSet, null, itemToAdd);
        } else {
            addConstraintsLine(constraintSet, containerContent[containerViewCount - 1], itemToAdd);
        }
        constraintSet.applyTo(binding.playfieldContainer);

        return containerContent;
    }

    private View[] addNewItemInLine(ConstraintSet constraintSet, ConstraintLayout line, View[] lineContent, int lineViewCount, View itemToAdd) {
        itemToAdd.setId(ConstraintLayout.generateViewId());

        line.addView(itemToAdd);
        lineContent[lineViewCount] = itemToAdd;

        constraintSet.clone(line);

        if (lineViewCount < 1) {
            addConstraintsItem(constraintSet, null, itemToAdd);
        } else {
            addConstraintsItem(constraintSet, lineContent[lineViewCount - 1], itemToAdd);
        }
        constraintSet.applyTo(line);

        return lineContent;
    }

    private void addConstraintsLine(ConstraintSet constraintSet, View viewBefore, View view) {
        // connect to sides
        constraintSet.connect(view.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
        constraintSet.connect(view.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);

        if (viewBefore == null) {
            // connect first line to parent
            constraintSet.connect(view.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
        } else {
            constraintSet.connect(viewBefore.getId(), ConstraintSet.BOTTOM, view.getId(), ConstraintSet.TOP, 0);
            constraintSet.connect(view.getId(), ConstraintSet.TOP, viewBefore.getId(), ConstraintSet.BOTTOM, 0);
        }
    }

    private void addConstraintsItem(ConstraintSet constraintSet, View viewBefore, View view) {
        // connect top and bttom
        constraintSet.connect(view.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
        constraintSet.connect(view.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);

        if (viewBefore == null) {
            // connect first item to parent
            constraintSet.connect(view.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
        } else {
            constraintSet.connect(viewBefore.getId(), ConstraintSet.END, view.getId(), ConstraintSet.START, 0);
            constraintSet.connect(view.getId(), ConstraintSet.START, viewBefore.getId(), ConstraintSet.END, 0);
        }
    }

}