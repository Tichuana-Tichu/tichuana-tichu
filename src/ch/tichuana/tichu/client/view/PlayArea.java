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
	private Label[] headings = new Label[6];
	private Label onwTeamLbl;
	private Label opponentLbl;
	private Label[] tichuLbl;
	public static final int NAME_COLUMN_INDEX = 0;
	public static final int HAND_COLUMN_INDEX = 1;
	public static final int TICHU_COLUMN_INDEX = 2;
	public static final int PLAYED_COLUMN_INDEX = 3;
	public static final int TEAM_COLUMN_INDEX = 4;
	public static final int SCORE_COLUMN_INDEX = 5;
	public static final int LAST_PLAYER_ROW = 9;

	/**
	 * creates a table-like, resizeable grid for information about the game-flow
	 * @author Philipp
	 * @param clientModel following MVC pattern
	 */
	PlayArea(ClientModel clientModel) {

		this.clientModel = clientModel;
		this.t = ServiceLocator.getServiceLocator().getTranslator();
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

		this.add(new Separator(), NAME_COLUMN_INDEX, 1, 6, 1);

		this.playerLbl = new Label[4];
		for (int i = 0; i < playerLbl.length; i++) {
			playerLbl[i] = new Label(t.getString("initPlayerColumn"));
		}

		this.tichuLbl = new Label[4];
		for (int i = 0; i < tichuLbl.length; i++) {
			tichuLbl[i] = new Label("");
		}
		this.add(tichuLbl[0], TICHU_COLUMN_INDEX, 2);
		this.add(tichuLbl[1], TICHU_COLUMN_INDEX, 4);
		this.add(tichuLbl[2], TICHU_COLUMN_INDEX, 6);
		this.add(tichuLbl[3], TICHU_COLUMN_INDEX, 8);

		this.add(playerLbl[0], NAME_COLUMN_INDEX, 2, 1, 1);
		GridPane.setVgrow(playerLbl[0], Priority.ALWAYS);
		this.add(new Separator(), NAME_COLUMN_INDEX, 3, 4, 1);

		this.add(playerLbl[1], NAME_COLUMN_INDEX, 4, 1, 1);
		GridPane.setVgrow(playerLbl[1], Priority.ALWAYS);
		this.add(new Separator(), NAME_COLUMN_INDEX, 5, 6, 1);

		this.add(playerLbl[2], NAME_COLUMN_INDEX, 6, 1, 1);
		GridPane.setVgrow(playerLbl[2], Priority.ALWAYS);
		this.add(new Separator(), NAME_COLUMN_INDEX, 7, 4, 1);

		this.add(playerLbl[3], NAME_COLUMN_INDEX, 8, 1, 1);
		GridPane.setVgrow(playerLbl[3], Priority.ALWAYS);

		this.add(new Separator(), NAME_COLUMN_INDEX, 9, 8, 1);

		for (int i = 2; i < 9; i+=2) {
			this.add(new Label(""), HAND_COLUMN_INDEX, i);
		}

		this.onwTeamLbl = new Label(t.getString("yourTeam"));
		this.onwTeamLbl.getStyleClass().add("teamLabel");
		this.opponentLbl = new Label(t.getString("opponents"));
		this.opponentLbl.getStyleClass().add("teamLabel");
		this.add(onwTeamLbl, TEAM_COLUMN_INDEX, 2, 1, 3);
		this.add(opponentLbl, TEAM_COLUMN_INDEX, 6, 1, 3);

		for (int i = 2; i < LAST_PLAYER_ROW; i+=2) {
			this.add(new CardArea(clientModel), PLAYED_COLUMN_INDEX, i);
		}

		Label totalScore = new Label("0");
		totalScore.getStyleClass().add("scoreLabels");
		Label opponentTotal = new Label("0");
		opponentTotal.getStyleClass().add("scoreLabels");

		this.add(totalScore, SCORE_COLUMN_INDEX, 2, 1, 3);
		this.add(opponentTotal, SCORE_COLUMN_INDEX, 6, 1, 3);

		for (int i = 2; i < LAST_PLAYER_ROW; i+=2) {
			CardArea ca =  (CardArea) getNodeByRowColumnIndex(i, PLAYED_COLUMN_INDEX);
			ca.initThumbnails();
		}
		this.maxWidth(6000);
		this.maxHeight(6000);
		this.setHgap(2);
		this.setVgap(2);
	}

	/**
	 * updates the name of this player
	 * @author Philipp
	 */
	void updatePlayerName() {
		this.playerLbl[0].setText(clientModel.getPlayerName());
	}

	/**
	 * updates the names of all the other players
	 * @author Philipp
	 */
	public void updateNameColumn() {
		this.playerLbl[1].setText(clientModel.getTeamMate());
		this.playerLbl[2].setText(clientModel.getOpponent(0));
		this.playerLbl[3].setText(clientModel.getOpponent(1));
	}

	/**
	 * updates the handColumn by getting the actual hand size, or if not updating a move from this player
	 * by dividing the int value from the Label with the now played cards
	 * @author Philipp
	 * @param playerName the player whose column needs to be updated
	 * @param playedCards the size of the move he made
	 */
	private void updateHandColumn(String playerName, int playedCards) {
		Label handLabel = (Label) getNodeByRowColumnIndex(getPlayerRow(playerName), HAND_COLUMN_INDEX);

		if (playerName.equals(clientModel.getPlayerName())) {
			handLabel.setText(String.valueOf(clientModel.getHand().getCards().size()));
		} else {
			int handSize = Integer.parseInt(handLabel.getText());
			if (handSize == playedCards) //makes sure values dont get negative
				handLabel.setText("0");
			else
				handLabel.setText(String.valueOf(handSize-playedCards));
		}
	}

	/**
	 * sets the hand sizes of all players in real-time during initializing game process (pushing & announcing)
	 * @author Philipp
	 * @param handSize size of the current hand during initial game processes
	 */
	public void initHandColumn(int handSize) {
		for (int i = 2; i < LAST_PLAYER_ROW; i+=2) {
			Label lbl = (Label) getNodeByRowColumnIndex(i, HAND_COLUMN_INDEX);
			lbl.setText(String.valueOf(handSize));
		}
	}

	/**
	 * updates the tichuColumn with the announced tichuType
	 * @author Philipp
	 * @param playerName player whose Label needs to be adjusted
	 * @param tichuType the type of tichu the given player announces
	 */
	public void updateTichuColumn(String playerName, TichuType tichuType) {

		Label tichuLabel = (Label) getNodeByRowColumnIndex(getPlayerRow(playerName), TICHU_COLUMN_INDEX);

		switch (tichuType) {
			case GrandTichu: tichuLabel.setText(t.getString("GrandTichu")); break;
			case SmallTichu: tichuLabel.setText(t.getString("SmallTichu")); break;
			case none: tichuLabel.setText(t.getString("noTichu")); break;
		}
	}

	/**
	 * clears the tichuColumn when a new match starts
	 * @author Philipp
	 */
	public void clearTichuColumn() {
		for (int i = 2; i < LAST_PLAYER_ROW; i+=2) {
			Label tichuLabel =  (Label) getNodeByRowColumnIndex(i, TICHU_COLUMN_INDEX);
			tichuLabel.setText("");
		}
	}

	/**
	 * updates the playedColumn with the current move and calls updateHandColumn method
	 * @author Philipp
	 * @param playerName player whose CardArea needs to be adjusted
	 * @param cards cards the given player chooses to play
	 */
	public void updatePlayedColumn(String playerName, ArrayList<Card> cards) {
		CardArea cardArea = (CardArea) getNodeByRowColumnIndex(getPlayerRow(playerName), PLAYED_COLUMN_INDEX);

		updateHandColumn(playerName, cards.size()); //also updates HandColumn
		cardArea.updateThumbnails(cards);
	}

	/**
	 * deletes all displayed moves and re-initialize the playedColumn
	 * @author Philipp
	 */
	public void clearPlayedColumn() {
		for (int i = 2; i < LAST_PLAYER_ROW; i+=2) {
			CardArea ca =  (CardArea) getNodeByRowColumnIndex(i, PLAYED_COLUMN_INDEX);
			ca.deleteThumbnails();
			ca.initThumbnails();
		}
	}

	/**
	 * updates the scoreColumn
	 * @author Philipp
	 * @param ownScore the current score of the team this instance participates
	 * @param opponentScore the current score of the opponents
	 */
	public void updateTotalPoints(int ownScore, int opponentScore) {
		Label own = (Label) getNodeByRowColumnIndex(2, SCORE_COLUMN_INDEX);
		Label opponent = (Label) getNodeByRowColumnIndex(6, SCORE_COLUMN_INDEX);
		own.setText(String.valueOf(ownScore));
		opponent.setText(String.valueOf(opponentScore));
	}

	/**
	 * gets the column depending on the queried player
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
	 * updates the language specific components
	 * @author dominik
	 */
	public void update() {
		Translator translator = ServiceLocator.getServiceLocator().getTranslator();
		this.headings[0].setText(translator.getString("name"));
		this.headings[1].setText(translator.getString("hand"));
		this.headings[2].setText(translator.getString("tichu"));
		this.headings[3].setText(translator.getString("played"));
		this.headings[4].setText(translator.getString("team"));
		this.headings[5].setText(translator.getString("total"));
		this.onwTeamLbl.setText(translator.getString("yourTeam"));
		this.opponentLbl.setText(translator.getString("opponents"));


		if (clientModel.getTeamMate() == null) {
			this.playerLbl[1].setText(translator.getString("initPlayerColumn"));
			this.playerLbl[2].setText(translator.getString("initPlayerColumn"));
			this.playerLbl[3].setText(translator.getString("initPlayerColumn"));
		}

		for (int i = 2; i < LAST_PLAYER_ROW; i+=2) {
			Label tichuLabel = (Label) getNodeByRowColumnIndex(i, TICHU_COLUMN_INDEX);
			String tichuType = tichuLabel.getText();

			switch (tichuType){
				case "Grosses Tichu":
				case "Grand Tichu":
				case "大提楚":
					tichuLabel.setText(translator.getString("GrandTichu")); break;
				case "Kleines Tichu":
				case "Small Tichu":
				case "小提丘":
					tichuLabel.setText(translator.getString("SmallTichu")); break;

				case "-": tichuLabel.setText(translator.getString("noTichu")); break;
			}
		}
	}
}