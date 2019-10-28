package ch.tichuana.tichu.server.model;

import ch.tichuana.tichu.commons.message.*;
import java.util.logging.Logger;

public class Game {

	private Logger logger = Logger.getLogger("");
	private ServerModel serverModel;
	private int gameID;
	private int currentScore;
	private int matchesPlayed = 0;
	private volatile boolean closed;
	private boolean cardsDealed;
	private Team[] teams = new Team[2];

	/**
	 * Game will be started from ServerModel as soon as 4 player are connected to server
	 * Game-Object ist able to communicate via broadcast to all clients
	 * @author Philipp
	 * @param teamOne the first team created by the ServerModel
	 * @param teamTwo the second team created by the ServerModel
	 * @param serverModel to have access to serverModel.broadcast()
	 */
	Game(Team teamOne, Team teamTwo, ServerModel serverModel) {
		this.gameID = getUniqueID();
		this.currentScore = 0;
		this.teams[0] = teamOne;
		this.teams[1] = teamTwo;
		this.serverModel = serverModel;
		this.closed = false;
		this.cardsDealed = false;
	}

	/**
	 * called from controller to send messages to ClientModel (Client)
	 * @author Philipp
	 * @param messageType from a specific type
	 */
	private void sendMessage(MessageType messageType, String identifier) {
		serverModel.broadcast(messageType, identifier);
	}

	/**
	 * @author Philipp
	 * @return Player of which isTurn is true
	 */
	public Player getNextPlayer() {
		// TODO - implement Match.getNextPlayer
		return null;
	}

	/**
	 * for identification
	 * @author Philipp
	 * @return uniqueID
	 */
	private synchronized int getUniqueID() {
		int uniqueID = 0;
		return ++uniqueID;
	}

	//Getter & Setter
	public int getGameID() {
		return this.gameID;
	}
	public int getMAX_SCORE() {
		int MAX_SCORE = 1000;
		return MAX_SCORE;
	}
	public int getCurrentScore() {
		return this.currentScore;
	}
	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
	}
	public int getMatchesPlayed() {
		return this.matchesPlayed;
	}
	public void setMatchesPlayed(int matchesPlayed) {
		this.matchesPlayed = matchesPlayed;
	}
	public Team[] getTeams() {
		return this.teams;
	}
}