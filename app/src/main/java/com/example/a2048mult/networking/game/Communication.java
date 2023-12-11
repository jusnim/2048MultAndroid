package com.example.a2048mult.networking.game;

import com.example.a2048mult.game.logic.Player;
import com.example.a2048mult.game.Move;

public interface Communication {

    /**
     * serialize the given move and send it to the other Devices
     * @param move - can be UP, DOWN, LEFT, RIGHT
     */
    void sendMove(Move move);

    /**
     * serialize the given move and send it to the other Devices
     * @param x - x-location of spawnedTile of currentPlayer
     * @param y - y-location of spawnedTile of currentPlayer
     */
    void sendNewTile(int x, int y);

    /**
     * serialize a leaveNotice of a given Player and send it to the other Devices
     * @param player - player which leaved the MultiplayerGame
     */
    void sendLeaveNotice(Player player);

}
