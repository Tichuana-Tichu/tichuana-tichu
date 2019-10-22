package ch.tichuana.tichu.commons.test;

import ch.tichuana.tichu.commons.message.JoinMsg;
import ch.tichuana.tichu.commons.message.Message;
import ch.tichuana.tichu.commons.message.MessageType;
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
    }

}