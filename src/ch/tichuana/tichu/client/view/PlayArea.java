package ch.tichuana.tichu.client.view;

import ch.tichuana.tichu.client.services.ServiceLocator;
import ch.tichuana.tichu.client.services.Translator;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class PlayArea extends GridPane {

	private Translator translator;

	PlayArea() {

		this.translator = ServiceLocator.getServiceLocator().getTranslator();
		Label[] headings = new Label[7];
		headings[0] = new Label(translator.getString("name"));
		headings[1] = new Label(translator.getString("team"));
		headings[2] = new Label(translator.getString("hand"));
		headings[3] = new Label(translator.getString("tichu"));
		headings[4] = new Label(translator.getString("played"));
		headings[5] = new Label(translator.getString("matchPoints"));
		headings[6] = new Label(translator.getString("total"));

		for (int i = 0; i < headings.length; i++)	{
			this.add(headings[i], i+0, 0);
		}

		Label[] playerLbl = new Label[4];
		playerLbl[0] = new Label("Philipp");
		playerLbl[1] = new Label("Chrigi");
		playerLbl[2] = new Label("Domi");
		playerLbl[3] = new Label("Digi");

		for (int i = 0; i < playerLbl.length; i++)	{
			this.add(playerLbl[i], 0, i+1);
		}
	}

}