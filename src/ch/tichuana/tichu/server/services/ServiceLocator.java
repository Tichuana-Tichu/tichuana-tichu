package server.services;

public class ServiceLocator {

	private static ServiceLocator serviceLocator;
	private Properties properties;
	private DatabaseConnector databaseConnector;

	private ServiceLocator() {
		// TODO - implement ServiceLocator.ServiceLocator
	}

	private ServiceLocator getServiceLocator() {
		return this.serviceLocator;
	}

}