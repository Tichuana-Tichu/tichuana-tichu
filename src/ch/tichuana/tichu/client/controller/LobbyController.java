package ch.tichuana.tichu.client.controller;

import ch.tichuana.tichu.client.model.ClientModel;
import ch.tichuana.tichu.client.services.ServiceLocator;
import ch.tichuana.tichu.client.view.GameView;
import ch.tichuana.tichu.client.view.LobbyView;
import javafx.application.Platform;
import javafx.stage.Stage;

public class LobbyController {

	private ClientModel clientModel;
	private GameView gameView;
	private Stage stage;

	/**
	 * attaches listener to the stage-size to make the Logo responsive
	 * sets Login-Button on Action, reads user input and connects to server, with credential from config.properties
	 * changes to PlayView and instantiates PlayController, after this client connected successfully to the server
	 * @author Philipp
     * @param clientModel following MVC pattern
     * @param gameView following MVC pattern
	 * @param stage following MVC pattern
     */
	public LobbyController(ClientModel clientModel, GameView gameView, Stage stage) {

		this.clientModel = clientModel;
		this.gameView = gameView;
		this.stage = stage;

		this.stage.heightProperty().addListener((observable, oldValue, newValue) ->
				this.gameView.getLobbyView().getTichuView().setFitHeight(newValue.intValue()*0.5));

		this.stage.widthProperty().addListener((observable, oldValue, newValue) ->
				this.gameView.getLobbyView().getTichuView().setFitWidth(newValue.intValue()*0.8));

		this.gameView.getLobbyView().getLoginBtn().setOnAction(event -> {
			LobbyView lv = this.gameView.getLobbyView();

			int port = Integer.parseInt(ServiceLocator.getServiceLocator().getConfiguration().getProperty("port"));
			String ipAddress =ServiceLocator.getServiceLocator().getConfiguration().getProperty("ipAddress");
			if (!lv.getUserField().getText().isEmpty() || !lv.getPasswordField().getText().isEmpty()) {
                String playerName = lv.getUserField().getText();
                String password = lv.getPasswordField().getText();
                this.clientModel.connect(ipAddress, port, playerName, password);
            }
		});

		this.clientModel.getConnectedProperty().addListener(event -> {
			new PlayController(this.clientModel, this.gameView, this.stage);
			Platform.runLater(() -> this.gameView.updateView());
		});
	}
}