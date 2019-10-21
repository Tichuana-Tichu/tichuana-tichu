package ch.tichuana.tichu.client.services;

import java.util.Locale;
import java.util.ResourceBundle;

public class Translator {

	private Locale locale;
	private ResourceBundle resourceBundle;

	/**
	 * 
	 * @param locale
	 */
	public Translator(String locale) {
		this.locale = new Locale(locale);
		String path = ServiceLocator.getServiceLocator().getConfiguration().getProperty("resourceBundle.path");
		this.resourceBundle = ResourceBundle.getBundle(path, this.locale);
	}

	/**
	 * 
	 * @param locale
	 */
	public Translator(Locale locale) {
		// TODO - implement Translator.Translator
	}

	/**
	 * 
	 * @param key
	 */
	public String getString(String key) {
		return resourceBundle.getString(key);
	}

}