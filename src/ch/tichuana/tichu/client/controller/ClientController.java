package ch.tichuana.tichu.client.controller;

import ch.tichuana.tichu.client.model.ClientModel;
import ch.tichuana.tichu.client.services.ServiceLocator;
import ch.tichuana.tichu.client.view.GameView;
import ch.tichuana.tichu.client.view.LobbyView;
import ch.tichuana.tichu.commons.message.MessageType;
import ch.tichuana.tichu.commons.models.TichuType;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
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

		this.clientModel.getNewestMessageProperty().addListener(this::prompt);

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

		this.stage.heightProperty().addListener((observable, oldValue, newValue) -> {
				this.gameView.getLobbyView().getTichuView().setFitHeight(newValue.intValue()*0.5);
		});

		this.stage.widthProperty().addListener((observable, oldValue, newValue) -> {
			this.gameView.getLobbyView().getTichuView().setFitWidth(newValue.intValue()*0.8);
			HBox cardLabels = this.gameView.getPlayView().getBottomView().getCardArea().getCardsLabels();

			if (oldValue.intValue() < newValue.intValue()) { //screen gets bigger
				cardLabels.setSpacing(((stage.getWidth()-(newValue.floatValue()-stage.getWidth()))/15)
						-stage.getMinWidth()/9);
			}
			else { //screen get smaller
				cardLabels.setSpacing(((stage.getWidth()-(stage.getWidth()-newValue.floatValue()))/15)
						-stage.getMinWidth()/9);
			}
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

	/**
	 *
	 * @param observableValue
	 * @param oldVal
	 * @param newVal
	 */
	private void prompt(ObservableValue<? extends String> observableValue, String oldVal, String newVal) {
		Platform.runLater(() -> this.gameView.getPlayView().getBottomView().getCardArea().setConsole(newVal));
	}

	private void checkValidCombination() {
		// TODO - implement ClientController.checkValidCombination
	}

}