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

		if (!this.serverModel.getPlayers().isEmpty()) {
			logger.info("sent message");

			this.serverModel.getPlayers().get(0).getAnnouncedGrandTichuProperty().addListener((observable, oldValue, newValue) -> {
				if(newValue)
					this.serverModel.broadcast(MessageType.AnnouncedTichuMsg, "GrandTichu");
			});
		}
	}
}