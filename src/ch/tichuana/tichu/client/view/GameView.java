package ch.tichuana.tichu.client.view;

import ch.tichuana.tichu.client.model.ClientModel;
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
	private Button play;
	private Button schupfen;
	private Button smallTichu;
	private Button grandTichu;
	private Button connectBtn;

	/**
	 * @author Philipp
	 * @param stage testing
	 * @param clientModel testing
	 */
	public GameView(Stage stage, ClientModel clientModel) {

		this.stage = stage;
		this.clientModel = clientModel;
		//temporary instantiation
		this.play = new Button("play");
		this.schupfen = new Button("schupfen");
		this.smallTichu = new Button("small Tichu");
		this.grandTichu = new Button("grand Tichu");
		this.connectBtn = new Button("connect");
		VBox root = new VBox(this.connectBtn, this.grandTichu, this.play, this.schupfen, this.smallTichu);
		// TODO - implement GameView.GameView

		Scene scene = new Scene(root);
		/*
		scene.getStylesheets().add(
				getClass().getResource("style.css").toExternalForm());
		 */
		stage.setScene(scene);
		stage.setTitle("Tichu-Client");
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
	public Button getPlay() {
		return play;
	}
	public Button getSchupfen() {
		return this.schupfen;
	}
	public Button getSmallTichu() {
		return this.smallTichu;
	}
	public Button getGrandTichu() {
		return this.grandTichu;
	}
	public Button getConnectBtn(){
		return this.connectBtn;
	}
	public Stage getStage() {
		return this.stage;
	}
}