package ch.tichuana.tichu.server.model;

import ch.tichuana.tichu.client.model.Hand;
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
	private ArrayList<Card> currentMove;
	private ArrayList<Card> hand;

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

				if (Message.receive(socket) != null) {
					Message msg = Message.receive(socket);

					if (msg instanceof JoinMsg) {
						Player.this.playerName = ((JoinMsg) msg).getPlayerName();
						send(new ConnectedMsg(true));
					}
					/*
					switch (msg.getMsgType()) {

						case JoinMsg:
							Player.this.playerName = ((JoinMsg) msg).getPlayerName();
							send(new ConnectedMsg(true));
							break;

						case TichuMsg:
							switch (msg.getTichuType()) {

								case SmallTichu:
									Player.this.announcedTichu = true;
									break;

								case GrandTichu:
									Player.this.announcedGrandTichu = true;
									break;

								case none:
									Player.this.announcedTichu = false;
									Player.this.announcedGrandTichu = false;
									break;
							}
							break;

						case SchupfenMsg:
							if (msg.getPlayerName().equals(Player.this.playerName))
								this.hand.add(msg.getCard());
							break;

						case PlayMsg:
							this.currentMove = msg.getCards();
							break;

						case UpdateMsg:

							if (msg.getNextPlayer().equals(Player.this.playerName))
								this.hisTurn = true;
							else
								this.hisTurn = false;
							break;
					}
					*/
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
	public ArrayList<Card> getCurrentMove() {
		return currentMove;
	}
	public void setCurrentMove(ArrayList<Card> currentMove) {
		this.currentMove = currentMove;
	}
}