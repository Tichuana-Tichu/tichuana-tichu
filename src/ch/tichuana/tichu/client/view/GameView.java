package ch.tichuana.tichu.client.view;

import ch.tichuana.tichu.client.model.ClientModel;
import ch.tichuana.tichu.client.services.Configuration;
import ch.tichuana.tichu.client.services.ServiceLocator;
import ch.tichuana.tichu.client.services.Translator;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GameView {

	private Stage stage;
	private PlayView playView;
	private LobbyView lobbyView;
	private Translator translator;
	private Configuration configuration;
	private double initialStageWith;
	private double initialStageHeight;

	/**
     * initializes the scene with the LobbyView and adds associated stylesheet
	 * instantiates translator and configuration objects and assign them to the service locator
	 * assigns the scene to the stage
	 * @author Philipp
	 * @param stage following MVC pattern
	 * @param clientModel following MVC pattern
	 */
	public GameView(Stage stage, ClientModel clientModel) {

		this.stage = stage;
		this.translator = ServiceLocator.getServiceLocator().getTranslator();
		this.configuration = ServiceLocator.getServiceLocator().getConfiguration();

		this.lobbyView = new LobbyView();
		Scene lobby = new Scene(this.lobbyView);


		lobby.getStylesheets().add(
				getClass().getResource(configuration.getProperty("lobbyStyle")).toExternalForm());

		this.stage.setScene(lobby);
		this.stage.setTitle(translator.getString("application.name"));
	}

	/**
	 * will be called from the client object in the main method
	 * creates preventively a playView object for later exchange
	 * sets up the stage dependent of the screen size of the device
	 * @author Philipp
	 */
	public void start() {
		this.playView = new PlayView();
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

		//set Stage boundaries to visible bounds of the main screen
		stage.setX(primaryScreenBounds.getMinX());
		stage.setY(primaryScreenBounds.getMinY());
		stage.setWidth(primaryScreenBounds.getWidth()*0.9);
		//remembers initial stage size for proper change to playView after resizing
		this.initialStageWith = stage.getWidth();
		this.initialStageHeight = stage.getHeight();
		stage.setHeight(primaryScreenBounds.getHeight()*0.9);
        stage.setMinWidth(stage.getWidth()/3);
        stage.setMinHeight(stage.getHeight()*0.9);

		stage.show();
	}

	/**
	 * @author Philipp
	 */
	public void stop() {
		stage.hide();
		Platform.exit();
	}

	/**
	 * changes stage to playView object and assign associated stylesheet
	 * sets stage to the given scene and triggers an event-handler
	 * @author Philipp
	 */
	public void updateView() {
		Scene game = new Scene(this.playView);
		game.getStylesheets().add(
				getClass().getResource(configuration.getProperty("playStyle")).toExternalForm());

		stage.setMinWidth(this.initialStageWith*0.85);
		stage.setMinHeight(this.initialStageHeight);

		stage.setScene(game);
		//initial trigger of the event-handler to rearrange the cardLabels
		stage.setWidth(stage.getWidth()-1);
	}

	//Getters
	public Stage getStage() {
		return this.stage;
	}
	public PlayView getPlayView() {
		return playView;
	}
	public LobbyView getLobbyView() {
		return lobbyView;
	}
}