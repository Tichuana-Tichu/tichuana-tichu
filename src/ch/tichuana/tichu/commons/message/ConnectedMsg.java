package ch.tichuana.tichu.commons.message;

import org.json.simple.JSONObject;

public class ConnectedMsg extends Message {

	private boolean status;

	public boolean getStatus() {
		return this.status;
	}

	/**
	 * @author Christian
	 * @param status
	 */
	public ConnectedMsg(boolean status) {
		this.status = status;
		super.setMsgType(MessageType.ConnectedMsg);
	}

	@Override
	public String toString() {
		JSONObject json = new JSONObject();
		json.put("msg",this.getMsgType().toString());
		json.put("status", Boolean.toString(this.status));
		return json.toJSONString();
	}
}