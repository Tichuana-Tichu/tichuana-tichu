package ch.tichuana.tichu.client.view;

import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.Rank;
import ch.tichuana.tichu.commons.models.Suit;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CardArea extends VBox {

	private HBox cardsLabels;

	public enum CardAreaType {Cards, Thumbnails;}

    /**
     * instantiates a new Card for each CardLabel
     * instantiates HBox with all CardLabels in it
	 * @author Philipp
     */
	CardArea(CardAreaType cat, int cardCounter) {
		this.cardsLabels = new HBox();

		if (cat.equals(CardAreaType.Cards)) {

			for (int i = 0; i < cardCounter; i++) {
				CardLabel cardLabel = new CardLabel();
				//TODO - exchange after testing with real cards from GameStartedMsg
				cardLabel.setCard(new Card(Suit.Pagodas, Rank.Ace));
				cardsLabels.getChildren().add(cardLabel);
				cardsLabels.setSpacing(-140);
			}
			this.getChildren().add(this.cardsLabels);
		} else {

			for (int i = 0; i < cardCounter; i++) {
				CardLabel cardLabel = new CardLabel();
				//TODO - exchange after testing with real cards from GameStartedMsg
				cardLabel.setThumbnail(new Card(Rank.mahjong));
				cardsLabels.getChildren().add(cardLabel);
				cardsLabels.setSpacing(-10);
			}
			this.getChildren().add(this.cardsLabels);
		}
	}

	//Getter
	public HBox getCardsLabels() {
		return this.cardsLabels;
	}
}