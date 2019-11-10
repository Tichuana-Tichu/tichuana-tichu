package ch.tichuana.tichu.server.model;

import ch.tichuana.tichu.commons.message.*;
import ch.tichuana.tichu.commons.models.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public class Game {

	private Logger logger = Logger.getLogger("");
	private ServerModel serverModel;
	private int gameID;
	private int currentScore;
	private int matchesPlayed = 0;
	private volatile boolean closed;
	private boolean cardsDealed;
	private Player[] playersInOrder;
	private int currentPlayer;
	private Team[] teams = new Team[2];
	private DeckOfCards deck;

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
		this.deck.shuffleDeck();
	}

	/**
	 * Deals eight cards to every client by sending a custom DealMsg
	 * @author Christian
	 */
	public void dealFirstEightCards(){
		ArrayList<Card> cards = new ArrayList<>();
		cards.addAll(Arrays.asList(deck.getFirstHalf()));
		int rangeCounter = 0;
		for (Player p : playersInOrder){
			ArrayList<Card> hand = new ArrayList();
			hand.addAll(cards.subList(rangeCounter,rangeCounter+8));
			p.getHand().addAll(hand);
			Message msg = new DealMsg(hand);
			p.sendMessage(msg);
			rangeCounter += 8;
		}
	}

	/**
	 * Deals the remaining six cards to every client by sending a custom DealMsg
	 * @author Christian
	 */
	public void dealRemainingCards(){
		ArrayList<Card> cards = new ArrayList<>();
		cards.addAll(Arrays.asList(deck.getSecondHalf()));
		int rangeCounter = 0;
		for (Player p : playersInOrder){
			ArrayList<Card> hand = new ArrayList();
			hand.addAll(cards.subList(rangeCounter,rangeCounter+6));
			p.getHand().addAll(hand);
			Message msg = new DealMsg(hand);
			p.sendMessage(msg);
			rangeCounter += 6;
		}
	}

	public void schupfen(){

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
	 * Returns the next player whos turn it is
	 * @author Christian
	 * @return Player
	 */
	public Player getNextPlayer() {
		currentPlayer = (currentPlayer+1)&4;

		// should handle if a player is already done
		while (playersInOrder[currentPlayer].isDone()){
			currentPlayer = (currentPlayer+1)&4;
		}
		return playersInOrder[currentPlayer];
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