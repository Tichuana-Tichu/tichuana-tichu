package ch.tichuana.tichu.client.view;

import ch.tichuana.tichu.client.services.ServiceLocator;
import ch.tichuana.tichu.client.services.Translator;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class LobbyView extends VBox {

	private Image image;
	private ImageView imageView;
	private TextField userField;
	private PasswordField passwordField;
	private Button loginBtn;
	private HBox root;
	private Translator translator;
	private Label userLabel, passwordLabel;

	public LobbyView() {
		this.translator = ServiceLocator.getServiceLocator().getTranslator();
		this.userField = new TextField();
		this.userLabel = new Label(translator.getString("lobbyview.username"));
		this.passwordField = new PasswordField();
		this.passwordLabel = new Label(translator.getString("lobbyview.password"));
		this.loginBtn = new Button(translator.getString("lobbyview.login"));
		Region spacer = new Region();
		//spacer.setPrefHeight(40);
		VBox.setVgrow(spacer, Priority.ALWAYS);

		passwordField.setId("pw");

		this.getStyleClass().add("lobbyview");

		this.getChildren().addAll(spacer, userLabel, userField, passwordLabel, passwordField, loginBtn);
	}

	public HBox getRoot() {
		return this.root;
	}

}