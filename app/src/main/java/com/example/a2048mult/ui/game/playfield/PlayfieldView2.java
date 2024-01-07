package com.example.a2048mult.ui.game.playfield;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.a2048mult.databinding.ViewPlayfieldBinding;
import com.example.a2048mult.game.logic.Player;


public class PlayfieldView2 extends ConstraintLayout implements PlayfieldUI {

    // TODO make each PLayfield a thread and playfieldtile a theraed
    private ViewPlayfieldBinding binding;
    private ConstraintSet constraintSet;


    /**
     * [y][x]
     * y - represents lines without spaces
     * x- represents tiles and spaces
     * --> 2*x - represents every tile in a line
     */
    private View[][] allViews;

    public PlayfieldView2(@NonNull Context context) {
        super(context);
        init(context);
    }

    public PlayfieldView2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayfieldView2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public PlayfieldView2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    /**
     * initialize view with margin given in Config
     *
     * @param context is passed down by constructor
     * @see PlayfieldConfig#marginBorderFloat
     */
    private void init(@NonNull Context context) {

        binding = ViewPlayfieldBinding.inflate(LayoutInflater.from(context), this, true);

        // changing marginToBorders
        binding.testTop.setGuidelinePercent(PlayfieldConfig.marginBorderFloat);
        binding.testBot.setGuidelinePercent(1f - PlayfieldConfig.marginBorderFloat);
        binding.testRight.setGuidelinePercent(1f - PlayfieldConfig.marginBorderFloat);
        binding.testLeft.setGuidelinePercent(PlayfieldConfig.marginBorderFloat);
    }

    public ViewPlayfieldBinding getBinding() {
        return binding;
    }


    /**
     * draws a Playfield with the given data of levels
     * Playfield will be a View of type ConstraintLayout
     * The inner connections and handling of their margin/distance is handled by the combination of other ConstraintLayouts and using of Constraints
     *
     * @param data - 2-dimensional array, with [y][x] representing each Tile with their level
     * @implNote level == 0, represents a placeholder tile, used in the background
     * @implNote level == -1, invisible tile
     * @see ConstraintLayout
     */
    private void drawPlayfieldStateInContainer(int[][] data, ConstraintLayout container) {
        // TODO remove lines in implementation, so less nesting, so faster

        int width = data.length;
        int height = data[0].length;

        allViews = new View[height][width];
        constraintSet = new ConstraintSet();
        PlayfieldTileView newTile;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                newTile = new PlayfieldTileView(container.getContext());
                newTile.setLevel(data[y][x]);
                newTile.setLayoutParams(new LayoutParams(0, 0));
                newTile.setId(ConstraintLayout.generateViewId());
                container.addView(newTile);
                allViews[y][x] = newTile;
            }
        }

        constraintSet.clone(container);
        constraintSet.connect(allViews[0][0].getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
        constraintSet.connect(allViews[0][0].getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, 0);
        constraintSet.connect(allViews[0][0].getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, 0);
        constraintSet.connect(allViews[0][0].getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, 0);
//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                if (x == 0) {
//                    constraintSet.connect(allViews[x][y].getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, PlayfieldConfig.marginTileInDP);
//                    constraintSet.connect(allViews[x][y].getId(), ConstraintSet.END, allViews[x+1][y].getId(), ConstraintSet.START, PlayfieldConfig.marginTileInDP);
//                }  else if (x == width - 1) {
//                    constraintSet.connect(allViews[x][y].getId(), ConstraintSet.START, allViews[x-1][y].getId(), ConstraintSet.END, PlayfieldConfig.marginTileInDP);
//                    constraintSet.connect(allViews[x][y].getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, PlayfieldConfig.marginTileInDP);
//                } else {
//                    constraintSet.connect(allViews[x][y].getId(), ConstraintSet.START, allViews[x-1][y].getId(), ConstraintSet.END, PlayfieldConfig.marginTileInDP);
//                    constraintSet.connect(allViews[x][y].getId(), ConstraintSet.END, allViews[x+1][y].getId(), ConstraintSet.START, PlayfieldConfig.marginTileInDP);
//                }
//
//                if (y == 0) {
//                    constraintSet.connect(allViews[x][y].getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, PlayfieldConfig.marginTileInDP);
//                    constraintSet.connect(allViews[x][y].getId(), ConstraintSet.BOTTOM, allViews[x][y+1].getId(), ConstraintSet.TOP, PlayfieldConfig.marginTileInDP);
//                } else if (y == height - 1) {
//                    constraintSet.connect(allViews[x][y].getId(), ConstraintSet.TOP, allViews[x][y-1].getId(), ConstraintSet.BOTTOM, PlayfieldConfig.marginTileInDP);
//                    constraintSet.connect(allViews[x][y].getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, PlayfieldConfig.marginTileInDP);
//                } else {
//                    constraintSet.connect(allViews[x][y].getId(), ConstraintSet.TOP, allViews[x][y-1].getId(), ConstraintSet.BOTTOM, PlayfieldConfig.marginTileInDP);
//                    constraintSet.connect(allViews[x][y].getId(), ConstraintSet.BOTTOM, allViews[x][y+1].getId(), ConstraintSet.TOP, PlayfieldConfig.marginTileInDP);
//                }
//            }
//        }
        constraintSet.applyTo(container);
    }

    // TODO private
    public void drawPlayfieldBackground(int width, int height) {
        int[][] bgData = new int[height][width];
        drawPlayfieldStateInContainer(bgData, binding.backgroundContainer);
    }

    // TODO private
    public void drawPlayfieldState(int[][] data) {
        for (int y = 0; y < data.length; y++) {
            for (int x = 0; x < data[0].length; x++) {
                if (data[y][x] == 0) {
                    data[y][x] = -1;
                }
            }
        }
        drawPlayfieldStateInContainer(data, binding.playfieldContainer);
    }


    @Override
    public void initPlayer(Player player) {
        // TODO body method
    }

    @Override
    public void drawPlayer(Player player) {
        // TODO body method

        drawPlayfieldBackground(player.getPlayfieldState().getField().length, player.getPlayfieldState().getField().length);

        this.drawPlayfieldState(player.getPlayfieldState().getField());

    }
}