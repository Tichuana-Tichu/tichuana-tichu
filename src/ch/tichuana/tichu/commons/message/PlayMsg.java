package ch.tichuana.tichu.commons.message;

import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.TichuType;

import java.util.ArrayList;

public class PlayMsg extends Message {

	private ArrayList<Card> cards;

	public ArrayList<Card> getCards() {
		return this.cards;
	}

	/**
	 * 
	 * @param cards
	 */
	public PlayMsg(ArrayList<Card> cards) {
		// TODO - implement PlayMsg.PlayMsg
	}

}