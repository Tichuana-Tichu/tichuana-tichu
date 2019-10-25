package ch.tichuana.tichu.server.model;

public class Game {

	private int gameID;
	public static int MAX_SCORE = 1000;
	private int currentScore = 0;
	private int matchesPlayed = 0;
	private Team[] teams;

	/**
	 * 
	 * @param teamOne
	 * @param teamTwo
	 */
	public Game(Team teamOne, Team teamTwo) {
		this.gameID = getUniqueID();
		this.currentScore = 0;
		teams[0] = teamOne;
		teams[1] = teamTwo;
	}

	public Player getNextPlayer() {
		// TODO - implement Match.getNextPlayer
		return null;
	}

	/**
	 *
	 * @return uniqueID
	 */
	private synchronized int getUniqueID() {
		int uniqueID = 0;
		return uniqueID++;
	}

	//Getter & Setter
	public int getGameID() {
		return this.gameID;
	}
	public int getMAX_SCORE() {
		return this.MAX_SCORE;
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