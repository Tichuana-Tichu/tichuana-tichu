package ch.tichuana.tichu.server.controller;

import ch.tichuana.tichu.commons.message.MessageType;
import ch.tichuana.tichu.commons.models.TichuType;
import ch.tichuana.tichu.server.model.ServerModel;
import ch.tichuana.tichu.server.model.SimpleMessageProperty;
import ch.tichuana.tichu.server.services.ServiceLocator;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;

import java.util.logging.Logger;

public class ServerController {

	private Logger logger = Logger.getLogger("");
	private ServerModel serverModel;
	private volatile int playerCount;

	/**
	 * controls game flow because listening to SimpleBooleanProperties
	 * @author Philipp
	 * @param serverModel following MVC pattern
	 */
	public ServerController(ServerModel serverModel) {
		this.serverModel = serverModel;
		this.playerCount = 0;
		//instantly starting server with port number from config.properties
		int port = Integer.parseInt(ServiceLocator.getServiceLocator().getConfiguration().getProperty("port"));
		this.serverModel.startServer(port);
		//reacts, if new player logged in
		this.serverModel.getPlayers().addListener(this::activatePlayer);
	}

	/**
	 * attaches listeners to all Properties of the new Player
	 * @author philipp
	 */
	private void activatePlayer(Observable observable) {
		this.serverModel.getPlayer(playerCount).getAnnouncedGrandTichuProperty().addListener(
				e -> broadcastGrandTichu(serverModel.getPlayer(playerCount).getAnnouncedGrandTichuProperty()));
		this.serverModel.getPlayer(playerCount).getAnnouncedTichuProperty().addListener(
				e -> broadcastTichu(serverModel.getPlayer(playerCount).getAnnouncedTichuProperty()));
		this.serverModel.getPlayer(playerCount).getHisHisTurnProperty().addListener(this::broadcastUpdate);
		this.serverModel.getPlayer(playerCount).getHasMahjongProperty().addListener(this::broadcastUpdate);
		this.playerCount++;
	}

	/**
	 * informs all players about announcing GrandTichu
	 * @author philipp (revised by Christian)
	 * @param property if changed to true
	 */
	private void broadcastGrandTichu(SimpleMessageProperty property) {
		if (property.getValue()) {
			this.serverModel.broadcast(MessageType.AnnouncedTichuMsg, TichuType.GrandTichu.toString());
		}
	}

	/**
	 * informs all players about announcing Tichu
	 * @author philipp (revised by Christian)
	 * @param property changed to true
	 */
	private void broadcastTichu(SimpleMessageProperty property) {
		if (property.getValue()) {
			this.serverModel.broadcast(MessageType.AnnouncedTichuMsg, TichuType.SmallTichu.toString());
		}
	}

	/**
	 * informs all players
	 * @author philipp
	 * @param newVal if changed to true
	 */
	private void broadcastUpdate(ObservableValue<? extends Boolean> obs, Boolean oldVal, Boolean newVal) {

	}
}