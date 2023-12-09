package com.example.a2048mult.GameAppearance.Playfield;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;


import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Space;
import com.example.a2048mult.databinding.ViewPlayfieldBinding;


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

    /**
     * initialize view with margin given in Config
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
     * @see ConstraintLayout
     * @param data , 2-dimensional array, with [y][x] representing each Tile with their level
     * @implNote level < 1, represents a placeholder tile, used in the background
     */
    void drawPlayfieldState(int[][] data){
        int width = data.length;
        int height = data[0].length;

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

                lineContent = addNewTile(lineConstraintSet, line,lineContent, lineViewCount,data[y][x]);
                lineViewCount++;

                // add space
                if (lineViewCount < 2 * width - 1) {
                    Log.e("!","call");
                    lineContent = addNewSpace(lineConstraintSet, line, lineContent, lineViewCount);
                    lineViewCount++;
                }
            }

            lineConstraintSet.clone(line);
            lineConstraintSet.connect(lineContent[lineContent.length - 1].getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
            lineConstraintSet.applyTo(line);
        }

        containerConstraintSet.clone(binding.playfieldContainer);
        containerConstraintSet.connect(containerContent[containerContent.length - 1].getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
        containerConstraintSet.applyTo(binding.playfieldContainer);
    }

    /**
     *  draws a playfield only consisting of placeholder-tiles
     * @param width - defines width of playfield
     * @param height - defines width of playfield
     * @see this#drawPlayfieldState(int[][])
     */
    void drawPlayfieldSizing(int width, int height) {
        int[][] bgData = new int[height][width];
        drawPlayfieldState(bgData);
    }

    /**
     * adds a new Line (ConstraintLayout) - acting as a container for Spaces and Tiles
     * a part of drawing the playfield
     * @see this#drawPlayfieldState(int[][])
     * @param constraintSet - used to set constraints
     * @param containerContent - used to save views and connecting them later on via constraints
     * @param containerViewCount - used to give index of containerContent
     * @return containerContent + 1 Element
     */
    private View[] addNewRow(ConstraintSet constraintSet, View[] containerContent, int containerViewCount) {
        ConstraintLayout row = new ConstraintLayout(binding.playfieldContainer.getContext());
        row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0));
        return addNewLine(constraintSet, containerContent, containerViewCount, row);
    }


    /**
     * adds a new Tile (ConstraintLayout)
     * a part of drawing the playfield
     * @see this#drawPlayfieldState(int[][])
     * @param constraintSet - used to set constraints
     * @param line - the specific row/line, where the tile is going to be added
     * @param lineContent - used to save views and connecting them later on via constraints
     * @param lineViewCount - used to give index of containerContent
     * @param level - for setting the level of the tile
     * @return lineContent + 1 Element
     */
    private View[] addNewTile(ConstraintSet constraintSet, ConstraintLayout line, View[] lineContent, int lineViewCount, int level) {
        PlayfieldTileView tile = new PlayfieldTileView(line.getContext());
        tile.setLevel(level);
        tile.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT));
        return addNewItemInLine(constraintSet,line,lineContent,lineViewCount,tile);
    }

    /**
     * adds a new Space Line to seperate the Rows containing Views
     * a part of drawing the playfield
     * @see this#drawPlayfieldState(int[][])
     * @param constraintSet - used to set constraints
     * @param containerContent - used to save views and connecting them later on via constraints
     * @param containerViewCount - used to give index of containerContent
     * @return containerContent + 1 Element
     */
    private View[] addNewLineSpace(ConstraintSet constraintSet, View[] containerContent, int containerViewCount) {
        Space space = new Space(binding.playfieldContainer.getContext());
        space.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, PlayfieldConfig.marginTileInDP));
        return addNewLine(constraintSet, containerContent, containerViewCount, space);
    }

    /**
     * adds Space between the Tiles
     * a part of drawing the playfield
     * @see this#drawPlayfieldState(int[][])
     * @param constraintSet - used to set constraints
     * @param line - the specific row/line, where the tile is going to be added
     * @param lineContent - used to save views and connecting them later on via constraints
     * @param lineViewCount - used to give index of containerContent
     * @return lineContent + 1 Element
     */
    private View[] addNewSpace(ConstraintSet constraintSet, ConstraintLayout line, View[] lineContent, int lineViewCount) {
        Space space = new Space(binding.playfieldContainer.getContext());
        space.setLayoutParams(new LayoutParams(PlayfieldConfig.marginTileInDP, LayoutParams.MATCH_PARENT));
        return addNewItemInLine(constraintSet,line,lineContent,lineViewCount,space);
    }

    /**
     * general Method for adding horizontalViews to the container of the Playfield
     * a part of drawing the playfield
     * @see this#drawPlayfieldState(int[][])
     * @param constraintSet - used to set constraints
     * @param containerContent - used to save views and connecting them later on via constraints
     * @param containerViewCount - used to give index of containerContent
     * @param itemToAdd - the Item, that should be added
     * @return containerContent + 1 Element
     */
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

    /**
     * general Method for adding Views to the given line
     * a part of drawing the playfield
     * @see this#drawPlayfieldState(int[][])
     * @param constraintSet - used to set constraints
     * @param line - the specific row/line, where the tile is going to be added
     * @param lineContent - used to save views and connecting them later on via constraints
     * @param lineViewCount - used to give index of containerContent
     * @param itemToAdd - the item, that should be added
     * @return lineContent + 1 Element
     */
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


    /**
     * method for creating constraints between each line and fixing them on the sides of the container
     * @param constraintSet - used to set constraints
     * @param viewBefore - the view located left of the second view
     * @param view - the second view
     */
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

    /**
     * method for creating constraints between each View horizontally and fixing them on the Top and Bottom edge of the line
     * @param constraintSet - used to set constraints
     * @param viewBefore - the view located left of the second view
     * @param view - the second view
     */
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