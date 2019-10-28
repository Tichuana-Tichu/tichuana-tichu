package ch.tichuana.tichu.server.model;

import ch.tichuana.tichu.commons.message.JoinMsg;
import ch.tichuana.tichu.commons.message.Message;

import java.io.IOException;
import java.net.Socket;

public class Player {

	private String playerName;
	private Socket socket;
	private ServerModel serverModel;
	private boolean announcedTichu;
	private boolean announcedGrandTichu;
	private boolean hisTurn;
	private boolean hasMahjong;

	/**
	 * 
	 * @param socket
	 */
	public Player(ServerModel serverModel, Socket socket) {
		this.serverModel = serverModel;
		this.socket = socket;

		Runnable r = new Runnable() {
			@Override
			public void run() {
				while (true) {
					Message msg = Message.receive(socket);
					if(msg instanceof JoinMsg) {
						Player.this.playerName = ((JoinMsg) msg).getPlayerName();
					}
				}
			}
		};
		Thread t = new Thread(r);
		t.start();
	}

	public void stop() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void send(Message msg) {
		msg.send(socket);
	}

	//Getter & Setter
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
}