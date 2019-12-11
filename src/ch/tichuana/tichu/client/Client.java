package ch.tichuana.tichu.client;

import ch.tichuana.tichu.client.model.ClientModel;
import ch.tichuana.tichu.client.controller.*;
import ch.tichuana.tichu.client.services.Configuration;
import ch.tichuana.tichu.client.services.ServiceLocator;
import ch.tichuana.tichu.client.services.Translator;
import ch.tichuana.tichu.client.view.*;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.Locale;

public class Client extends Application {

	private GameView gameView;

	/**
	 * @author Philipp
	 * @param args runtime arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Starting the Game
	 * @author Philipp
	 * @param primaryStage JavaFX start method
	 */
	@Override
	public void start(Stage primaryStage) {
		initialize();

		ClientModel clientModel = new ClientModel();
		this.gameView = new GameView(primaryStage, clientModel);
		new LobbyController(clientModel, gameView, primaryStage);

		gameView.start();
	}

	/**
	 * @author Philipp
	 */
	@Override
	public void stop() {
		if (gameView != null)
			gameView.stop();

	}

	/**
	 * initializes the ServiceLocator
	 * sets Configuration and Translator as services
	 * @author Christian
	 */
	private static void initialize(){
		ServiceLocator serviceLocator = ServiceLocator.getServiceLocator();

		// initialize properties from file
		Configuration configuration = new Configuration("src/ch/tichuana/tichu/client/resources/config.properties");
		serviceLocator.setConfiguration(configuration);

		// initialize Translator
		Translator translator = new Translator(new Locale(configuration.getProperty("locale")));
		serviceLocator.setTranslator(translator);

		Font.loadFont(Client.class.getResourceAsStream(
				"src/ch/tichuana/tichu/client/resources/MATURASC.TTF"), 20.0);
	}

}