package ch.tichuana.tichu.client.services;

import ch.tichuana.tichu.client.view.Settings;
import javafx.event.Event;
import javafx.scene.control.MenuItem;

import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class Translator {

	private Locale locale;
	private ResourceBundle resourceBundle;

	/**
	 * Translator class loads properties from a resource bundle. Based on the JavaFX-App-Template.
	 * @author Christian
	 * @param localeString
	 */
	public Translator(String localeString) {
		ServiceLocator serviceLocator= ServiceLocator.getServiceLocator();
		String path = serviceLocator.getConfiguration().getProperty("resourceBundle.path");
		this.locale = new Locale(localeString);
		this.resourceBundle = ResourceBundle.getBundle(path, this.locale);
	}

	/**
	 * Alternate Constructor, locale as parameter
	 * @author Christian
	 * @param locale
	 */
	public Translator(Locale locale) {
		ServiceLocator serviceLocator= ServiceLocator.getServiceLocator();
		String path = serviceLocator.getConfiguration().getProperty("resourceBundle.path");
		this.locale = locale;
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