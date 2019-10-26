package ch.tichuana.tichu.commons.message;

import ch.tichuana.tichu.commons.models.TichuType;
import org.json.simple.JSONObject;

public class DemandTichuMsg extends Message {

	private TichuType tichuType;

	/**
	 * @author Christian
	 * @param tichuType
	 */
	public DemandTichuMsg(TichuType tichuType) {
		this.tichuType = tichuType;
		this.setMsgType(MessageType.DemandTichuMsg);
	}

	@Override
	public TichuType getTichuType() {
		return this.tichuType;
	}

	@Override
	public String toString() {
		JSONObject json = new JSONObject();
		json.put("msg",this.getMsgType().toString());
		json.put("tichuType", this.tichuType.toString());
		return json.toJSONString();
	}


}