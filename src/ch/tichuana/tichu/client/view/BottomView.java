package ch.tichuana.tichu.client.view;

import javafx.scene.layout.HBox;

public class BottomView extends HBox {

	private ControlArea controlArea;
	private PlayArea playArea;

	public BottomView() {

		this.controlArea = new ControlArea();
		this.getChildren().add(this.controlArea);
	}

	//Getter
	public ControlArea getControlArea() {
		return controlArea;
	}
	public PlayArea getPlayArea() {
		return playArea;
	}
}