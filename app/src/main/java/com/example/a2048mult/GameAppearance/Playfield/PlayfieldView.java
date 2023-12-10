package com.example.a2048mult.GameAppearance.Playfield;

import androidx.constraintlayout.widget.ConstraintLayout;

public interface PlayfieldView {


    /**
     *  draws a playfield only consisting of placeholder-tiles
     * @param width - defines width of playfield
     * @param height - defines width of playfield
     */
    public void drawPlayfieldBackground(int width, int height);

    /**
     * draws a Playfield with the given data of levels
     * @see ConstraintLayout
     * @param data , 2-dimensional array, with [y][x] representing each Tile with their level
     * @implNote level < 1, represents a placeholder tile, used in the background
     */
    public void drawPlayfieldState(int[][] data);

    /**
     * draws a single new Tile at position the given position
     * @param x - x-Position
     * @param y - y-Position
     * @param level - level of the tile
     */
    public void spawnTileAt(int x, int y, int level);

    /**
     * removes a tile from the gui
     * @param x - x-Position
     * @param y - y-Position
     */
    public void removeTile(int x, int y);

    /**
     * move a tile from one position to another
     * @param xFrom - x-Position of Tile
     * @param yFrom - y-Position of Tile
     * @param xTo - x-Position where the Tile is moving
     * @param yTo - y-Position where the Tile is moving
     */
    public void moveTile(int xFrom, int yFrom, int xTo, int yTo);
}
