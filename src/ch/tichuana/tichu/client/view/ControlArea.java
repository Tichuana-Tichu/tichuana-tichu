package ch.tichuana.tichu.client.view;

import ch.tichuana.tichu.client.services.Configuration;
import ch.tichuana.tichu.client.services.ServiceLocator;
import ch.tichuana.tichu.client.services.Translator;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;

public class ControlArea extends VBox {

	private Translator translator;
	private Configuration configuration;
	private ToggleButton playBtn;
	private ToggleButton schupfenBtn;
	private ToggleButton smallTichuBtn;
	private ToggleButton grandTichuBtn;

	/**
	 * @author Philipp
	 */
	ControlArea() {

		this.translator = ServiceLocator.getServiceLocator().getTranslator();
		this.configuration = ServiceLocator.getServiceLocator().getConfiguration();
		//temporary instantiation
		this.playBtn = new ToggleButton(translator.getString("controlarea.play"));
		this.playBtn.setDisable(true);
		this.schupfenBtn = new ToggleButton(translator.getString("controlarea.schupfen"));
		this.schupfenBtn.setDisable(true);
		this.smallTichuBtn = new ToggleButton(translator.getString("controlarea.smalltichu"));
		this.smallTichuBtn.setDisable(true);
		this.grandTichuBtn = new ToggleButton(translator.getString("controlarea.grandtichu"));
		this.grandTichuBtn.setDisable(true);
		this.getChildren().addAll(this.grandTichuBtn, this.smallTichuBtn, this.schupfenBtn, this.playBtn);
	}

	/**
	 * @author dominik
	 */
	public void update(){
		Translator translator = ServiceLocator.getServiceLocator().getTranslator();
		this.playBtn.setText(translator.getString("controlarea.play"));
		this.schupfenBtn.setText(translator.getString("controlarea.schupfen"));
		this.smallTichuBtn.setText(translator.getString("controlarea.smalltichu"));
		this.grandTichuBtn.setText(translator.getString("controlarea.grandtichu"));
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