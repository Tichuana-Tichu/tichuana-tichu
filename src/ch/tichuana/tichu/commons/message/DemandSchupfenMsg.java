package ch.tichuana.tichu.commons.message;

import org.json.simple.JSONObject;

public class DemandSchupfenMsg extends Message {

	private String playerName;

	/**
	 * Asks the players to push a card each to a given player
	 * @author Christian
	 * @param playerName name of player to push cards to
	 */
	public DemandSchupfenMsg(String playerName) {
		this.playerName = playerName;
		this.setMsgType(MessageType.DemandSchupfenMsg);
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
		json.put("playerName",this.playerName);
		return json.toJSONString();
	}

	@Override
	public String getPlayerName() {
		return this.playerName;
	}

}