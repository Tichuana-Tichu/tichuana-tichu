package ch.tichuana.tichu.server.model;

import ch.tichuana.tichu.commons.message.Message;
import ch.tichuana.tichu.commons.message.MessageType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class ServerModel {

	protected final ObservableList<Player> players = FXCollections.observableArrayList();
	private final Logger logger = Logger.getLogger("");
	private ServerSocket listener;
	private  volatile boolean stop = false;
	private Team teamOne;
	private Team teamTwo;
	private Game[] games;
	final int MAX_GAMES = 1;

	public void startServer(int port) {
		logger.info("server started");
		try {
			listener = new ServerSocket(port, 10, null);
			Runnable r = () -> {
				while (!stop) {
					try {
						Socket socket = listener.accept();
						Player player = new Player(ServerModel.this, socket);

						if (players.size() == 2) {
							this.teamOne = new Team(players.get(0), players.get(1));
						}
						if (players.size() == 4) {
							this.teamTwo = new Team(players.get(2), players.get(3));
							games[MAX_GAMES-1] = new Game(teamOne, teamTwo, socket);
						}

						players.add(player);
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

	public void broadcast(MessageType messageType, String identifier) {
		logger.info("Broadcasting message to players");
		for (Player p : players) {
			p.sendMessage(MessageType.UpdateMsg, "");
		}
	}
}