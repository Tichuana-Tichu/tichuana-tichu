package ch.tichuana.tichu.commons.models;

import java.util.ArrayList;
import java.util.Collections;

public enum Combination {
	HighCard, OnePair, ThreeOfAKind, Steps, FullHouse, Straight,
	FourOfAKindBomb, FourOfAKindPhoenix, StraightFlushBomb;

	public static ArrayList<Card> clonedCards;

	/**
	 * All Combinations are going to be checked and returned by currentEval.
	 *
	 * @param cards ArrayList including selected cards from client view
	 * @return combination type
	 * @author dominik
	 */
	public static Combination evaluateCombination(ArrayList<Card> cards) {
		Combination currentEval = HighCard;

		clonedCards = (ArrayList<Card>) cards.clone();
		Collections.sort(clonedCards);

		if (isOnePair(clonedCards)) currentEval = OnePair;
		if (isThreeOfAKind(clonedCards)) currentEval = ThreeOfAKind;
		if (isSteps(clonedCards)) currentEval = Steps;
		if (isFullHouse(clonedCards)) currentEval = FullHouse;
		if (isStraight(clonedCards)) currentEval = Straight;
		if (isFourOfAKindBomb(clonedCards)) currentEval = FourOfAKindBomb;
		if (isFourOfAKindPhoenix(clonedCards)) currentEval = FourOfAKindPhoenix;
		if (isStraightFlushBomb(clonedCards)) currentEval = StraightFlushBomb;

		return currentEval;
	}

	/**
	 * checks if cards contain phoenix
	 * @param cards
	 * @return
	 * @author dominik
	 */
	private static boolean containsPhoenix(ArrayList<Card> cards) {return cards.contains(new Card(Rank.phoenix));}

	/**
	 * checks if cards contain dog
	 * @param cards
	 * @return
	 * @author dominik
	 */
	private static boolean containsDog(ArrayList<Card> cards) {
		return cards.contains(new Card(Rank.dog));
	}

	/**
	 * check if cards contain majhong
	 * @author dominik
	 * @param cards
	 * @return
	 */
	private static boolean containsMahjong(ArrayList<Card> cards) {return cards.contains(new Card(Rank.mahjong));}

	/**
	 * check if cards contain dragon
	 * @author dominik
	 * @param cards
	 * @return
	 */
	private static boolean containsDragon(ArrayList<Card> cards) {return cards.contains(new Card(Rank.dragon));}

	/**
	 * checks if cards contain two cards of the same rank
	 * or a phoenix and a random second card
	 * @param cards
	 * @return
	 * @author dominik
	 */
	public static boolean isOnePair(ArrayList<Card> cards) {

		if (cards.size() == 2 && !containsDog(cards) && !containsMahjong(cards) && !containsDragon(cards)) {
			return cards.get(0).getRank() == cards.get(1).getRank() || containsPhoenix(cards);
		} else
			return false;
	}

	/**
	 * Check if three cards in the Array have the same rank.
	 * Phoenix control implemented.
	 * @param cards
	 * @return
	 * @author dominik
	 */
	public static boolean isThreeOfAKind(ArrayList<Card> cards) {

		if (cards.size() == 3 && !containsDog(cards) && !containsDragon(cards) && !containsMahjong(cards)) {
			// 3 equal cards
			return (cards.get(0).getRank() == cards.get(1).getRank() &&
					cards.get(1).getRank() == cards.get(2).getRank()) ||
					// or 2 equals cards and a phoenix
					(cards.get(0).getRank() == cards.get(1).getRank() &&
							containsPhoenix(cards));
		} else
			return false;
	}

