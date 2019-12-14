package ch.tichuana.tichu.commons.message;

import ch.tichuana.tichu.commons.models.TichuType;
import org.json.simple.JSONObject;

public class AnnouncedTichuMsg extends Message {

	private String playerName;
	private TichuType tichuType;

	/**
	 * Player sends this message to server announcing a type of tichu
	 * @author Christian
	 * @param playerName name of player announcing tichu
	 * @param tichuType type of tichu announced
	 */

	public AnnouncedTichuMsg(String playerName, TichuType tichuType) {
		this.playerName = playerName;
		this.tichuType = tichuType;
		this.setMsgType(MessageType.AnnouncedTichuMsg);
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
		json.put("tichuType", this.tichuType.toString());
		json.put("player", playerName);
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