package ch.tichuana.tichu.commons.models;

import java.util.ArrayList;

public enum Combination {
	HighCard, OnePair, ThreeOfAKind, Steps, FullHouse, Straight, FourOfAKindBomb, StraightFlushBomb;

	Combination() {
		// TODO - implement Combination.Combination
	}

	/**
	 * 
	 * @param cards
	 */
	public static Combination evaluateCombination(ArrayList<Card> cards) {
		// TODO - implement Combination.evaluateCombination
		return null;
	}

	/**
	 * 
	 * @param cards
	 */
	public static boolean isOnePair(ArrayList<Card> cards) {
		// TODO - implement Combination.isOnePair
		return false;
	}

	/**
	 * 
	 * @param cards
	 */
	public static boolean isThreeOfAKind(ArrayList<Card> cards) {
		// TODO - implement Combination.isThreeOfAKind
		return false;
	}

	/**
	 * 
	 * @param cards
	 */
	public static boolean isSteps(ArrayList<Card> cards) {
		// TODO - implement Combination.isSteps
		return false;
	}

	/**
	 * 
	 * @param cards
	 */
	public static boolean isFullHouse(ArrayList<Card> cards) {
		// TODO - implement Combination.isFullHouse
		return false;
	}

	/**
	 * 
	 * @param cards
	 */
	public static boolean isStraight(ArrayList<Card> cards) {
		// TODO - implement Combination.isStraight
		return false;
	}

	/**
	 * 
	 * @param cards
	 */
	public static boolean isFourOfAKindBomb(ArrayList<Card> cards) {
		// TODO - implement Combination.isFourOfAKindBomb
		return false;
	}

	/**
	 * 
	 * @param cards
	 */
	public static boolean isStraightFlushBomb(ArrayList<Card> cards) {
		// TODO - implement Combination.isStraightFlushBomb
		return false;
	}

}