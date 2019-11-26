package ch.tichuana.tichu.client.model;

import ch.tichuana.tichu.commons.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;

public class Hand {

	protected final ObservableList<Card> cards;

	Hand(ArrayList<Card> cards) {
		this.cards = FXCollections.observableArrayList();
		this.cards.addAll(cards);
		Collections.sort(this.cards);
	}

	public void playCards() {
		// TODO - implement Hand.playCards
	}

	void addCards(ArrayList<Card> cards) {
		this.cards.addAll(cards);
		Collections.sort(this.cards);
	}

	//Getter
	public ObservableList<Card> getCards() {
		return this.cards;
	}
}