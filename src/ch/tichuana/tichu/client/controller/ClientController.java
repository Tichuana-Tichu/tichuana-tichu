package ch.tichuana.tichu.client.controller;

import ch.tichuana.tichu.client.model.ClientModel;
import ch.tichuana.tichu.client.services.ServiceLocator;
import ch.tichuana.tichu.client.view.GameView;
import ch.tichuana.tichu.commons.message.MessageType;

public class ClientController {

	/**
	 * connects buttons and texField from GameView to model, to send messages
	 * @author Philipp
     * @param clientModel following MVC pattern
     * @param gameView following MVC pattern
     */
	public ClientController(ClientModel clientModel, GameView gameView) {

		gameView.getStage().setOnCloseRequest(event -> clientModel.disconnect());

		//TODO - connect Event-Handler as soon a the GUI is ready

		gameView.getConnectBtn().setOnAction(event -> {
			gameView.getConnectBtn().setDisable(true);
			int port = Integer.parseInt(ServiceLocator.getServiceLocator().getConfiguration().getProperty("port"));
			String ipAddress =ServiceLocator.getServiceLocator().getConfiguration().getProperty("ipAddress");
			String playerName = "Ruedi";
			String password = "1234";
			clientModel.connect(ipAddress, port, playerName, password);
		});

		gameView.getGrandTichu().setOnAction(event -> {
			clientModel.sendMessage(MessageType.TichuMsg);
		});

		gameView.getSmallTichu().setOnAction(event -> {
			clientModel.sendMessage(MessageType.TichuMsg);
		});

		gameView.getSchupfen().setOnAction(event -> {
			clientModel.sendMessage(MessageType.SchupfenMsg);
		});

		gameView.getPlay().setOnAction(event -> {
			clientModel.sendMessage(MessageType.PlayMsg);
		});

	}

	private void checkValidCombination() {
		// TODO - implement ClientController.checkValidCombination
	}

}