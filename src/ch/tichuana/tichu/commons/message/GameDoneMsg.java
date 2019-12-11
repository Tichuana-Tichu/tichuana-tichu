package ch.tichuana.tichu.commons.message;

import org.json.simple.JSONObject;

public class GameDoneMsg extends Message{

    private int ownScore, opponentScore;
    private boolean done;

    public GameDoneMsg(int ownScore, int opponentScore, boolean done){
        this.setMsgType(MessageType.GameDoneMsg);
        this.ownScore = ownScore;
        this.opponentScore = opponentScore;
        this.done = done;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("msg", this.getMsgType().toString());
        json.put("opponentScore", opponentScore);
        json.put("ownScore", ownScore);
        json.put("done",done);
        return json.toJSONString();
    }

    @Override
    public int getOpponentScore() {
        return opponentScore;
    }
    @Override
    public int getOwnScore() {
        return ownScore;
    }
    @Override
    public boolean isDone() {
        return done;
    }
}
