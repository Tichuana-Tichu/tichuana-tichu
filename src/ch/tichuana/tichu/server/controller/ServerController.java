package ch.tichuana.tichu.server.controller;

import ch.tichuana.tichu.commons.message.AnnouncedTichuMsg;
import ch.tichuana.tichu.commons.message.MessageType;
import ch.tichuana.tichu.commons.models.TichuType;
import ch.tichuana.tichu.server.model.Player;
import ch.tichuana.tichu.server.model.ServerModel;
import ch.tichuana.tichu.server.model.SimpleMessageProperty;
import ch.tichuana.tichu.server.model.Team;
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
	 * @author philipp (revised by Christian)
	 */
	private void activatePlayer(Observable observable) {
		int size = serverModel.getPlayers().size();
		Player player = serverModel.getPlayers().get(size-1);

        player.getAnnouncedTichuProperty().addListener(
                e -> broadcastTichu(player.getAnnouncedTichuProperty()));
        player.getHisHisTurnProperty().addListener(this::broadcastUpdate);
        player.getHasMahjongProperty().addListener(this::broadcastUpdate);

        makeTeams(size);
	}

    /**
     * creates Teams and creates the Game if all players have connected
     * @author Christian
     * @param size number of players (size of list)
     */
	private void makeTeams(int size){
        // make teams
        if (size == 2){
            serverModel.setTeamOne(new Team(serverModel.getPlayer(0),serverModel.getPlayer(1)));
            logger.info("Team 1 created");
        }
        if (size == 4){
            serverModel.setTeamTwo(new Team(serverModel.getPlayer(2),serverModel.getPlayer(3)));
            logger.info("Team 2 created");
            serverModel.createGame();
            logger.info("Game created");
            startGame();
        }
    }

    private void startGame(){
		serverModel.getGame().start();
		logger.info("Game started");
		serverModel.getGame().dealFirstEightCards();
		logger.info("First eight cards dealt");
	}

	/**
	 * informs all players about announcing Tichu
	 * @author philipp (revised by Christian)
	 * @param property changed to true
	 */
	private void broadcastTichu(SimpleMessageProperty property) {
		if (property.getValue()) {
			AnnouncedTichuMsg msg = new AnnouncedTichuMsg(
					property.getMessage().getPlayerName(), property.getMessage().getTichuType());
			this.serverModel.broadcast(msg);
		}
		// clients will always send a tichu response even if they don't announce it (-> tichuType=none)
		serverModel.increaseTichuResponses();
		if (serverModel.getTichuResponses() == 4){
			serverModel.getGame().dealRemainingCards();
		}
		else if (serverModel.getTichuResponses() == 8) {
			// TODO: schupfen
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