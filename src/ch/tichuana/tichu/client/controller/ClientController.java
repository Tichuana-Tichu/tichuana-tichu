package ch.tichuana.tichu.client.controller;

import ch.tichuana.tichu.client.model.ClientModel;
import ch.tichuana.tichu.client.services.ServiceLocator;
import ch.tichuana.tichu.client.view.GameView;
import ch.tichuana.tichu.client.view.LobbyView;
import ch.tichuana.tichu.commons.message.MessageType;
import ch.tichuana.tichu.commons.models.TichuType;
import javafx.application.Platform;
import javafx.stage.Stage;

public class ClientController {

	private ClientModel clientModel;
	private GameView gameView;
	private Stage stage;

	/**
	 * connects buttons and texField from GameView to model, to send messages
	 * @author Philipp
     * @param clientModel following MVC pattern
     * @param gameView following MVC pattern
     */
	public ClientController(ClientModel clientModel, GameView gameView, Stage stage) {

		this.clientModel = clientModel;
		this.gameView = gameView;
		this.stage = stage;

		this.gameView.getLobbyView().getLoginBtn().setOnAction(event -> {
			LobbyView lv = this.gameView.getLobbyView();

			lv.getLoginBtn().setDisable(true);
			int port = Integer.parseInt(ServiceLocator.getServiceLocator().getConfiguration().getProperty("port"));
			String ipAddress =ServiceLocator.getServiceLocator().getConfiguration().getProperty("ipAddress");
			String playerName = lv.getUserField().getText();
			String password = lv.getPasswordField().getText();
			this.clientModel.connect(ipAddress, port, playerName, password);

		});

		this.gameView.getPlayView().getBottomView().getControlArea().getGrandTichuBtn().setOnAction(event -> {
			this.clientModel.sendMessage(TichuType.GrandTichu);
		});

		this.gameView.getPlayView().getBottomView().getControlArea().getSmallTichuBtn().setOnAction(event -> {
			this.clientModel.sendMessage(TichuType.SmallTichu);
		});

		this.gameView.getPlayView().getBottomView().getControlArea().getSchupfenBtn().setOnAction(event -> {
			this.clientModel.sendMessage(MessageType.SchupfenMsg, "");
		});

		this.gameView.getPlayView().getBottomView().getControlArea().getPlayBtn().setOnAction(event -> {
			this.clientModel.sendMessage(MessageType.PlayMsg, "");
		});

		this.gameView.getStage().setOnCloseRequest(event -> this.clientModel.disconnect());

		this.clientModel.getConnectedProperty().addListener(event -> {
			Platform.runLater(() -> this.gameView.updateView());
		});
	}

	private void checkValidCombination() {
		// TODO - implement ClientController.checkValidCombination
	}

}