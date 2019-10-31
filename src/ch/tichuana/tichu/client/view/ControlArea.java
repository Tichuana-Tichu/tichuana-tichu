package ch.tichuana.tichu.client.view;

import ch.tichuana.tichu.client.services.Configuration;
import ch.tichuana.tichu.client.services.ServiceLocator;
import ch.tichuana.tichu.client.services.Translator;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ControlArea extends VBox {

	private Translator translator;
	private Configuration configuration;
	private Button playBtn;
	private Button schupfenBtn;
	private Button smallTichuBtn;
	private Button grandTichuBtn;

	public ControlArea() {

		this.translator = ServiceLocator.getServiceLocator().getTranslator();
		this.configuration = ServiceLocator.getServiceLocator().getConfiguration();
		//temporary instantiation
		this.playBtn = new Button(translator.getString("controlarea.play"));
		this.schupfenBtn = new Button(translator.getString("controlarea.schupfen"));
		this.smallTichuBtn = new Button(translator.getString("controlarea.smalltichu"));
		this.grandTichuBtn = new Button(translator.getString("controlarea.grandtichu"));
		this.getChildren().addAll(this.grandTichuBtn, this.smallTichuBtn, this.schupfenBtn, this.playBtn);
	}

	//Getter
	public Button getPlayBtn() {
		return playBtn;
	}
	public Button getSchupfenBtn() {
		return schupfenBtn;
	}
	public Button getSmallTichuBtn() {
		return smallTichuBtn;
	}
	public Button getGrandTichuBtn() {
		return grandTichuBtn;
	}
}