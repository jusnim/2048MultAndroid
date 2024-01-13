package com.example.a2048mult.ui.game.playfield;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.a2048mult.databinding.ViewPlayfieldBinding;
import com.example.a2048mult.game.states.Player;
import com.example.a2048mult.game.states.PlayfieldTurn;
import com.example.a2048mult.game.states.PlayfieldTurnAnimTuple;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.Stack;


public class PlayfieldView extends ConstraintLayout implements DrawPlayfieldUI {
    private ViewPlayfieldBinding binding;
    private ConstraintSet constraintSet;

    private ConstraintLayout container;
    private ConstraintLayout.LayoutParams containerLayoutParams;

    private Queue<PlayfieldTileView> nonUsedTiles = new ArrayDeque<>(16);
    private Stack<PlayfieldTileView> tileReferenceStack = new Stack<>();

    private Handler handler;

    /**
     * [y][x]
     * y - represents lines without spaces
     * x- represents tiles and spaces
     * --> 2*x - represents every tile in a line
     */
    private PlayfieldTileView[][] playfieldViews;


    // also used for determining position of new tile
    private PlayfieldTileView[][] backgroundViews;
    private int counter = 0;

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
        HandlerThread moveThread = new HandlerThread("moveThread", 3);
        moveThread.start();
        binding = ViewPlayfieldBinding.inflate(LayoutInflater.from(context), this, true);


        this.container = binding.container;
        binding.getRoot().removeView(this.container);
        this.containerLayoutParams = (LayoutParams) binding.container.getLayoutParams();
//        ((ViewGroup)this.container.getParent()).removeView(binding.container);


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
        ConstraintLayout newContainer;
        PlayfieldTileView[][] allViews;
        if (inBackground) {
            newContainer = new ConstraintLayout(getContext());
            backgroundViews = new PlayfieldTileView[height][width];
            allViews = backgroundViews;
        } else {
            newContainer = this.container;
            playfieldViews = new PlayfieldTileView[height][width];
            allViews = playfieldViews;
        }
        constraintSet = new ConstraintSet();

        PlayfieldTileView newTile;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                newTile = new PlayfieldTileView(getContext());
                newTile.setLevel(data[y][x]);
                newTile.setLayoutParams(new LayoutParams(0, 0));
                newTile.setId(ConstraintLayout.generateViewId());
                newContainer.addView(newTile);
                allViews[y][x] = newTile;

