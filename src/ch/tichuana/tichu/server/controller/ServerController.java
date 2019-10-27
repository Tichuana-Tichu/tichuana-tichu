package ch.tichuana.tichu.server.controller;

import ch.tichuana.tichu.commons.message.MessageType;
import ch.tichuana.tichu.server.model.ServerModel;
import ch.tichuana.tichu.server.services.ServiceLocator;

import java.util.logging.Logger;

public class ServerController {

	private Logger logger = Logger.getLogger("");
	private ServerModel serverModel;

	/**
	 * controls game flow because listening to SimpleBooleanProperties
	 * @author Philipp
	 * @param serverModel following MVC pattern
	 */
	public ServerController(ServerModel serverModel) {
		this.serverModel = serverModel;
		int port = Integer.parseInt(ServiceLocator.getServiceLocator().getConfiguration().getProperty("port"));

		this.serverModel.startServer(port);

		/*
		this.serverModel.getPlayer(0).getAnnouncedGrandTichuProperty().addListener((observable, oldValue, newValue) -> {
			logger.info("sent message");
			if(newValue)
				this.serverModel.broadcast(MessageType.AnnouncedTichuMsg, "GrandTichu");
		});

		/*
		if (!this.serverModel.getPlayers().isEmpty()) {
			logger.info("sent message");


		}
		 */
	}
}