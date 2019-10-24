package ch.tichuana.tichu.commons.message;

import org.json.simple.JSONObject;

public class DemandSchupfenMsg extends Message {

	private String playerName;

	/**
	 * @author Christian
	 * @param playerName
	 */
	public DemandSchupfenMsg(String playerName) {
		this.playerName = playerName;
		this.setMsgType(MessageType.DemandSchupfenMsg);
	}

	@Override
	public String getPlayerName() {
		return this.playerName;
	}

	@Override
	public String toString() {
		JSONObject json = new JSONObject();
		json.put("msg",this.getMsgType().toString());
		json.put("playerName",this.playerName);
		return json.toJSONString();
	}
}