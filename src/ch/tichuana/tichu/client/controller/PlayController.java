package ch.tichuana.tichu.client.controller;

import ch.tichuana.tichu.client.model.ClientModel;
import ch.tichuana.tichu.client.services.ServiceLocator;
import ch.tichuana.tichu.client.services.Translator;
import ch.tichuana.tichu.client.view.CardLabel;
import ch.tichuana.tichu.client.view.GameView;
import ch.tichuana.tichu.commons.message.DealMsg;
import ch.tichuana.tichu.commons.message.SchupfenMsg;
import ch.tichuana.tichu.commons.message.TichuMsg;
import ch.tichuana.tichu.commons.message.UpdateMsg;
import ch.tichuana.tichu.commons.models.Card;
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
    private Translator translator;

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

        /*
        computation of the negative spacing related to the stage size
         */
        this.stage.widthProperty().addListener((observable, oldVal, newVal) -> {

            HBox cardLabels = this.gameView.getPlayView().getBottomView().getCardArea().getCardsLabels();

            if (oldVal.intValue() < newVal.intValue()) { //screen gets bigger
                cardLabels.setSpacing(((this.stage.getWidth() - (newVal.floatValue() - this.stage.getWidth())) / 15)
                        - this.stage.getMinWidth() / 9);
            } else { //screen get smaller
                cardLabels.setSpacing(((this.stage.getWidth() - (this.stage.getWidth() - newVal.floatValue())) / 15)
                        - this.stage.getMinWidth() / 9);
            }
        });

        /*
        event-handler of the StringProperty to show the latest message on the console
         */
        this.clientModel.getMsgCodeProperty().newestMsgProperty().addListener((observable, oldVal, newVal) ->
                Platform.runLater(() -> this.gameView.getPlayView().getBottomView().setConsole(newVal)));

        /*
        handling of all messageTypes via specific handleMsg method
         */
        this.clientModel.getMsgCodeProperty().addListener((obs, oldVal, newVal) -> {

            switch (newVal.intValue()) {
                case 2: handleGameStartedMsg(); break;
                case 3: handleFirstDealMsg(); break;
                case 4: handleAnnouncedTichuMsg(); break;
                case 5: handleSecondDealMsg(); break;
                case 6: handleDemandSchupfenMsg(); break;
                case 7: Platform.runLater(() ->
                        this.gameView.getPlayView().getBottomView().getControlArea().getSchupfenBtn().setDisable(true));
                        break;
                case 8: handleSchupfenMsg(false); break;
                case 9: handleSchupfenMsg(true); break;
                case 10: handleUpdateMsg(); break;
            }
        });

        /*
        event-handler of the GrandTichu Button
         */
        this.gameView.getPlayView().getBottomView().getControlArea().getGrandTichuBtn().setOnAction(event -> {
            this.clientModel.sendMessage(new TichuMsg(clientModel.getPlayerName(), TichuType.GrandTichu));
        });

        /*
        event-handler of the SmallTichu Button
         */
        this.gameView.getPlayView().getBottomView().getControlArea().getSmallTichuBtn().setOnAction(event -> {
            this.clientModel.sendMessage(new TichuMsg(clientModel.getPlayerName(), TichuType.SmallTichu));
        });

        /*
        event-handler of the Schupfen Button
         */
        this.gameView.getPlayView().getBottomView().getControlArea().getSchupfenBtn().setOnAction(event -> {

            ArrayList<Card> cards = getSelectedCards();
            String player = this.clientModel.getMsgCodeProperty().getMessage().getPlayerName();
            this.clientModel.sendMessage(new SchupfenMsg(player, cards.get(0)));
            this.clientModel.getHand().remove(cards.get(0));
            this.clientModel.getHand().sort();
        });

        /*
        event-handler of the multi-used Play/Pass Button
         */
        this.gameView.getPlayView().getBottomView().getControlArea().getPlayBtn().setOnAction(event -> {

            int code = this.clientModel.getMsgCode();
            if (code == 3 || code == 4 || code == 5 || code == 20)
                this.clientModel.sendMessage(new TichuMsg(this.clientModel.getPlayerName(), TichuType.none));
            else {
                ArrayList<Card> cards = getSelectedCards();
                this.clientModel.sendMessage(new DealMsg(cards));
                this.clientModel.getHand().sort();
            }
        });

        /*
        disconnects client if stage is closed
         */
        this.gameView.getStage().setOnCloseRequest(event -> this.clientModel.disconnect());
    }

    /**
     *
     * @author Philipp
     */
    private void handleUpdateMsg() {
        if (this.clientModel.isMyTurn()) {
            Platform.runLater(() ->
                this.gameView.getPlayView().getBottomView().getControlArea().getPlayBtn().setDisable(false));
        } else {
            Platform.runLater(() ->
                    this.gameView.getPlayView().getBottomView().getControlArea().getPlayBtn().setDisable(true));
        }
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
            Platform.runLater(() ->
                this.gameView.getPlayView().getBottomView().getControlArea().getSchupfenBtn().setDisable(true));

        }
    }

    /**
     *
     * @author Philipp
     */
    private void handleDemandSchupfenMsg() {
        Platform.runLater(() -> {
            this.gameView.getPlayView().getBottomView().getControlArea().getSchupfenBtn().setDisable(false);
            this.gameView.getPlayView().getBottomView().getControlArea().getPlayBtn()
                    .setText(this.translator.getString("controlarea.play"));
        });
    }

    /**
     *
     * @author Philipp
     */
    private void handleSecondDealMsg() {
        //enables Buttons again to announce SmallTichu or none
        clientModel.getHand().sort();
        if (!this.clientModel.announcedGrandTichu()) {
            Platform.runLater(() -> {
                this.gameView.getPlayView().getBottomView().getControlArea().getSmallTichuBtn().setDisable(false);
                this.gameView.getPlayView().getBottomView().getControlArea().getPlayBtn().setDisable(false);
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
        //gets the playerName and the tichuType of the current message
        String playerName = this.clientModel.getMsgCodeProperty().getMessage().getPlayerName();
        TichuType tichuType = this.clientModel.getMsgCodeProperty().getMessage().getTichuType();

        //if i have announced Tichu myself
        if (this.clientModel.getPlayerName().equals(playerName) ) {
            //if i have announced GrandTichu myself
            if (tichuType.equals(TichuType.GrandTichu)) {
                this.clientModel.setGrandTichu(true);
            }
            //update TichuColumn and disable buttons again
            Platform.runLater(() -> {
                this.gameView.getPlayView().getPlayArea().updateTichuColumn(playerName, tichuType);
                this.gameView.getPlayView().getBottomView().getControlArea().getGrandTichuBtn().setDisable(true);
                this.gameView.getPlayView().getBottomView().getControlArea().getSmallTichuBtn().setDisable(true);
                this.gameView.getPlayView().getBottomView().getControlArea().getPlayBtn().setDisable(true);
            });
            //if someone else announced Tichu
        } else {
            //just altering the TichuColumn
            Platform.runLater(() -> {
                this.gameView.getPlayView().getPlayArea().updateTichuColumn(playerName, tichuType);
            });
        }
    }

    /**
     *
     * @author Philipp
     */
    private void handleFirstDealMsg() {
        this.clientModel.getHand().getCards().addListener(this::activateHand);

        //sets 8 cards and enables Buttons to be able to announce tichu
        this.clientModel.getHand().sort();
        Platform.runLater(() -> {
            this.stage.setWidth(stage.getWidth()-0.1);
            this.gameView.getPlayView().getBottomView().getControlArea().getPlayBtn()
                    .setText(translator.getString("controlarea.pass"));
            this.gameView.getPlayView().getBottomView().getControlArea().getGrandTichuBtn().setDisable(false);
            this.gameView.getPlayView().getBottomView().getControlArea().getPlayBtn().setDisable(false);
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
     * @param observable
     */
    private void activateHand(Observable observable) {
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

        HBox cardLabels = this.gameView.getPlayView().getBottomView().getCardArea().getCardsLabels();

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
     * @return
     */
    private ArrayList<Card> getSelectedCards() {
        HBox cardLabels = this.gameView.getPlayView().getBottomView().getCardArea().getCardsLabels();
        ArrayList<Card> selectedCards = new ArrayList<Card>();

        for (Node cl : cardLabels.getChildren()) {
            CardLabel label = (CardLabel) cl;

            if (label.isSelected()) {
                selectedCards.add(label.getCard());
            }
        }
        return selectedCards;
    }
}