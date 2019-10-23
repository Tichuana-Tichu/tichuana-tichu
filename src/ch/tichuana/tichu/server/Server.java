package ch.tichuana.tichu.server;

import ch.tichuana.tichu.server.controller.ServerController;
import ch.tichuana.tichu.server.model.*;

public class Server {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ServerModel serverModel = new ServerModel();
		ServerController serverController = new ServerController(serverModel);
	}
}