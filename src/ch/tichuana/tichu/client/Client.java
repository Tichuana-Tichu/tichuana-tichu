package ch.tichuana.tichu.client;

import ch.tichuana.tichu.client.model.ClientModel;
import ch.tichuana.tichu.client.controller.*;
import ch.tichuana.tichu.client.services.Configuration;
import ch.tichuana.tichu.client.services.ServiceLocator;
import ch.tichuana.tichu.client.services.Translator;
import ch.tichuana.tichu.client.view.*;
import javafx.application.Application;
import javafx.stage.Stage;
import java.util.Locale;

public class Client extends Application {

	private ClientModel clientModel;
	private GameView gameView;
	private ClientController clientController;

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * 
	 * @param primaryStage
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		initialize();
		this.clientModel = new ClientModel();
		this.gameView = new GameView(primaryStage, clientModel);
		this.clientController = new ClientController(clientModel, gameView, primaryStage);

		gameView.start();
	}

	@Override
	public void stop() {
		if (gameView != null)
			gameView.stop();

	}

	public static void initialize(){
		ServiceLocator serviceLocator = ServiceLocator.getServiceLocator();

		// initialize properties from file
		Configuration configuration = new Configuration("src/ch/tichuana/tichu/client/resources/config.properties");
		serviceLocator.setConfiguration(configuration);

		// initialize Translator
		Translator translator = new Translator(new Locale(configuration.getProperty("locale")));
		serviceLocator.setTranslator(translator);
	}
}