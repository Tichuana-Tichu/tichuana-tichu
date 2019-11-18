package ch.tichuana.tichu.client.model;

import javafx.beans.property.SimpleIntegerProperty;
import ch.tichuana.tichu.commons.models.*;

import java.util.ArrayList;

public class Hand {

	private ArrayList<Card> cards;
	private SimpleIntegerProperty cardsRemaining;



	Hand(ArrayList<Card> cards) {
		this.cards = cards;
	}

	public void playCards() {
		// TODO - implement Hand.playCards
	}
	//Getters
	public ArrayList<Card> getCards() {
		return this.cards;
	}
	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}
	public SimpleIntegerProperty getCardsRemaining() {
		return this.cardsRemaining;
	}
}