package ch.tichuana.tichu.commons.message;

import ch.tichuana.tichu.commons.models.TichuType;

public class TichuMsg extends Message {

	private String playerName;
	private TichuType tichuType;

	public String getPlayerName() {
		return this.playerName;
	}

	public TichuType getTichuType() {
		return this.tichuType;
	}

	/**
	 * 
	 * @param playerName
	 * @param tichuType
	 */
	public TichuMsg(String playerName, TichuType tichuType) {
		// TODO - implement TichuMsg.TichuMsg
	}

}