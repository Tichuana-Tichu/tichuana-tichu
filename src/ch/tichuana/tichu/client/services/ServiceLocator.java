package ch.tichuana.tichu.client.services;

public class ServiceLocator {

	private static ServiceLocator serviceLocator;
	private Translator translator;
	private Configuration configuration;

	/**
	 * ServiceLocator is a singleton. There's only one instance.
	 * Private Constructor only used by factory method.
	 * @author Christian
	 */
	private ServiceLocator() {
		// No need for code, only needs to be private
	}

	/**
	 * Factory method returns ServiceLocator if it already exists. Will create it, if not.
	 * @return serviceLocator
	 */
	public static ServiceLocator getServiceLocator() {
		if (serviceLocator == null){
			serviceLocator = new ServiceLocator();
		}
		return serviceLocator;
	}

	// getters and setters
	public Translator getTranslator() {
		return translator;
	}
	public void setTranslator(Translator translator) {
		this.translator = translator;
	}
	public Configuration getConfiguration() {
		return configuration;
	}
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
}