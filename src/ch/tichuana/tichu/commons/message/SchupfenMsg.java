package ch.tichuana.tichu.commons.message;

import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.TichuType;

public class SchupfenMsg extends Message {

	private String playerName;
	private Card card;

	public String getPlayerName() {
		return this.playerName;
	}

	public Card getCard() {
		return this.card;
	}

	/**
	 * 
	 * @param playerName
	 * @param card
	 */
	public SchupfenMsg(String playerName, Card card) {
		// TODO - implement SchupfenMsg.SchupfenMsg
	}

}