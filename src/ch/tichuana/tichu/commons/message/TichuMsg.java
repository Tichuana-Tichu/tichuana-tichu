package ch.tichuana.tichu.commons.message;

import ch.tichuana.tichu.commons.models.TichuType;
import org.json.simple.JSONObject;

public class TichuMsg extends Message {

	private String playerName;
	private TichuType tichuType;
	/**
	 * @author Christian
	 * @param playerName
	 * @param tichuType
	 */
	public TichuMsg(String playerName, TichuType tichuType) {
		this.playerName = playerName;
		this.tichuType = tichuType;
		this.setMsgType(MessageType.TichuMsg);
	}

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