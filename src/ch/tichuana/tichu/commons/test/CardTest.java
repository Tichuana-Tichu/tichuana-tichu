package ch.tichuana.tichu.commons.test;

import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.Rank;
import ch.tichuana.tichu.commons.models.Suit;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {


    @Test
    void toJSON() {
        Card card = new Card(Suit.Jade,Rank.Ace);
        JSONObject json = new JSONObject();
        json.put("suit",Suit.Jade.toString());
        json.put("rank",Rank.Ace.toString());
        assertEquals(json.toJSONString(), card.toJSON().toJSONString());
    }

    @Test
    void cardFactory() {
        Card c1 = new Card(Suit.Jade,Rank.Ace);
        Card card = Card.cardFactory(c1.toJSON());
        assertEquals(c1.getRank(), card.getRank());
        assertEquals(c1.getSuit(), card.getSuit());
    }

    @Test
    void getScoreValue() {
        Card card = new Card(Suit.Jade,Rank.Ace);
        assertEquals(0,card.getScoreValue());

        card = new Card(Suit.Jade,Rank.five);
        assertEquals(5,card.getScoreValue());

        card = new Card(Suit.Jade,Rank.ten);
        assertEquals(10,card.getScoreValue());

       card = new Card(Rank.dragon);
       assertEquals(25, card.getScoreValue());

        card = new Card(Rank.phoenix);
        assertEquals(-25, card.getScoreValue());
    }

    @Test
    void compareTo() {
    }
}