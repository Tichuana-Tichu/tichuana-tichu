package ch.tichuana.tichu.commons.message;

import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.TichuType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class DealMsg extends Message {

	private ArrayList<Card> cards;

	/**
	 * Deals a set of cards to each player
	 * @author Christian
	 * @param cards cards to be dealt to player
	 */
	public DealMsg(ArrayList<Card> cards) {
		this.cards = cards;
		this.setMsgType(MessageType.DealMsg);
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
		json.put("cards",cards);
		return json.toJSONString();
	}

	@Override
	public ArrayList<Card> getCards() {
		return this.cards;
	}

}