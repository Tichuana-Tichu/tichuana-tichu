package ch.tichuana.tichu.server.model;

import ch.tichuana.tichu.commons.message.*;
import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.TichuType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

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
	 * @param serverModel
	 * @param socket
	 */
	public Player(ServerModel serverModel, Socket socket) {
		this.serverModel = serverModel;
		this.socket = socket;

		Runnable r = () -> {
			while (true) {
				Message msg = Message.receive(socket);

				switch (msg.getMsgType()) {

					case AnnouncedTichuMsg:
						break;

					case JoinMsg:
						Player.this.playerName = ((JoinMsg) msg).getPlayerName();
						break;

					case CreatePlayerMsg:
						break;

					case ConnectedMsg:
						break;

					case GameStartedMsg:
						break;

					case DealMsg:
						break;

					case ReceivedMsg:
						break;

					case DemandTichuMsg:
						break;

					case TichuMsg:
						break;

					case DemandSchupfenMsg:
						break;

					case SchupfenMsg:
						break;

					case PlayMsg:
						break;

					case UpdateMsg:
						break;
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

	/**
	 *
	 * @param msg
	 */
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