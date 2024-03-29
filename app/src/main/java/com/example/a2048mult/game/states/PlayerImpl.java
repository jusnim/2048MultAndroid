package com.example.a2048mult.game.states;

public class PlayerImpl implements Player {

    private String name;
    private long score;

    private PlayfieldTurn playfieldTurn = new PlayfieldTurnImpl();

    private PlayfieldState playfieldState;

    public PlayerImpl(String name, long score, PlayfieldState playfieldState) {
        this.name = name;
        this.score = score;
        this.playfieldState = playfieldState;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public long getScore() {
        return this.score;
    }

    @Override
    public void addScore(long points) {
        score += points;
    }

    @Override
    public PlayfieldState getPlayfieldState() {
        return this.playfieldState;
    }


    @Override
    public void setPlayfieldSize(int size) {
        this.playfieldState = new PlayfieldStateImpl(size);
    }

    @Override
    public PlayfieldTurn getPlayfieldTurn() {
        return this.playfieldTurn;
    }

    @Override
    public void setPlayfieldTurn(PlayfieldTurn playfieldTurn) {
        this.playfieldTurn = playfieldTurn;
    }
}
