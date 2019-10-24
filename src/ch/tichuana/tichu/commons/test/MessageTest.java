package ch.tichuana.tichu.commons.test;

import ch.tichuana.tichu.commons.message.DealMsg;
import ch.tichuana.tichu.commons.message.Message;
import ch.tichuana.tichu.commons.message.MessageType;
import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.Rank;
import ch.tichuana.tichu.commons.models.Suit;
import ch.tichuana.tichu.commons.models.TichuType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class MessageTest {

    @Test
    public void testJoinMessage(){
        JSONObject json = new JSONObject();
        json.put("msg","JoinMsg");
        json.put("playerName","player1");
        json.put("password","pw123");
        Message msg = Message.parseMessage(json);
        assertEquals(MessageType.JoinMsg, msg.getMsgType());
        assertEquals("player1", msg.getPlayerName());
        assertEquals("pw123", msg.getPassword());
        assertEquals(json.toJSONString(), msg.toString());
    }

    @Test
    public void testCreatePlayerMsg(){
        JSONObject json = new JSONObject();
        json.put("msg", "CreatePlayerMsg");
        json.put("playerName","player1");
        json.put("password","pw123");
        Message msg = Message.parseMessage(json);
        assertEquals(MessageType.CreatePlayerMsg, msg.getMsgType());
        assertEquals("player1", msg.getPlayerName());
        assertEquals("pw123", msg.getPassword());
        assertEquals(json.toJSONString(), msg.toString());
    }

    @Test
    public void testConnectedMsg(){
        JSONObject json = new JSONObject();
        json.put("msg", "ConnectedMsg");
        json.put("status","true");
        Message msg = Message.parseMessage(json);
        assertEquals(MessageType.ConnectedMsg, msg.getMsgType());
        assertEquals("true", Boolean.toString(msg.getStatus()));
        assertEquals(json.toJSONString(), msg.toString());
    }

    @Test
    public void testDemandSchupfenMsg(){
        JSONObject json = new JSONObject();
        json.put("msg", "DemandSchupfenMsg");
        json.put("playerName","player1");
        Message msg = Message.parseMessage(json);
        assertEquals(MessageType.DemandSchupfenMsg, msg.getMsgType());
        assertEquals("player1", msg.getPlayerName());
        assertEquals(json.toJSONString(), msg.toString());
    }

    @Test
    public void testTichuMsg(){
        JSONObject json = new JSONObject();
        json.put("msg", "TichuMsg");
        json.put("playerName","player1");
        json.put("tichuType","GrandTichu");
        Message msg = Message.parseMessage(json);
        assertEquals(MessageType.TichuMsg, msg.getMsgType());
        assertEquals("player1", msg.getPlayerName());
        assertEquals(TichuType.GrandTichu, msg.getTichuType());
        assertEquals(json.toJSONString(), msg.toString());
    }

    @Test
    public void testReceivedMsg(){
        JSONObject json = new JSONObject();
        json.put("msg", "ReceivedMsg");
        json.put("status","true");
        Message msg = Message.parseMessage(json);
        assertEquals(MessageType.ReceivedMsg, msg.getMsgType());
        assertEquals("true", Boolean.toString(msg.getStatus()));
        assertEquals(json.toJSONString(), msg.toString());
    }

    @Test
    public void testDemandTichuMsg(){
        JSONObject json = new JSONObject();
        json.put("msg", "DemandTichuMsg");
        json.put("tichuType","GrandTichu");
        Message msg = Message.parseMessage(json);
        assertEquals(MessageType.DemandTichuMsg, msg.getMsgType());
        assertEquals(TichuType.GrandTichu, msg.getTichuType());
        assertEquals(json.toJSONString(), msg.toString());
    }

    @Test
    public void testAnnouncedTichuMsg(){
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        array.add("Christian");
        array.add("Philipp");
        json.put("msg", "AnnouncedTichuMsg");
        json.put("tichuType","GrandTichu");
        json.put("players", array);
        Message msg = Message.parseMessage(json);
        assertEquals(MessageType.AnnouncedTichuMsg, msg.getMsgType());
        assertEquals(TichuType.GrandTichu, msg.getTichuType());
        assertEquals(true, msg.getPlayers().contains("Christian"));
        assertEquals(json.toJSONString(), msg.toString());
    }

    @Test
    public void testGameStartedMsg(){
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        array.add("Christian");
        array.add("Philipp");
        json.put("msg", "GameStartedMsg");
        json.put("teamMate","Dominik");
        json.put("opponents", array);
        String[] opponents = {"Christian","Philipp"};
        Message msg = Message.parseMessage(json);
        assertEquals(MessageType.GameStartedMsg, msg.getMsgType());
        assertEquals("Dominik", msg.getTeamMate());
        assertEquals(json.toJSONString(), msg.toString());
    }

    @Test
    public void testSchupfenMsg(){
        Card card = new Card(Suit.Jade, Rank.Ace);
        JSONObject json = new JSONObject();
        json.put("msg", "SchupfenMsg");
        json.put("playerName","player1");
        json.put("card", card.toJSON());
        Message msg = Message.parseMessage(json);
        assertEquals(MessageType.SchupfenMsg, msg.getMsgType());
        assertEquals("player1", msg.getPlayerName());
        assertEquals(true, card.equals(msg.getCard()));
        assertEquals(json.toJSONString(), msg.toString());
    }

    @Test
    public void testDealMsg(){
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        Card c1 = new Card(Suit.Jade, Rank.Ace);
        Card c2 = new Card(Suit.Swords, Rank.Ace);
        Card c3 = new Card(Suit.Swords, Rank.five);
        array.add(c1.toJSON());
        array.add(c2.toJSON());
        json.put("msg", "DealMsg");
        json.put("cards", array);

        Message msg = Message.parseMessage(json);
        assertEquals(MessageType.DealMsg, msg.getMsgType());
        assertTrue(msg.getCards().contains(c1));
        assertTrue(msg.getCards().contains(c2));
        assertTrue(msg.getCards().contains(c2));
        assertFalse(msg.getCards().contains(c3));
        assertEquals(json.toJSONString(), msg.toString());

    }


}