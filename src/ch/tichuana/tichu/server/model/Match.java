package ch.tichuana.tichu.server.model;

import ch.tichuana.tichu.commons.message.*;
import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.Rank;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public class Match {

	private int matchID;
	private int currentScore;
	private ServerModel serverModel;
	private static int MIN_PLAYER = 2;
	private Logger logger = Logger.getLogger("");
	private Trick trick = null;
	private Player firstPlayer = null;

	public Match(ServerModel serverModel) {
		this.serverModel = serverModel;
		this.matchID = getUniqueID();
		this.currentScore = 0;
	}

	/**
	 * Will start the actual game-play of a match. It is called after the schupfen-process is complete
	 * @author Christian
	 */
	public void start(){
		this.trick = new Trick(this.serverModel);
		for (Player p : serverModel.getGame().getPlayersInOrder()){
			if (p.getHand().contains(new Card(Rank.mahjong))){
				// the player with the mahjong card will begin the match. So we have to set currentPlayer in Game
				// to the players index-1. this way getNextPlayer will return him when called.
				serverModel.getGame().setCurrentPlayer(
						Arrays.asList(serverModel.getGame().getPlayersInOrder()).indexOf(p));
				UpdateMsg msg = new UpdateMsg(p.getPlayerName(), "", new ArrayList<Card>(),0,0,
						getPlayerNames(), getRemainingCards());
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
	protected void dealFirstEightCards(){
		try{Thread.sleep(300);}catch (Exception e){e.printStackTrace();}
		ArrayList<Card> cards = new ArrayList<>(Arrays.asList(serverModel.getGame().getDeck().getFirstHalf()));
		int rangeCounter = 0;
		for (Player p : serverModel.getGame().getPlayersInOrder()){
			ArrayList<Card> hand = new ArrayList<>(cards.subList(rangeCounter, rangeCounter + 8));
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
		try{Thread.sleep(300);}catch (Exception e){e.printStackTrace();}
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
	 * @param card card to push
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
	 * Tells the current Trick to update with new move. Will add points to teams if the old is won and create new Trick
	 * @author Christian
	 * @param messageProperty msg property containing the update message
	 */
	public void handleUpdate(SimpleMessageProperty messageProperty) {
		Player player = messageProperty.getPlayer();
		ArrayList<Card> move = messageProperty.getMessage().getCards();
		this.trick.update(player, move);
		logger.info(player.getPlayerName() + " played move: " +
				messageProperty.getMessage().getCards().size() + " cards");

		// if the player has no more cards, he is done for this match
		if (player.getHand().isEmpty()) {
			if (firstPlayer == null) {
				firstPlayer = player;
			}
			player.setDone(true);
			logger.info(player.getPlayerName() + " is done");
		}

		// if a team is done we start a new match
		if (isTeamDone()) {
			evaluateFinalMove();
			logger.info("Evaluation done");
			Team[] teams = serverModel.getGame().getTeams();
			Player nextPlayer = serverModel.getGame().getNextPlayer();

			// if the Player is already done, get the next one
			while (nextPlayer.isDone()) {
				nextPlayer = serverModel.getGame().getNextPlayer();
			}
			for (int i = 0; i < teams.length; i++) {
				UpdateMsg msg = new UpdateMsg(
						nextPlayer.getPlayerName(),
						trick.getLastPlayerName(),
						messageProperty.getMessage().getCards(), // was: last valid move, now: last move even when empty
						teams[(i + 1) % 2].getCurrentScore(), teams[i].getCurrentScore(),
						getPlayerNames(),getRemainingCards());
				for (Player p : teams[i].getPlayers()) {
					p.sendMessage(msg);
				}
			}
			serverModel.getGame().startMatch();
		} else {

			if (this.trick.isWon()) {
				// add trick to winning player
				this.trick.getCurrentWinner().addTrick(this.trick);

				// set currentPlayer to the index of this player-1, so getNextPlayer() will return this player
				int index = Arrays.asList(serverModel.getGame().getPlayersInOrder()).indexOf(trick.getCurrentWinner());
				serverModel.getGame().setCurrentPlayer(index - 1);

				this.trick = new Trick(serverModel);
			}

			// handle special case if card is a dog
			if (move.size() == 1 && move.get(0).equals(new Card(Rank.dog))) {

				Player teamMate = serverModel.getGame().getTeamByMember(player).getOtherMemberByMember(player);

				// if teammate is done we set the current player to the position just before him
				// so getNextPlayer() returns him. if that isn't the case we do nothing
				if (!teamMate.isDone()) {
					int teamMatePosition = Arrays.asList(
							serverModel.getGame().getPlayersInOrder()).indexOf(teamMate);
					serverModel.getGame().setCurrentPlayer(teamMatePosition-1);
				}
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
						trick.getLastPlayerName(),
						messageProperty.getMessage().getCards(), // was: last valid move, now: last move even when empty
						teams[(i + 1) % 2].getCurrentScore(), teams[i].getCurrentScore(),
						getPlayerNames(),getRemainingCards());
				for (Player p : teams[i].getPlayers()) {
					p.sendMessage(msg);
				}
			}
			logger.info("Sent custom update message");
		}
	}

	/**
	 * Will add the loosers hands score value to score of winning team
	 * @author Christian
	 */
	private void evaluateFinalMove(){
		Team loosingTeam;

        // this last trick is now over. all of its points will be given to the winners team
        this.trick.getCurrentWinner().addTrick(this.trick);

		for (Player p : serverModel.getGame().getPlayersInOrder()) {

			Team pTeam = serverModel.getGame().getTeamByMember(p);

			// add/remove points for Tichus
			switch (p.getTichuType()){
				case none:
					break;
				case SmallTichu:
					pTeam.addPoints((p == firstPlayer) ? 100 : -100);
					break;
				case GrandTichu:
					pTeam.addPoints((p == firstPlayer) ? 200 : -200);
					break;
			}

			// when the players hand isn't empty, his cards points will be given to the opposing team
			if (!p.getHand().isEmpty()){

				// score of tricks won will be given to the winners team
				Team winnersTeam = serverModel.getGame().getTeamByMember(trick.getCurrentWinner());
				for (Trick t : p.getTricksWon()){
					winnersTeam.addPoints(t.getScore());
				}
				// remove all tricks from looser
				p.getTricksWon().clear();

				// score of card in hand will be given to opposing team
				loosingTeam = serverModel.getGame().getTeamByMember(p);
				int score = 0;
				for (Card c : p.getHand()) {
					score += c.getScoreValue();
				}
				serverModel.getGame().getOpposingTeam(loosingTeam).addPoints(score);
			} else {
				// get all the score of all the players tricks and add it to the teams points
				Team team = serverModel.getGame().getTeamByMember(p);
				for (Trick t : p.getTricksWon()){
					team.addPoints(t.getScore());
				}
			}
		}
	}

	/**
	 * checks if there is a team of which both members are done
	 * @return boolean is team done
	 */
	private boolean isTeamDone(){
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
	 * creates a unique match id
	 * @return uniqueID
	 */
	private synchronized int getUniqueID() {
		int uniqueID = 0;
		return uniqueID++;
	}

	/**
	 * Creates a string array containing the names of all players
	 * @author Christian
	 * @return player names
	 */
	private String[] getPlayerNames(){
		String[] names = new String[4];
		for(int i = 0; i < serverModel.getGame().getPlayersInOrder().length; i++){
			names[i] = serverModel.getGame().getPlayersInOrder()[i].getPlayerName();
		}
		return names;
	}

	/**
	 * creates an array of counts of cards left in each players hand
	 * @author Christian
	 * @return array card count
	 */
	private int[] getRemainingCards(){
		int[] remainingCards = new int[4];
		for(int i = 0; i < serverModel.getGame().getPlayersInOrder().length; i++){
			remainingCards[i] = serverModel.getGame().getPlayersInOrder()[i].getHand().size();
		}
		return remainingCards;
	}
}