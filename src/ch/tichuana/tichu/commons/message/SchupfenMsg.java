package ch.tichuana.tichu.commons.message;

import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.TichuType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class SchupfenMsg extends Message {

	private String playerName;
	private Card card;

	/**
	 * Message allowing players to push a card to another player
	 * @author Christian
	 * @param playerName players name
	 * @param card card to push
	 */
	public SchupfenMsg(String playerName, Card card) {
		this.playerName = playerName;
		this.card = card;
		this.setMsgType(MessageType.SchupfenMsg);
	}

	/**
	 * returns a json string with content of message
	 * @author Christian
	 * @return json representation of Message
	 */
	@Override
	public String toString() {
		JSONObject json = new JSONObject();
		json.put("msg",this.getMsgType().toString());
		json.put("playerName", this.playerName.toString());
		json.put("card",card.toJSON());
		return json.toJSONString();
	}

	@Override
	public String getPlayerName() {
		return this.playerName;
	}

	@Override
	public Card getCard() {
		return this.card;
	}


}