package ch.tichuana.tichu.client.controller;

import ch.tichuana.tichu.client.chat.ChatController;
import ch.tichuana.tichu.client.chat.ChatView;
import ch.tichuana.tichu.client.model.ClientModel;
import ch.tichuana.tichu.client.model.OldMove;
import ch.tichuana.tichu.client.model.SimpleMessageProperty;
import ch.tichuana.tichu.client.services.ServiceLocator;
import ch.tichuana.tichu.client.services.Translator;
import ch.tichuana.tichu.client.view.*;
import ch.tichuana.tichu.commons.message.*;
import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.Combination;
import ch.tichuana.tichu.commons.models.TichuType;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;

class PlayController {

    private ClientModel clientModel;
    private GameView gameView;
    private Stage stage;
    private ArrayList<Card> receivedCards = new ArrayList<>();
    private static final int MAX_HANDSIZE = 14;
    private static final int INIT_HANDSIZE = 8;
    private OldMove oldMove = new OldMove();
    private Translator translator;
    private int passCounter = 0;

    /**
     * attaches listener to the stage-width to make the CardArea responsive,
     * updates TextField (console) on incoming Messages from the server,
     * handling all messagesTypes depending on the int code which is set in the clientModel
     * sets Buttons on action, to send Messages to server when being clicked,
     * makes the cardArea listen to mouse event for auto-validation of the selection,
     * sets the Language menu on action,
     * disconnects client from server when the windows is closed
     * general information: local variables are used as often as possible to avoid ugly getter chains
     * @author Philipp
     * @param clientModel following MVC pattern
     * @param gameView following MVC pattern
     * @param stage following MVC pattern
     */
    PlayController(ClientModel clientModel, GameView gameView, Stage stage) {
        this.translator = ServiceLocator.getServiceLocator().getTranslator();
        this.clientModel = clientModel;
        this.gameView = gameView;
        this.stage = stage;

        /* creating the chatController */
        Platform.runLater(() -> new ChatController(ChatView.getView(), clientModel));

        /* computation of the negative spacing related to the stage size */
        this.stage.widthProperty().addListener((observable, oldVal, newVal) -> {

            HBox cardLabels = this.gameView.getPlayView().getBottomView().getCardArea();

            if (oldVal.intValue() < newVal.intValue()) { //screen gets bigger
                cardLabels.setSpacing(((this.stage.getWidth() - (newVal.floatValue() - this.stage.getWidth())) / 15)
                        - this.stage.getMinWidth() / 9);
            } else { //screen get smaller
                cardLabels.setSpacing(((this.stage.getWidth() - (this.stage.getWidth() - newVal.floatValue())) / 15)
                        - this.stage.getMinWidth() / 9);
            }
        });

        ControlArea ca = this.gameView.getPlayView().getBottomView().getControlArea();
        SimpleMessageProperty smp = this.clientModel.getMsgCodeProperty();

        /* event-handler of the StringProperty to show the latest message on the console */
        smp.newestMsgProperty().addListener((observable, oldVal, newVal) ->
                Platform.runLater(() -> this.gameView.getPlayView().getBottomView().setConsole(newVal)));

        /* handling of all messageTypes via specific handleMsg method */
        smp.addListener((obs, oldVal, newVal) -> {

            switch (newVal.intValue()) {
                case 2: handleGameStartedMsg(); break;
                case 3: handleFirstDealMsg(); break;
                case 4: handleAnnouncedTichuMsg(); break;
                case 5: handleSecondDealMsg(); break;
                case 6: Platform.runLater(() -> ca.getSchupfenBtn().setDisable(false)); break;
                case 7: Platform.runLater(() -> ca.getSchupfenBtn().setDisable(true)); break;
                case 8: handleSchupfenMsg(false); break;
                case 9: handleSchupfenMsg(true); break;
                case 10: handleUpdateMsg(); break;
                case 11: handleGameDoneMsg(); break;
            }
        });

        /* sends GrandTichuMsg to the server */
        ca.getGrandTichuBtn().setOnAction(event ->
                clientModel.sendMessage(new TichuMsg(this.clientModel.getPlayerName(), TichuType.GrandTichu)));

        /* sends SmallTichuMsg to the server */
        ca.getSmallTichuBtn().setOnAction(event ->
                clientModel.sendMessage(new TichuMsg(this.clientModel.getPlayerName(), TichuType.SmallTichu)));

        /* sends SchupfenMsg to the server and removes card out of hand and disables button*/
        ca.getSchupfenBtn().setOnAction(event -> {
            Platform.runLater(() -> ca.getSchupfenBtn().setDisable(true));
            ArrayList<Card> cards = getSelectedCards();
            if (!cards.isEmpty()) {
                this.clientModel.sendMessage(new SchupfenMsg(getTriggeringPlayer(), cards.get(0)));
                this.clientModel.getHand().remove(cards.get(0));
                this.clientModel.getHand().sort();
            } else
                Platform.runLater(() -> ca.getSchupfenBtn().setDisable(false));
        });

        /* sends Tichu or DealMsg depending on MsgCode */
        ca.getPlayBtn().setOnAction(event -> {
            int code = this.clientModel.getMsgCode();
            if (code == 3 || code == 4 || code == 5 || code == 20)
                this.clientModel.sendMessage(new TichuMsg(this.clientModel.getPlayerName(), TichuType.none));

            else {
                ArrayList<Card> newMove = getSelectedCards();

                if (!newMove.isEmpty()) {

                    if (Combination.isValidMove(oldMove, newMove)) {
                        this.clientModel.sendMessage(new PlayMsg(newMove));
                        this.clientModel.getHand().removeCards(newMove);
                        this.clientModel.getHand().sort();
                    }
                } else { // able to pass only in between a trick not at the beginning
                    if (!clientModel.getMsgCodeProperty().getMessage().getLastPlayer().isEmpty())
                        this.clientModel.sendMessage(new PlayMsg(new ArrayList<>()));
                }
            }
        });

        /* setting CardArea on action */
        gameView.getPlayView().getBottomView().getCardArea().setOnMouseClicked(e -> autoValidateMove());

        /* setting MenuItems in LangMenu on action */
        for(MenuItem m : this.gameView.getPlayView().getSettings().getLangMenu().getItems()) {
            m.setOnAction(this::changeTranslator);
        }
    }

