package ch.tichuana.tichu.server.controller;

import ch.tichuana.tichu.client.services.ServiceLocator;
import ch.tichuana.tichu.server.model.ServerModel;

public class ServerController {
	private final ServerModel serverModel;

	public ServerController(ServerModel serverModel) {
		this.serverModel = serverModel;
		//TODO - something is wrong with the getProperty method
		int port = 8080;//Integer.parseInt(ServiceLocator.getServiceLocator().getConfiguration().getProperty("port"));

		serverModel.startServer(port);
	}

}