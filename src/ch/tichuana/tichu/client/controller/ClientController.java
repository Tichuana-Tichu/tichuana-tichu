package ch.tichuana.tichu.client.controller;

import ch.tichuana.tichu.client.model.ClientModel;
import ch.tichuana.tichu.client.services.ServiceLocator;
import ch.tichuana.tichu.client.view.GameView;
import ch.tichuana.tichu.commons.message.MessageType;
import ch.tichuana.tichu.commons.models.TichuType;

public class ClientController {

	/**
	 * connects buttons and texField from GameView to model, to send messages
	 * @author Philipp
     * @param clientModel following MVC pattern
     * @param gameView following MVC pattern
     */
	public ClientController(ClientModel clientModel, GameView gameView) {

		gameView.getSignInBtn().setOnAction(event -> {
			gameView.getSignInBtn().setDisable(true);
			int port = Integer.parseInt(ServiceLocator.getServiceLocator().getConfiguration().getProperty("port"));
			String ipAddress =ServiceLocator.getServiceLocator().getConfiguration().getProperty("ipAddress");
			String playerName = "Ruedi";
			String password = "1234";
			clientModel.connect(ipAddress, port, playerName, password);
		});

		gameView.getGrandTichuBtn().setOnAction(event -> {
			clientModel.sendMessage(TichuType.GrandTichu);
		});

		gameView.getSmallTichuBtn().setOnAction(event -> {
			clientModel.sendMessage(TichuType.SmallTichu);
		});

		gameView.getSchupfenBtn().setOnAction(event -> {
			clientModel.sendMessage(MessageType.SchupfenMsg, "");
		});

		gameView.getPlayBtn().setOnAction(event -> {
			clientModel.sendMessage(MessageType.PlayMsg, "");
		});

		gameView.getStage().setOnCloseRequest(event -> clientModel.disconnect());
	}

	private void checkValidCombination() {
		// TODO - implement ClientController.checkValidCombination
	}

}