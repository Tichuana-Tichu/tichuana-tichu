package ch.tichuana.tichu.client.view;

import ch.tichuana.tichu.client.model.ClientModel;
import ch.tichuana.tichu.client.services.ServiceLocator;
import ch.tichuana.tichu.client.services.Translator;
import javafx.scene.layout.BorderPane;

public class PlayView extends BorderPane {

	private BottomView bottomView;
	private PlayArea playArea;
	//private StatArea statArea;
	private Settings settings;
	private Translator translator;
	private ClientModel clientModel;

    /**
     * extends BorderPane including a BottomView in the bottom area,
     * a MenuBar at the top and a PlayArea in the center
	 * @author Philipp
     */
	PlayView(ClientModel clientModel) {
		this.clientModel = clientModel;
		this.translator = ServiceLocator.getServiceLocator().getTranslator();

		this.bottomView = new BottomView(clientModel);
		this.playArea = new PlayArea(clientModel);
		//this.statArea = new StatArea();
		this.settings = new Settings();

		this.setBottom(this.bottomView);
		//this.setLeft(this.statArea);
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
	/*
	public StatArea getStatArea() {
		return statArea;
	}
	 */
	public Settings getSettings() {
		return settings;
	}
}