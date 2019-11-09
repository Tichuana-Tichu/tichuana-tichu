package ch.tichuana.tichu.commons.message;

import ch.tichuana.tichu.commons.models.TichuType;
import ch.tichuana.tichu.server.model.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class AnnouncedTichuMsg extends Message {

	private String playerName;
	private TichuType tichuType;

	/**
	 * @author Christian
	 * @param playerName
	 * @param tichuType
	 */

	public AnnouncedTichuMsg(String playerName, TichuType tichuType) {
		this.playerName = playerName;
		this.tichuType = tichuType;
		this.setMsgType(MessageType.AnnouncedTichuMsg);
	}

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