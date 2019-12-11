package ch.tichuana.tichu.client.model;

import ch.tichuana.tichu.commons.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;

public class Hand {

	protected final ObservableList<Card> cards;

    /**
     * @author Philipp
     * @param cards cards that need to be added
     */
	Hand(ArrayList<Card> cards) {
		this.cards = FXCollections.observableArrayList();
		this.cards.addAll(cards);
	}

    /**
     * @author Philipp
     * @param cards cards that need to be added
     */
	public void addCards(ArrayList<Card> cards) {
		this.cards.addAll(cards);
	}

    /**
     * @author Philipp
     * @param c card that need to be deleted
     */
	public void remove(Card c) {
		this.cards.remove(c);
	}

	/**
	 *
	 * @author Philipp
	 * @param cards cards that need to be deleted
	 */
	public void removeCards(ArrayList<Card> cards) {
		this.cards.removeAll(cards);
	}

	public void sort() {
		Collections.sort(this.cards);
	}

	//Getter
	public ObservableList<Card> getCards() {
		return this.cards;
	}
}