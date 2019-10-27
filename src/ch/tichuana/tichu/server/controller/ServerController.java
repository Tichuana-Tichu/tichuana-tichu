package ch.tichuana.tichu.server.controller;

import ch.tichuana.tichu.server.model.ServerModel;
import ch.tichuana.tichu.server.services.ServiceLocator;

public class ServerController {

	/**
	 * @author Philipp
	 * @param serverModel
	 */
	public ServerController(ServerModel serverModel) {
		int port = Integer.parseInt(ServiceLocator.getServiceLocator().getConfiguration().getProperty("port"));

		serverModel.startServer(port);
	}

}