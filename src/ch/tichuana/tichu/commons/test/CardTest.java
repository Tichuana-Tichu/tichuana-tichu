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
    }

    @Test
    void getScoreValue() {
    }

    @Test
    void compareTo() {
    }
}