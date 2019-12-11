package ch.tichuana.tichu.client.view;

import ch.tichuana.tichu.client.model.ClientModel;
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
	BottomView(ClientModel clientModel) {
		this.controlArea = new ControlArea();
		this.cardArea = new CardArea(clientModel);
		this.cardArea.updateBlankCards();
		this.console = new TextField();
		this.console.setEditable(false);
		this.console.setFocusTraversable(false);
		VBox cardsNConsole = new VBox(this.cardArea, this.console);

		this.setRight(this.controlArea);
		this.setCenter(cardsNConsole);
	}

	/**
	 * @author dominik
	 */
	public void update(){
	controlArea.update();
	//TODO Console is not translated ATM.
	}

	//Getter & Setter
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