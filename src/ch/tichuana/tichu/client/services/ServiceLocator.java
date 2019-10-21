package client.services;

public class ServiceLocator {

	private static ServiceLocator serviceLocator;
	private Translator translator;
	private Properties properties;
	private Locale[] locales;

	private ServiceLocator() {
		// TODO - implement ServiceLocator.ServiceLocator
	}

	public static ServiceLocator getServiceLocator() {
		return this.serviceLocator;
	}

}