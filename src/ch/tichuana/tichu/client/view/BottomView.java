package ch.tichuana.tichu.client.view;

import javafx.scene.layout.AnchorPane;

public class BottomView extends AnchorPane {

	private ControlArea controlArea;
	private CardArea cardArea;

	public BottomView() {

		this.controlArea = new ControlArea();
		this.cardArea = new CardArea();

		AnchorPane.setLeftAnchor(this.cardArea, 15.0);
		AnchorPane.setRightAnchor(this.controlArea, 15.0);
		//VBox.setVgrow(this.cardArea, Priority.ALWAYS);
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