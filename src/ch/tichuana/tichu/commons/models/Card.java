package commons.models;

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
	}

	public JSONObject toJSON() {
		// TODO - implement Card.toJSON
	}

	/**
	 * 
	 * @param JSONObject
	 */
	public static Card cardFactory(int JSONObject) {
		// TODO - implement Card.cardFactory
	}

	public int getScoreValue() {
		// TODO - implement Card.getScoreValue
	}

	/**
	 * 
	 * @param c
	 */
	public int compareTo(Card c) {
		// TODO - implement Card.compareTo
	}


	public enum Suit {
		;

		int Stars;
		int Pagodas;
		int Swords;
		int Jade;

		public String toString() {
			// TODO - implement Suit.toString
		}

	}


	public enum Rank {
		;

		int dog;
		int mahjong;
		int two;
		int three;
		int four;
		int five;
		int six;
		int seven;
		int eight;
		int nine;
		int ten;
		int jack;
		int queen;
		int king;
		int ace;
		int phoenix;
		int dragon;

		public String toString() {
			// TODO - implement Rank.toString
		}

	}

}