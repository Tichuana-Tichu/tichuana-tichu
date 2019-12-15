package ch.tichuana.tichu.commons.message;

import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.TichuType;
import ch.tichuana.tichu.server.model.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class UpdateMsg extends Message {

	private String nextPlayer, lastPlayer;
	private ArrayList<Card> lastMove;
	private String[] players;
	private int[] remainingCards;
	private int opponentScore;
	private int ownScore;

	/**
	 * Message updating the client on last move, next player and current scores
	 * @author Christian
	 * @param nextPlayer next player in row
	 * @param lastMove last move played
	 * @param opponentScore score of opposing team
	 * @param ownScore score of own team
	 */
	public UpdateMsg(String nextPlayer, String lastPlayer, ArrayList<Card> lastMove, int opponentScore, int ownScore,
					 String[] players, int[] remainingCards) {
		this.nextPlayer = nextPlayer;
		this.lastPlayer = lastPlayer;
		this.lastMove = lastMove;
		this.opponentScore = opponentScore;
		this.ownScore = ownScore;
		this.players = players;
		this.remainingCards = remainingCards;
		this.setMsgType(MessageType.UpdateMsg);
	}

	/**
	 * returns a json string with content of message
	 * @author Christian
	 * @return json representation of Message
	 */
	@Override
	public String toString() {
		JSONObject json = new JSONObject();
		json.put("msg",this.getMsgType().toString());
		json.put("nextPlayer", nextPlayer);
		json.put("lastPlayer", lastPlayer);
		json.put("opponentScore",this.opponentScore);
		json.put("ownScore",this.ownScore);
		JSONArray array = new JSONArray();
		for (Card card : lastMove){
			array.add(card);
		}
		json.put("lastMove", lastMove);

		JSONArray jsonPlayers = new JSONArray();
		for (int i=0; i<players.length; i++){
			JSONObject pl = new JSONObject();
			pl.put("name", players[i]);
			pl.put("number", remainingCards[i]);
			jsonPlayers.add(pl);
		}

		json.put("remainingCards", jsonPlayers);

		return json.toJSONString();
	}

	/**
	 * Returns the number of cards a player has left on his hand by his name
	 * @author Christian
	 * @param name player name
	 * @return number of cards
	 */
	@Override
	public int getRemainingCardsByPlayerName(String name) {
		int pos = Arrays.asList(this.players).indexOf(name);
		return remainingCards[pos];
	}

	/**
	 * Returns the number of players left in the match
	 * @author Christian
	 * @return number of players left in the match
	 */
	@Override
	public int getRemainingPlayers(){
		int playerCount = 0;
		for (int i : remainingCards){
			if (i != 0){
				playerCount++;
			}
		}
		return playerCount;
	}

	@Override
	public String getNextPlayer() {
		return this.nextPlayer;
	}

	@Override
	public ArrayList<Card> getLastMove() {
		return this.lastMove;
	}

	@Override
	public int getOpponentScore() {
		return this.opponentScore;
	}

	@Override
	public int getOwnScore() {
		return this.ownScore;
	}

	@Override
	public String getLastPlayer(){
		return this.lastPlayer;
	}

}