	/**
	 * Check if there are 4 or more cards in the array, if not return false. If there are, check if they are pairs and
	 * the rank is one higher then the first pair.
	 * @param cards
	 * @return
	 * @author christian, dominik, philipp
	 */
	public static boolean isSteps(ArrayList<Card> cards) {
		boolean phoenixUsed = false;

		// At first we check if the size is dividable by 2 and larger than 3 -> else no step
		// Also can't contain dog, Dragon or Mahjong
		if ((cards.size() % 2 == 0 && cards.size() > 3) && (!containsDog(cards) ||
			!containsDragon(cards) || !containsMahjong(cards))) {

			// in a step-combination every second card has a rank 1 higher than the previous card
			// if a phoenix is used the system will still work since the phoenix is always the last card (ordered list)
			for (int i = 0; i < cards.size()-2; i+=2) {
				if (!(cards.get(i).getRank().ordinal()+1 == cards.get(i+2).getRank().ordinal()))
					return false;
			}

			if (containsPhoenix(cards)) { //complex handling with phoenix

				clonedCards = (ArrayList<Card>) cards.clone();
				clonedCards.remove(clonedCards.get(clonedCards.size()-1));

				for (int i = 1; i < cards.size()-2; i++) {
					// if the previous cards doesn't have a rank of n-1 AND the following card doesn't have a rank
					// of n+1 this is where we use the phoenix. If it's already been used its no step and returns false
					if (clonedCards.get(i).getRank() != clonedCards.get(i - 1).getRank() &&
							clonedCards.get(i).getRank() != clonedCards.get(i + 1).getRank()) {
						if (phoenixUsed) {
							return false;
						}
						else {
							phoenixUsed = true;
						}
					}
				}

			} else { //simple handling without phoenix
				for (int i = 0; i < cards.size(); i += 2) {
					if (cards.get(i).getRank() != cards.get(i + 1).getRank()) {
						return false;
					}
				}
			}
		}
		else{
			return false;
		}
		return true;
	}


	/**
	 * The first 3 cards and the second 2, or the first 2 and the following 3 cards need to be the same.
	 * Phoenix control implemented.
	 * @author dominik
	 * @return
	 * @param cards
	 */
	public static boolean isFullHouse(ArrayList<Card> cards) {

		//complex handling with phoenix
		if (cards.size() == 5 && !containsDog(cards) && !containsDragon(cards) && !containsMahjong(cards)) {

			if (containsPhoenix(cards)) {

				if (cards.get(0).getRank() == cards.get(2).getRank() ||
					cards.get(1).getRank() == cards.get(3).getRank() ||
					(cards.get(0).getRank() == cards.get(1).getRank() &&
					cards.get(2).getRank() == cards.get(3).getRank())) {
					return true;
				} else
					return false;

			} else {//simple handling without phoenix
				return (cards.get(0).getRank() == cards.get(1).getRank() &&
						cards.get(2).getRank() == cards.get(4).getRank()) ||
						cards.get(0).getRank() == cards.get(2).getRank() &&
						cards.get(3).getRank() == cards.get(4).getRank();
			}
		} else
			return false;
	}

	/**
	 * Check if every following Card is one rank higher then the one before. Also check if there are special cards
	 * included.
	 * @author dominik
	 * @return
	 * @param cards
	 */
	public static boolean isStraight(ArrayList<Card> cards) {
		boolean found = true;
		boolean phoenixUsed = false;

		if(cards.size() > 4 && !containsMahjong(cards) && !containsDog(cards) && !containsDragon(cards)){
			if(containsPhoenix(cards)){
				for(int i = 0; i < cards.size() - 2 && found; i++){
					if (cards.get(i).getRank().ordinal() != cards.get(i + 1).getRank().ordinal() -1){
						if (phoenixUsed && cards.get(i + 1).getRank().ordinal() != 13){
							return false;
						}else {
							phoenixUsed = true;
						}
					}
				}
			}else {
				for (int i = 0; i < cards.size() - 2 && found; i++) {
					if (cards.get(i).getRank().ordinal() != cards.get(i + 1).getRank().ordinal() -1)
						found = false;
				}
			}
		}else
			return false;
		return found;
	}

	/**
	 * Check if all 4 Cards have same Rank and do not include any special card.
	 * @author dominik
	 * @return
	 * @param cards
	 */
	public static boolean isFourOfAKindBomb(ArrayList<Card> cards) {

		if (cards.size() == 4 && !containsPhoenix(cards) && !containsDragon(cards) &&
			!containsDog(cards) && !containsMahjong(cards)) {

			if(cards.get(0).getRank().ordinal() == cards.get(3).getRank().ordinal()){
				return true;
			}else{
				return false;
			}

		}else{
			return false;
		}
	}

