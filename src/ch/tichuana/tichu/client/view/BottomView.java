package ch.tichuana.tichu.client.view;

import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class BottomView extends BorderPane {

	private ControlArea controlArea;
	private CardArea cardArea;
	private TextField console;
	private VBox cardsNConsole;

	/**
	 * extends BorderPane including a console in the top area
	 * a CardArea in the left area and a ControlArea in the right area
	 * @author Philipp
	 */
	BottomView() {

		this.controlArea = new ControlArea();
		this.cardArea = new CardArea(CardArea.CardAreaType.Blank, 8);
		this.console = new TextField();
		this.console.setEditable(false);
		this.console.setFocusTraversable(false);

		this.cardsNConsole = new VBox(new CardArea(CardArea.CardAreaType.Blank, 8), this.console);
		//VBox cardsNConsole = new VBox(this.cardArea, this.console);

		this.setRight(this.controlArea);
		this.setCenter(cardsNConsole);
	}

	//Getter
	public ControlArea getControlArea() {
		return controlArea;
	}
	public CardArea getCardArea() {
		return cardArea;
	}
	public void setConsole(String newestMessage) {
		this.console.setText(newestMessage);
	}
	public void setCardArea(CardArea cardArea, int i) {
		this.cardArea = cardArea;
		this.cardArea = new CardArea(CardArea.CardAreaType.Cards, i);
		this.cardsNConsole.getChildren().set(0, this.cardArea);
	}
}