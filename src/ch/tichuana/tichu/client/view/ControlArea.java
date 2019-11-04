package ch.tichuana.tichu.client.view;

import ch.tichuana.tichu.client.services.Configuration;
import ch.tichuana.tichu.client.services.ServiceLocator;
import ch.tichuana.tichu.client.services.Translator;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;

public class ControlArea extends VBox {

	private Translator translator;
	private Configuration configuration;
	private ToggleButton playBtn;
	private ToggleButton schupfenBtn;
	private ToggleButton smallTichuBtn;
	private ToggleButton grandTichuBtn;

	public ControlArea() {

		this.translator = ServiceLocator.getServiceLocator().getTranslator();
		this.configuration = ServiceLocator.getServiceLocator().getConfiguration();
		//temporary instantiation
		this.playBtn = new ToggleButton(translator.getString("controlarea.play"));
		this.schupfenBtn = new ToggleButton(translator.getString("controlarea.schupfen"));
		this.smallTichuBtn = new ToggleButton(translator.getString("controlarea.smalltichu"));
		this.grandTichuBtn = new ToggleButton(translator.getString("controlarea.grandtichu"));
		this.getChildren().addAll(this.grandTichuBtn, this.smallTichuBtn, this.schupfenBtn, this.playBtn);
	}

	//Getter
	public ToggleButton getPlayBtn() {
		return playBtn;
	}
	public ToggleButton getSchupfenBtn() {
		return schupfenBtn;
	}
	public ToggleButton getSmallTichuBtn() {
		return smallTichuBtn;
	}
	public ToggleButton getGrandTichuBtn() {
		return grandTichuBtn;
	}
}