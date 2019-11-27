package ch.tichuana.tichu.server.model;

import java.util.logging.Logger;

public class Team implements Comparable<Team> {

	private int teamID;
	private Player[] players;
	private int currentScore;
	private boolean finished;
	private Logger logger;

	/**
	 * 
	 * @param playerOne
	 * @param playerTwo
	 */
	public Team(Player playerOne, Player playerTwo) {
		logger = Logger.getLogger("");
		players = new Player[2];
		this.teamID = getUniqueID();
		players[0] = playerOne;
		players[1] = playerTwo;
		this.currentScore = 0;
		this.finished = false;
	}

	/**
	 * adds points to current score
	 * @author Christian
	 * @param points
	 */
	public void addPoints(int points){
		this.currentScore += points;
	}

	/**
	 *
	 * @return uniqueID
	 */
	private synchronized int getUniqueID() {
		int uniqueID = 0;
		return uniqueID++;
	}

	/**
	 * 
	 * @param t
	 */
	@Override
	public int compareTo(Team t) {
		return this.currentScore - t.currentScore;
	}

	//Getter & Setter
	public int getTeamID() {
		return this.teamID;
	}
	public Player[] getPlayers() {
		return this.players;
	}
	public int getCurrentScore() {
		return this.currentScore;
	}
	public boolean isFinished() {
		return this.finished;
	}
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
}