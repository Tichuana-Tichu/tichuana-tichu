package ch.tichuana.tichu.commons.message;

import org.json.simple.JSONObject;

public class CreatePlayerMsg extends Message {

	private String playerName;
	private String password;

	/**
	 * @author Christian
	 * @param playerName name of player
	 * @param password players password
	 */
	public CreatePlayerMsg(String playerName, String password) {
		this.playerName = playerName;
		this.password = password;
		super.setMsgType(MessageType.CreatePlayerMsg);
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
		json.put("playerName", this.playerName);
		json.put("password", this.password);
		return json.toJSONString();
	}

	@Override
	public String getPlayerName() {
		return this.playerName;
	}

	@Override
	public String getPassword() {
		return this.password;
	}
}