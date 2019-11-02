package ch.tichuana.tichu.client.view;

import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.Rank;
import ch.tichuana.tichu.commons.models.Suit;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class CardArea extends VBox {

	private HBox cardsLabels;
	private TextArea console;

	CardArea() {

		this.cardsLabels = new HBox();
		this.console = new TextArea();
		this.console.setEditable(false);
		this.console.setFocusTraversable(false);

		for (int i = 0; i < 14; i++) {
			CardLabel cardLabel = new CardLabel();
			//TODO - exchange after testing with real cards
			cardLabel.setCard(new Card(Suit.Pagodas, Rank.Ace));
			cardsLabels.getChildren().add(cardLabel);
			cardsLabels.setSpacing(-40);
		}

		//HBox.setHgrow(this.cardsLabels, Priority.ALWAYS);
		this.getChildren().addAll(this.console, this.cardsLabels);
	}

	//Getter
	public HBox getCardsLabels() {
		return this.cardsLabels;
	}
	public TextArea getConsole() {
		return console;
	}
	public void setConsole(String newestMessage) {
		this.console.setText(newestMessage);
	}
}