package commons.models;

public enum Combination {
	;

	int HighCard;
	int OnePair;
	int ThreeOfAKind;
	int Steps;
	int FullHouse;
	int Straight;
	int FourOfAKindBomb;
	int StraightFlushBomb;

	Combination() {
		// TODO - implement Combination.Combination
	}

	/**
	 * 
	 * @param cards
	 */
	public static Combination evaluateCombination(ArrayList<Card> cards) {
		// TODO - implement Combination.evaluateCombination
	}

	/**
	 * 
	 * @param cards
	 */
	public static boolean isOnePair(ArrayList<Card> cards) {
		// TODO - implement Combination.isOnePair
	}

	/**
	 * 
	 * @param cards
	 */
	public static boolean isThreeOfAKind(ArrayList<Card> cards) {
		// TODO - implement Combination.isThreeOfAKind
	}

	/**
	 * 
	 * @param cards
	 */
	public static boolean isSteps(ArrayList<Card> cards) {
		// TODO - implement Combination.isSteps
	}

	/**
	 * 
	 * @param cards
	 */
	public static boolean isFullHouse(ArrayList<Card> cards) {
		// TODO - implement Combination.isFullHouse
	}

	/**
	 * 
	 * @param cards
	 */
	public static boolean isStraight(ArrayList<Card> cards) {
		// TODO - implement Combination.isStraight
	}

	/**
	 * 
	 * @param cards
	 */
	public static boolean isFourOfAKindBomb(ArrayList<Card> cards) {
		// TODO - implement Combination.isFourOfAKindBomb
	}

	/**
	 * 
	 * @param cards
	 */
	public static boolean isStraightFlushBomb(ArrayList<Card> cards) {
		// TODO - implement Combination.isStraightFlushBomb
	}

}