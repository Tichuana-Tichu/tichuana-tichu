package ch.tichuana.tichu.client.view;

import ch.tichuana.tichu.client.model.ClientModel;
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
		Translator translator = ServiceLocator.getServiceLocator().getTranslator();

		//temporary instantiation
		this.playBtn = new Button("play/pass");
		this.schupfenBtn = new Button("schupfen");
		this.smallTichuBtn = new Button("small Tichu");
		this.grandTichuBtn = new Button("grand Tichu");
		this.signInBtn = new Button("signIn");
		VBox root = new VBox(this.signInBtn, this.grandTichuBtn, this.smallTichuBtn, this.schupfenBtn, this.playBtn);
		// TODO - implement GameView.GameView

		Scene scene = new Scene(root);
		Scene lobby = new Scene(new LobbyView());
		/*
		scene.getStylesheets().add(
				getClass().getResource("style.css").toExternalForm());
		 */
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