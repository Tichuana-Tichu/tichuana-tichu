package ch.tichuana.tichu.client.model;

import ch.tichuana.tichu.commons.models.Card;

import java.util.ArrayList;

/**
 * the last valid move can also save the player which played the move
 */
public class OldMove extends ArrayList<Card> {

    private String player;

    public String getPlayer() {
        return player;
    }
    public void setPlayer(String player) {
        this.player = player;
    }
}
