package ch.tichuana.tichu.server.model;

import ch.tichuana.tichu.commons.message.DealMsg;
import ch.tichuana.tichu.commons.message.Message;
import ch.tichuana.tichu.commons.message.SchupfenMsg;
import ch.tichuana.tichu.commons.message.UpdateMsg;
import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.Combination;
import ch.tichuana.tichu.commons.models.Rank;
import ch.tichuana.tichu.server.Server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public class Match {

	private int matchID;
	private int currentScore;
	private ServerModel serverModel;
	static int MIN_PLAYER = 2;
	private DeckOfCards deck;
	private Logger logger = Logger.getLogger("");

	public Match(ServerModel serverModel) {
		this.serverModel = serverModel;
		this.matchID = getUniqueID();
		this.currentScore = 0;
	}

	/**
	 * Will start the actual game-play of a match. It is called after the schupfen-process is complete
	 * @atuhor Christian
	 */
	public void start(){
		for (Player p : serverModel.getGame().getPlayersInOrder()){
			if (p.getHand().contains(new Card(Rank.majhong))){
				// the player with the mahjong card will begin the match. So we have to set currentPlayer in Game
				// to the players index-1. this way getNextPlayer will return him when called.
				serverModel.getGame().setCurrentPlayer(
						Arrays.asList(serverModel.getGame().getPlayersInOrder()).indexOf(p)-1);
				UpdateMsg msg = new UpdateMsg(p.getPlayerName(), new ArrayList<Card>(),0,0);
				serverModel.broadcast(msg);
				break;
			}
		}
	}

	/**
	 * Deals eight cards to every client by sending a custom DealMsg
	 * @author Christian
	 */
	public void dealFirstEightCards(){
		ArrayList<Card> cards = new ArrayList<>(Arrays.asList(serverModel.getGame().getDeck().getFirstHalf()));
		int rangeCounter = 0;
		for (Player p : serverModel.getGame().getPlayersInOrder()){
			ArrayList<Card> hand = new ArrayList<Card>(cards.subList(rangeCounter, rangeCounter + 8));
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
		ArrayList<Card> cards = new ArrayList<>(Arrays.asList(serverModel.getGame().getDeck().getSecondHalf()));
		int rangeCounter = 0;
		for (Player p : serverModel.getGame().getPlayersInOrder()){
			ArrayList<Card> hand = new ArrayList<Card>(cards.subList(rangeCounter, rangeCounter + 6));
			p.getHand().addAll(hand);
			Message msg = new DealMsg(hand);
			p.sendMessage(msg);
			rangeCounter += 6;
		}
	}

	/**
	 * removed the card from origin players hand and adds it to it's new owner
	 * @author Christian
	 * @param card
	 * @param player target player
	 */
	public void schupfen(Card card, Player player){
		Message msg= new SchupfenMsg("", card);
		// origin player
		for (Player p : serverModel.getGame().getPlayersInOrder()){
			if (p.getHand().contains(card)){
				p.getHand().remove(card);
				msg = new SchupfenMsg(p.getPlayerName(),card);
			}
		}
		player.getHand().add(card);
		player.sendMessage(msg);
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