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
		// TODO - implement Team.Team
	}

	/**
	 * 
	 * @param o
	 */
	@Override
	public int compareTo(Team o) {
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