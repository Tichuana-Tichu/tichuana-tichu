package ch.tichuana.tichu.server.model;

import ch.tichuana.tichu.commons.message.DealMsg;
import ch.tichuana.tichu.commons.message.Message;
import ch.tichuana.tichu.commons.message.SchupfenMsg;
import ch.tichuana.tichu.commons.message.UpdateMsg;
import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.Rank;
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
	private Stich stich = null;

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
		this.stich = new Stich(this.serverModel);
		for (Player p : serverModel.getGame().getPlayersInOrder()){
			if (p.getHand().contains(new Card(Rank.majhong))){
				// the player with the mahjong card will begin the match. So we have to set currentPlayer in Game
				// to the players index-1. this way getNextPlayer will return him when called.
				serverModel.getGame().setCurrentPlayer(
						Arrays.asList(serverModel.getGame().getPlayersInOrder()).indexOf(p));
				UpdateMsg msg = new UpdateMsg(p.getPlayerName(), new ArrayList<Card>(),0,0);
				serverModel.broadcast(msg);
				break;
			}
		}
		logger.info("Match "+matchID+" started");
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
		logger.info("first eight cards dealt");
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
		logger.info("remaining cards dealt");
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
	 * Tells the current Stich to update with new move. Will add points to teams if the old is won and create new Stich
	 * @atuhor Christian
	 * @param messageProperty
	 */
	public void handleUpdate(SimpleMessageProperty messageProperty) {
		Player player = messageProperty.getPlayer();
		this.stich.update(player, messageProperty.getMessage().getCards());

		// if the player has no more cards, he is done for this match
		if (player.getHand().isEmpty()) {
			player.setDone(true);
		}

		// if a team is done we start a new match
		if (isTeamDone()) {
			//TODO: What to do with the played cards on the table?
			evaluateFinalMove();
			serverModel.getGame().startMatch();
		} else {

			if (this.stich.isWon()) {
				Team team = serverModel.getGame().getTeamByMember(this.stich.getCurrentWinner());
				team.addPoints(this.stich.getScore());

				// set currentPlayer to the index of this player-1, so getNextPlayer() will return this player
				int index = Arrays.asList(serverModel.getGame().getPlayersInOrder()).indexOf(stich.getCurrentWinner());
				serverModel.getGame().setCurrentPlayer(index - 1);

				this.stich = new Stich(serverModel);
			}

			Team[] teams = serverModel.getGame().getTeams();
			Player nextPlayer = serverModel.getGame().getNextPlayer();

			// if the Player is already done, get the next one
			while (nextPlayer.isDone()) {
				nextPlayer = serverModel.getGame().getNextPlayer();
			}
			// send all members of each team an UpdateMessage
			for (int i = 0; i < teams.length; i++) {
				UpdateMsg msg = new UpdateMsg(
						nextPlayer.getPlayerName(),
						this.stich.getLastMove(),
						teams[(i + 1) % 2].getCurrentScore(), teams[i].getCurrentScore());
				for (Player p : teams[i].getPlayers()) {
					p.sendMessage(msg);
				}
			}
		}
	}

	/**
	 * Will add the loosers hands score value to score of winning team
	 * @author Christian
	 */
	public void evaluateFinalMove(){
		Team loosingTeam = null;
		for (Player p : serverModel.getGame().getPlayersInOrder()) {
			// when the players hand isn't empty, his cards points will be given to the opposing team
			if (!p.getHand().isEmpty()){
				loosingTeam = serverModel.getGame().getTeamByMember(p);
				int score = 0;
				for (Card c : p.getHand()) {
					score += c.getScoreValue();
				}
				serverModel.getGame().getOpposingTeam(loosingTeam).addPoints(score);
			}
		}
	}

	/**
	 * checks if there is a team of which both members are done
	 * @return
	 */
	public boolean isTeamDone(){
		// check if both players of a team are done (terminal condition for match)
		boolean teamDone = false;
		for (Team t : serverModel.getGame().getTeams()) {
			Player[] players = t.getPlayers();
			if (players[0].isDone() && players[1].isDone()) {
				teamDone = true;
			}
		}
		return teamDone;
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