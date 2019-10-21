package server.model;

public class Player {

	private String playerName;
	private Socket socket;
	private boolean announcedTichu;
	private boolean announcedGrandTichu;
	private boolean hisTurn;
	private boolean hasMahjong;

	public String getPlayerName() {
		return this.playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public boolean isAnnouncedTichu() {
		return this.announcedTichu;
	}

	public void setAnnouncedTichu(boolean announcedTichu) {
		this.announcedTichu = announcedTichu;
	}

	public boolean isAnnouncedGrandTichu() {
		return this.announcedGrandTichu;
	}

	public void setAnnouncedGrandTichu(boolean announcedGrandTichu) {
		this.announcedGrandTichu = announcedGrandTichu;
	}

	public boolean isHisTurn() {
		return this.hisTurn;
	}

	public void setHisTurn(boolean hisTurn) {
		this.hisTurn = hisTurn;
	}

	public boolean isHasMahjong() {
		return this.hasMahjong;
	}

	public void setHasMahjong(boolean hasMahjong) {
		this.hasMahjong = hasMahjong;
	}

	/**
	 * 
	 * @param socket
	 */
	public Player(Socket socket) {
		// TODO - implement Player.Player
	}

	public static Player getPlayerByName() {
		// TODO - implement Player.getPlayerByName
	}

}