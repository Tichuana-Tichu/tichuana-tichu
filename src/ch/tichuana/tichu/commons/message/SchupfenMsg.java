package commons.message;

import client.view.*;

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