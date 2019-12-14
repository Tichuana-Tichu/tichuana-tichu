package ch.tichuana.tichu.commons.message;

import org.json.simple.JSONObject;

public class ConnectedMsg extends Message {

	private boolean status;

	public boolean getStatus() {
		return this.status;
	}

	/**
	 * Tells the player if he successfully connected to the server
	 * @author Christian
	 * @param status boolean stating if client was connected to server
	 */
	public ConnectedMsg(boolean status) {
		this.status = status;
		super.setMsgType(MessageType.ConnectedMsg);
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
		json.put("status", Boolean.toString(this.status));
		return json.toJSONString();
	}
}