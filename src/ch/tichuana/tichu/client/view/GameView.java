package ch.tichuana.tichu.client.view;

import ch.tichuana.tichu.client.model.ClientModel;
import ch.tichuana.tichu.client.services.Configuration;
import ch.tichuana.tichu.client.services.ServiceLocator;
import ch.tichuana.tichu.client.services.Translator;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameView {

	private Stage stage;
	private ClientModel clientModel;
	private PlayView playView;
	private LobbyView lobbyView;
	private Translator translator;
	private Configuration configuration;

	/**
     * Buttons for testing reasons only, in case of a merging conflict: move lines into ControlArea.java
	 * @author Philipp
	 * @param stage testing
	 * @param clientModel testing
	 */
	public GameView(Stage stage, ClientModel clientModel) {

		this.stage = stage;
		this.clientModel = clientModel;
		this.translator = ServiceLocator.getServiceLocator().getTranslator();
		this.configuration = ServiceLocator.getServiceLocator().getConfiguration();

		this.lobbyView = new LobbyView();
		Scene lobby = new Scene(this.lobbyView);

		this.playView = new PlayView();

		lobby.getStylesheets().add(
				getClass().getResource(configuration.getProperty("lobbyStyle")).toExternalForm());

		this.stage.setScene(lobby);
		this.stage.setTitle(translator.getString("application.name"));
	}

	public void start() {
		stage.show();
		// Prevent resizing below initial size
		stage.setMinWidth(stage.getWidth());
		stage.setMinHeight(stage.getHeight());
	}

	public void stop() {
		stage.hide();
		Platform.exit();
	}

	public void updateView() {
		Scene game = new Scene(this.playView);
		game.getStylesheets().add(
				getClass().getResource(configuration.getProperty("playStyle")).toExternalForm());
		stage.setScene(game);
		stage.setTitle(translator.getString("application.name"));
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