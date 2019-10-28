package ch.tichuana.tichu.server;

import ch.tichuana.tichu.client.services.Configuration;
import ch.tichuana.tichu.server.controller.ServerController;
import ch.tichuana.tichu.server.model.*;
import ch.tichuana.tichu.server.services.DatabaseConnector;
import ch.tichuana.tichu.server.services.PlayerRepository;
import ch.tichuana.tichu.server.services.ServiceLocator;

import java.util.Locale;

public class Server {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ServerModel serverModel = new ServerModel();
		ServerController serverController = new ServerController(serverModel);
		initialize();
	}

	public static void initialize(){
		ServiceLocator serviceLocator = ServiceLocator.getServiceLocator();

		// initialize properties from file
		Configuration configuration = new Configuration("src/ch/tichuana/tichu/server/resources/config.properties");
		serviceLocator.setConfiguration(configuration);

		// initialize Database connector
		DatabaseConnector databaseConnector = new DatabaseConnector();
		serviceLocator.setDatabaseConnector(databaseConnector);

		PlayerRepository playerRepository = new PlayerRepository();
		serviceLocator.setPlayerRepository(playerRepository);
	}
}