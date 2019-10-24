package ch.tichuana.tichu.server.model;

public class Match {

	private int matchID;
	private int currentScore;
	static int MIN_PLAYER = 2;

	public Match() {
		// TODO - implement Match.Match
	}

	public Player getNextPlayer() {
		// TODO - implement Match.getNextPlayer
		return null;
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