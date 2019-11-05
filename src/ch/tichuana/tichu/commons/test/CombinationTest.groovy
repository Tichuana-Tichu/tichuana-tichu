package ch.tichuana.tichu.commons.test

import ch.tichuana.tichu.commons.models.Card
import ch.tichuana.tichu.commons.models.Combination
import ch.tichuana.tichu.commons.models.Rank
import ch.tichuana.tichu.commons.models.Suit
import org.junit.Test
import static org.junit.jupiter.api.Assertions.*

class CombinationTest {

    @Test
    void isOnePair(){
    Card c1 = new Card(Suit.Swords, Rank.Ace);
    Card c2 = new Card(Suit.Pagodas,Rank.Ace);
    Combination.clonedCards.add(c1, c2);
    assertEquals(Combination.OnePair)
}


    @Test
    void isThreeOfAKind(){
        Card c1 = new Card(Suit.Jade,Rank.five);
        Card c2 = new Card(Suit.Pagodas,Rank.five);
        Card c3 = new Card(Suit.Stars, Rank.five);
        Combination.clonedCards.add(c1, c2, c3);
        assertEquals(Combination.isThreeOfAKind())
    }

    @Test
    void isSteps(){
        Card c1 = new Card(Suit.Jade,Rank.five);
        Card c2 = new Card(Suit.Pagodas,Rank.five);
        Card c3 = new Card(Suit.Stars, Rank.six);
        Card c4 = new Card(Suit.Pagodas, Rank.six);
        Card c5 = new Card(Suit.Jade, Rank.seven);
        Card c6 = new Card(Suit.Pagodas, Rank.seven);
        Combination.clonedCards.add(c1, c2, c3, c4, c5, c6);
        assertEquals(Combination.isSteps());
    }

    @Test
    void isFullHouse(){
        Card c1 = new Card(Suit.Jade,Rank.five);
        Card c2 = new Card(Suit.Pagodas,Rank.five);
        Card c3 = new Card(Suit.Stars, Rank.six);
        Card c4 = new Card(Suit.Pagodas, Rank.six);
        Card c5 = new Card(Suit.Jade, Rank.six);
        Combination.clonedCards.add(c1, c2, c3, c4, c5);
        assertEquals(Combination.isFullHouse());
    }

    @Test
    void isStraight(){
        Card c1 = new Card(Suit.Jade,Rank.three)
        Card c2 = new Card(Suit.Pagodas,Rank.four)
        Card c3 = new Card(Suit.Stars, Rank.five)
        Card c4 = new Card(Suit.Pagodas, Rank.six)
        Combination.clonedCards.add(c1, c2, c3, c4)
        assertEquals(Combination.isStraight())
    }

    @Test
    void isFourOfAKindBomb(){
        Card c1 = new Card(Suit.Jade,Rank.three)
        Card c2 = new Card(Suit.Pagodas,Rank.three)
        Card c3 = new Card(Suit.Stars, Rank.three)
        Card c4 = new Card(Suit.Pagodas, Rank.three)
        Combination.clonedCards.add(c1, c2, c3, c4)
        assertEquals(Combination.isFourOfAKindBomb())
    }

    @Test
    void isStraightFlushBomb(){
        Card c1 = new Card(Suit.Jade,Rank.five)
        Card c2 = new Card(Suit.Jade, Rank.six)
        Card c3 = new Card(Suit.Jade, Rank.seven)
        Card c4 = new Card(Suit.Jade, Rank.eight)
        Card c5 = new Card(Suit.Jade, Rank.nine)
        Combination.clonedCards.add(c1, c2, c3, c4, c5)
        assertEquals(Combination.isStraightFlushBomb())
    }





}