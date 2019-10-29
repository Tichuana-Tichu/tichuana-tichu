package ch.tichuana.tichu.client.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LobbyView extends VBox {

	private Image image;
	private ImageView imageView;
	private TextField userField;
	private PasswordField passwordField;
	private Button loginBtn;
	private HBox root;

	public LobbyView() {
		this.userField = new TextField("username");
		this.passwordField = new PasswordField();
		this.loginBtn = new Button("Login");

		this.getChildren().addAll(userField, passwordField,loginBtn);
	}

	public HBox getRoot() {
		return this.root;
	}

}