package ch.tichuana.tichu.commons.message;

import org.json.simple.JSONObject;

public class JoinMsg extends Message {

	private String playerName;
	private String password;

	@Override
	public String getPlayerName() {
		return this.playerName;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	/**
	 * 
	 * @param playerName
	 * @param password
	 */
	public JoinMsg(String playerName, String password) {
		this.playerName = playerName;
		this.password = password;
		super.setMsgType(MessageType.JoinMsg);
	}

	@Override
	public String toString(){
		JSONObject json = new JSONObject();
		json.put("msg",this.getMsgType().toString());
		json.put("playerName", this.playerName);
		json.put("password", this.password);
		return json.toJSONString();
	}

}