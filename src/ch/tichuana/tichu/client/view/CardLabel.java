package ch.tichuana.tichu.client.view;

import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.Suit;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



public class CardLabel extends Label {

	private Image cardimg;
	private ImageView cardView;
	private Card card;

	public CardLabel(Card card) {
		super();
		this.card = card;
	}

	public ImageView getCardView() {
		return this.cardView;
	}

	public void setCard(Card card) {
		this.card = card;
	}

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

}