package com.example.a2048mult.ui.game.playfield;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Space;

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
    private View[] containerContent;


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
//        Runnable r = () -> {
        int width = data.length;
        int height = data[0].length;

        allViews = new View[height][];
        containerContent = new View[2 * height - 1];
        constraintSet = new ConstraintSet();
        int containerViewCount = 0;

        for (int y = 0; y < height; y++) {

            // add line
            containerContent = addNewRow(containerContent, containerViewCount, container);
            containerViewCount++;

            // adding space
            if (containerViewCount < 2 * height - 1) {
                containerContent = addNewLineSpace(containerContent, containerViewCount, container);
                containerViewCount++;
            }

            ConstraintLayout line = (ConstraintLayout) containerContent[y * 2];
            View[] lineContent = new View[2 * width - 1];
            int lineViewCount = 0;

            for (int x = 0; x < width; x++) {
                // add tile
                lineContent = addNewTile(line, lineContent, lineViewCount, data[y][x]);
                lineViewCount++;

                // add space
                if (lineViewCount < 2 * width - 1) {
                    lineContent = addNewSpace(line, lineContent, lineViewCount);
                    lineViewCount++;
                }
            }
            allViews[y] = lineContent;
            constraintSet.clone(line);
            constraintSet.connect(lineContent[lineContent.length - 1].getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
            constraintSet.applyTo(line);
        }

        constraintSet.clone(container);
        constraintSet.connect(containerContent[containerContent.length - 1].getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
        constraintSet.applyTo(container);

//        };
//        this.post(r);
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

        View fromTile = allViews[yFrom][xFrom * 2];
        View toTile = allViews[yTo][xTo * 2];

        ObjectAnimator animation;
        float xDiff = toTile.getX() - fromTile.getX();
        if (xDiff != 0) {
            animation = ObjectAnimator.ofFloat(fromTile, "translationX", xDiff);
        } else {
            float yDiff = containerContent[yTo].getHeight() + containerContent[yTo].getY() - containerContent[yFrom].getY();

            animation = ObjectAnimator.ofFloat(fromTile, "translationY", yDiff);
        }

        animation.setDuration(PlayfieldConfig.animationDurationInMs);
        animation.start();
        // TODO wenn zuende
//        removeTile(xFrom,yFrom);
        return animation;
    }


    private ObjectAnimator replaceTile(int x, int y, int level, long animationDuration) throws InterruptedException {

//        while(allViews == null){
//            Thread.sleep(100);
//        }

        ConstraintLayout line = ((ConstraintLayout) containerContent[2 * y]);
        constraintSet.clone(line);

        View tileToRemove = allViews[y][x * 2];

        constraintSet.clear(tileToRemove.getId());
        int index = line.indexOfChild(tileToRemove);
        line.removeView(tileToRemove);

        PlayfieldTileView tile = new PlayfieldTileView(line.getContext());
        tile.setLevel(level);
        tile.setId(generateViewId());
        line.addView(tile, index);


        if (x > 0) {
            View spaceBefore = allViews[y][x * 2 - 1];
            // TODO reset constraint one pointer to rmovedtile not removed
//            constraintSet.clear(spaceBefore.getId());
//            constraintSet.connect(spaceBefore.getId(),ConstraintSet.START, allViews[y][x * 2 - 2].getId(), ConstraintSet.END,0);
            addConstraintsItem(spaceBefore, tile);
        } else {
            addConstraintsItem(null, tile);
        }

        if (x == (allViews[y].length + 1) / 2 - 1) {
            constraintSet.connect(tile.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
        } else {
            View spaceAfter = allViews[y][x * 2 + 1];
            // TODO reset constraint one pointer to rmovedtile not removed
//            constraintSet.clear(spaceAfter.getId());
            addConstraintsItem(tile, spaceAfter);
//            addConstraintsItem(spaceAfter, allViews[y][x * 2 + 2]);
        }
        constraintSet.applyTo(line);
        allViews[y][x * 2] = tile;

        ObjectAnimator scaleUp = ObjectAnimator.ofPropertyValuesHolder(tile,
                PropertyValuesHolder.ofFloat("scaleX", 0.2f, 1f),
                PropertyValuesHolder.ofFloat("scaleY", 0.2f, 1f));
        scaleUp.setDuration(animationDuration);

        scaleUp.start();

        return scaleUp;
    }


    /**
     * draws a playfield only consisting of placeholder-tiles
     *
     * @param width  - defines width of playfield
     * @param height - defines width of playfield
     * @see this#drawPlayfieldState(int[][])
     */
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

    /**
     * adds a new Line (ConstraintLayout) - acting as a container for Spaces and Tiles
     * a part of drawing the playfield
     *
     * @param containerContent   - used to save views and connecting them later on via constraints
     * @param containerViewCount - used to give index of containerContent
     * @return containerContent + 1 Element
     * @see this#drawPlayfieldState(int[][])
     */
    private View[] addNewRow(View[] containerContent, int containerViewCount, ConstraintLayout container) {
        ConstraintLayout row = new ConstraintLayout(container.getContext());
        row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0));
        return addNewLine(containerContent, containerViewCount, row, container);
    }


    /**
     * adds a new Tile (ConstraintLayout)
     * a part of drawing the playfield
     *
     * @param line          - the specific row/line, where the tile is going to be added
     * @param lineContent   - used to save views and connecting them later on via constraints
     * @param lineViewCount - used to give index of containerContent
     * @param level         - for setting the level of the tile
     * @return lineContent + 1 Element
     * @see this#drawPlayfieldState(int[][])
     */
    private View[] addNewTile(ConstraintLayout line, View[] lineContent, int lineViewCount, int level) {
        PlayfieldTileView tile = new PlayfieldTileView(line.getContext());
        tile.setLevel(level);
        tile.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT));
        return addNewItemInLine(line, lineContent, lineViewCount, tile);
    }

    /**
     * adds a new Space Line to seperate the Rows containing Views
     * a part of drawing the playfield
     *
     * @param containerContent   - used to save views and connecting them later on via constraints
     * @param containerViewCount - used to give index of containerContent
     * @return containerContent + 1 Element
     * @see this#drawPlayfieldState(int[][])
     */
    private View[] addNewLineSpace(View[] containerContent, int containerViewCount, ConstraintLayout container) {
        Space space = new Space(container.getContext());
        space.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, PlayfieldConfig.marginTileInDP));
        return addNewLine(containerContent, containerViewCount, space, container);
    }

    /**
     * adds Space between the Tiles
     * a part of drawing the playfield
     *
     * @param line          - the specific row/line, where the tile is going to be added
     * @param lineContent   - used to save views and connecting them later on via constraints
     * @param lineViewCount - used to give index of containerContent
     * @return lineContent + 1 Element
     * @see this#drawPlayfieldState(int[][])
     */
    private View[] addNewSpace(ConstraintLayout line, View[] lineContent, int lineViewCount) {
        Space space = new Space(line.getContext());
        space.setLayoutParams(new LayoutParams(PlayfieldConfig.marginTileInDP, LayoutParams.MATCH_PARENT));
        return addNewItemInLine(line, lineContent, lineViewCount, space);
    }

    /**
     * general Method for adding horizontalViews to the container of the Playfield
     * a part of drawing the playfield
     *
     * @param containerContent   - used to save views and connecting them later on via constraints
     * @param containerViewCount - used to give index of containerContent
     * @param itemToAdd          - the Item, that should be added
     * @return containerContent + 1 Element
     * @see this#drawPlayfieldState(int[][])
     */
    private View[] addNewLine(View[] containerContent, int containerViewCount, View itemToAdd, ConstraintLayout container) {
        itemToAdd.setId(ConstraintLayout.generateViewId());

        container.addView(itemToAdd);
        containerContent[containerViewCount] = itemToAdd;

        constraintSet.clone(container);

        if (containerViewCount < 1) {
            addConstraintsLine(null, itemToAdd);
        } else {
            addConstraintsLine(containerContent[containerViewCount - 1], itemToAdd);
        }
        constraintSet.applyTo(container);

        return containerContent;
    }

    /**
     * general Method for adding Views to the given line
     * a part of drawing the playfield
     *
     * @param line          - the specific row/line, where the tile is going to be added
     * @param lineContent   - used to save views and connecting them later on via constraints
     * @param lineViewCount - used to give index of containerContent
     * @param itemToAdd     - the item, that should be added
     * @return lineContent + 1 Element
     * @see this#drawPlayfieldState(int[][])
     */
    private View[] addNewItemInLine(ConstraintLayout line, View[] lineContent, int lineViewCount, View itemToAdd) {
        itemToAdd.setId(ConstraintLayout.generateViewId());

        line.addView(itemToAdd);
        lineContent[lineViewCount] = itemToAdd;

        constraintSet.clone(line);

        if (lineViewCount < 1) {
            addConstraintsItem(null, itemToAdd);
        } else {
            addConstraintsItem(lineContent[lineViewCount - 1], itemToAdd);
        }
        constraintSet.applyTo(line);

        return lineContent;
    }


    /**
     * method for creating constraints between each line and fixing them on the sides of the container
     *
     * @param viewBefore - the view located left of the second view
     * @param view       - the second view
     */
    private void addConstraintsLine(View viewBefore, View view) {
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

    /**
     * method for creating constraints between each View horizontally and fixing them on the Top and Bottom edge of the line
     *
     * @param viewBefore - the view located left of the second view
     * @param view       - the second view
     */
    private void addConstraintsItem(View viewBefore, View view) {
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

    @Override
    public void initPlayer(Player player) {
        // TODO body method
    }

    @Override
    public void drawPlayer(Player player) {
        // TODO body method
    }
}