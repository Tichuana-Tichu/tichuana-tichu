package ch.tichuana.tichu.server.controller;

import ch.tichuana.tichu.client.services.ServiceLocator;
import ch.tichuana.tichu.server.model.ServerModel;

public class ServerController {

	public ServerController(ServerModel serverModel) {
		serverModel.startServer(Integer.parseInt(ServiceLocator.getServiceLocator().getConfiguration().getProperty("port")));
	}

}