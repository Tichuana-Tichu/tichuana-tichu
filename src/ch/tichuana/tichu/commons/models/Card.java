package ch.tichuana.tichu.commons.models;

import org.json.simple.JSONObject;

public class Card implements Comparable {

	private Suit suit;
	private Rank rank;

	public Suit getSuit() {
		return this.suit;
	}

	public Rank getRank() {
		return this.rank;
	}

	public Card() {
		// TODO - implement Card.Card
	}

	public String toString() {
		// TODO - implement Card.toString
		return null;
	}

	public JSONObject toJSON() {
		// TODO - implement Card.toJSON
		return null;
	}

	/**
	 * 
	 * @param jsonObject
	 */
	public static Card cardFactory(JSONObject jsonObject) {
		/* TODO - implement Card.cardFactory */
		return null;
	}

	public int getScoreValue() {
		// TODO - implement Card.getScoreValue
		return 0;
	}

	/**
	 * 
	 * @param o
	 */
	@Override
	public int compareTo(Object o) {
		return 0;
	}
}