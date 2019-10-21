package ch.tichuana.tichu.server.services;

import java.util.Properties;

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