package ch.tichuana.tichu.server.model;

public class Match {

	private int matchID;
	private int currentScore;
	static int MIN_PLAYER = 2;

	public Match() {
		this.matchID = getUniqueID();
		this.currentScore = 0;
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
	public int getMatchID() {
		return this.matchID;
	}
	public int getCurrentScore() {
		return this.currentScore;
	}
	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
	}
	public int getMIN_PLAYER() {
		return this.MIN_PLAYER;
	}

}