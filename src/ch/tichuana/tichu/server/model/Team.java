package ch.tichuana.tichu.server.model;

public class Team implements Comparable<Team> {

	private int teamID;
	private Player[] players;
	private int currentScore;
	private boolean finished;

	/**
	 * 
	 * @param playerOne
	 * @param playerTwo
	 */
	public Team(Player playerOne, Player playerTwo) {
		this.teamID = 1;
		players[0] = playerOne;
		players[1] = playerTwo;
		this.currentScore = 0;
		this.finished = false;
	}

	/**
	 * 
	 * @param t
	 */
	@Override
	public int compareTo(Team t) {
		return 0;
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