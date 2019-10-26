package ch.tichuana.tichu.commons.message;

import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.TichuType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class UpdateMsg extends Message {

	private String nextPlayer;
	private ArrayList<Card> lastMove;
	private int opponentScore;
	private int ownScore;

	/**
	 * @author Christian
	 * @param nextPlayer
	 * @param lastMove
	 * @param opponentScore
	 * @param ownScore
	 */
	public UpdateMsg(String nextPlayer, ArrayList<Card> lastMove, int opponentScore, int ownScore) {
		this.nextPlayer = nextPlayer;
		this.lastMove = lastMove;
		this.opponentScore = opponentScore;
		this.ownScore = ownScore;
		this.setMsgType(MessageType.UpdateMsg);
	}

	@Override
	public String toString() {
		JSONObject json = new JSONObject();
		json.put("msg",this.getMsgType().toString());
		json.put("nextPlayer", nextPlayer);
		json.put("opponentScore",this.opponentScore);
		json.put("ownScore",this.ownScore);
		JSONArray array = new JSONArray();
		for (Card card : lastMove){
			array.add(card);
		}
		json.put("lastMove", lastMove);
		return json.toJSONString();
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

}