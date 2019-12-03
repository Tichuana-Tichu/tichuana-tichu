package ch.tichuana.tichu.client.view;

import ch.tichuana.tichu.client.model.ClientModel;
import ch.tichuana.tichu.client.services.ServiceLocator;
import ch.tichuana.tichu.client.services.Translator;
import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.TichuType;
import javafx.collections.ObservableList;
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

	/**
	 * creates a table-like, resizeable grid for information about the game-flow
	 * @author Philipp
	 * @param clientModel
	 */
	PlayArea(ClientModel clientModel) {

		this.clientModel = clientModel;
		this.t = ServiceLocator.getServiceLocator().getTranslator();
		Label[] headings = new Label[7];
		headings[0] = new Label(t.getString("name"));
		headings[1] = new Label(t.getString("hand"));
		headings[2] = new Label(t.getString("tichu"));
		headings[3] = new Label(t.getString("played"));
		headings[4] = new Label(t.getString("team"));
		headings[5] = new Label(t.getString("matchPoints"));
		headings[6] = new Label(t.getString("total"));

		for (int i = 0; i < headings.length; i++)	{
			this.add(headings[i], i, 0);
			GridPane.setHgrow(headings[i], Priority.ALWAYS);
		}

		this.add(new Separator(), 0, 1, 7, 1);

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
		this.add(new Separator(), 0, 5, 7, 1);

		this.add(playerLbl[2], 0, 6, 1, 1);
		GridPane.setVgrow(playerLbl[2], Priority.ALWAYS);
		this.add(new Separator(), 0, 7, 4, 1);

		this.add(playerLbl[3], 0, 8, 1, 1);
		GridPane.setVgrow(playerLbl[3], Priority.ALWAYS);

		this.add(new Separator(), 0, 9, 9, 1);

		Label l1 = new Label("");
		l1.getStyleClass().add("teamLabel");
		Label l2 = new Label("");
		l2.getStyleClass().add("teamLabel");
		this.add(l1, 4, 2, 1, 3);
		this.add(l2, 4, 6, 1, 3);

		this.add(new CardArea(clientModel), 3, 2);
		this.add(new CardArea(clientModel), 3, 4);
		this.add(new CardArea(clientModel), 3, 6);
		this.add(new CardArea(clientModel), 3, 8);

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

	public void updateTeamColumn(String playerName) {
		Label teamLabel = (Label) getNodeByRowColumnIndex(getPlayerRow(playerName), 4);
		teamLabel.setText("1");
	}

	/**
	 *
	 * @author Philipp
	 * @param playerName
	 * @param tichuType
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
	 *
	 * @author Philipp
	 * @param cards
	 * @param playerName
	 */
	public void updatePlayedColumn(String playerName, ArrayList<Card> cards) {

		CardArea cardArea = (CardArea) getNodeByRowColumnIndex(getPlayerRow(playerName), 3);
		cardArea.updateThumbnails(cards);
	}

	/**
	 *
	 * @author Philipp
	 * @param playerName
	 */
	public void updatePlayedColumn(String playerName) {

		CardArea cardArea = (CardArea) getNodeByRowColumnIndex(getPlayerRow(playerName), 3);
		cardArea.deleteThumbnails();
	}

	/**
	 *
	 * @author Philipp
	 * @param playerName
	 * @return
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

	public void updateHandColumn() {
		for (int i = 2; i < this.getColumnCount(); i+=2) {
			this.add(new Label(""), 2, i);
		}
	}

	public void updateMatchPoints() {
		for (int i = 2; i < this.getColumnCount(); i+=2) {
			this.add(new Label(""), 5, i);
		}
	}

	public void updateTotalPoints() {
		for (int i = 2; i < this.getColumnCount(); i+=2) {
			this.add(new Label(""), 6, i);
		}
	}
}