package ch.tichuana.tichu.commons.message;

import ch.tichuana.tichu.commons.models.TichuType;
import ch.tichuana.tichu.server.model.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class AnnouncedTichuMsg extends Message {

	private ArrayList<Player> players;
	private TichuType tichuType;

	public ArrayList<Player> getPlayers() {
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
	public AnnouncedTichuMsg(ArrayList<Player> players, TichuType tichuType) {
		this.players = players;
		this.tichuType = tichuType;
	}

}