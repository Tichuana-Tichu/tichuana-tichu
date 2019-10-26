package ch.tichuana.tichu.server.controller;

import ch.tichuana.tichu.server.model.ServerModel;
import ch.tichuana.tichu.server.services.ServiceLocator;

public class ServerController {
	private final ServerModel serverModel;

	public ServerController(ServerModel serverModel) {
		this.serverModel = serverModel;
		int port = Integer.parseInt(ServiceLocator.getServiceLocator().getConfiguration().getProperty("port"));

		serverModel.startServer(port);
	}

}