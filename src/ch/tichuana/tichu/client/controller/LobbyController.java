package ch.tichuana.tichu.client.controller;

import ch.tichuana.tichu.client.model.ClientModel;
import ch.tichuana.tichu.client.services.ServiceLocator;
import ch.tichuana.tichu.client.services.Translator;
import ch.tichuana.tichu.client.view.GameView;
import ch.tichuana.tichu.client.view.LobbyView;
import ch.tichuana.tichu.client.view.ServerSelector;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.control.MenuItem;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class LobbyController {

	private ClientModel clientModel;
	private GameView gameView;
	private Stage stage;
	private Translator t;

	/**
	 * attaches listener to the stage-size to make the Logo responsive
	 * calls login method on some events, updates LoginStatus with StringProperty
	 * changes to PlayView and instantiates PlayController, after this client connected successfully to the server
	 * @author Philipp
     * @param clientModel following MVC pattern
     * @param gameView following MVC pattern
	 * @param stage following MVC pattern
     */
	public LobbyController(ClientModel clientModel, GameView gameView, Stage stage) {
		ServiceLocator serviceLocator = ServiceLocator.getServiceLocator();
		this.t = serviceLocator.getTranslator();
		this.clientModel = clientModel;
		this.gameView = gameView;
		this.stage = stage;

		this.stage.heightProperty().addListener((observable, oldValue, newValue) ->
				this.gameView.getLobbyView().getTichuView().setFitHeight(newValue.intValue()*0.5));

		this.stage.widthProperty().addListener((observable, oldValue, newValue) ->
				this.gameView.getLobbyView().getTichuView().setFitWidth(newValue.intValue()*0.8));

		this.gameView.getLobbyView().getPasswordField().setOnAction(this::login);

		this.gameView.getLobbyView().getLoginBtn().setOnAction(this::login);

		this.clientModel.getMsgCodeProperty().newestMsgProperty().addListener((observable, oldValue, newValue) ->
				Platform.runLater(() -> this.gameView.getLobbyView().setLoginStatus(newValue)));


		this.clientModel.getMsgCodeProperty().addListener((obs, oldVal, newVal) -> {
			if (newVal.intValue() == 1) {
				new PlayController(this.clientModel, this.gameView, this.stage);
				Platform.runLater(() -> this.gameView.updateView());
			}
		});

		// add listener for every language menu item
		for(MenuItem m : this.gameView.getLobbyView().getSettings().getLangMenu().getItems()){
			m.setOnAction(this::changeTranslator);
		}

		ServerSelector serverSelector = ServerSelector.getServerSelector();
		serverSelector.getConfirm().setOnAction(e -> {
			serviceLocator.getConfiguration().setProperty("ipAddress",
					serverSelector.getHost());
			serviceLocator.getConfiguration().setProperty("port",
					serverSelector.getPort());
			serverSelector.getSave().setDisable(false);
		});
		serverSelector.getSave().setOnAction(e -> {
			serviceLocator.getConfiguration().save();
			serverSelector.getSave().setDisable(true);
		});
	}

	/**
	 * initialize new Translator for language change.
	 * @author dominik
	 */
	public void changeTranslator(Event event){
		MenuItem m = (MenuItem) event.getSource();

		if (m.getText().equals(t.getString("langMenu.german"))){

			Translator de = new Translator("de");
			ServiceLocator.getServiceLocator().setTranslator(de);

		} else if (m.getText().equals(t.getString("langMenu.english"))){

			Translator en = new Translator("en");
			ServiceLocator.getServiceLocator().setTranslator(en);
		} else if (m.getText().equals(t.getString("langMenu.chinese"))){

			Translator ch = new Translator("ch");
			ServiceLocator.getServiceLocator().setTranslator(ch);
		}
		t = ServiceLocator.getServiceLocator().getTranslator();
		gameView.getLobbyView().update();
		ServerSelector.getServerSelector().update();
	}

	/**
	 * sets Login-Button & PasswordField on Action, reads user input and connects to server,
	 * with credential from config.properties
	 * @author Philipp
	 * @param actionEvent button press or enter in passwordField
	 */
	private void login(ActionEvent actionEvent) {
		LobbyView lv = this.gameView.getLobbyView();

		int port = Integer.parseInt(ServiceLocator.getServiceLocator().getConfiguration().getProperty("port"));
		String ipAddress =ServiceLocator.getServiceLocator().getConfiguration().getProperty("ipAddress");

		if (lv.getUserField().getText().isEmpty()) {
			this.clientModel.getMsgCodeProperty().setNewestMsg(t.getString("emptyUserField"));
		} else if (lv.getPasswordField().getText().isEmpty()) {
			this.clientModel.getMsgCodeProperty().setNewestMsg(t.getString("emptyPasswordField"));
		} else {
			String playerName = lv.getUserField().getText();
			String password = lv.getPasswordField().getText();
			this.clientModel.connect(ipAddress, port, playerName, password);
		}
	}
}