                if (data[y][x] == PlayfieldConfig.invisibleTile) {
                    this.nonUsedTiles.add(newTile);
                }

            }
        }


        constraintSet.clone(newContainer);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (inBackground) {
                    doConstraintsBasedOnPosition(x, y, width, height, allViews, 0);
                    continue;
                }
                doConstraintsBasedOnPosition(x, y, width, height, allViews, PlayfieldConfig.marginTileInDP);
            }
        }
        constraintSet.applyTo(newContainer);


        if (inBackground) {
            ((Activity) this.binding.getRoot().getContext()).runOnUiThread(() -> {
                binding.getRoot().addView(newContainer);
            });
        }

        this.container = newContainer;
        this.container.setLayoutParams(this.containerLayoutParams);

    }

    private void doConstraintsBasedOnPosition(int x, int y, int width, int height, PlayfieldTileView[][] allViews, int plusMargin) {
        if (x == 0) {
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, PlayfieldConfig.marginTileInDP);
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.END, backgroundViews[y][x + 1].getId(), ConstraintSet.START, PlayfieldConfig.marginTileInDP + plusMargin);
        } else if (x == width - 1) {
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.START, backgroundViews[y][x - 1].getId(), ConstraintSet.END, PlayfieldConfig.marginTileInDP + plusMargin);
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, PlayfieldConfig.marginTileInDP);
        } else {
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.START, backgroundViews[y][x - 1].getId(), ConstraintSet.END, PlayfieldConfig.marginTileInDP + plusMargin);
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.END, backgroundViews[y][x + 1].getId(), ConstraintSet.START, PlayfieldConfig.marginTileInDP + plusMargin);
        }

        if (y == 0) {
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, PlayfieldConfig.marginTileInDP);
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.BOTTOM, backgroundViews[y + 1][x].getId(), ConstraintSet.TOP, PlayfieldConfig.marginTileInDP + plusMargin);
        } else if (y == height - 1) {
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.TOP, backgroundViews[y - 1][x].getId(), ConstraintSet.BOTTOM, PlayfieldConfig.marginTileInDP + plusMargin);
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, PlayfieldConfig.marginTileInDP);
        } else {
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.TOP, backgroundViews[y - 1][x].getId(), ConstraintSet.BOTTOM, PlayfieldConfig.marginTileInDP + plusMargin);
            constraintSet.connect(allViews[y][x].getId(), ConstraintSet.BOTTOM, backgroundViews[y + 1][x].getId(), ConstraintSet.TOP, PlayfieldConfig.marginTileInDP + plusMargin);
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

        HandlerThread handlerThread = new HandlerThread("mainHandler", 0);
        handlerThread.start();
        this.handler = new Handler(handlerThread.getLooper());

        PlayfieldTurnAnimTuple animation;
        printAllViews();
        animation = playfieldTurn.pollNextAnimation();
        while (animation != null) {
            Log.e("!", String.valueOf(animation.type));

            doAnimation(animation);
            animation = playfieldTurn.pollNextAnimation();
        }
    }

    private void doAnimation(PlayfieldTurnAnimTuple animation) {
        int delay = 10;
        switch (animation.type) {
            case SPAWN:
                // move next avaible invisible tile to position
                PlayfieldTileView playfieldTileView2 = this.nonUsedTiles.poll();

                for (int y = 0; y < playfieldViews.length; y++) {
                    for (int x = 0; x < playfieldViews.length; x++) {
                        if (playfieldViews[y][x] == playfieldTileView2) {
                            playfieldViews[y][x] = null;
                            break;
                        }
                    }
                }
                moveTile(playfieldTileView2, animation.tile.getNewX(), animation.tile.getNewY(), 0);

                Runnable rSpawn = () -> {
//                    // spawn Tile
                    spawnTileAt(animation.tile.getNewX(), animation.tile.getNewY(), animation.tile.getLevel());

                    Log.e("!", "     SPAWN");
                };
                this.handler.postDelayed(rSpawn, PlayfieldConfig.animationDurationInMs + 3 * delay);
                break;
            case MOVE:
                Runnable rMove = () -> {
                    moveTile(animation.tile.getOldX(), animation.tile.getOldY(), animation.tile.getNewX(), animation.tile.getNewY());
                    PlayfieldTileView playfieldTileView = this.nonUsedTiles.poll();
                    moveTile(playfieldTileView, animation.tile.getOldX(), animation.tile.getOldY(), 0);
                    Log.e("!", "     MOVE");
                };
                this.handler.postAtFrontOfQueue(rMove);
                break;
            case REMOVE:
                /// RICHTIG
                PlayfieldTileView playfieldTileView = playfieldViews[animation.tile.getOldY()][animation.tile.getOldX()];
                Runnable rRemove;
                rRemove = () -> {
                    replaceTile(playfieldTileView, -1, 0);
                    Log.e("!", "     REMOVE");
                };
                this.handler.postDelayed(rRemove, PlayfieldConfig.animationDurationInMs);
                break;

            case REMOVE_REFERENCE:
                Runnable rRemoveNewPos = () -> {
                    if (!tileReferenceStack.empty()) {
                        replaceTile(tileReferenceStack.pop(), -1, 0);
                        Log.e("!", "     SAA_REFERENCE");
                    }
                };
                this.handler.postDelayed(rRemoveNewPos, PlayfieldConfig.animationDurationInMs + delay);

            case SAVE_REFERENCE:
                PlayfieldTileView toPlaceholder = playfieldViews[animation.tile.getOldY()][animation.tile.getOldX()];
                this.tileReferenceStack.add(toPlaceholder);
                Log.e("!", "     SAVE_REFERENCE");
                break;
        }
    }

    public ObjectAnimator spawnTileAt(int x, int y, int level) {
        return replaceTile(x, y, level, PlayfieldConfig.animationSpawnDurationInMs);
    }

    private void moveTile(PlayfieldTileView playfieldTileView, int xTo, int yTo, int duration) {

        playfieldViews[yTo][xTo] = playfieldTileView;
        this.nonUsedTiles.add(playfieldTileView);

        PlayfieldTileView fromTile = playfieldTileView;
        PlayfieldTileView toTile = backgroundViews[yTo][xTo];

        float xDiff = toTile.getX() - fromTile.getX();
        float yDiff = toTile.getY() - fromTile.getY();


        Runnable rUpdateCoordinates = () -> {
            if (xDiff != 0 || yDiff != 0) {
                fromTile.setX(toTile.getX());
                fromTile.setY(toTile.getY());
            }
        };

        fromTile.animate()
                .x(toTile.getX())
                .y(toTile.getY())
                .setDuration(duration)
                .withEndAction(rUpdateCoordinates)
                .start();
    }

    private void moveTile(int xFrom, int yFrom, int xTo, int yTo) {
        PlayfieldTileView fromTile = playfieldViews[yFrom][xFrom];
        PlayfieldTileView toTile = backgroundViews[yTo][xTo];
        playfieldViews[yTo][xTo] = fromTile;

        float xDiff = toTile.getX() - fromTile.getX();
        float yDiff = toTile.getY() - fromTile.getY();


        Runnable rUpdateCoordinates = () -> {
            if (xDiff != 0 || yDiff != 0) {
                fromTile.setX(toTile.getX());
                fromTile.setY(toTile.getY());
            }
        };

        fromTile.animate()
                .x(toTile.getX())
                .y(toTile.getY())
                .setDuration(PlayfieldConfig.animationDurationInMs)
                .withEndAction(rUpdateCoordinates)
                .start();
    }

    private ObjectAnimator replaceTile(int x, int y, int level, long animationDuration) {
        playfieldViews[y][x].setLevel(level);
        ObjectAnimator scaleUp = ObjectAnimator.ofPropertyValuesHolder(playfieldViews[y][x],
                PropertyValuesHolder.ofFloat("scaleX", 0.2f, 1f),
                PropertyValuesHolder.ofFloat("scaleY", 0.2f, 1f));
        scaleUp.start();
        scaleUp.setDuration(animationDuration);

        return scaleUp;
    }

    private ObjectAnimator replaceTile(PlayfieldTileView playfieldTileView, int level, long animationDuration) {
        playfieldTileView.setLevel(level);
        ObjectAnimator scaleUp = ObjectAnimator.ofPropertyValuesHolder(playfieldTileView,
                PropertyValuesHolder.ofFloat("scaleX", 0.2f, 1f),
                PropertyValuesHolder.ofFloat("scaleY", 0.2f, 1f));
        scaleUp.start();
        scaleUp.setDuration(animationDuration);

        return scaleUp;
    }

    @Override
    public void initPlayer(Player player) {
        this.drawPlayfieldBackground(player.getPlayfieldState().getField().length, player.getPlayfieldState().getField().length);
        this.drawPlayfieldState(player.getPlayfieldState().getField());
    }

    private void printAllViews() {
        String string = "";
        for (PlayfieldTileView[] line : this.playfieldViews) {
            string += (Arrays.toString(line) + "\n");
        }
        Log.e("!", string);
    }


}