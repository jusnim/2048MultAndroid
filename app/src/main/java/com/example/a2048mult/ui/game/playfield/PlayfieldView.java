package com.example.a2048mult.ui.game.playfield;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.a2048mult.databinding.ViewPlayfieldBinding;
import com.example.a2048mult.game.states.GameTile;
import com.example.a2048mult.game.states.Player;
import com.example.a2048mult.game.states.PlayfieldTurn;
import com.example.a2048mult.game.states.PlayfieldTurnAnimTuple;
import com.example.a2048mult.game.states.PlayfieldTurnAnimationType;


public class PlayfieldView extends ConstraintLayout implements PlayfieldUI {
    private ViewPlayfieldBinding binding;
    private ConstraintSet constraintSet;

    private ConstraintLayout playfieldContainer;
    private ConstraintLayout backgroundContainer;

    private ConstraintLayout.LayoutParams playfieldLayoutParams;
    private ConstraintLayout.LayoutParams backgroundLayoutParams;

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

        this.playfieldContainer = binding.playfieldContainer;
        binding.getRoot().removeView(this.playfieldContainer);

        this.playfieldLayoutParams = (LayoutParams) binding.playfieldContainer.getLayoutParams();
        this.backgroundLayoutParams = (LayoutParams) binding.backgroundContainer.getLayoutParams();
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
    private void drawPlayfieldState(int[][] data, Boolean inBackground) {

        int width = data.length;
        int height = data[0].length;

        allViews = new View[height][width];
        constraintSet = new ConstraintSet();

        ConstraintLayout container = new ConstraintLayout(getContext());
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
                doConstraintsBasedOnPosition(x, y, width, height);
            }
        }

        constraintSet.applyTo(container);


        ((Activity) this.binding.getRoot().getContext()).runOnUiThread(() -> {
            binding.getRoot().addView(container);
        });

        if (inBackground) {
            container.setLayoutParams(this.backgroundLayoutParams);
            this.backgroundContainer = container;
        } else {
            container.setLayoutParams(this.playfieldLayoutParams);
            this.playfieldContainer = container;
        }

    }

    private void doConstraintsBasedOnPosition(int x, int y, int width, int height) {
        if (x == 0) {
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, PlayfieldConfig.marginTileInDP);
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.END, allViews[y][x + 1].getId(), ConstraintSet.START, PlayfieldConfig.marginTileInDP);
        } else if (x == width - 1) {
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.START, allViews[y][x - 1].getId(), ConstraintSet.END, PlayfieldConfig.marginTileInDP);
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, PlayfieldConfig.marginTileInDP);
        } else {
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.START, allViews[y][x - 1].getId(), ConstraintSet.END, PlayfieldConfig.marginTileInDP);
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.END, allViews[y][x + 1].getId(), ConstraintSet.START, PlayfieldConfig.marginTileInDP);
        }

        if (y == 0) {
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, PlayfieldConfig.marginTileInDP);
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.BOTTOM, allViews[y + 1][x].getId(), ConstraintSet.TOP, PlayfieldConfig.marginTileInDP);
        } else if (y == height - 1) {
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.TOP, allViews[y - 1][x].getId(), ConstraintSet.BOTTOM, PlayfieldConfig.marginTileInDP);
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, PlayfieldConfig.marginTileInDP);
        } else {
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.TOP, allViews[y - 1][x].getId(), ConstraintSet.BOTTOM, PlayfieldConfig.marginTileInDP);
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.BOTTOM, allViews[y + 1][x].getId(), ConstraintSet.TOP, PlayfieldConfig.marginTileInDP);
        }
    }


    void drawPlayfieldBackground(int width, int height) {
        int[][] bgData = new int[height][width];
        drawPlayfieldState(bgData, true);
    }

    void drawPlayfieldState(int[][] data) {

        int[][] copyData = data.clone();
        for (int i = 0; i < data.length; i++) {
            copyData[i] = data[i].clone();
        }

        for (int y = 0; y < copyData.length; y++) {
            for (int x = 0; x < copyData[0].length; x++) {
                if (copyData[y][x] == 0) {
                    copyData[y][x] = -1;
                }
            }
        }
        drawPlayfieldState(copyData, false);
    }

    @Override
    public void drawPlayfieldTurn(PlayfieldTurn playfieldTurn) {
        PlayfieldTurnAnimTuple<PlayfieldTurnAnimationType, GameTile> animation;

        animation = playfieldTurn.pollNextAnimation();
        while (animation != null) {

            doAnimation(animation);
            animation = playfieldTurn.pollNextAnimation();
        }
    }

    private void doAnimation
            (PlayfieldTurnAnimTuple<PlayfieldTurnAnimationType, GameTile> animation) {
        switch (animation.type) {
            case SPAWN:
                spawnTileAt(animation.tile.getNewX(), animation.tile.getNewY(), animation.tile.getLevel());
                break;
            case MOVE:
                moveTile(animation.tile.getOldX(), animation.tile.getOldY(), animation.tile.getNewX(), animation.tile.getNewY());
                break;
            case REMOVE:
                removeTile(animation.tile.getNewX(), animation.tile.getNewY());
                break;
        }
    }


    public ObjectAnimator spawnTileAt(int x, int y, int level) {
        return replaceTile(x, y, level, PlayfieldConfig.animationSpawnDurationInMs);
    }

    public ObjectAnimator removeTile(int x, int y) {
        return replaceTile(x, y, PlayfieldConfig.invisibleTile, PlayfieldConfig.animationDurationInMs);
    }

    public void mergeTile(int x1, int y1, int x2, int y2, int level) {
        moveTile(x1, y1, x2, y2).addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                removeTile(x1, y1);
                removeTile(x2, y2);
                spawnTileAt(x2, y2, level);
            }
        });
    }

    private ObjectAnimator moveTile(int xFrom, int yFrom, int xTo, int yTo) {
        final ObjectAnimator[] animation = new ObjectAnimator[1];
        ((Activity) binding.getRoot().getContext()).runOnUiThread(() -> {
            // TODO save into ALLVIEWs
            View fromTile = allViews[yFrom][xFrom];
            View toTile = allViews[yTo][xTo];


            float xDiff = toTile.getX() - fromTile.getX();
            if (xDiff != 0) {
                animation[0] = ObjectAnimator.ofFloat(fromTile, "translationX", xDiff);
            } else {
                float yDiff = toTile.getY() - fromTile.getY();
                animation[0] = ObjectAnimator.ofFloat(fromTile, "translationY", yDiff);
            }

            animation[0].setDuration(PlayfieldConfig.animationDurationInMs);
            animation[0].start();
            // TODO wenn zuende
//        removeTile(xFrom,yFrom);
        });
        return animation[0];
    }

    private ObjectAnimator replaceTile(int x, int y, int level, long animationDuration) {
        ObjectAnimator[] scaleUp = new ObjectAnimator[1];
        ((Activity) binding.getRoot().getContext()).runOnUiThread(() -> {
            constraintSet.clone(this.playfieldContainer);

            View tileToRemove = allViews[y][x];
            int id = tileToRemove.getId();
            constraintSet.clear(tileToRemove.getId());
            int index = this.playfieldContainer.indexOfChild(tileToRemove);
            this.playfieldContainer.removeView(tileToRemove);

            PlayfieldTileView newTile = new PlayfieldTileView(this.playfieldContainer.getContext());
            newTile.setLevel(level);
            newTile.setId(id);
            this.playfieldContainer.addView(newTile, index);

            doConstraintsBasedOnPosition(x, y, allViews.length, allViews[0].length);
            constraintSet.applyTo(this.playfieldContainer);

            allViews[y][x] = newTile;

            scaleUp[0] = ObjectAnimator.ofPropertyValuesHolder(newTile,
                    PropertyValuesHolder.ofFloat("scaleX", 0.2f, 1f),
                    PropertyValuesHolder.ofFloat("scaleY", 0.2f, 1f));


            scaleUp[0].setDuration(animationDuration);
            scaleUp[0].start();
        });

        return scaleUp[0];
    }

    @Override
    public void initPlayer(Player player) {
        this.drawPlayfieldBackground(player.getPlayfieldState().getField().length, player.getPlayfieldState().getField().length);
        this.drawPlayfieldState(player.getPlayfieldState().getField());


//        this.backgroundContainer.bringToFront();
//        this.playfieldContainer.bringToFront();
    }
}