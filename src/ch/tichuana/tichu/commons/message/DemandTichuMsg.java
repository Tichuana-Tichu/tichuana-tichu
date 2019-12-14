package ch.tichuana.tichu.commons.message;

import ch.tichuana.tichu.commons.models.TichuType;
import org.json.simple.JSONObject;

public class DemandTichuMsg extends Message {

	private TichuType tichuType;

	/**
	 * Asks the players to announce a type of tichu
	 * @author Christian
	 * @param tichuType Type of tichu players have to announce
	 */
	public DemandTichuMsg(TichuType tichuType) {
		this.tichuType = tichuType;
		this.setMsgType(MessageType.DemandTichuMsg);
	}

	/**
	 * returns a json string with content of message
	 * @author Christian
	 * @return json representation of Message
	 */
	@Override
	public String toString() {
		JSONObject json = new JSONObject();
		json.put("msg",this.getMsgType().toString());
		json.put("tichuType", this.tichuType.toString());
		return json.toJSONString();
	}

	@Override
	public TichuType getTichuType() {
		return this.tichuType;
	}
}