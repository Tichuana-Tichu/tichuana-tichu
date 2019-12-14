package ch.tichuana.tichu.commons.message;

import ch.tichuana.tichu.commons.models.TichuType;
import org.json.simple.JSONObject;

public class TichuMsg extends Message {

	private String playerName;
	private TichuType tichuType;

	/**
	 * Message allowing the player to announce a type of Tichu
	 * @author Christian
	 * @param playerName players name
	 * @param tichuType type of tichu
	 */
	public TichuMsg(String playerName, TichuType tichuType) {
		this.playerName = playerName;
		this.tichuType = tichuType;
		this.setMsgType(MessageType.TichuMsg);
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
		json.put("playerName", this.playerName);
		json.put("tichuType", this.tichuType.toString());
		return json.toJSONString();
	}

	@Override
	public String getPlayerName() {
		return this.playerName;
	}

	@Override
	public TichuType getTichuType() {
		return this.tichuType;
	}
}