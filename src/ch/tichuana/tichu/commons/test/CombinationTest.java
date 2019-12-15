package ch.tichuana.tichu.commons.test;

import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.Combination;
import ch.tichuana.tichu.commons.models.Rank;
import ch.tichuana.tichu.commons.models.Suit;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CombinationTest {

    /** Testing the Combination class with created cards.
     * @author dominik
     */
    @Test
    void isOnePair(){
        // Test with normal cards
        Card c1 = new Card(Suit.Swords, Rank.Ace);
        Card c2 = new Card(Suit.Pagodas,Rank.Ace);
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        assertTrue(Combination.isOnePair(hand));

        // Test with wrong cards
        c1 = new Card(Suit.Swords, Rank.Ace);
        c2 = new Card(Suit.Pagodas,Rank.two);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        assertFalse(Combination.isOnePair(hand));

        // Test with special Card dog
        c1 = new Card(Rank.dog);
        c2 = new Card(Suit.Pagodas,Rank.two);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        assertFalse(Combination.isOnePair(hand));


        // Test with special Card dog
        c1 = new Card(Rank.mahjong);
        c2 = new Card(Suit.Pagodas,Rank.two);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        assertFalse(Combination.isOnePair(hand));

        // Test with special Card dog
        c1 = new Card(Rank.dragon);
        c2 = new Card(Suit.Pagodas,Rank.two);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        assertFalse(Combination.isOnePair(hand));


        // Test with Phoenix
        c1 = new Card(Suit.Swords, Rank.Ace);
        c2 = new Card(Rank.phoenix);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        assertTrue(Combination.isOnePair(hand));
    }

    /** Test for method isThreeOfAKind()
     * @author dominik
     */
    @Test
    void isThreeOfAKind(){
        // Test with correct cards
        Card c1 = new Card(Suit.Swords, Rank.Ace);
        Card c2 = new Card(Suit.Pagodas,Rank.Ace);
        Card c3 = new Card(Suit.Jade, Rank.Ace);
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        assertTrue(Combination.isThreeOfAKind(hand));

        // Test with Phoenix
        c1 = new Card(Suit.Jade, Rank.Ace);
        c2 = new Card(Suit.Swords, Rank.Ace);
        c3 = new Card(Rank.phoenix);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        assertTrue(Combination.isThreeOfAKind(hand));

        // Test with phoenix but no threeOfAKind
        c1 = new Card(Suit.Jade, Rank.two);
        c2 = new Card(Suit.Swords, Rank.Ace);
        c3 = new Card(Rank.phoenix);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        assertFalse(Combination.isThreeOfAKind(hand));

        // Test with special cards dragon
        c1 = new Card(Rank.dragon);
        c2 = new Card(Suit.Swords, Rank.Ace);
        c3 = new Card(Rank.phoenix);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        assertFalse(Combination.isThreeOfAKind(hand));

        // Test with another special card mahjong
        c1 = new Card(Suit.Jade, Rank.two);
        c2 = new Card(Suit.Swords, Rank.Ace);
        c3 = new Card(Rank.mahjong);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        assertFalse(Combination.isThreeOfAKind(hand));

        // Test with another special card dog
        c1 = new Card(Suit.Jade, Rank.two);
        c2 = new Card(Suit.Swords, Rank.Ace);
        c3 = new Card(Rank.dog);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        assertFalse(Combination.isThreeOfAKind(hand));

        // Test with two special card dog & majhong
        c1 = new Card(Suit.Jade, Rank.two);
        c2 = new Card(Rank.mahjong);
        c3 = new Card(Rank.dog);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        assertFalse(Combination.isThreeOfAKind(hand));

        // Test with another special card dragon
        c1 = new Card(Suit.Jade, Rank.two);
        c2 = new Card(Suit.Swords, Rank.Ace);
        c3 = new Card(Rank.dragon);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        assertFalse(Combination.isThreeOfAKind(hand));

        // Test with another special card dragon but included phoenix
        c1 = new Card(Rank.dragon);
        c2 = new Card(Suit.Swords, Rank.Ace);
        c3 = new Card(Rank.phoenix);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        assertFalse(Combination.isThreeOfAKind(hand));
    }

    /** Test for the method isSteps()
     * @author dominik
     */
    @Test
    void isSteps(){
        // Test with normal cards
        // 3 3 4 4 5 5
        Card c1 = new Card(Suit.Pagodas,Rank.three);
        Card c2 = new Card(Suit.Jade, Rank.three);
        Card c3 = new Card(Suit.Swords, Rank.four);
        Card c4 = new Card(Suit.Pagodas,Rank.four);
        Card c5 = new Card(Suit.Pagodas,Rank.five);
        Card c6 = new Card(Suit.Swords, Rank.five);
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        hand.add(c4);
        hand.add(c5);
        hand.add(c6);
        assertTrue(Combination.isSteps(hand));

        // Test with normal cards
        // 4 4 5 5
        c1 = new Card(Suit.Swords, Rank.four);
        c2 = new Card(Suit.Pagodas,Rank.four);
        c3 = new Card(Suit.Jade, Rank.five);
        c4 = new Card(Suit.Swords, Rank.five);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        hand.add(c4);
        assertTrue(Combination.isSteps(hand));

        // Test with normal cards and phoenix
        // 2 2 3 P
        c1 = new Card(Suit.Swords, Rank.two);
        c2 = new Card(Suit.Pagodas,Rank.two);
        c3 = new Card(Suit.Jade, Rank.three);
        c4 = new Card(Rank.phoenix);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        hand.add(c4);
        assertTrue(Combination.isSteps(hand));

        // Test with normal cards and phoenix
        // 2 2 3 3 4 P
        c1 = new Card(Suit.Swords, Rank.two);
        c2 = new Card(Suit.Pagodas,Rank.two);
        c3 = new Card(Suit.Jade, Rank.three);
        c4 = new Card(Suit.Swords, Rank.three);
        c5 = new Card(Suit.Swords, Rank.four);
        c6 = new Card(Rank.phoenix);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        hand.add(c4);
        hand.add(c5);
        hand.add(c6);
        assertTrue(Combination.isSteps(hand));

        // Test with normal cards and phoenix
        // 2 2 3 4 4 P
        c1 = new Card(Suit.Swords, Rank.two);
        c2 = new Card(Suit.Pagodas,Rank.two);
        c3 = new Card(Suit.Jade, Rank.three);
        c4 = new Card(Suit.Swords, Rank.four);
        c5 = new Card(Suit.Swords, Rank.four);
        c6 = new Card(Rank.phoenix);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        hand.add(c4);
        hand.add(c5);
        hand.add(c6);
        assertTrue(Combination.isSteps(hand));

        // Test with normal cards and phoenix, but a false card
        // 5 5 6 7 P
        c1 = new Card(Suit.Swords, Rank.five);
        c2 = new Card(Suit.Pagodas,Rank.five);
        c3 = new Card(Suit.Jade, Rank.six);
        c4 = new Card(Suit.Swords, Rank.seven);
        c5 = new Card(Rank.phoenix);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        hand.add(c4);
        hand.add(c5);
        assertFalse(Combination.isSteps(hand));

        // Test with mahjong and phoenix
        c1 = new Card(Rank.mahjong);
        c2 = new Card(Suit.Pagodas,Rank.two);
        c3 = new Card(Suit.Jade, Rank.two);
        c4 = new Card(Suit.Swords, Rank.three);
        c5 = new Card(Suit.Swords, Rank.three);
        c6 = new Card(Rank.phoenix);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        hand.add(c4);
        hand.add(c5);
        hand.add(c6);
        assertFalse(Combination.isSteps(hand));

        // Test with not enough cards
        c1 = new Card(Suit.Swords, Rank.two);
        c2 = new Card(Suit.Pagodas,Rank.three);
        c5 = new Card(Suit.Swords, Rank.four);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c5);
        assertFalse(Combination.isSteps(hand));

        // Test with majhong and phoenix
        c1 = new Card(Rank.mahjong);
        c2 = new Card(Suit.Pagodas,Rank.two);
        c3 = new Card(Suit.Jade, Rank.three);
        c4 = new Card(Rank.dragon);
        c5 = new Card(Rank.phoenix);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        hand.add(c4);
        hand.add(c5);
        assertFalse(Combination.isSteps(hand));
    }

    /**
     * Test for the Method isFullHouse()
     * @author dominik
     */
    @Test
    void isFullHouse(){
        // Test with normal cards
        Card c1 = new Card(Suit.Swords, Rank.two);
        Card c2 = new Card(Suit.Pagodas,Rank.two);
        Card c3 = new Card(Suit.Jade, Rank.four);
        Card c4 = new Card(Suit.Swords, Rank.four);
        Card c5 = new Card(Suit.Pagodas,Rank.four);
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        hand.add(c4);
        hand.add(c5);
        assertTrue(Combination.isFullHouse(hand));

        // Test with normal cards
        c1 = new Card(Suit.Swords, Rank.Ace);
        c2 = new Card(Suit.Pagodas,Rank.Ace);
        c3 = new Card(Suit.Jade, Rank.four);
        c4 = new Card(Suit.Swords, Rank.four);
        c5 = new Card(Suit.Stars, Rank.four);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        hand.add(c4);
        hand.add(c5);
        assertTrue(Combination.isSteps(hand));

        // Test with normal cards and phoenix
        c1 = new Card(Suit.Swords, Rank.Ace);
        c2 = new Card(Suit.Pagodas,Rank.Ace);
        c3 = new Card(Suit.Jade, Rank.four);
        c4 = new Card(Suit.Swords, Rank.four);
        c5 = new Card(Rank.phoenix);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        hand.add(c4);
        hand.add(c5);
        assertTrue(Combination.isSteps(hand));

        // Test with normal cards and phoenix
        c1 = new Card(Suit.Swords, Rank.Ace);
        c2 = new Card(Suit.Pagodas, Rank.four);
        c3 = new Card(Suit.Jade, Rank.four);
        c4 = new Card(Suit.Swords, Rank.four);
        c5 = new Card(Rank.phoenix);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        hand.add(c4);
        hand.add(c5);
        assertTrue(Combination.isSteps(hand));

        // Test with normal cards and phoenix and majhong
        c1 = new Card(Rank.mahjong);
        c2 = new Card(Suit.Pagodas, Rank.four);
        c3 = new Card(Suit.Jade, Rank.four);
        c4 = new Card(Suit.Swords, Rank.three);
        c5 = new Card(Rank.phoenix);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        hand.add(c4);
        hand.add(c5);
        assertFalse(Combination.isFullHouse(hand));

        // Test with just four cards
        c2 = new Card(Suit.Pagodas, Rank.four);
        c3 = new Card(Suit.Jade, Rank.four);
        c4 = new Card(Suit.Swords, Rank.four);
        c5 = new Card(Suit.Swords, Rank.Ace);
        hand = new ArrayList<>();
        hand.add(c2);
        hand.add(c3);
        hand.add(c4);
        hand.add(c5);
        assertFalse(Combination.isFullHouse(hand));

        // Test with four cards, phoenix included
        c1 = new Card(Suit.Pagodas, Rank.three);
        c2 = new Card(Suit.Pagodas, Rank.four);
        c3 = new Card(Suit.Jade, Rank.four);
        c5 = new Card(Rank.phoenix);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        hand.add(c5);
        assertFalse(Combination.isFullHouse(hand));
    }

    /**
     * Test for the method isStraight()
     * @author dominik
     */
    @Test
    void isStraight(){
        // Test with normal cards
        Card c1 = new Card(Suit.Swords, Rank.two);
        Card c2 = new Card(Suit.Pagodas,Rank.three);
        Card c3 = new Card(Suit.Jade, Rank.four);
        Card c4 = new Card(Suit.Swords, Rank.five);
        Card c5 = new Card(Suit.Pagodas,Rank.six);
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        hand.add(c4);
        hand.add(c5);
        assertTrue(Combination.isStraight(hand));

        // Test with normal cards and phoenix
        c1 = new Card(Suit.Stars, Rank.three);
        c2 = new Card(Suit.Pagodas, Rank.four);
        c3 = new Card(Suit.Jade, Rank.five);
        c4 = new Card(Suit.Swords, Rank.six);
        c5 = new Card(Rank.phoenix);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        hand.add(c4);
        hand.add(c5);
        assertTrue(Combination.isStraight(hand));

        // Test with normal cards and phoenix and majhong
        c1 = new Card(Rank.mahjong);
        c2 = new Card(Suit.Pagodas, Rank.two);
        c3 = new Card(Suit.Jade, Rank.three);
        c4 = new Card(Suit.Swords, Rank.four);
        c5 = new Card(Rank.phoenix);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        hand.add(c4);
        hand.add(c5);
        assertFalse(Combination.isStraight(hand));

        // Test with normal cards and dragon
        c1 = new Card(Suit.Stars, Rank.three);
        c2 = new Card(Suit.Pagodas, Rank.four);
        c3 = new Card(Suit.Jade, Rank.five);
        c4 = new Card(Suit.Swords, Rank.six);
        c5 = new Card(Rank.dragon);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        hand.add(c4);
        hand.add(c5);
        assertFalse(Combination.isStraight(hand));
    }

    /**
     * Test for the method isFourOfAKindBomb()
     * @author dominik
     */
    @Test
    void isFourOfAKindBomb(){
        // Test with normal cards
        Card c1 = new Card(Suit.Swords, Rank.two);
        Card c2 = new Card(Suit.Pagodas,Rank.two);
        Card c3 = new Card(Suit.Jade, Rank.two);
        Card c4 = new Card(Suit.Stars, Rank.two);
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        hand.add(c4);
        assertTrue(Combination.isFourOfAKindBomb(hand));

        // Test with normal cards
        c1 = new Card(Suit.Swords, Rank.three);
        c2 = new Card(Suit.Pagodas,Rank.two);
        c3 = new Card(Suit.Jade, Rank.two);
        c4 = new Card(Suit.Stars, Rank.two);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        hand.add(c4);
        assertFalse(Combination.isFourOfAKindBomb(hand));

        // Test with normal cards and phoenix
        c1 = new Card(Suit.Swords, Rank.two);
        c2 = new Card(Suit.Pagodas,Rank.two);
        c3 = new Card(Suit.Jade, Rank.two);
        c4 = new Card(Rank.phoenix);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        hand.add(c4);
        assertFalse(Combination.isFourOfAKindBomb(hand));

        // Test with normal cards and phoenix
        c1 = new Card(Suit.Swords, Rank.two);
        c2 = new Card(Suit.Pagodas,Rank.two);
        c3 = new Card(Suit.Jade, Rank.two);
        c4 = new Card(Rank.mahjong);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        hand.add(c4);
        assertFalse(Combination.isFourOfAKindBomb(hand));
    }

    /**
     * Test for the method isFourOfAKindPhoenix()
     * @author dominik
     */
    @Test
    void isFourOfAKindPhoenix(){
        Card c1 = new Card(Suit.Swords, Rank.two);
        Card c2 = new Card(Suit.Pagodas,Rank.two);
        Card c3 = new Card(Suit.Jade, Rank.two);
        Card c4 = new Card(Suit.Stars, Rank.two);
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        hand.add(c4);
        assertFalse(Combination.isFourOfAKindPhoenix(hand));

        // Test with normal cards
        c1 = new Card(Suit.Swords, Rank.three);
        c2 = new Card(Suit.Pagodas,Rank.two);
        c3 = new Card(Suit.Jade, Rank.two);
        c4 = new Card(Suit.Stars, Rank.two);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        hand.add(c4);
        assertFalse(Combination.isFourOfAKindPhoenix(hand));

        // Test with normal cards and phoenix
        c4 = new Card(Rank.phoenix);
        c2 = new Card(Suit.Pagodas,Rank.two);
        c3 = new Card(Suit.Jade, Rank.two);
        c1 = new Card(Suit.Stars, Rank.two);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        hand.add(c4);
        assertTrue(Combination.isFourOfAKindPhoenix(hand));

    }

    /**
     * method isStraightFlush()
     * @author dominik
     */
    @Test
    void isStraightFlush(){
        //Test with normal Cards
        Card c1 = new Card(Suit.Stars, Rank.three);
        Card c2 = new Card(Suit.Stars,Rank.four);
        Card c3 = new Card(Suit.Stars, Rank.five);
        Card c4 = new Card(Suit.Stars, Rank.six);
        Card c5 = new Card(Suit.Stars, Rank.seven);
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        hand.add(c4);
        hand.add(c5);

        assertTrue(Combination.isStraightFlushBomb(hand));

        // Test with normal Cards
        c1 = new Card(Suit.Stars, Rank.five);
        c2 = new Card(Suit.Stars,Rank.six);
        c3 = new Card(Suit.Stars, Rank.seven);
        c4 = new Card(Suit.Stars, Rank.eight);
        c5 = new Card(Suit.Stars, Rank.nine);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        hand.add(c4);
        hand.add(c5);

        assertTrue(Combination.isStraightFlushBomb(hand));

        //Test with phoenix
        c1 = new Card(Suit.Stars, Rank.five);
        c2 = new Card(Suit.Stars,Rank.six);
        c3 = new Card(Suit.Stars, Rank.seven);
        c4 = new Card(Suit.Stars, Rank.eight);
        c5 = new Card(Rank.phoenix);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        hand.add(c4);
        hand.add(c5);

        assertFalse(Combination.isStraightFlushBomb(hand));

        //Test with majhong
        c1 = new Card(Suit.Stars, Rank.five);
        c2 = new Card(Suit.Stars,Rank.six);
        c3 = new Card(Suit.Stars, Rank.seven);
        c4 = new Card(Suit.Stars, Rank.eight);
        c5 = new Card(Rank.mahjong);
        hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        hand.add(c4);
        hand.add(c5);

        assertFalse(Combination.isStraightFlushBomb(hand));
    }
}