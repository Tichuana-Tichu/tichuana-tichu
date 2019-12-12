package ch.tichuana.tichu.commons.test;

import ch.tichuana.tichu.commons.message.DealMsg;
import ch.tichuana.tichu.commons.message.Message;
import ch.tichuana.tichu.commons.message.MessageType;
import ch.tichuana.tichu.commons.message.UpdateMsg;
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
        //System.out.println("joinmsg: "+msg);
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
        //System.out.println("createplayermsg: "+msg);
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
        //System.out.println("connectedmsg: "+msg);
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
        //System.out.println("demandschupfenmsg: "+msg);
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
        //System.out.println("tichumsg: "+msg);
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
        //System.out.println("receivedmsg: "+msg);
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
        //System.out.println("demandtichumsg: "+msg);
    }

    @Test
    public void testAnnouncedTichuMsg(){
        JSONObject json = new JSONObject();
        json.put("msg", "AnnouncedTichuMsg");
        json.put("tichuType","GrandTichu");
        json.put("player", "Christian");
        Message msg = Message.parseMessage(json);
        assertEquals(MessageType.AnnouncedTichuMsg, msg.getMsgType());
        assertEquals(TichuType.GrandTichu, msg.getTichuType());
        assertEquals("Christian", msg.getPlayerName());
        assertEquals(json.toJSONString(), msg.toString());
        //System.out.println("announcedtichumsg: "+msg);
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
        //System.out.println("gamestartmsg: "+msg);
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
        //System.out.println("schupfenmsg: "+msg);
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
        //System.out.println("dealmsg: "+msg);
    }

    @Test
    public void testPlayMsg(){
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        Card c1 = new Card(Suit.Jade, Rank.Ace);
        Card c2 = new Card(Suit.Swords, Rank.Ace);
        Card c3 = new Card(Suit.Swords, Rank.five);
        array.add(c1);
        array.add(c2.toJSON());
        json.put("msg", "PlayMsg");
        json.put("cards", array);
        Message msg = Message.parseMessage(json);
        assertEquals(MessageType.PlayMsg, msg.getMsgType());
        assertTrue(msg.getCards().contains(c1));
        assertTrue(msg.getCards().contains(c2));
        assertTrue(msg.getCards().contains(c2));
        assertFalse(msg.getCards().contains(c3));
        assertEquals(json.toJSONString(), msg.toString());
        //System.out.println("playmsg: "+msg);
    }

    @Test
    public void testUpdateMsg(){
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        JSONArray playerArray = new JSONArray();
        Card c1 = new Card(Suit.Jade, Rank.Ace);
        Card c2 = new Card(Suit.Swords, Rank.Ace);
        array.add(c1.toJSON());
        array.add(c2.toJSON());
        json.put("lastPlayer", "");
        json.put("msg", "UpdateMsg");
        json.put("nextPlayer", "player1");
        json.put("lastMove", array);
        json.put("opponentScore", 1);
        json.put("ownScore", 1);

        JSONObject player;

        for (int i=0; i<4; i++){
            player = new JSONObject();
            player.put("name", "player"+i);
            player.put("number", 1+i);
            playerArray.add(player);
        }

        json.put("remainingCards",playerArray);

        ArrayList<Card> move = new ArrayList<>();
        move.add(c1);
        move.add(c2);
        UpdateMsg upmsg = new UpdateMsg("player1","player4", move,0,0,new String[]{"player1","player2","3","4"}, new int[]{14,14,14,14});
        assertTrue(upmsg.getLastMove().contains(c1));


        Message msg2 = Message.parseMessage(json);
        assertTrue(msg2.getLastMove().contains(c1));


        Message msg = Message.parseMessage(json);
        assertEquals("", msg.getLastPlayer());
        assertEquals(MessageType.UpdateMsg, msg.getMsgType());
        assertTrue(msg.getLastMove().contains(c1));
        assertTrue(msg.getLastMove().contains(c2));
        assertEquals("player1", msg.getNextPlayer());
        assertEquals(1,msg.getOpponentScore());
        assertEquals(1,msg.getOwnScore());
        assertEquals(2,msg.getRemainingCardsByPlayerName("player1"));
        assertEquals(3,msg.getRemainingCardsByPlayerName("player2"));
        System.out.println("updatemsg: "+msg);
    }

    @Test
    public void testGameDone() {
        JSONObject json = new JSONObject();
        json.put("msg","GameDoneMsg");
        json.put("ownScore",20);
        json.put("opponentScore",100);
        json.put("done",true);


        Message msg = Message.parseMessage(json);

        assertEquals(true, msg.isDone());
        assertEquals(20,msg.getOwnScore());
        assertEquals(100, msg.getOpponentScore());

    }


}