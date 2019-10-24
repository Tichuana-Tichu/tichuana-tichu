package ch.tichuana.tichu.commons.message;

import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.TichuType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class PlayMsg extends Message {

	private ArrayList<Card> cards;

	/**
	 * 
	 * @param cards
	 */
	public PlayMsg(ArrayList<Card> cards) {
		this.cards = cards;
		this.setMsgType(MessageType.PlayMsg);
	}

	@Override
	public String toString() {
		JSONObject json = new JSONObject();
		json.put("msg",this.getMsgType().toString());
		JSONArray array = new JSONArray();
		for (Card card : cards){
			array.add(card);
		}
		json.put("cards",cards);
		return json.toJSONString();
	}

	@Override
	public ArrayList<Card> getCards() {
		return this.cards;
	}

}