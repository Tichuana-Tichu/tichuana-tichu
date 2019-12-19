package ch.tichuana.tichu.client.model;

import ch.tichuana.tichu.commons.models.Card;

import java.util.ArrayList;


public class OldMove extends ArrayList<Card> {

    private String player;

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}
