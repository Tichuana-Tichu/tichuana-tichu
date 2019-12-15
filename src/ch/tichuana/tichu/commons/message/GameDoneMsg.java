package ch.tichuana.tichu.commons.message;

import org.json.simple.JSONObject;

public class GameDoneMsg extends Message{

    private int ownScore, opponentScore;
    private boolean done;

    /**
     * Game done message is sent after every match updating the scores and informing if the game is done or not.
     * @author Christian
     * @param ownScore score of player's team
     * @param opponentScore score of player's opposing team
     * @param done boolean stating if game is over
     */
    public GameDoneMsg(int ownScore, int opponentScore, boolean done){
        this.setMsgType(MessageType.GameDoneMsg);
        this.ownScore = ownScore;
        this.opponentScore = opponentScore;
        this.done = done;
    }

    /**
     * returns a json string with content of message
     * @author Christian
     * @return json representation of Message
     */
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
