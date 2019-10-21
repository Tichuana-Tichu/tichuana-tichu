package server.model;

public class Team implements Comparable {

	private int teamID;
	private Player[] players;
	private int currentScore;
	private boolean finished;

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

	/**
	 * 
	 * @param playerOne
	 * @param playerTwo
	 */
	public Team(Player playerOne, Player playerTwo) {
		// TODO - implement Team.Team
	}

	/**
	 * 
	 * @param t
	 */
	public int compareTo(Team t) {
		// TODO - implement Team.compareTo
	}

}