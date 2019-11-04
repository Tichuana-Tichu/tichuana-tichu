package ch.tichuana.tichu.client.view;

import ch.tichuana.tichu.client.controller.PlayController;
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
	private ClientModel clientModel;//testing reasons.. delete after!!

	/**
     * Buttons for testing reasons only, in case of a merging conflict: move lines into ControlArea.java
	 * @author Philipp
	 * @param stage testing
	 * @param clientModel testing
	 */
	public GameView(Stage stage, ClientModel clientModel) {

		this.stage = stage;
		this.clientModel = clientModel;//testing reasons.. delete after!!
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
	 * @author Philipp
	 */
	public void start() {
		this.playView = new PlayView();
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

		//set Stage boundaries to visible bounds of the main screen
		stage.setX(primaryScreenBounds.getMinX());
		stage.setY(primaryScreenBounds.getMinY());
		stage.setWidth(primaryScreenBounds.getWidth()*0.9);
		stage.setHeight(primaryScreenBounds.getHeight()*0.9);
        stage.setMinWidth(stage.getWidth()/3);
        stage.setMinHeight(stage.getHeight()*0.9);

		stage.show();
		//testing reasons.. delete after!!
		updateView();
		PlayController playController = new PlayController(clientModel, this, this.stage);
	}

	/**
	 * @author Philipp
	 */
	public void stop() {
		stage.hide();
		Platform.exit();
	}

	/**
	 * @author Philipp
	 */
	public void updateView() {
		Scene game = new Scene(this.playView);
		game.getStylesheets().add(
				getClass().getResource(configuration.getProperty("playStyle")).toExternalForm());

		stage.setMinWidth(stage.getWidth()*0.85);
		stage.setMinHeight(stage.getHeight());
		stage.setScene(game);
		stage.setWidth(stage.getWidth()-1); //for proper positioning right away
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