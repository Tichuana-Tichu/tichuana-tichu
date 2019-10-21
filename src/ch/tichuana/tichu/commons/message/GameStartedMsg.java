package commons.message;

import server.model.*;

public class GameStartedMsg extends Message {

	private Player teamMate;
	private Player[] opponents;

	public Player getTeamMate() {
		return this.teamMate;
	}

	public Player[] getOpponents() {
		return this.opponents;
	}

	/**
	 * 
	 * @param teamMate
	 * @param opponents
	 */
	public GameStartedMsg(Player teamMate, Player[] opponents) {
		// TODO - implement GameStartedMsg.GameStartedMsg
	}

}