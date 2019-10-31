package ch.tichuana.tichu.client.view;

import ch.tichuana.tichu.client.services.ServiceLocator;
import ch.tichuana.tichu.client.services.Translator;
import javafx.scene.layout.BorderPane;

public class PlayView extends BorderPane {

	private BottomView bottomView;
	private PlayArea playArea;
	private StatArea statArea;
	private Translator translator;

	public PlayView() {

		this.translator = ServiceLocator.getServiceLocator().getTranslator();

		this.bottomView = new BottomView();
		this.playArea = new PlayArea();
		this.statArea = new StatArea();

		this.setBottom(this.bottomView);
		this.setLeft(this.statArea);
		this.setCenter(this.playArea);
	}

	//Getter
	public BottomView getBottomView() {
		return bottomView;
	}
	public PlayArea getPlayArea() {
		return playArea;
	}
	public StatArea getStatArea() {
		return statArea;
	}
}