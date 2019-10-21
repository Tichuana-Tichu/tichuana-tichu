package client.model;

import client.view.*;

public class Hand {

	private ArrayList<Card> cards;
	private SimpleIntegerProperty cardsRemaining;

	public ArrayList<Card> getCards() {
		return this.cards;
	}

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}

	public SimpleIntegerProperty getCardsRemaining() {
		return this.cardsRemaining;
	}

	public Hand() {
		// TODO - implement Hand.Hand
	}

	public void playCards() {
		// TODO - implement Hand.playCards
	}

}