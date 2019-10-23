package ch.tichuana.tichu.commons.test;

import ch.tichuana.tichu.commons.message.Message;
import ch.tichuana.tichu.commons.message.MessageType;
import ch.tichuana.tichu.commons.models.TichuType;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

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

}