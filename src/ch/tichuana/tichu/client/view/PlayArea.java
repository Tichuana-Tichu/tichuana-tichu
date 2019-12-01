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
		headings[1] = new Label(t.getString("team"));
		headings[2] = new Label(t.getString("hand"));
		headings[3] = new Label(t.getString("tichu"));
		headings[4] = new Label(t.getString("played"));
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
		this.add(tichuLbl[0], 3, 2, 1, 1);
		this.add(tichuLbl[1], 3, 4, 1, 1);
		this.add(tichuLbl[2], 3, 6, 1, 1);
		this.add(tichuLbl[3], 3, 8, 1, 1);

		this.add(playerLbl[0], 0, 2, 1, 1);
		GridPane.setVgrow(playerLbl[0], Priority.ALWAYS);
		this.add(new Separator(), 0, 3, 5, 1);

		this.add(playerLbl[1], 0, 4, 1, 1);
		GridPane.setVgrow(playerLbl[1], Priority.ALWAYS);
		this.add(new Separator(), 0, 5, 7, 1);

		this.add(playerLbl[2], 0, 6, 1, 1);
		GridPane.setVgrow(playerLbl[2], Priority.ALWAYS);
		this.add(new Separator(), 0, 7, 5, 1);

		this.add(playerLbl[3], 0, 8, 1, 1);
		GridPane.setVgrow(playerLbl[3], Priority.ALWAYS);

		this.add(new Separator(), 0, 9, 9, 1);

		for (int i = 2; i < this.getColumnCount(); i+=2) {
			this.getChildren().add((new CardArea(clientModel)));
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

	/**
	 *
	 * @author Philipp
	 * @param playerName
	 * @param tichuType
	 */
	public void updateTichuColumn(String playerName, TichuType tichuType) {

		Label tichuLabel = (Label) getNodeByRowColumnIndex(getPlayerRow(playerName), 3);

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
	 */
	public void updatePlayedColumn(ArrayList<Card> cards) {
		String player = this.clientModel.getPlayerName();

		for (int i = 2; i < this.getColumnCount(); i+=2) {

			CardArea cardArea = (CardArea) getNodeByRowColumnIndex(getPlayerRow(player), 4);
			cardArea.updateThumbnails(cards);
		}
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

	public void updateTeamColumn() {
		for (int i = 2; i < this.getColumnCount(); i+=2) {
			this.add(new Label(""), 1, i);
		}
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