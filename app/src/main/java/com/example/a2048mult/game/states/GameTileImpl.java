package com.example.a2048mult.game.states;

public class GameTileImpl implements GameTile {

    private int oldX;
    private int oldY;
    private int newX;
    private int newY;

    private int level;
    private int oldestX;
    private int oldestY;

    @Override
    public void updateCoordinates(int newX, int newY) {
        GameTile.super.updateCoordinates(newX, newY);
    }

    public GameTileImpl(int newX, int newY, int level){
        this.newX = newX;
        this.newY = newY;
        this.level = level;
    }

    public GameTileImpl(int oldX, int oldY, int newX, int newY, int level){
        this.oldX = oldX;
        this.oldY = oldY;
        this.newX = newX;
        this.newY = newY;
        this.level = level;
    }

    @Override
    public void changeNewToOld() {
        GameTile.super.changeNewToOld();
    }

    @Override
    public int getOldX() {
        return this.oldX;
    }

    @Override
    public void setOldX(int x) {
        this.oldX = x;
    }

    @Override
    public int getOldY() {
        return this.oldY;
    }

    @Override
    public void setOldY(int y) {
        this.oldY = y;
    }

    @Override
    public int getNewX() {
        return this.newX;
    }

    @Override
    public void setNewX(int x) {
        this.newX = x;
    }

    @Override
    public int getNewY() {
        return this.newY;
    }

    @Override
    public void setNewY(int y) {
        this.newY = y;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public void setOldestX(int x) {
        this.oldestX = x;
    }

    @Override
    public void setOldestY(int y) {
        this.oldestY = y;
    }

    @Override
    public int getOldestX() {
        return this.oldestX;
    }

    @Override
    public int getOldestY() {
        return this.oldestY;
    }
}
