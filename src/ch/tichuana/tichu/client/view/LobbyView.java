package ch.tichuana.tichu.client.view;

import ch.tichuana.tichu.client.services.Configuration;
import ch.tichuana.tichu.client.services.ServiceLocator;
import ch.tichuana.tichu.client.services.Translator;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LobbyView extends BorderPane {

	private Settings settings;
	private TextField userField;
	private PasswordField passwordField;
	private ToggleButton loginBtn;
	private ImageView tichuView;

	LobbyView(Stage stage) {
		Translator translator = ServiceLocator.getServiceLocator().getTranslator();
		Configuration config = ServiceLocator.getServiceLocator().getConfiguration();
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		this.settings = new Settings();

		this.userField = new TextField();
		this.userField.setPromptText(translator.getString("lobbyview.username"));
		this.userField.setFocusTraversable(false);
		this.userField.getStyleClass().add("userPrompt");

		this.passwordField = new PasswordField();
		this.passwordField.setPromptText(translator.getString("lobbyview.password"));
		this.passwordField.setFocusTraversable(false);
		this.passwordField.getStyleClass().add("userPrompt");

		this.loginBtn = new ToggleButton(translator.getString("lobbyview.login"));

		VBox controls = new VBox(userField, passwordField, loginBtn);
		this.setBottom(controls);
		this.setTop(settings);

		Image tichuImg = new Image(config.getProperty("tichuImg"));
		this.tichuView = new ImageView(tichuImg);

		tichuView.setFitHeight(primaryScreenBounds.getHeight()*0.5);
		tichuView.setPreserveRatio(true);
		this.setCenter(tichuView);
	}

	//Getter
	public TextField getUserField() {
		return userField;
	}
	public PasswordField getPasswordField() {
		return passwordField;
	}
	public ToggleButton getLoginBtn() {
		return loginBtn;
	}
	public Settings getSettings() {
		return settings;
	}
	public ImageView getTichuView() {
		return tichuView;
	}
}