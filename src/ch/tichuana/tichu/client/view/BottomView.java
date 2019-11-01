package ch.tichuana.tichu.client.view;

import javafx.scene.layout.HBox;

public class BottomView extends HBox {

	private ControlArea controlArea;
	private CardArea cardArea;

	public BottomView() {

		this.controlArea = new ControlArea();
		this.cardArea = new CardArea();
		this.getChildren().addAll(this.cardArea, this.controlArea);
	}

	//Getter
	public ControlArea getControlArea() {
		return controlArea;
	}
	public CardArea getCardArea() {
		return cardArea;
	}
}