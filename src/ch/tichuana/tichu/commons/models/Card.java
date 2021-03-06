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

	}

	/**
	 * Constructor with both parameters, not usable for special Cards.
	 * @author Christian
	 * @param suit suit of card
	 * @param rank rank of card
	 */
	public Card(Suit suit, Rank rank){
		this.suit = suit;
		this.rank = rank;
	}

	/**
	 * Constructor for special cards, as they do not have a suit
	 * @author Christian
	 * @param rank the rank of the card
	 */
	public Card(Rank rank){
		this.rank = rank;
	}

	@Override
	public String toString() {
		return this.toJSON().toJSONString();
	}

	/**
	 * Returns a JSONObject-Representation of the card
	 * @author Christian
	 * @return jsonObject
	 */
	public JSONObject toJSON() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("rank", this.rank.toString());
		if(this.suit != null){
			jsonObject.put("suit", this.suit.toString());
		}
		return jsonObject;
	}

	/**
	 * Factory-method that creates a Card from its JSON-representation
	 * @author Christian
	 * @param jsonObject json representation of card
	 * @return card
	 */
	public static Card cardFactory(JSONObject jsonObject) {
		Rank rank = Rank.valueOf((String) jsonObject.get("rank"));
		String suitString = (String) jsonObject.get("suit");
		if (suitString != null){
			Suit suit = Suit.valueOf(suitString);
			return new Card(suit, rank);
		} else {
			return new Card(rank);
		}
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
			case "King":
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
	 * @param o Object to compare to
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

	/**
	 * checks if card are equal based on rank and suit (if available)
	 * @param o Object to compare to
	 * @return boolean if equal
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Card) {

			Card card = (Card) o;

			if (this.rank == card.getRank()){
				if(this.suit == null && card.getSuit() == null) {
					if (this.getRank() == card.getRank()) {
						return true;
					}
				}
				else if (this.suit == null || card.getSuit() ==null){
					return false;
				}
				else if (card.getRank() == this.rank && card.getSuit() == this.getSuit()) {
					return true;
				}
			}
		}
		return false;
	}

	public Suit getSuit() {
		return this.suit;
	}

	public Rank getRank() {
		return this.rank;
	}
}