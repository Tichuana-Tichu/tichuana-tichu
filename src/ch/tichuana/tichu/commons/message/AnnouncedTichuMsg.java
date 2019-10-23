package ch.tichuana.tichu.commons.message;

import ch.tichuana.tichu.commons.models.TichuType;
import ch.tichuana.tichu.server.model.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class AnnouncedTichuMsg extends Message {

	private ArrayList<String> players;
	private TichuType tichuType;

	public ArrayList<String> getPlayers() {
		return this.players;
	}

	public TichuType getTichuType() {
		return this.tichuType;
	}

	/**
	 * 
	 * @param players
	 * @param tichuType
	 */

	public AnnouncedTichuMsg(ArrayList<String> players, TichuType tichuType) {
		this.players = players;
		this.tichuType = tichuType;
		this.setMsgType(MessageType.AnnouncedTichuMsg);
	}

	@Override
	public String toString() {
		JSONObject json = new JSONObject();
		json.put("msg",this.getMsgType().toString());
		json.put("tichuType", this.tichuType.toString());
		JSONArray players = new JSONArray();
		for (String player : this.players){
			players.add(player);
		}
		json.put("players", players);
		return json.toJSONString();
	}

}