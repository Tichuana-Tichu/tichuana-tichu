package ch.tichuana.tichu.client.view;

import ch.tichuana.tichu.client.model.ClientModel;
import ch.tichuana.tichu.client.services.ServiceLocator;
import ch.tichuana.tichu.client.services.Translator;
import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.TichuType;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.util.ArrayList;

public class PlayArea extends GridPane {

	private ClientModel clientModel;
	private Translator t;
	private Label[] playerLbl;
	private Label[] headings = new Label[7];
	private Label l1;
	private Label l2;


	/**
	 * creates a table-like, resizeable grid for information about the game-flow
	 * @author Philipp
	 * @param clientModel following MVC pattern
	 */
	PlayArea(ClientModel clientModel) {

		this.clientModel = clientModel;
		this.t = ServiceLocator.getServiceLocator().getTranslator();
		Label[] headings = new Label[6];
		headings[0] = new Label(t.getString("name"));
		headings[1] = new Label(t.getString("hand"));
		headings[2] = new Label(t.getString("tichu"));
		headings[3] = new Label(t.getString("played"));
		headings[4] = new Label(t.getString("team"));
		headings[4].setId("teamHeading");
		headings[5] = new Label(t.getString("total"));

		for (int i = 0; i < headings.length; i++)	{
			this.add(headings[i], i, 0);
			GridPane.setHgrow(headings[i], Priority.ALWAYS);
		}

		this.add(new Separator(), 0, 1, 6, 1);

		this.playerLbl = new Label[4];
		for (int i = 0; i < playerLbl.length; i++) {
			playerLbl[i] = new Label(t.getString("initPlayerColumn"));
		}

		Label[] tichuLbl = new Label[4];
		for (int i = 0; i < tichuLbl.length; i++) {
			tichuLbl[i] = new Label("");
		}
		this.add(tichuLbl[0], 2, 2);
		this.add(tichuLbl[1], 2, 4);
		this.add(tichuLbl[2], 2, 6);
		this.add(tichuLbl[3], 2, 8);

		this.add(playerLbl[0], 0, 2, 1, 1);
		GridPane.setVgrow(playerLbl[0], Priority.ALWAYS);
		this.add(new Separator(), 0, 3, 4, 1);

		this.add(playerLbl[1], 0, 4, 1, 1);
		GridPane.setVgrow(playerLbl[1], Priority.ALWAYS);
		this.add(new Separator(), 0, 5, 6, 1);

		this.add(playerLbl[2], 0, 6, 1, 1);
		GridPane.setVgrow(playerLbl[2], Priority.ALWAYS);
		this.add(new Separator(), 0, 7, 4, 1);

		this.add(playerLbl[3], 0, 8, 1, 1);
		GridPane.setVgrow(playerLbl[3], Priority.ALWAYS);

		this.add(new Separator(), 0, 9, 8, 1);

		this.add(new Label(""), 1, 2);
		this.add(new Label(""), 1, 4);
		this.add(new Label(""), 1, 6);
		this.add(new Label(""), 1, 8);

		l1 = new Label(t.getString("yourTeam"));
		l1.getStyleClass().add("teamLabel");
		l2 = new Label(t.getString("opponents"));
		l2.getStyleClass().add("teamLabel");
		this.add(l1, 4, 2, 1, 3);
		this.add(l2, 4, 6, 1, 3);

		this.add(new CardArea(clientModel), 3, 2);
		this.add(new CardArea(clientModel), 3, 4);
		this.add(new CardArea(clientModel), 3, 6);
		this.add(new CardArea(clientModel), 3, 8);


		Label totalScore = new Label("0");
		totalScore.getStyleClass().add("scoreLabels");
		Label opponentTotal = new Label("0");
		opponentTotal.getStyleClass().add("scoreLabels");

		this.add(totalScore, 5, 2, 1, 3);
		this.add(opponentTotal, 5, 6, 1, 3);

		for (int i = 2; i < 9; i+=2) {
			CardArea ca =  (CardArea) getNodeByRowColumnIndex(i, 3);
			ca.initThumbnails();
		}

		this.maxWidth(6000);
		this.maxHeight(6000);
		this.setHgap(2);
		this.setVgap(2);
	}

	/**
	 * @author Philipp
	 */
	void updatePlayerName() {
		this.playerLbl[0].setText(clientModel.getPlayerName());
	}

	/**
	 * @author Philipp
	 */
	public void updateNameColumn() {
		this.playerLbl[1].setText(clientModel.getTeamMate());
		this.playerLbl[2].setText(clientModel.getOpponent(0));
		this.playerLbl[3].setText(clientModel.getOpponent(1));
	}

	private void updateHandColumn(String playerName, int playedCards) {
		Label handLabel = (Label) getNodeByRowColumnIndex(getPlayerRow(playerName), 1);

		if (playerName.equals(clientModel.getPlayerName())) {
			handLabel.setText(String.valueOf(clientModel.getHand().getCards().size()));
		} else {
			int handSize = Integer.parseInt(handLabel.getText());
			if (handSize == playedCards)
				handLabel.setText("0");
			else
				handLabel.setText(String.valueOf(handSize-playedCards));
		}
	}

