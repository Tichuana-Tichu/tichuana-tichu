package ch.tichuana.tichu.client.view;

import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class BottomView extends BorderPane {

	private ControlArea controlArea;
	private CardArea cardArea;
	private TextField console;

	/**
	 * extends BorderPane including a console in the top area
	 * a CardArea in the left area and a ControlArea in the right area
	 * @author Philipp
	 */
	BottomView() {

		this.controlArea = new ControlArea();
		this.cardArea = new CardArea();
		this.console = new TextField();
		this.console.setEditable(false);
		this.console.setFocusTraversable(false);

		VBox cardsNConsole = new VBox(this.cardArea, this.console);

		//this.setLeft(this.cardArea);
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
}