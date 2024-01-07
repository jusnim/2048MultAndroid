package com.example.a2048mult.game.logic;

public class PlayerImpl implements Player {

    private String name;
    private long score;

    private PlayfieldState playfieldState;

    public PlayerImpl(String name, long score, PlayfieldState playfieldState) {
        this.name = name;
        this.score = score;
        this.playfieldState = playfieldState;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public long getScore() {
        return 0;
    }

    @Override
    public void addScore(long points) {

    }

    @Override
    public PlayfieldState getPlayfieldState() {
        return this.playfieldState;
    }
}
