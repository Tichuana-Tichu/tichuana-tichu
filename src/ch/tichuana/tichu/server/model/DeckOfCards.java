package ch.tichuana.tichu.server.model;

import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.Rank;
import ch.tichuana.tichu.commons.models.Suit;

import java.util.ArrayList;
import java.util.Collections;

public class DeckOfCards {
    private ArrayList<Card> deck;


    public DeckOfCards(){
        deck = new ArrayList<Card>();
        makeDeckOfCards();
    }

    /**
     * Creates a complete deck of valid tichu cards. Stores the cards in instance variable deck
     * @author Christian
     */
    public void makeDeckOfCards(){
        deck.add(new Card(Rank.majhong));
        deck.add(new Card(Rank.dog));
        deck.add(new Card(Rank.dragon));
        deck.add(new Card(Rank.phoenix));
        for (Suit suit : Suit.values()){
            for (Rank rank : Rank.values()){
                if(rank.ordinal()>1 && rank.ordinal()<15)
                    deck.add(new Card(suit,rank));
            }
        }
    }

    /**
     * Reorders the deck into random order
     * @author Christian
     */
    public void shuffleDeck(){
        Collections.shuffle(this.deck);
    }


    /**
     * returns the first 4x8 Cards of the shuffled deck
     * @author Christian
     * @return cards
     */
    public Card[] getFirstHalf(){
        Card[] cards = new Card[32];
        for (int i=0; i<32; i++){
            cards[i] = deck.get(i);
        }
        return cards;
    }

    /**
     * returns the remaining 4x7 cards of the shuffled deck
     * @author Christian
     * @return cards
     */
    public Card[] getSecondHalf(){
        Card[] cards = new Card[24];
        for (int i = deck.size()-1; i>=32; i--){
            cards[i-32] = deck.get(i);
        }
        return cards;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }
}