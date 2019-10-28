package ch.tichuana.tichu.server.model;

import ch.tichuana.tichu.commons.message.JoinMsg;
import ch.tichuana.tichu.commons.message.Message;
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
	private Game[] games;

	public void startServer(int port) {
		logger.info("start server");
		try {
			listener = new ServerSocket(port, 10, null);
			Runnable r = new Runnable() {
				@Override
				public void run() {
					while (!stop) {
						try {
							Socket socket = listener.accept();
							Player player = new Player(ServerModel.this, socket);
							players.add(player);
						} catch (IOException e) {
							logger.info(e.toString());
						}
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

	public void broadcast(Message msg) {
		logger.info("Broadcasting message to players");
		for (Player p : players) {
			p.send(msg);
		}
	}
}