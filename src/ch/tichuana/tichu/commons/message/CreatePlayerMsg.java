package ch.tichuana.tichu.commons.message;

public class CreatePlayerMsg extends Message {

	private String playerName;
	private String password;

	public String getPlayerName() {
		return this.playerName;
	}

	public String getPassword() {
		return this.password;
	}

	/**
	 * 
	 * @param playerName
	 * @param password
	 */
	public CreatePlayerMsg(String playerName, String password) {
		// TODO - implement CreatePlayerMsg.CreatePlayerMsg
	}

}