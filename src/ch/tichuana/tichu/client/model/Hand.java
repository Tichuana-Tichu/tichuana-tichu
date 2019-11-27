package ch.tichuana.tichu.client.model;

import ch.tichuana.tichu.commons.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Hand {

	protected final ObservableList<Card> cards;

	Hand(ArrayList<Card> cards) {
		this.cards = FXCollections.observableArrayList();
		this.cards.addAll(cards);
	}

	public void playCards() {
		// TODO - implement Hand.playCards
	}

	public void addCards(ArrayList<Card> cards) {
		this.cards.addAll(cards);
	}

	public void remove(Card c) {
		this.cards.remove(c);
	}

	//Getter
	public ObservableList<Card> getCards() {
		return this.cards;
	}
}