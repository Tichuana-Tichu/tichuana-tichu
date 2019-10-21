package ch.tichuana.tichu.client;

import ch.tichuana.tichu.client.model.ClientModel;
import ch.tichuana.tichu.client.controller.*;
import ch.tichuana.tichu.client.services.ServiceLocator;
import ch.tichuana.tichu.client.view.*;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Client extends Application {

	private ClientModel clientModel;
	private ClientController clientController;
	private GameView gameView;

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO - implement Client.main
	}

	/**
	 * 
	 * @param primaryStage
	 */

	@Override
	public void start(Stage primaryStage) throws Exception {

	}

	public static void initalize(){
		ServiceLocator serviceLocator = ServiceLocator.getServiceLocator();

		// initialize properites from file
		Properties properties = new Properties();
		try (InputStream input = new FileInputStream("../resources/config.properties")) {
			properties.load(input);
		} catch (IOException e){
			e.printStackTrace();
		}
		serviceLocator.setProperties(properties);
	}
}