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

import java.util.Arrays;


public class PlayfieldView extends ConstraintLayout implements DrawPlayfieldUI {
    private ViewPlayfieldBinding binding;
    private ConstraintSet constraintSet;

    private ConstraintLayout container;

    private ConstraintLayout.LayoutParams containerLayoutParams;

    private Handler handler;
    private Handler moveHandler;

    /**
     * [y][x]
     * y - represents lines without spaces
     * x- represents tiles and spaces
     * --> 2*x - represents every tile in a line
     */
    private PlayfieldTileView[][] playfieldViews;


    // also used for determining position of new tile
    private PlayfieldTileView[][] backgroundViews;

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
        HandlerThread handlerThread = new HandlerThread("handler", 0);
        handlerThread.start();
        this.handler = new Handler(handlerThread.getLooper());

        HandlerThread moveThread = new HandlerThread("moveThread", 0);
        moveThread.start();
        this.moveHandler = new Handler(moveThread.getLooper());


        binding = ViewPlayfieldBinding.inflate(LayoutInflater.from(context), this, true);


        this.container = binding.container;
        binding.getRoot().removeView(this.container);
//        ((ViewGroup)this.container.getParent()).removeView(binding.container);
        this.containerLayoutParams = (LayoutParams) binding.container.getLayoutParams();


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
                if (data[y][x] == PlayfieldConfig.invisibleTile) {
                    continue;
                }
                newTile = new PlayfieldTileView(getContext());
                newTile.setLevel(data[y][x]);
                newTile.setLayoutParams(new LayoutParams(0, 0));
                newTile.setId(ConstraintLayout.generateViewId());
                newContainer.addView(newTile);
                allViews[y][x] = newTile;
            }
        }

        constraintSet.clone(newContainer);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (allViews[y][x] == null) {
                    continue;
                }
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


        int counter = 0;
        Runnable r = () -> {
            PlayfieldTurnAnimTuple animation;
            printAllViews();
            animation = playfieldTurn.pollNextAnimation();
            while (animation != null) {
                Log.e("!", String.valueOf(animation.type));

                doAnimation(animation);
                animation = playfieldTurn.pollNextAnimation();
            }
            ;


            Runnable r2 = () -> {
                printAllViews();
            };
            handler.postDelayed(r2, PlayfieldConfig.animationDurationInMs + 20);

        };
        this.handler.post(r);
//        Log.e("!", "counter" + counter);

    }

    private void doAnimation(PlayfieldTurnAnimTuple animation) {
        int delay;
        switch (animation.type) {
            case SPAWN:

                Runnable rSpawn = () -> {
                    addTileIfNull(animation.tile.getNewX(),animation.tile.getNewY());
                    spawnTileAt(animation.tile.getNewX(), animation.tile.getNewY(), animation.tile.getLevel());
                };
                this.handler.postDelayed(rSpawn, PlayfieldConfig.animationDurationInMs *2);
                break;

            case MOVE:
                Runnable rMove = () -> {
                    moveTile(animation.tile.getOldX(), animation.tile.getOldY(), animation.tile.getNewX(), animation.tile.getNewY());
                };
                Runnable rAfterMove = () -> {
                    printAllViews();
//                    replaceTile(animation.tile.getOldX(), animation.tile.getOldY(), 10, 0);
//                    replaceTile(animation.tile.getNewX(), animation.tile.getNewY(), animation.tile.getLevel(), 2000);
                };

                this.handler.post(rMove);
                this.handler.postDelayed(rAfterMove, PlayfieldConfig.animationDurationInMs);

                break;
            case REMOVE:
                Runnable rRemove= () -> {
                    removeTile(animation.tile.getNewX(), animation.tile.getNewY());
                };
                this.handler.postDelayed(rRemove,PlayfieldConfig.animationDurationInMs+200);
                break;
        }
    }

    public ObjectAnimator spawnTileAt(int x, int y, int level) {
        return replaceTile(x, y, level, PlayfieldConfig.animationSpawnDurationInMs);
    }

    public void removeTile(int x, int y) {
        binding.getRoot().removeView(playfieldViews[y][x]);
        playfieldViews[y][x] = null;
//        return replaceTile(x, y, PlayfieldConfig.invisibleTile, PlayfieldConfig.animationDurationInMs);
    }

    private ObjectAnimator moveTile(int xFrom, int yFrom, int xTo, int yTo) {
        final ObjectAnimator[] animation = new ObjectAnimator[1];




        PlayfieldTileView fromTile = playfieldViews[yFrom][xFrom];
        PlayfieldTileView toTile = backgroundViews[yTo][xTo];

        playfieldViews[yTo][xTo] = fromTile;
        playfieldViews[yFrom][xFrom] = null;

        float xDiff = toTile.getX() - fromTile.getX();
        if (xDiff != 0) {
            animation[0] = ObjectAnimator.ofFloat(fromTile, "translationX", xDiff);
        } else {
            float yDiff = toTile.getY() - fromTile.getY();
            animation[0] = ObjectAnimator.ofFloat(fromTile, "translationY", yDiff);
        }

        animation[0].setDuration(PlayfieldConfig.animationDurationInMs);
        animation[0].start();


        return animation[0];
    }

    private void addTileIfNull(int x, int y){
        if (playfieldViews[y][x] == null) {
            PlayfieldTileView playfieldTileView = new PlayfieldTileView(getContext());
            playfieldTileView.setLayoutParams(backgroundViews[y][x].getLayoutParams());
            ((Activity) this.binding.getRoot().getContext()).runOnUiThread(() -> {
                this.container.addView(playfieldTileView);
            });
            playfieldViews[y][x] = playfieldTileView;
        }
    }

    private ObjectAnimator replaceTile(int x, int y, int level, long animationDuration) {
        if (playfieldViews[y][x] == null) {
            PlayfieldTileView playfieldTileView = new PlayfieldTileView(getContext());
            playfieldTileView.setLayoutParams(backgroundViews[y][x].getLayoutParams());
            ((Activity) this.binding.getRoot().getContext()).runOnUiThread(() -> {
                this.container.addView(playfieldTileView);
            });
            playfieldViews[y][x] = playfieldTileView;
        }

        playfieldViews[y][x].setLevel(level);
        ObjectAnimator scaleUp = ObjectAnimator.ofPropertyValuesHolder(playfieldViews[y][x],
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
    }


}