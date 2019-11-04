package ch.tichuana.tichu.client.view;

import ch.tichuana.tichu.client.services.Configuration;
import ch.tichuana.tichu.client.services.ServiceLocator;
import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.Suit;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;

import java.util.Objects;

public class CardLabel extends Label {

	private Image cardImg;
	private ImageView cardView;
	private Card card;
	private Configuration config;

	/**
	 *
	 */
	public CardLabel() {
		super();
		this.getStyleClass().add("card");
		this.config = ServiceLocator.getServiceLocator().getConfiguration();
	}

	/**
	 *
	 * @param card
	 */
	public void setCard(Card card) {
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

		if (card != null) {
			String fileName = cardToFileName(card);
			Image image = new Image(config.getProperty("cards")+fileName);
			ImageView imv = new ImageView(image);
			imv.setFitWidth(primaryScreenBounds.getWidth()/15);
			imv.setPreserveRatio(true);
			this.setGraphic(imv);
		} else {
			this.setGraphic(null);
		}
	}

	/**
	 *
	 * @param card
	 * @return
	 */
	private String cardToFileName(Card card){
		String fileName = "";
		Suit suit = card.getSuit();
		if (suit != null) { //for special cards
			fileName += suit.toString()+"_";
		}
		fileName += card.getRank().toString().toLowerCase();
		fileName += ".png";
		return fileName;
	}

	//Getter & Setter
    public ImageView getCardView() {
        return this.cardView;
    }
}