package ch.tichuana.tichu.client.services;

import java.util.Locale;
import java.util.ResourceBundle;

public class Translator {

	private Locale locale;
	private ResourceBundle resourceBundle;

	/**
	 * Translator class loads properties from a resource bundle. Based on the JavaFX-App-Template.
	 * @author Christian
	 * @param locale
	 */
	public Translator(String locale) {
		this.locale = new Locale(locale);
		String path = ServiceLocator.getServiceLocator().getConfiguration().getProperty("resourceBundle.path");
		this.resourceBundle = ResourceBundle.getBundle(path, this.locale);
	}

	/**
	 * Alternate Constructor, locale as parameter
	 * @author Christian
	 * @param locale
	 */
	public Translator(Locale locale) {
		this.locale = locale;
		String path = ServiceLocator.getServiceLocator().getConfiguration().getProperty("resourceBundle.path");
		this.resourceBundle = ResourceBundle.getBundle(path, this.locale);
	}

	/**
	 * Returns the value belonging to its key.
	 * @author Christian
	 * @param key
	 */
	public String getString(String key) {
		return resourceBundle.getString(key);
	}

}