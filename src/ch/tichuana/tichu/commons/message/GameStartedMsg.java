package ch.tichuana.tichu.commons.message;

import ch.tichuana.tichu.server.model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class GameStartedMsg extends Message {

	private String teamMate;
	private String[] opponents;

	/**
	 * Tells the players that the game has started, who their teammate is and who they're up against
	 * @author Christian
	 * @param teamMate player name of player's teammate
	 * @param opponents names of player's opponents
	 */
	public GameStartedMsg(String teamMate, String[] opponents) {
		this.teamMate = teamMate;
		this.opponents = opponents;
		this.setMsgType(MessageType.GameStartedMsg);
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