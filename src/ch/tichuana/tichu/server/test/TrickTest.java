package ch.tichuana.tichu.server.test;

import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.Rank;
import ch.tichuana.tichu.commons.models.Suit;
import ch.tichuana.tichu.server.Server;
import ch.tichuana.tichu.server.model.ServerModel;
import ch.tichuana.tichu.server.model.Trick;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TrickTest {

    @Test
    public void getScoreTest() {
        ArrayList<Card> move = new ArrayList<>();
        move.add(new Card(Suit.Pagodas, Rank.King));
        move.add(new Card(Suit.Swords, Rank.King));

        ServerModel serverModel = new ServerModel();
        Trick trick = new Trick(serverModel);

        trick.addMove(move);

        assertEquals(20,trick.getScore());


    }

    @Test
    public void testCardGetScore() {
        Card card = new Card(Suit.Jade,Rank.King);

        assertEquals(10, card.getScoreValue());
    }

}