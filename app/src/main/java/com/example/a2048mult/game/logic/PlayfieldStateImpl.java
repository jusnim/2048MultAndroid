package com.example.a2048mult.game.logic;

import android.util.Log;

public class PlayfieldStateImpl implements PlayfieldState {
    /**
     * The GameState class which provides the playing field.
     * @author Niklas Paggel
     */
    /**
     * field[x][y] startet bei 0, 0
     */
    private final int fieldSizeX;
    private final int fieldSizeY;


    private final int field[/*X*/][/*Y*/];  //0, 0 ist oben links

    public PlayfieldStateImpl(int fieldSizeX, int fieldSizeY) {
        if (fieldSizeX < 1 || fieldSizeY < 1) {
            throw new IllegalArgumentException("You need at least 1 in size to have a playing field at all");
        }
        this.fieldSizeX = fieldSizeX;
        this.fieldSizeY = fieldSizeY;
        field = new int[fieldSizeX][fieldSizeY];
    }

    public PlayfieldStateImpl(int fieldSize) {
        this(fieldSize, fieldSize);
    }

    public PlayfieldStateImpl() {
        this(4);
    }

    /**
     * für Multiplayer um Tiles zu setzen
     *
     * @param x     Coordinate
     * @param y     Coordinate
     * @param value of tile
     * @return
     */
    @Override
    public boolean setTile(int x, int y, int value) {
        //TODO: frei Felder besetzen
        if (field[x][y] != 0) {
            return false;
        }
        field[x][y] = value;
        return true;
    }

    public int getFieldSizeX() {
        return fieldSizeX;
    }

    public int getFieldSizeY() {
        return fieldSizeY;
    }

    @Override
    public int getTile(int x, int y) {
        return field[x][y];
    }

    @Override
    public int[][] getField() {
        return field;
    }


    @Override
    public void setField(int[][] field) {
        for (int x = 0; x < getFieldSizeX(); x++) {
            if (getFieldSizeY() >= 0)
                System.arraycopy(field[x], 0, this.field[x], 0, getFieldSizeY());
        }
    }
}
