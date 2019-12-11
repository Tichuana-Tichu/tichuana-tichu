package ch.tichuana.tichu.client.controller;

import ch.tichuana.tichu.client.model.ClientModel;
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
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.ArrayList;

class PlayController {

    private ClientModel clientModel;
    private GameView gameView;
    private Stage stage;
    private ArrayList<Card> receivedCards = new ArrayList<>();
    private static ArrayList<Card> oldMove = new ArrayList<>();
    private Translator translator;
    private boolean firstRound = true;
    private int pushCounter = 1;
    private int passCounter = 0;

    /**
     * attaches listener to the stage-width to make the CardArea responsive
     * updates TextField (console) on incoming Messages from the server
     * sets Buttons on action, to send Messages to server when being clicked
     * disconnects client from server when the windows is closed
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
                case 6: handleDemandSchupfenMsg(); break;
                case 7: Platform.runLater(() -> ca.getSchupfenBtn().setDisable(true)); break;
                case 8: handleSchupfenMsg(false); break;
                case 9: handleSchupfenMsg(true); break;
                case 10: handleUpdateMsg(); break;
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
                this.clientModel.sendMessage(new SchupfenMsg(getPlayerName(), cards.get(0)));
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

        /* disconnects client if stage is closed */
        this.gameView.getStage().setOnCloseRequest(event -> this.clientModel.disconnect());
    }

    /**
     *
     * @author Philipp
     */
    private void handleUpdateMsg() {
        this.pushCounter = 1;
        UpdateMsg msg = (UpdateMsg) this.clientModel.getMsgCodeProperty().getMessage();
        String lastPlayer = msg.getLastPlayer();
        ControlArea ca = this.gameView.getPlayView().getBottomView().getControlArea();
        PlayArea pa = this.gameView.getPlayView().getPlayArea();

        Platform.runLater(() -> pa.updateTotalPoints(clientModel.getOwnScore(), clientModel.getOpponentScore()));

        if (!msg.getLastMove().isEmpty()) {
            oldMove = this.clientModel.getMsgCodeProperty().getMessage().getLastMove();
            this.passCounter = 0;
            Platform.runLater(() -> pa.updatePlayedColumn(lastPlayer, msg.getLastMove()));
        } else {
            if (this.passCounter == msg.getRemainingPlayers()-1) {
                Platform.runLater(pa::clearPlayedColumn);
                oldMove.clear();
                this.passCounter = 0;
            }
            this.passCounter++;
        }

        if (this.clientModel.isMyTurn())
            Platform.runLater(() -> ca.getPlayBtn().setDisable(false));

        else
            Platform.runLater(() -> ca.getPlayBtn().setDisable(true));
    }

    /**
     *
     * @author Philipp
     */
    private void handleSchupfenMsg(boolean finished) {

        if (!finished)
            receivedCards.add(this.clientModel.getMsgCodeProperty().getMessage().getCard());
        else {
            try { Thread.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }
            clientModel.getHand().addCards(receivedCards);
            clientModel.getHand().sort();
        }
    }

    /**
     *
     * @author Philipp
     */
    private void handleDemandSchupfenMsg() {
        ControlArea ca = this.gameView.getPlayView().getBottomView().getControlArea();

        if (this.pushCounter == 1)
            Platform.runLater(() -> ca.getPlayBtn().setText(this.translator.getString("controlarea.play")));

        this.pushCounter++;
        Platform.runLater(() -> ca.getSchupfenBtn().setDisable(false));
    }

    /**
     *
     * @author Philipp
     */
    private void handleSecondDealMsg() {
        ControlArea ca = this.gameView.getPlayView().getBottomView().getControlArea();

        Platform.runLater(() ->
                gameView.getPlayView().getPlayArea().initHandColumn( 14));

        //enables Buttons again to announce SmallTichu or none
        clientModel.getHand().sort();
        if (!this.clientModel.announcedGrandTichu()) {
            Platform.runLater(() -> {
                ca.getSmallTichuBtn().setDisable(false);
                ca.getPlayBtn().setDisable(false);
            });
            //automatically sends GrandTichu msg
        } else {
            try {
                Thread.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }
            this.clientModel.sendMessage(new TichuMsg(clientModel.getPlayerName(), TichuType.GrandTichu));
        }
    }

    /**
     *
     * @author Philipp
     */
    private void handleAnnouncedTichuMsg() {
        //gets the tichuType of the current message
        TichuType tichuType = this.clientModel.getMsgCodeProperty().getMessage().getTichuType();
        ControlArea ca = this.gameView.getPlayView().getBottomView().getControlArea();
        PlayArea pa = this.gameView.getPlayView().getPlayArea();

        //if i have announced Tichu myself
        if (this.clientModel.getPlayerName().equals(getPlayerName())) {
            //if i have announced GrandTichu myself
            if (tichuType.equals(TichuType.GrandTichu)) {
                this.clientModel.setGrandTichu(true);
            }
            //update TichuColumn and disable buttons again
            Platform.runLater(() -> {
                pa.updateTichuColumn(getPlayerName(), tichuType);
                ca.getGrandTichuBtn().setDisable(true);
                ca.getSmallTichuBtn().setDisable(true);
                ca.getPlayBtn().setDisable(true);
            });
        } else //just altering the TichuColumn if someone else announced tichu
            Platform.runLater(() -> pa.updateTichuColumn(getPlayerName(), tichuType));
    }

    /**
     *
     * @author Philipp
     */
    private void handleFirstDealMsg() {

        if (!this.firstRound) {
            this.clientModel.getMsgCodeProperty().setNewestMsg(translator.getString("matchWon"));
            Platform.runLater(() ->
                    gameView.getPlayView().getBottomView().getControlArea().getPlayBtn().setDisable(true));
            try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        this.firstRound = false;

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
            pa.initHandColumn(8);
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
     *
     * @author Philipp
     * @param obs observable value from the listener
     */
    private void activateHand(Observable obs) {
        Platform.runLater(() -> {
            this.gameView.getPlayView().getBottomView().getCardArea().updateCardLabels();
            this.stage.setWidth(this.stage.getWidth()-0.1);
            makeCardsClickable();
        });
    }

    /**
     *
     * @author Philipp
     */
    private void makeCardsClickable() {

        HBox cardLabels = this.gameView.getPlayView().getBottomView().getCardArea();

        for (Node cl : cardLabels.getChildren()) {

            cl.setOnMouseClicked(event -> {
                CardLabel clickedLabel = (CardLabel) event.getSource();
                if (clickedLabel.getStyleClass().removeIf(s -> s.equals("clickedLabel")))
                    clickedLabel.getStyleClass().remove("clickedLabel");

                else
                    clickedLabel.getStyleClass().add("clickedLabel");
                clickedLabel.setSelected(!clickedLabel.isSelected());
            });
        }
    }

    /**
     *
     * @author Philipp
     * @return returns the currently selected CardLabels
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
     * @author Philipp
     * @return returns the playerName of the current message
     */
    private String getPlayerName() {
        return this.clientModel.getMsgCodeProperty().getMessage().getPlayerName();
    }
}