	/**
	 * Same as method isFourOfAKindBomb, but with the phoenix it's not a bomb.
	 * Phoenix control.
	 * @author dominik
	 * @param cards
	 * @return
	 */
	public static boolean isFourOfAKindPhoenix(ArrayList<Card> cards) {

		if(cards.size() == 4 && containsPhoenix(cards) && !containsDog(cards) &&
				!containsMahjong(cards) && !containsDragon(cards)) {

			if (cards.get(0).getRank().ordinal() == cards.get(2).getRank().ordinal()){
				return true;
			}else
				return false;
		}return false;
	}

	/**
	 * Check first if it is a Straight,in method straight ist already checked if it contains
	 * 	 * a special card. If method isStraight() is true, then check Suits of card.
	 * @author dominik
	 * @param cards
	 * @return
	 */
	public static boolean isStraightFlushBomb(ArrayList<Card> cards) {
		boolean straight = Combination.isStraight(cards);
		boolean flush = true;

		if (containsPhoenix(cards)){
			return false;
		}else{
			Suit suitOfCard = cards.get(0).getSuit();
			for(int i = 1; i < cards.size() -1 && flush; i++) {
				if(cards.get(i).getSuit() != suitOfCard)
					flush = false;
			}
		}

		return (straight && flush);
	}


	/**
	 * Will compare the previous move to the current one and evaluate if the new move is not only valid,
	 * but also beats the previous.
	 * @param oldMove
	 * @param newMove
	 * @return isValidMove
	 */
	public static boolean isValidMove(ArrayList<Card> oldMove, ArrayList<Card> newMove){
		ArrayList<Card> oldClone = (ArrayList<Card>) oldMove.clone();
		ArrayList<Card> newClone = (ArrayList<Card>) newMove.clone();
		Collections.sort(oldClone);
		Collections.sort(newClone);

		// When the first move of a "Stich" is played, oldMove is empty. If evaluateCombination returns anything but
		// a HighCard it is a valid move. If its a high card we need to check if it actually is a single card.
		if (oldMove.isEmpty()){
			if (evaluateCombination(newMove) == HighCard && newMove.size() == 1){
				return true;
			} else {
				return false;
			}
		}
		// all evaluations are based on what the previous move was
		switch (evaluateCombination(oldClone)){

			case HighCard:
				// newMove has to be HighCard as well and have a higher rank, else -> false
				if(evaluateCombination(newClone) == HighCard &&
				oldClone.get(0).compareTo(newClone.get(0)) < 0){

					// a dog can not be played on a mahjong and vise versa, everything else can
					if(containsMahjong(newMove)){
						return false;
					} else {
						return true;
					}
				} else {
					return false;
				}

			case OnePair:
				// even if one played a pair with phoenix we can always compare first cards (sorted list)
				if(evaluateCombination(newClone) == OnePair &&
				oldClone.get(0).compareTo(newClone.get(0))<0){
					return true;
				} else {
					return false;
				}

			case ThreeOfAKind:
				// same as OnePair
				if(evaluateCombination(newClone) == ThreeOfAKind &&
						oldClone.get(0).compareTo(newClone.get(0))<0){
					return true;
				} else {
					return false;
				}

			case Steps:
				break;
			case FullHouse:
				break;
			case Straight:
				break;

			case FourOfAKindBomb:
				// bomb will beat anything exept higher bomb (and straightFlushBomb)
				// so we check if previous was also a bomb
				Combination comb = evaluateCombination(newClone);
				if (comb == StraightFlushBomb){
					return true;
				}
				if(comb == FourOfAKindBomb){
					// check if this bomb is higher than previous bomb
					if (oldClone.get(0).compareTo(newClone.get(0))<0){
						return true;
					} else {
						return false;
					}
				} else {
					return true;
				}

			case FourOfAKindPhoenix:
				// TODO: Not sure about rules
				break;
			case StraightFlushBomb:
				break;
		}
		return false;
	}
}