	public void initHandColumn(int cards) {
		Label h1 = (Label) getNodeByRowColumnIndex(2, 1);
		Label h2 = (Label) getNodeByRowColumnIndex(4, 1);
		Label h3 = (Label) getNodeByRowColumnIndex(6, 1);
		Label h4 = (Label) getNodeByRowColumnIndex(8, 1);
		h1.setText(String.valueOf(cards));
		h2.setText(String.valueOf(cards));
		h3.setText(String.valueOf(cards));
		h4.setText(String.valueOf(cards));
	}

	/**
	 *
	 * @author Philipp
	 * @param playerName player whose Label needs to be adjusted
	 * @param tichuType the type of tichu the given player announces
	 */
	public void updateTichuColumn(String playerName, TichuType tichuType) {

		Label tichuLabel = (Label) getNodeByRowColumnIndex(getPlayerRow(playerName), 2);

		switch (tichuType) {
			case GrandTichu: tichuLabel.setText(t.getString("GrandTichu")); break;
			case SmallTichu: tichuLabel.setText(t.getString("SmallTichu")); break;
			case none: tichuLabel.setText(t.getString("noTichu")); break;
		}
	}

	/**
	 * @author Philipp
	 */
	public void clearTichuColumn() {
		for (int i = 2; i < 9; i+=2) {
			Label tichuLabel =  (Label) getNodeByRowColumnIndex(i, 2);
			tichuLabel.setText("");
		}
	}

	/**
	 *
	 * @author Philipp
	 * @param playerName player whose CardArea needs to be adjusted
	 * @param cards cards the given player chooses to play
	 */
	public void updatePlayedColumn(String playerName, ArrayList<Card> cards) {
		CardArea cardArea = (CardArea) getNodeByRowColumnIndex(getPlayerRow(playerName), 3);

		updateHandColumn(playerName, cards.size()); //also updates HandColumn

		if (!cards.isEmpty())
			cardArea.updateThumbnails(cards);
		else
			cardArea.deleteThumbnails();
	}

	/**
	 * @author Philipp
	 */
	public void clearPlayedColumn() {
		for (int i = 2; i < 9; i+=2) {
			CardArea ca =  (CardArea) getNodeByRowColumnIndex(i, 3);
			ca.deleteThumbnails();
			ca.initThumbnails();
		}
	}

	/**
	 *
	 * @author Philipp
	 * @param ownScore the current score of the team this instance participates
	 * @param opponentScore the current score of the oppnents
	 */
	public void updateTotalPoints(int ownScore, int opponentScore) {
		Label own = (Label) getNodeByRowColumnIndex(2, 6);
		Label opponent = (Label) getNodeByRowColumnIndex(6, 6);
		own.setText(String.valueOf(ownScore));
		opponent.setText(String.valueOf(opponentScore));
	}

	/**
	 *
	 * @author Philipp
	 * @param playerName player to which the column belongs
	 * @return returns the row index, which belongs to the requested player
	 */
	private int getPlayerRow(String playerName) {
		int result = 0;

		for (Label label : this.playerLbl) {
			if (label.getText().equals(playerName)) {
				result = GridPane.getRowIndex(label);
				break;
			}
		}
		return result;
	}

	/**
	 * returns a node at a given position inside the GridPane
	 * got method structure from:
	 * https://stackoverflow.com/questions/20825935/javafx-get-node-by-row-and-column
	 * @author Philipp
	 * @param row index of a given node
	 * @param column index of a given node
	 * @return node at a given row & column index
	 */
	private Node getNodeByRowColumnIndex (final int row, final int column) {
		Node result = null;
		ObservableList<Node> children = this.getChildren();

		for (Node node : children) {
			if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
				result = node;
				break;
			}
		}
		return result;
	}

	/**
	 * @author dominik
	 */
	public void update() {
		Translator translator = ServiceLocator.getServiceLocator().getTranslator();
		this.headings[0].setText(translator.getString("name"));
		this.headings[1].setText(translator.getString("hand"));
		this.headings[2].setText(translator.getString("tichu"));
		this.headings[3].setText(translator.getString("played"));
		this.headings[4].setText(translator.getString("team"));
		this.headings[5].setText(translator.getString("matchPoints"));
		this.headings[6].setText(translator.getString("total"));
		this.l1.setText(translator.getString("yourTeam"));
		this.l2.setText(translator.getString("opponents"));

		if (clientModel.getTeamMate() == null) {
			this.playerLbl[1].setText(translator.getString("initPlayerColumn"));
			this.playerLbl[2].setText(translator.getString("initPlayerColumn"));
			this.playerLbl[3].setText(translator.getString("initPlayerColumn"));
		}

	}

}