package ch.tichuana.tichu.server.model;

import ch.tichuana.tichu.commons.message.AnnouncedTichuMsg;
import ch.tichuana.tichu.commons.message.Message;
import ch.tichuana.tichu.commons.message.MessageType;
import ch.tichuana.tichu.commons.models.TichuType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ServerModel {

	protected final ObservableList<Player> players = FXCollections.observableArrayList();
	private final Logger logger = Logger.getLogger("");
	private ServerSocket listener;
	private  volatile boolean stop = false;
	private Team teamOne;
	private Team teamTwo;
	private Game game;

	/**
	 * starts Server and listens for new clients to connect
	 * instantiates new Player-, Team-, and Game-Objects
	 * @author Philipp
	 * @param port gets portnumber through controller from config.properties
	 */
	public void startServer(int port) {
		logger.info("server started");
		try {
			listener = new ServerSocket(port, 10, null);
			Runnable r = () -> {
				while (!stop) {
					try {

						Socket socket = listener.accept();
						Player player = new Player(ServerModel.this, socket);

					} catch (IOException e) {
						logger.info(e.toString());
					}
				}
			};
			Thread t = new Thread(r, "ServerSocket");
			t.start();

		} catch (IOException e) {
			logger.info(e.toString());
		}
	}

	/**
	 * stops ServerSocket and Player Sockets
	 * @author Philipp
	 */
	public void stopServer() {
		logger.info("Stop all players");
		for(Player p : players) p.stop();

		logger.info("stop server");
		stop = true;
		if (listener != null) {
			try {
				listener.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * messages that need to be sent via broadcast to all clients
	 * @author Philipp
	 * @param messageType from a specific type
	 * @param identifier and with additional information to create Message-Object
	 */
	public void broadcast(MessageType messageType, String identifier) {
		logger.info("Broadcasting message to players");

		for (Player p : players) {
			p.sendMessage(messageType, identifier);
		}
	}

	/**
	 * broadcasts a given message to all players
	 * @author Christian
	 * @param message Message to be sent to all players
	 */
	public void broadcast(Message message) {
		logger.info("Broadcasting message to players");
		for (Player p : players) {
			p.sendMessage(message);
		}
	}

	public void createGame(){
		this.game = new Game(teamOne, teamTwo, this);
	}

	// Getters and setters
	public Game getGame() {
		return game;
	}
	public ObservableList<Player> getPlayers() {
		return players;
	}
	public Player getPlayer(int i) {return players.get(i); }
	public void setTeamOne(Team teamOne) {
		this.teamOne = teamOne;
	}
	public void setTeamTwo(Team teamTwo) {
		this.teamTwo = teamTwo;
	}
	public void setGame(Game game) {
		this.game = game;
	}
}