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

	private Scene scene;
	private Stage stage;
	private ClientModel clientModel;
	private Pane root;
	private Translator translator;
	private Configuration configuration;

	//temporary Buttons
	private Button playBtn;
	private Button schupfenBtn;
	private Button smallTichuBtn;
	private Button grandTichuBtn;
	private Button signInBtn;

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

		//temporary instantiation
		this.playBtn = new Button(translator.getString("controlarea.play"));
		this.schupfenBtn = new Button(translator.getString("controlarea.schupfen"));
		this.smallTichuBtn = new Button(translator.getString("controlarea.smalltichu"));
		this.grandTichuBtn = new Button(translator.getString("controlarea.grandtichu"));
		this.signInBtn = new Button("signIn");
		VBox root = new VBox(this.signInBtn, this.grandTichuBtn, this.smallTichuBtn, this.schupfenBtn, this.playBtn);
		// TODO - implement GameView.GameView

		Scene scene = new Scene(root);
		Scene lobby = new Scene(new LobbyView());

		scene.getStylesheets().add(
				getClass().getResource(configuration.getProperty("stylesheet")).toExternalForm());

		stage.setScene(lobby);
		stage.setTitle(translator.getString("application.name"));
		stage.show();
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

	//temporary Getters
	public Button getPlayBtn() {
		return playBtn;
	}
	public Button getSchupfenBtn() {
		return this.schupfenBtn;
	}
	public Button getSmallTichuBtn() {
		return this.smallTichuBtn;
	}
	public Button getGrandTichuBtn() {
		return this.grandTichuBtn;
	}
	public Button getSignInBtn(){
		return this.signInBtn;
	}
	public Stage getStage() {
		return this.stage;
	}
}