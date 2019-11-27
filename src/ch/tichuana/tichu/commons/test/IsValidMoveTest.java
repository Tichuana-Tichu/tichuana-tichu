package ch.tichuana.tichu.commons.test;

import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.Combination;
import ch.tichuana.tichu.commons.models.Rank;
import ch.tichuana.tichu.commons.models.Suit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class IsValidMoveTest {

    static Rank[] rank = Rank.values();
    static ArrayList<Card> cards = new ArrayList<Card>();
    static ArrayList<Card> oldMove = new ArrayList<Card>();
    static ArrayList<Card> newMove = new ArrayList<Card>();

    @BeforeAll
    public static void makeCards(){
        // create all cards
        cards.add(new Card(Rank.dog));
        cards.add(new Card(Rank.mahjong));
        for (int i = 2; i<15; i++){
            cards.add(new Card(Suit.Pagodas, rank[i]));
        }
        cards.add(new Card(Rank.phoenix));
        cards.add(new Card(Rank.dragon));
    }

    @Test
    public void testHighCard(){
        // Test if two beats mahjong
        oldMove.add(cards.get(1));
        newMove.add(cards.get(2));
        assertTrue(Combination.isValidMove(oldMove,newMove));

        oldMove.clear(); newMove.clear();

        // Check if ten beats four
        oldMove.add(cards.get(4));
        newMove.add(cards.get(10));
        assertTrue(Combination.isValidMove(oldMove,newMove));

        oldMove.clear(); newMove.clear();

        // Check if Dragon beats Ace
        oldMove.add(cards.get(14));
        newMove.add(cards.get(16));
        assertTrue(Combination.isValidMove(oldMove,newMove));

        oldMove.clear(); newMove.clear();

        // Check if Dragon beats Phoenix
        oldMove.add(cards.get(15));
        newMove.add(cards.get(16));
        assertTrue(Combination.isValidMove(oldMove,newMove));

        oldMove.clear(); newMove.clear();

        // Ensure that lower Ranks don't beat highers
        oldMove.add(cards.get(13));
        newMove.add(cards.get(12));
        assertFalse(Combination.isValidMove(oldMove,newMove));

        oldMove.clear(); newMove.clear();

        // Ensure that dog can't be played on Mahjong
        oldMove.add(cards.get(1));
        newMove.add(cards.get(0));
        assertFalse(Combination.isValidMove(oldMove,newMove));

        oldMove.clear(); newMove.clear();

        // Ensure that dog can't be played on Mahjong
        oldMove.add(cards.get(0));
        newMove.add(cards.get(1));
        assertFalse(Combination.isValidMove(oldMove,newMove));
    }

    @Test
    public void testOnePair(){
        oldMove.clear(); newMove.clear();


    }

}