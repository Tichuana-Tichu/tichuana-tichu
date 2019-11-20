package ch.tichuana.tichu.client.view;

import ch.tichuana.tichu.client.model.ClientModel;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class BottomView extends BorderPane {

	private ControlArea controlArea;
	private CardArea cardArea;
	private TextField console;
	private VBox cardsNConsole;
	private ClientModel clientModel;

	/**
	 * extends BorderPane including a console in the top area
	 * a CardArea in the left area and a ControlArea in the right area
	 * @author Philipp
	 */
	BottomView(ClientModel clientModel) {
		this.clientModel = clientModel;
		this.controlArea = new ControlArea();
		this.cardArea = new CardArea(clientModel, CardArea.CardAreaType.Blank, 8);
		this.console = new TextField();
		this.console.setEditable(false);
		this.console.setFocusTraversable(false);
		this.cardsNConsole = new VBox(this.cardArea, this.console);

		this.setRight(this.controlArea);
		this.setCenter(cardsNConsole);
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
	public void setCardArea(CardArea.CardAreaType cardAreaType, int i) {
		this.cardArea = new CardArea(clientModel, cardAreaType, i);
		this.cardsNConsole.getChildren().set(0, this.cardArea);
	}
	public void setRemainingCards(int i) {
		this.cardArea.appendCards(i);
	}
}