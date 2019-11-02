package ch.tichuana.tichu.client.view;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class BottomView extends BorderPane {

	private ControlArea controlArea;
	private CardArea cardArea;

	public BottomView() {

		this.controlArea = new ControlArea();
		this.cardArea = new CardArea();

		this.setLeft(this.cardArea);
		this.setRight(this.controlArea);
	}

	//Getter
	public ControlArea getControlArea() {
		return controlArea;
	}
	public CardArea getCardArea() {
		return cardArea;
	}
}