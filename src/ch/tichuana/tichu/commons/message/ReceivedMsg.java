package ch.tichuana.tichu.commons.message;

import org.json.simple.JSONObject;

public class ReceivedMsg extends Message {

	private boolean status;
	/**
	 * client can confirm he received the server message
	 * @author Christian
	 * @param status boolean stating if message was received
	 */
	public ReceivedMsg(boolean status) {
		this.status = status;
		this.setMsgType(MessageType.ReceivedMsg);
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

	@Override
	public boolean getStatus() {
		return this.status;
	}
}