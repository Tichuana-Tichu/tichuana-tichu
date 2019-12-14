package ch.tichuana.tichu.client.view;

import ch.tichuana.tichu.client.model.ClientModel;
import javafx.scene.layout.BorderPane;

public class PlayView extends BorderPane {

	private BottomView bottomView;
	private PlayArea playArea;
	private Settings settings;

	/**
     * extends BorderPane including a BottomView in the bottom area,
     * a MenuBar at the top and a PlayArea in the center
	 * @author Philipp
     */
	PlayView(ClientModel clientModel) {
		this.bottomView = new BottomView(clientModel);
		this.playArea = new PlayArea(clientModel);
		this.settings = Settings.getTopSettings();
		this.setBottom(this.bottomView);
		this.setCenter(this.playArea);
		this.setTop(this.settings);
	}

	/**
	 * @author dominik
	 */
	public void update(){
		this.bottomView.update();
		this.settings.update();
		this.playArea.update();
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