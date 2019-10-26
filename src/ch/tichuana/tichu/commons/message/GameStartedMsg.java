package ch.tichuana.tichu.commons.message;

import ch.tichuana.tichu.server.model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class GameStartedMsg extends Message {

	private String teamMate;
	private String[] opponents;

	/**
	 * @author Christian
	 * @param teamMate
	 * @param opponents
	 */
	public GameStartedMsg(String teamMate, String[] opponents) {
		this.teamMate = teamMate;
		this.opponents = opponents;
		this.setMsgType(MessageType.GameStartedMsg);
	}

	@Override
	public String toString() {
		JSONObject json = new JSONObject();
		json.put("msg",this.getMsgType().toString());
		json.put("teamMate", this.teamMate.toString());
		JSONArray players = new JSONArray();
		for (String player : this.opponents){
			players.add(player);
		}
		json.put("opponents", players);
		return json.toJSONString();
	}

	@Override
	public String getTeamMate() {
		return this.teamMate;
	}

	@Override
	public String[] getOpponents() {
		return this.opponents;
	}

}