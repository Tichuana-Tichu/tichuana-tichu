package ch.tichuana.tichu.commons.message;

import org.json.simple.JSONObject;

public class ChatMsg extends Message{

    private String playerName;
    private String content;

    @Override
    public String getPlayerName() {
        return this.playerName;
    }

    @Override
    public String getContent() {
        return this.content;
    }

    /**
     * @author Christian
     * @param playerName
     * @param content
     */
    public ChatMsg(String playerName, String content) {
        this.playerName = playerName;
        this.content = content;
        super.setMsgType(MessageType.ChatMsg);
    }

    @Override
    public String toString(){
        JSONObject json = new JSONObject();
        json.put("msg",this.getMsgType().toString());
        json.put("playerName", this.playerName);
        json.put("content", this.content);
        return json.toJSONString();
    }
}