    /**
     * updates the points column in the view with the new scores,
     * makes sure no inputs is possible and then wait 5 seconds for UX reasons:
     * user has the chance to see why the Game/Match is done
     * @author Philipp
     */
    private void handleGameDoneMsg() {
        ControlArea ca = this.gameView.getPlayView().getBottomView().getControlArea();
        PlayArea pa = this.gameView.getPlayView().getPlayArea();
        Platform.runLater(() -> {
            pa.updateTotalPoints(clientModel.getOwnScore(), clientModel.getOpponentScore());
            ca.getPlayBtn().setDisable(true);
        });

        try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    /**
     * the first if/else block decides whether the last player played or passed
     * depending on that the view will be updated and the pass counter will be updated
     * the second if/else block decides whether its this player's turn or not and updates the GUI accordingly
     * @author Philipp
     */
    private void handleUpdateMsg() {
        UpdateMsg msg = (UpdateMsg) this.clientModel.getMsgCodeProperty().getMessage();
        String lastPlayer = msg.getLastPlayer();
        ControlArea ca = this.gameView.getPlayView().getBottomView().getControlArea();
        PlayArea pa = this.gameView.getPlayView().getPlayArea();

        autoValidateMove();

        if (!msg.getLastMove().isEmpty()) {
            this.oldMove.clear();
            this.oldMove.addAll(msg.getLastMove()); //TODO - Testing
            this.oldMove.setPlayer(msg.getLastPlayer()); //TODO - Testing
            this.passCounter = 0;
            Platform.runLater(() -> pa.updatePlayedColumn(lastPlayer, msg.getLastMove()));
        } else {
            if (this.passCounter == msg.getRemainingPlayers()-1) {
                Platform.runLater(pa::clearPlayedColumn);
                oldMove.clear();
                this.oldMove.setPlayer("");
                this.passCounter = 0;
            }
            this.passCounter++;
        }

        if (this.clientModel.isMyTurn()) {
            if (this.passCounter == msg.getRemainingPlayers()-1)
                if (clientModel.getPlayerName().equals(oldMove.getPlayer())) //TODO - Testing
                    Platform.runLater(() -> ca.getPlayBtn().setText(translator.getString("controlarea.get")));
            else
                Platform.runLater(() -> ca.getPlayBtn().setText(translator.getString("controlarea.pass")));
            Platform.runLater(() -> ca.getPlayBtn().setDisable(false));
        }
        else
            Platform.runLater(() -> ca.getPlayBtn().setDisable(true));
    }

    /**
     * decides whether all clients are done with pushing and the saved cards can be handed out,
     * or if not and the cards only need to be saved
     * @author Philipp
     * @param finished indicates, if process of pushing is done
     */
    private void handleSchupfenMsg(boolean finished) {
        if (!finished)
            receivedCards.add(this.clientModel.getMsgCodeProperty().getMessage().getCard());
        else { //Thread.sleep() prevents loss of the updateMsg for the last client receiving
            try { Thread.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }
            clientModel.getHand().addCards(receivedCards);
            clientModel.getHand().sort(); //sorting the hand including the new cards
        }
    }

    /**
     * the cards receiving in the second DealMsg have already been set by the ClientModel!
     * initializes the handColumn to prevent ugly resizing in the playArea while playing cards
     * attention: MAX_HANDSIZE is important for the handColumn in the PlayArea
     * decides whether the use did not announced GrandTichu and is now able to announce SmallTichu
     * or announced GrandTichu and needs to wait until all players made their announcements
     * @author Philipp
     */
    private void handleSecondDealMsg() {
        ControlArea ca = this.gameView.getPlayView().getBottomView().getControlArea();
        Platform.runLater(() -> gameView.getPlayView().getPlayArea().initHandColumn(MAX_HANDSIZE));

        clientModel.getHand().sort();

        if (!this.clientModel.announcedGrandTichu()) {//enables Buttons again for further announcements
            Platform.runLater(() -> {
                ca.getSmallTichuBtn().setDisable(false);
                ca.getPlayBtn().setDisable(false);
            });
        } else { //automatically sends GrandTichu msg if the client already announced GrandTichu (DesignDecision)
            try { //Thread.sleep() prevents loss of the AnnouncedTichuMsg for the last client receiving it
                Thread.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }
            this.clientModel.sendMessage(new TichuMsg(clientModel.getPlayerName(), TichuType.GrandTichu));
        }
    }

    /**
     * mainly altering the GUI depending on this player announced or not.
     * changes the instance variable "GrandTichu" in the clientModel if necessary
     * @author Philipp
     */
    private void handleAnnouncedTichuMsg() {
        TichuType tichuType = this.clientModel.getMsgCodeProperty().getMessage().getTichuType();
        ControlArea ca = this.gameView.getPlayView().getBottomView().getControlArea();
        PlayArea pa = this.gameView.getPlayView().getPlayArea();

        if (this.clientModel.getPlayerName().equals(getTriggeringPlayer())) { //if this player announced
            //if i have announced GrandTichu myself
            if (tichuType.equals(TichuType.GrandTichu)) {
                this.clientModel.setGrandTichu(true);
            }
            //update TichuColumn and disable buttons again
            Platform.runLater(() -> {
                pa.updateTichuColumn(getTriggeringPlayer(), tichuType);
                ca.getGrandTichuBtn().setDisable(true);
                ca.getSmallTichuBtn().setDisable(true);
                ca.getPlayBtn().setDisable(true);
            });
        } else //if someone else announced
            Platform.runLater(() -> pa.updateTichuColumn(getTriggeringPlayer(), tichuType));
    }

    /**
     * the cards receiving in the first DealMsg have already been set by the ClientModel!
     * resetting some important variable which have been set in the previous Match and
     * activating the the hand to automatically alter the GUI if cards are played or received
     * altering GUI to make it possible for the user to announce GrandTichu or pass
     * @author Philipp
     */
    private void handleFirstDealMsg() {
        ControlArea ca = this.gameView.getPlayView().getBottomView().getControlArea();
        PlayArea pa = this.gameView.getPlayView().getPlayArea();

        this.receivedCards.clear();
        oldMove.clear();
        this.clientModel.getHand().getCards().addListener(this::activateHand);

        //sets 8 cards and enables Buttons to be able to announce tichu
        this.clientModel.getHand().sort();
        Platform.runLater(() -> {
            stage.setWidth(stage.getWidth()-0.1);
            ca.getPlayBtn().setText(translator.getString("controlarea.pass"));
            ca.getGrandTichuBtn().setDisable(false);
            ca.getPlayBtn().setDisable(false);
            pa.initHandColumn(INIT_HANDSIZE);
            pa.clearTichuColumn();
            pa.clearPlayedColumn();
        });
    }

    /**
     * sets the playerNames of the teamMate and the opponents
     * @author Philipp
     */
    private void handleGameStartedMsg() {
        Platform.runLater(() -> this.gameView.getPlayView().getPlayArea().updateNameColumn());
    }

    /**
     * updating the CardsLabels when something changes in the Hand from the ClientModel and
     * making cards clickable
     * @author Philipp
     * @param obs unused but necessary observable value from the listener
     */
    private void activateHand(Observable obs) {
        Platform.runLater(() -> {
            this.gameView.getPlayView().getBottomView().getCardArea().updateCardLabels();
            this.stage.setWidth(this.stage.getWidth()-0.1);
            makeCardsClickable();
        });
    }

    /**
     * sets all cardLabels in the CardArea on action and toggles its isSelected variable accordingly
     * @author Philipp
     */
    private void makeCardsClickable() {
        HBox cardLabels = this.gameView.getPlayView().getBottomView().getCardArea();

        for (Node cl : cardLabels.getChildren()) {

            cl.setOnMouseClicked(event -> {
                CardLabel clickedLabel = (CardLabel) event.getSource(); //getting the clicked element
                clickedLabel.setSelected(!clickedLabel.isSelected()); //toggle value
                if (!clickedLabel.isSelected()) {
                    clickedLabel.getStyleClass().clear(); //removing all style classes if not selected anymore
                }
            });
        }
    }

    /**
     * if cards are selected the play button changes to play and if not it changes to pass
     * if the combination is valid according to the last move, the selected cards change their
     * border color to green, if not, they change their border color to red
     * @author Philipp
     */
    private void autoValidateMove() {
        ControlArea ca = this.gameView.getPlayView().getBottomView().getControlArea();
        ArrayList<CardLabel> selection = getSelectedCardLabels();

        if (!selection.isEmpty()) { //if at least one card is selected
            Platform.runLater(() -> ca.getPlayBtn().setText(this.translator.getString("controlarea.play")));

            if (Combination.isValidMove(oldMove, getSelectedCards())) {
                for (CardLabel cl : selection) {
                    if (!cl.getStyleClass().contains("validCombination"))
                        cl.getStyleClass().add("validCombination");
                    cl.getStyleClass().remove("invalidCombination");
                }

            } else {
                for (CardLabel cl : selection) {
                    if (!cl.getStyleClass().contains("invalidCombination"))
                        cl.getStyleClass().add("invalidCombination");
                    cl.getStyleClass().remove("validCombination");
                }
            }
        } else { //if no card is selected
            //decide whether all players already passed and the Trick is ready to be claimed or not
            //and alter the GUI accordingly
            if (this.passCounter != clientModel.getMsgCodeProperty().getMessage().getRemainingPlayers()-1)
                Platform.runLater(() -> ca.getPlayBtn().setText(this.translator.getString("controlarea.pass")));
            else
                if (clientModel.getPlayerName().equals(oldMove.getPlayer()))
                    Platform.runLater(() -> ca.getPlayBtn().setText(this.translator.getString("controlarea.get")));
        }
    }

    /**
     * Important: not similar to the following method. This method returns the current selection
     * based on the card objects
     * @author Philipp
     * @return the currently selected cards
     */
    private ArrayList<Card> getSelectedCards() {
        HBox cardLabels = this.gameView.getPlayView().getBottomView().getCardArea();
        ArrayList<Card> selectedCards = new ArrayList<>();

        for (Node cl : cardLabels.getChildren()) {
            CardLabel label = (CardLabel) cl;

            if (label.isSelected()) {
                selectedCards.add(label.getCard());
            }
        }
        return selectedCards;
    }

    /**
     * returns the current selection of the cardLabel objects
     * @author Philipp
     * @return the currently selected cardLabels
     */
    private ArrayList<CardLabel> getSelectedCardLabels() {
        HBox cardLabels = this.gameView.getPlayView().getBottomView().getCardArea();
        ArrayList<CardLabel> selectedCards = new ArrayList<>();

        for (Node cl : cardLabels.getChildren()) {
            CardLabel label = (CardLabel) cl;

            if (label.isSelected()) {
                selectedCards.add(label);
            }
        }
        return selectedCards;
    }

    /**
     * simple service method to avoid repeating ugly calls in the upper service methods
     * @author Philipp
     * @return returns the playerName of the current message
     */
    private String getTriggeringPlayer() {
        return this.clientModel.getMsgCodeProperty().getMessage().getPlayerName();
    }

    /**
     * initialize new a translator object for language change.
     * @author dominik
     * @param event click event on menuItem
     */
    public void changeTranslator(Event event) {
        MenuItem m = (MenuItem) event.getSource();

        if (m.getText().equals(translator.getString("langMenu.german"))) {

            Translator de = new Translator("de");
            ServiceLocator.getServiceLocator().setTranslator(de);

        } else if (m.getText().equals(translator.getString("langMenu.english"))) {

            Translator en = new Translator("en");
            ServiceLocator.getServiceLocator().setTranslator(en);

        } else if (m.getText().equals(translator.getString("langMenu.chinese"))){

            Translator ch = new Translator("ch");
            ServiceLocator.getServiceLocator().setTranslator(ch);
        }
        translator = ServiceLocator.getServiceLocator().getTranslator();
        gameView.getPlayView().update();
        ChatView.getView().update();
        clientModel.updateTranslator();
    }
}