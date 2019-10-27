package ch.tichuana.tichu.server.model;

import ch.tichuana.tichu.commons.message.*;
import ch.tichuana.tichu.commons.models.Card;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Game {

	private Logger logger = Logger.getLogger("");
	private Socket socket;
	private int gameID;
	private int currentScore;
	private int matchesPlayed = 0;
	private volatile boolean closed;
	private Team[] teams = new Team[2];

	/**
	 * Game will be started from ServerModel as soon as 4 player are connected to server
	 * Creates a new Thread to listen for general messages from clients
	 * @author Philipp
	 * @param teamOne the first team created by the ServerModel
	 * @param teamTwo the second team created by the ServerModel
	 * @param socket own Socket from ServerModel
	 */
	Game(Team teamOne, Team teamTwo, Socket socket) {
		this.gameID = getUniqueID();
		this.currentScore = 0;
		this.teams[0] = teamOne;
		this.teams[1] = teamTwo;
		this.socket = socket;
		this.closed = false;

		Runnable r = () -> {
			while (!closed) {
				Message msg = Message.receive(socket);

				if (msg instanceof AnnouncedTichuMsg) {
					logger.info("announced Tichu");
				}

				else if (msg instanceof SchupfenMsg) {

				}

				else if (msg instanceof UpdateMsg) {

				}

				else if (msg instanceof GameStartedMsg) {

				}
			}
		};
		Thread t = new Thread(r);
		t.start();
	}

	/**
	 * stops listening and closes socket
	 * @author Philipp
	 */
	public void stop() {
		this.closed = true;
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * called from controller to send messages to ClientModel (Client)
	 * @author Philipp
	 * @param messageType from a specific type
	 */
	public void sendMessage(MessageType messageType) {
		Message message;

		switch (messageType) {

			case CreatePlayerMsg:
				message = new CreatePlayerMsg("name", "password");
				message.send(socket);
				break;

			case ReceivedMsg:
				message = new ReceivedMsg(true);
				message.send(socket);
				break;

			case TichuMsg:

				break;

			case SchupfenMsg:

				break;

			case PlayMsg:
				message = new PlayMsg(new ArrayList<>());
				message.send(socket);
				break;
		}
	}

	/**
	 * @author Philipp
	 * @return Player of which isTurn is true
	 */
	public Player getNextPlayer() {
		// TODO - implement Match.getNextPlayer
		return null;
	}

	/**
	 * for identification
	 * @author Philipp
	 * @return uniqueID
	 */
	private synchronized int getUniqueID() {
		int uniqueID = 0;
		return ++uniqueID;
	}

	//Getter & Setter
	public int getGameID() {
		return this.gameID;
	}
	public int getMAX_SCORE() {
		int MAX_SCORE = 1000;
		return MAX_SCORE;
	}
	public int getCurrentScore() {
		return this.currentScore;
	}
	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
	}
	public int getMatchesPlayed() {
		return this.matchesPlayed;
	}
	public void setMatchesPlayed(int matchesPlayed) {
		this.matchesPlayed = matchesPlayed;
	}
	public Team[] getTeams() {
		return this.teams;
	}
}