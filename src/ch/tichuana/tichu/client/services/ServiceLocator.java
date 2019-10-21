package ch.tichuana.tichu.client.services;

import com.sun.java.accessibility.util.Translator;

import java.util.Locale;
import java.util.Properties;

public class ServiceLocator {

	private static ServiceLocator serviceLocator;
	private Translator translator;
	private Properties properties;
	private Locale[] locales;

	private ServiceLocator() {
		// TODO - implement ServiceLocator.ServiceLocator
	}

	public static ServiceLocator getServiceLocator() {
		return serviceLocator;
	}

}