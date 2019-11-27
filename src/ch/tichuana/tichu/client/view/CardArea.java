package ch.tichuana.tichu.client.view;

import ch.tichuana.tichu.client.model.ClientModel;
import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.Rank;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CardArea extends VBox {

	private HBox cardsLabels;
	private ClientModel clientModel;

	public enum CardAreaType {Blank, Cards, Thumbnails;}

    /**
     * instantiates a new Card for each CardLabel
     * instantiates HBox with all CardLabels in it
	 * @author Philipp
     */
	CardArea(ClientModel clientModel, CardAreaType cat, int cardCounter) {
		this.clientModel = clientModel;
		this.cardsLabels = new HBox();

		if (cat.equals(CardAreaType.Blank)) {
			for (int i = 0; i < cardCounter; i++) {
				CardLabel cardLabel = new CardLabel();
				cardLabel.setBlankCard();
				cardsLabels.getChildren().add(cardLabel);
				cardsLabels.setSpacing(-140);
			}
			this.getChildren().add(this.cardsLabels);
		}

		else if (cat.equals(CardAreaType.Cards)) {

			for (int i = 0; i < cardCounter; i++) {
				CardLabel cardLabel = new CardLabel();
				cardLabel.setCard(clientModel.getHand().getCards().get(i));
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

	public void updateCardLabels() {
		this.cardsLabels.getChildren().clear();
		for (Card c : this.clientModel.getHand().getCards()) {
			CardLabel cl = new CardLabel();
			cl.setCard(c);
			this.cardsLabels.getChildren().add(cl);
			cardsLabels.setSpacing(-140);
		}
	}

	//Getter
	public HBox getCardsLabels() {
		return this.cardsLabels;
	}
}