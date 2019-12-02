package ch.tichuana.tichu.client.view;

import ch.tichuana.tichu.client.model.ClientModel;
import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.Rank;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class CardArea extends HBox {

	private ClientModel clientModel;

	/**
	 * contains all CardLabels, multi use for BottomView and PlayView
	 * @author Philipp
	 */
	CardArea(ClientModel clientModel) {
		this.clientModel = clientModel;
	}

	/**
	 * fills the HBox with blank CardLabels, for the start screen
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
	 * fills and updates the HBox with CardLabels, in the BottomView
	 * depending on the current Hand
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
	 * fills and updates the HBox with Thumbnails in the PlayView
	 * depending on the current moves
	 * @author Philipp
	 * @param cards
	 */
	public void updateThumbnails(ArrayList<Card> cards) {
		for (Card card : cards) {
			CardLabel cardLabel = new CardLabel();
			cardLabel.setThumbnail(card);
			this.getChildren().add(cardLabel);
			this.setSpacing(-10);
		}
	}
}