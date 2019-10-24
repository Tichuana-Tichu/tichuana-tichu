package ch.tichuana.tichu.commons.models;

import org.json.simple.JSONObject;

public class Card implements Comparable {

	private Suit suit;
	private Rank rank;

	/**
	 * Default Constructor without parameters
	 * Variables set exclusively thorugh getters and setters
	 * @author Christian
	 */
	public Card() {
		// TODO - implement Card.Card
	}

	/**
	 * Constructor with both parameters, not usable for special Cards.
	 * @author Christian
	 * @param suit
	 * @param rank
	 */
	public Card(Suit suit, Rank rank){
		this.suit = suit;
		this.rank = rank;
	}

	@Override
	public String toString() {
		// TODO - implement Card.toString
		return null;
	}

	/**
	 * Returns a JSONObject-Representation of the card
	 * @author Christian
	 * @return jsonObject
	 */
	public JSONObject toJSON() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("rank", this.rank.toString());
		jsonObject.put("suit", this.suit.toString());
		return jsonObject;
	}

	/**
	 * Factory-method that creates a Card from its JSON-representation
	 * @author Christian
	 * @param jsonObject
	 */
	public static Card cardFactory(JSONObject jsonObject) {
		Rank rank = Rank.valueOf((String) jsonObject.get("rank"));
		Suit suit = Suit.valueOf((String) jsonObject.get("suit"));
		return new Card(suit, rank);
	}

	/**
	 * Returns the value (int) of a card when evaluating a players (or teams) scored points
	 * @author Christian
	 * @return value
	 */
	public int getScoreValue() {
		int value = 0;
		switch (this.rank.toString()){
			case "five":
				value = 5;
				break;
			case "ten":
			case "king":
				value = 10;
				break;
			case "dragon":
				value = 25;
				break;
			case "phoenix":
				value = -25;
				break;
		}
		return value;
	}

	/**
	 * Compares Cards based on their Rank.
	 * @author Christian
	 * @param o
	 */
	@Override
	public int compareTo(Object o) {
		try {
			Card card = (Card) o;
			return this.rank.ordinal() - card.rank.ordinal();
		} catch (Exception e) {
			return 0;
		}
	}


	public Suit getSuit() {
		return this.suit;
	}

	public Rank getRank() {
		return this.rank;
	}

}