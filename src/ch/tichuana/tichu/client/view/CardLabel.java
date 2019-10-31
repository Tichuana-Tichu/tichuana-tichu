package ch.tichuana.tichu.client.view;

import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.Suit;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class CardLabel extends Label {

	private Image cardImg;
	private ImageView cardView;
	private Card card;

	/**
	 *
	 */
	public CardLabel() {
		super();
		this.getStyleClass().add("card");
	}

	/**
	 *
	 * @param card
	 * @return
	 */
	public String cardToFilename(Card card){
		String fileName = "";
		Suit suit = card.getSuit();
		if (suit != null){
			fileName += suit.toString();
		}
		fileName += card.getRank().toString();
		fileName += ".png";
		return fileName;
	}

	//Getter & Setter
    public ImageView getCardView() {
        return this.cardView;
    }
	public void setCard(Card card) {
		this.card = card;
	}
}