package ch.tichuana.tichu.server.services;

import ch.tichuana.tichu.client.services.Configuration;
import java.util.logging.Logger;

public class ServiceLocator {

	private static ServiceLocator serviceLocator;
	private Configuration configuration;
	private DatabaseConnector databaseConnector;
	private PlayerRepository playerRepository;
	private final Logger logger = Logger.getLogger("");

	/**
	 * ServiceLocator is a singleton. There's only one instance.
	 * Private Constructor only used by factory method.
	 * @author Philipp
	 */
	private ServiceLocator() {
		//needs to stay here to be private
	}

	/**
	 * Factory method returns ServiceLocator if it already exists. Will create it, if not.
	 * @return serviceLocator
	 */
	public static ServiceLocator getServiceLocator() {
		if (serviceLocator == null) {
			serviceLocator = new ServiceLocator();
		}
		return serviceLocator;
	}

	//Getter and Setter
	public DatabaseConnector getDatabaseConnector() {
		return databaseConnector;
	}
	public void setDatabaseConnector(DatabaseConnector databaseConnector) {
		this.databaseConnector = databaseConnector;
	}
	public Configuration getConfiguration() {
		return configuration;
	}
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
	public PlayerRepository getPlayerRepository() {
		return playerRepository;
	}
	public void setPlayerRepository(PlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
	}
}