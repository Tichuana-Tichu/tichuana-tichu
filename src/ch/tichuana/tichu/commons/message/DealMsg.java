package ch.tichuana.tichu.commons.message;

import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.TichuType;

import java.util.ArrayList;

public class DealMsg extends Message {

	private ArrayList<Card> cards;

	public ArrayList<Card> getCards() {
		return this.cards;
	}

	/**
	 * 
	 * @param cards
	 */
	public DealMsg(ArrayList<Card> cards) {
		// TODO - implement DealMsg.DealMsg
	}

}