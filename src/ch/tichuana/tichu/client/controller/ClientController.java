package ch.tichuana.tichu.client.controller;

import ch.tichuana.tichu.client.model.ClientModel;
import ch.tichuana.tichu.client.services.ServiceLocator;
import ch.tichuana.tichu.client.view.GameView;

public class ClientController {

	final private ClientModel clientModel;
	final private GameView gameView;

    /**
     *
     * @param clientModel
     * @param gameView
     */
	public ClientController(ClientModel clientModel, GameView gameView) {

		this.clientModel = clientModel;
		this.gameView = gameView;

        Integer port = Integer.parseInt(ServiceLocator.getServiceLocator().getConfiguration().getProperty("port"));
        String ipAddress =ServiceLocator.getServiceLocator().getConfiguration().getProperty("ipAddress");
        String playerName = "name from TextField";
        String password = "password from PswdField";
        clientModel.connect(ipAddress, port, playerName, password);
		/* TODO - connect Event-Handler as soon a the GUI is ready
		gameView.connectBtn.setOnAction(event -> {
			gameView.connectBtn.setDisable(true);
			Integer port = Integer.parseInt(ServiceLocator.getServiceLocator().getConfiguration().getProperty("port"));
			String ipAddress =ServiceLocator.getServiceLocator().getConfiguration().getProperty("ipAddress");
			String playerName = "name from TextField";
			String password = "password from PswdField";
			clientModel.connect(ipAddress, port, playerName, password);
		});
		*/
	}

	private void checkValidCombination() {
		// TODO - implement ClientController.checkValidCombination
	}

}