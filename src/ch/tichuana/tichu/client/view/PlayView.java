package ch.tichuana.tichu.client.view;

import ch.tichuana.tichu.client.model.ClientModel;
import ch.tichuana.tichu.client.services.ServiceLocator;
import ch.tichuana.tichu.client.services.Translator;
import javafx.scene.layout.BorderPane;

public class PlayView extends BorderPane {

	private BottomView bottomView;
	private PlayArea playArea;
	private Settings settings;
	private Translator translator;

	/**
     * extends BorderPane including a BottomView in the bottom area,
     * a MenuBar at the top and a PlayArea in the center
	 * @author Philipp
     */
	PlayView(ClientModel clientModel) {
		this.translator = ServiceLocator.getServiceLocator().getTranslator();

		this.bottomView = new BottomView(clientModel);
		this.playArea = new PlayArea(clientModel);
		this.settings = new Settings();

		this.setBottom(this.bottomView);
		this.setCenter(this.playArea);
		this.setTop(this.settings);
	}

	//Getter
	public BottomView getBottomView() {
		return bottomView;
	}
	public PlayArea getPlayArea() {
		return playArea;
	}
	public Settings getSettings() {
		return settings;
	}
}