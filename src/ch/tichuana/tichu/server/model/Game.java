package ch.tichuana.tichu.server.model;

import ch.tichuana.tichu.commons.message.*;
import ch.tichuana.tichu.commons.models.Card;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Game {

	Logger logger = Logger.getLogger("");
	private Socket socket;
	private int gameID;
	public static int MAX_SCORE = 1000;
	private int currentScore = 0;
	private int matchesPlayed = 0;
	private boolean closed;
	private Team[] teams;

	/**
	 *
	 * @param teamOne
	 * @param teamTwo
	 * @param socket
	 */
	public Game(Team teamOne, Team teamTwo, Socket socket) {
		this.gameID = getUniqueID();
		this.currentScore = 0;
		teams[0] = teamOne;
		teams[1] = teamTwo;
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
				message = new PlayMsg(new ArrayList<Card>());
				message.send(socket);
				break;
		}
	}

	public Player getNextPlayer() {
		// TODO - implement Match.getNextPlayer
		return null;
	}

	/**
	 *
	 * @return uniqueID
	 */
	private synchronized int getUniqueID() {
		int uniqueID = 0;
		return uniqueID++;
	}

	//Getter & Setter
	public int getGameID() {
		return this.gameID;
	}
	public int getMAX_SCORE() {
		return this.MAX_SCORE;
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