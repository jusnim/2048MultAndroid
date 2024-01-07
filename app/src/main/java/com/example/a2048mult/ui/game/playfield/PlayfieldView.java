package com.example.a2048mult.ui.game.playfield;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.a2048mult.databinding.ViewPlayfieldBinding;
import com.example.a2048mult.game.logic.Player;


public class PlayfieldView extends ConstraintLayout implements PlayfieldUI {

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
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                doConstraintsBasedOnPosition(x,y,width,height);
            }
        }
        constraintSet.applyTo(container);
    }

    private void doConstraintsBasedOnPosition(int x,int y, int width, int height){
        if (x == 0) {
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, PlayfieldConfig.marginTileInDP);
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.END, allViews[y][x+1].getId(), ConstraintSet.START, PlayfieldConfig.marginTileInDP);
        }  else if (x == width - 1) {
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.START, allViews[y][x-1].getId(), ConstraintSet.END, PlayfieldConfig.marginTileInDP);
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, PlayfieldConfig.marginTileInDP);
        } else {
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.START, allViews[y][x-1].getId(), ConstraintSet.END, PlayfieldConfig.marginTileInDP);
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.END, allViews[y][x+1].getId(), ConstraintSet.START, PlayfieldConfig.marginTileInDP);
        }

        if (y == 0) {
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, PlayfieldConfig.marginTileInDP);
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.BOTTOM, allViews[y+1][x].getId(), ConstraintSet.TOP, PlayfieldConfig.marginTileInDP);
        } else if (y == height - 1) {
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.TOP, allViews[y-1][x].getId(), ConstraintSet.BOTTOM, PlayfieldConfig.marginTileInDP);
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, PlayfieldConfig.marginTileInDP);
        } else {
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.TOP, allViews[y-1][x].getId(), ConstraintSet.BOTTOM, PlayfieldConfig.marginTileInDP);
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.BOTTOM, allViews[y+1][x].getId(), ConstraintSet.TOP, PlayfieldConfig.marginTileInDP);
        }
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


    public ObjectAnimator spawnTileAt(int x, int y, int level) throws InterruptedException {
        return replaceTile(x, y, level, PlayfieldConfig.animationSpawnDurationInMs);
    }

    public ObjectAnimator removeTile(int x, int y) throws InterruptedException {
        return replaceTile(x, y, PlayfieldConfig.invisibleTile, PlayfieldConfig.animationDurationInMs);
    }

    public void mergeTile(int x1, int y1, int x2, int y2, int level) {
        moveTile(x1, y1, x2, y2).addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                try {
                    removeTile(x1, y1);
                    removeTile(x2, y2);
                    spawnTileAt(x2, y2, level);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    // TODO private
    public ObjectAnimator moveTile(int xFrom, int yFrom, int xTo, int yTo) {

        View fromTile = allViews[yFrom][xFrom];
        View toTile = allViews[yTo][xTo];

        ObjectAnimator animation;
        float xDiff = toTile.getX() - fromTile.getX();
        if (xDiff != 0) {
            animation = ObjectAnimator.ofFloat(fromTile, "translationX", xDiff);
        } else {
            float yDiff = toTile.getY() - fromTile.getY();
            animation = ObjectAnimator.ofFloat(fromTile, "translationY", yDiff);
        }
        animation.setDuration(PlayfieldConfig.animationDurationInMs);
        animation.start();
        // TODO wenn zuende
//        removeTile(xFrom,yFrom);
        return animation;
    }


    private ObjectAnimator replaceTile(int x, int y, int level, long animationDuration) throws InterruptedException {
        constraintSet.clone(binding.playfieldContainer);

        View tileToRemove = allViews[y][x];

        int id = tileToRemove.getId();
        constraintSet.clear(tileToRemove.getId());
        int index = binding.playfieldContainer.indexOfChild(tileToRemove);
        binding.playfieldContainer.removeView(tileToRemove);

        PlayfieldTileView newTile = new PlayfieldTileView(binding.playfieldContainer.getContext());
        newTile.setLevel(level);
        newTile.setId(id);
        binding.playfieldContainer.addView(newTile, index);

        doConstraintsBasedOnPosition(x,y,allViews.length,allViews[0].length);

        constraintSet.applyTo(binding.playfieldContainer);
        allViews[y][x] = newTile;

        ObjectAnimator scaleUp = ObjectAnimator.ofPropertyValuesHolder(newTile,
                PropertyValuesHolder.ofFloat("scaleX", 0.2f, 1f),
                PropertyValuesHolder.ofFloat("scaleY", 0.2f, 1f));
        scaleUp.setDuration(animationDuration);

        scaleUp.start();

        return scaleUp;
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