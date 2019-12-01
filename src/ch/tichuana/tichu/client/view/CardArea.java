package ch.tichuana.tichu.client.view;

import ch.tichuana.tichu.client.model.ClientModel;
import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.Rank;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class CardArea extends HBox {

	private ClientModel clientModel;

	/**
	 * @author Philipp
	 */
	CardArea(ClientModel clientModel) {
		this.clientModel = clientModel;
	}

	/**
	 *
	 * @author Philipp
	 */
	public void updateBlankCards() {
		for (int i = 0; i < 8; i++) {
			CardLabel cardLabel = new CardLabel();
			cardLabel.setBlankCard();
			this.getChildren().add(cardLabel);
			this.setSpacing(-140);
		}
	}

	/**
	 *
	 * @author Philipp
	 */
	public void updateCardLabels() {
		this.getChildren().clear();
		for (Card c : this.clientModel.getHand().getCards()) {
			CardLabel cl = new CardLabel();
			cl.setCard(c);
			this.getChildren().add(cl);
			this.setSpacing(-140);
		}
	}

	/**
	 *
	 * @author Philipp
	 * @param cards
	 */
	public void updateThumbnails(ArrayList<Card> cards) {
		for (int i = 0; i < cards.size(); i++) {
			CardLabel cardLabel = new CardLabel();
			cardLabel.setThumbnail(new Card(Rank.mahjong));
			this.getChildren().add(cardLabel);
			this.setSpacing(-10);
		}
	}
}