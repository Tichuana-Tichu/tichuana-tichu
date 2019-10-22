package ch.tichuana.tichu.client.services;

import ch.tichuana.tichu.client.Client;
import java.util.Locale;
import java.util.Properties;

public class ServiceLocator {

	private static ServiceLocator serviceLocator;
	private Translator translator;
	private Configuration configuration;
	private Locale[] locales; // = {new Locale("de"), new Locale("de")};
	final private Class<?> APP_CLASS = Client.class;

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
	public Class<?> getAPP_CLASS() {
		return APP_CLASS;
	}

}