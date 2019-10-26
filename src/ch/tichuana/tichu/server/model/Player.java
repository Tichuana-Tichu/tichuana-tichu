package ch.tichuana.tichu.server.model;

import ch.tichuana.tichu.commons.message.*;
import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.TichuType;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Player {

	private Logger logger = Logger.getLogger("");
	private String playerName;
	private Socket socket;
	private boolean closed;
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
		this.closed = false;

		Runnable r = () -> {
			while (!closed) {
				Message msg = Message.receive(socket);

				if (msg instanceof JoinMsg) {
					Player.this.playerName = ((JoinMsg) msg).getPlayerName();
					sendMessage(MessageType.ConnectedMsg, "true");
					logger.info("Player: "+msg.getPlayerName()+" logged in");
				}

				else if (msg instanceof TichuMsg) {
					logger.info("received tichuMsg");
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
				}

				else if (msg instanceof SchupfenMsg) {
					if (msg.getPlayerName().equals(Player.this.playerName))
						this.hand.add(msg.getCard());
				}

				else if (msg instanceof PlayMsg) {
					this.currentMove = msg.getCards();
				}

				else if (msg instanceof UpdateMsg) {
					if (msg.getNextPlayer().equals(Player.this.playerName))
						this.hisTurn = true;
					else
						this.hisTurn = false;
				}
			}
		};
		Thread t = new Thread(r);
		t.start();
	}

	public void stop() {
		this.closed = true;
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param messageType
	 */
	public void sendMessage(MessageType messageType, String identifier) {
		Message message;

		switch (messageType) {

			case ConnectedMsg:
				message = new ConnectedMsg(Boolean.parseBoolean(identifier));
				message.send(socket);
				break;

			case CreatePlayerMsg:
				message = new CreatePlayerMsg("name", "password");
				message.send(socket);
				break;

			case ReceivedMsg:
				message = new ReceivedMsg(true);
				message.send(socket);
				break;

			case TichuMsg:
				message = new TichuMsg(this.playerName, TichuType.GrandTichu);
				message.send(socket);
				break;

			case SchupfenMsg:
				message = new SchupfenMsg(this.playerName, new Card());
				message.send(socket);
				break;

			case PlayMsg:
				message = new PlayMsg(new ArrayList<Card>());
				message.send(socket);
				break;
		}
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