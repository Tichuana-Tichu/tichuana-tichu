package ch.tichuana.tichu.server.model;

public class Team implements Comparable<Team> {

	private int teamID;
	private Player[] players;
	private int currentScore;
	private boolean finished;

	/**
	 * creates a team with two players
	 * @param playerOne first player
	 * @param playerTwo second player
	 */
	public Team(Player playerOne, Player playerTwo) {
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
	 * @param points points to add to the team's score
	 */
	protected void addPoints(int points){
		this.currentScore += points;
	}

	/**
	 * returns a players team mate
	 * @param player player to get team mate of
	 * @return teammate
	 */
	protected Player getOtherMemberByMember(Player player) {
		if (player == players[0]) {
			return players[1];
		} else {
			return players[0];
		}
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
	 * Compare teams by score
	 * @param t other team
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