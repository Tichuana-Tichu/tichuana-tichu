package ch.tichuana.tichu.commons.message;

public class CreatePlayerMsg extends Message {

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
	public CreatePlayerMsg(String playerName, String password) {
		this.playerName = playerName;
		this.password = password;
		super.setMsgType(MessageType.CreatePlayerMsg);
	}

}