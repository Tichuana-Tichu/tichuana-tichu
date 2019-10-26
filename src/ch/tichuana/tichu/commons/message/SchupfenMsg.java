package ch.tichuana.tichu.commons.message;

import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.TichuType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class SchupfenMsg extends Message {

	private String playerName;
	private Card card;

	/**
	 * @author Christian
	 * @param playerName
	 * @param card
	 */
	public SchupfenMsg(String playerName, Card card) {
		this.playerName = playerName;
		this.card = card;
		this.setMsgType(MessageType.SchupfenMsg);
	}

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