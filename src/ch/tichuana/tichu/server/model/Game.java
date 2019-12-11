package ch.tichuana.tichu.server.model;

import ch.tichuana.tichu.commons.message.*;
import java.util.Arrays;
import java.util.logging.Logger;

public class Game {

	private Logger logger = Logger.getLogger("");
	private ServerModel serverModel;
	private int gameID;
	private int currentScore;
	private int matchesPlayed = 0;
	private volatile boolean closed;
	private Player[] playersInOrder;
	private int currentPlayer;
	private Team[] teams = new Team[2];
	private DeckOfCards deck;
	private Match currentMatch;
	private final int MAX_SCORE = 1000;

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
		this.deck = new DeckOfCards();
		this.currentPlayer = -1;
	}

	/**
	 * Method that starts the game. It organizes the players of both teams in the order of play.
	 * Sends a customized GameStartedMsg to every player. And then shuffles the deck
	 * @author Christian
	 */
	public void start(){
		GameStartedMsg msg;
		this.playersInOrder = new Player[4];
		Player[][] team = {teams[0].getPlayers(),teams[1].getPlayers()};
		int counter = 0;
		for (int x=0; x<2; x++){
			for (int y=0; y<2; y++){
				this.playersInOrder[counter] = team[y][x];
				counter++;
			}
		}
		for (int i=0; i<4; i++){
			String mate = playersInOrder[(i+2)%4].getPlayerName();
			String[] opponents = {playersInOrder[(i+1)%4].getPlayerName(), playersInOrder[(i+3)%4].getPlayerName()};
			msg = new GameStartedMsg(mate,opponents);
			playersInOrder[i].sendMessage(msg);
		}
		this.startMatch();
	}

	/**
	 * Returns the next player whos turn it is
	 * @author Christian
	 * @return Player
	 */
	public Player getNextPlayer() {
		currentPlayer = (currentPlayer+1)%4;

		// should handle if a player is already done
		while (playersInOrder[currentPlayer].isDone()){
			currentPlayer = (currentPlayer+1)%4;
		}
		return playersInOrder[currentPlayer];
	}

	/**
	 * Returns Player who's name equals parameter
	 * @param name wanted player's name
	 * @return player
	 */
	public Player getPlayerByName(String name){
		for (Player p : playersInOrder){
			if (p.getPlayerName().equals(name)){
				return p;
			}
		}
		return null;
	}

	/**
	 * returns a teams opponent
	 * @param team
	 * @return
	 */
	public Team getOpposingTeam(Team team){
		if(team == teams[0]){
			return teams[1];
		}
		else {
			return teams[0];
		}
	}

	/**
	 * starts a new Match in a game
	 * @author Christian
	 */
	public void startMatch(){
		boolean gameDone = false;
		if(isGameDone()){
			gameDone = true;
		} else {
			this.deck.shuffleDeck();
			this.currentMatch = new Match(serverModel);
			currentMatch.dealFirstEightCards();
			serverModel.setSchupfenResponses(0);
			serverModel.setTichuResponses(0);
			this.currentPlayer = -1;
			for (Player p : playersInOrder){
				p.setDone(false);
			}
		}

		for (Player p : playersInOrder){
			Message msg = new GameDoneMsg(
					getTeamByMember(p).getCurrentScore(),
					getOpposingTeam(getTeamByMember(p)).getCurrentScore(),
					gameDone);
		}
	}

	/**
	 * Returns the Team a given Player is in
	 * @param player
	 * @return
	 */
	public Team getTeamByMember(Player player){
		for (Team t : teams){
			if (Arrays.asList(t.getPlayers()).contains(player)) {
				return t;
			}
		}
		return null;
	}

	/**
	 * checks if a a team has already reached a total of 1000 points
	 * @author Christian
	 * @return
	 */
	public boolean isGameDone(){
		for (Team t : teams){
			if (t.getCurrentScore() >= MAX_SCORE){
				return true;
			}
		}
		return false;
	}

	/**
	 * counts how many players are still in the game
	 * @author Christian
	 * @return playerCount
	 */
	public int getNumberOfRemainingPlayers(){
		int playerCount = 0;
		for (Player p : playersInOrder){
			if (!p.isDone()){
				playerCount++;
			}
		}
		return playerCount;
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
	public Player[] getPlayersInOrder() {
		return playersInOrder;
	}
	public DeckOfCards getDeck() {
		return deck;
	}

	public Match getCurrentMatch() {
		return currentMatch;
	}

	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	public int getCurrentPlayer(){
		return currentPlayer;
	}
}