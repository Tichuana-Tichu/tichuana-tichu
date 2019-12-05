package ch.tichuana.tichu.server.controller;

import ch.tichuana.tichu.commons.message.AnnouncedTichuMsg;
import ch.tichuana.tichu.commons.message.DemandSchupfenMsg;
import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.server.model.Player;
import ch.tichuana.tichu.server.model.ServerModel;
import ch.tichuana.tichu.server.model.SimpleMessageProperty;
import ch.tichuana.tichu.server.model.Team;
import ch.tichuana.tichu.server.services.ServiceLocator;
import javafx.beans.Observable;

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
		player.getAnnouncedGrandTichuProperty().addListener(
				e -> broadcastTichu(player.getAnnouncedGrandTichuProperty()));
		player.getSchupfenProperty().addListener(
				e -> schupfen(player.getSchupfenProperty()));
        player.getPlayProperty().addListener(e -> handleUpdate(player.getPlayProperty()));
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

	/**
	 * starts the Game and deal first eight cards to each player
	 * @author Christian
	 */
	private void startGame(){
		serverModel.getGame().start();
		logger.info("Game started");
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
			// clients will always send a tichu response even if they don't announce it (-> tichuType=none)
			serverModel.increaseTichuResponses();
			if (serverModel.getTichuResponses() == 4) {
				serverModel.getGame().getCurrentMatch().dealRemainingCards();
			} else if (serverModel.getTichuResponses() == 8) {
				demandSchupfen();
				serverModel.setTichuResponses(0);
			}
			property.setValue(false);
		}
	}

	/**
	 * broadcasts a DemandSchupfenMsg with the name of the next player.
	 * @author Chrisitan
	 */
	private void demandSchupfen(){
		try{Thread.sleep(300);}catch (Exception e){e.printStackTrace();};
		Player p = serverModel.getGame().getNextPlayer();
		DemandSchupfenMsg msg = new DemandSchupfenMsg(p.getPlayerName());
		serverModel.broadcast(msg);
	}

	/**
	 * will call schupfen method in game class and give it the card and the target player
	 * @author Christian
	 * @param property SimpleMessageProperty for SchupfenMessages
	 */
	private void schupfen(SimpleMessageProperty property){
		if (property.getValue()){
			Card card = property.getMessage().getCard();
			Player player = serverModel.getGame().getPlayerByName(property.getMessage().getPlayerName());
			serverModel.getGame().getCurrentMatch().schupfen(card, player);
			serverModel.increaseSchupfenResponses();
			property.setValue(false);

			// When all Schupfen Responses have been received we can start the first match
			if (serverModel.getSchupfenResponses() >= 12){
				serverModel.getGame().getCurrentMatch().start();
			} else {
				if (serverModel.getSchupfenResponses()%3 == 0){
					demandSchupfen();
				}
			}
		}
	}

	/**
	 * If the value of Property is true it will call the handleUpdate method in Match-class.
	 * @author Christian
	 */
	private void handleUpdate(SimpleMessageProperty messageProperty) {
		if (messageProperty.getValue() ==true){
			this.serverModel.getGame().getCurrentMatch().handleUpdate(messageProperty);
			// set value false. so next update can set it true again.
			messageProperty.setValue(false);
		}
	}


}
