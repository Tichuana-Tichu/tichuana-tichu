package commons.message;

public class JoinMsg extends Message {

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
	public JoinMsg(String playerName, String password) {
		// TODO - implement JoinMsg.JoinMsg
	}

}