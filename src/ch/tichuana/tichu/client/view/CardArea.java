package ch.tichuana.tichu.client.view;

import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CardArea extends VBox {

	private HBox cardsLabels;
	private TextArea console;

	public CardArea() {
		this.cardsLabels = new HBox();
		this.console = new TextArea();

		this.getChildren().addAll(this.console, this.cardsLabels);
	}

	//Getter
	public HBox getCardsLabels() {
		return this.cardsLabels;
	}
}