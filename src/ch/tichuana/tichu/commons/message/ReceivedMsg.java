package ch.tichuana.tichu.commons.message;

import org.json.simple.JSONObject;

public class ReceivedMsg extends Message {

	private boolean status;
	/**
	 * 
	 * @param status
	 */
	public ReceivedMsg(boolean status) {
		this.status = status;
		this.setMsgType(MessageType.ReceivedMsg);
	}

	@Override
	public boolean getStatus() {
		return this.status;
	}

	@Override
	public String toString() {
		JSONObject json = new JSONObject();
		json.put("msg",this.getMsgType().toString());
		json.put("status", Boolean.toString(this.status));
		return json.toJSONString();
	}
}