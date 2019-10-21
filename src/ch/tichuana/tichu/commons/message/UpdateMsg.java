package commons.message;

import client.view.*;

public class UpdateMsg extends Message {

	private String nextPlayer;
	private ArrayList<Card> lastMove;
	private int opponentScore;
	private int ownScore;

	public String getNextPlayer() {
		return this.nextPlayer;
	}

	public ArrayList<Card> getLastMove() {
		return this.lastMove;
	}

	public int getOpponentScore() {
		return this.opponentScore;
	}

	public int getOwnScore() {
		return this.ownScore;
	}

	/**
	 * 
	 * @param nextPlayer
	 * @param lastMove
	 * @param opponentScore
	 * @param ownScore
	 */
	public UpdateMsg(String nextPlayer, ArrayList<Card> lastMove, int opponentScore, int ownScore) {
		// TODO - implement UpdateMsg.UpdateMsg
	}

}