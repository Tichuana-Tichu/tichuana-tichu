package ch.tichuana.tichu.client.controller;

import ch.tichuana.tichu.client.model.ClientModel;
import ch.tichuana.tichu.client.view.CardArea;
import ch.tichuana.tichu.client.view.GameView;
import ch.tichuana.tichu.commons.message.MessageType;
import ch.tichuana.tichu.commons.message.PlayMsg;
import ch.tichuana.tichu.commons.message.SchupfenMsg;
import ch.tichuana.tichu.commons.message.TichuMsg;
import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.Rank;
import ch.tichuana.tichu.commons.models.TichuType;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class PlayController {

    private ClientModel clientModel;
    private GameView gameView;
    private Stage stage;

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
        this.clientModel = clientModel;
        this.gameView = gameView;
        this.stage = stage;

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

        this.clientModel.getNewestMessageProperty().addListener((observable, oldVal, newVal) ->
                Platform.runLater(() -> this.gameView.getPlayView().getBottomView().setConsole(newVal)));

        this.clientModel.getMsgCodeProperty().addListener(this::handleMsg);

        this.gameView.getPlayView().getBottomView().getControlArea().getGrandTichuBtn().setOnAction(event -> {
            if (!this.clientModel.announcedGrandTichu()) {
                this.clientModel.setGrandTichu(true);
                this.clientModel.sendMessage(new TichuMsg(clientModel.getPlayerName(), TichuType.GrandTichu));
            }
        });

        this.gameView.getPlayView().getBottomView().getControlArea().getSmallTichuBtn().setOnAction(event -> {
            if (!this.clientModel.announcedSmallTichu()) {
                this.clientModel.setSmallTichu(true);
                this.clientModel.sendMessage(new TichuMsg(clientModel.getPlayerName(), TichuType.SmallTichu));
            }
        });

        this.gameView.getPlayView().getBottomView().getControlArea().getSchupfenBtn().setOnAction(event -> {
            //TODO - change to the real card from the game flow!
            if (this.clientModel.getMsgCode() == 6)
                this.clientModel.sendMessage(new SchupfenMsg(clientModel.getPlayerName(), new Card(Rank.dog)));
        });

        this.gameView.getPlayView().getBottomView().getControlArea().getPlayBtn().setOnAction(event -> {

            //if (this.clientModel.getMsgCode() == 3 || this.clientModel.getMsgCode() == 4) {
                this.clientModel.sendMessage(new TichuMsg(clientModel.getPlayerName(), TichuType.none));
            //} else {
                //TODO - change to the real cards from the game flow!
               // this.clientModel.sendMessage(new PlayMsg(new ArrayList<Card>()));
            //}
        });

        this.gameView.getStage().setOnCloseRequest(event -> this.clientModel.disconnect());
    }

    private void handleMsg(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {

        switch (newVal.intValue()) {

            case 2://GameStartedMsg
                Platform.runLater(() -> this.gameView.getPlayView().getPlayArea().updateNameColumn());
                break;

            case 3://DealMsg (first 8 cards)
                Platform.runLater(() -> {
                    this.gameView.getPlayView().getBottomView().setCardArea(CardArea.CardAreaType.Cards, 8);
                    this.stage.setWidth(stage.getWidth()-1);
                });
                break;

            case 4://AnnouncedTichuMsg
                String playerName = this.clientModel.getMsgCodeProperty().getMessage().getPlayerName();
                TichuType tichuType = this.clientModel.getMsgCodeProperty().getMessage().getTichuType();
                Platform.runLater(() ->
                    this.gameView.getPlayView().getPlayArea().updateTichuColumn(playerName, tichuType));
                break;

            case 5://DealMsg (remaining 6 cards)
                //this.clientModel.setMsgCode(4);
                Platform.runLater(() -> {
                    this.gameView.getPlayView().getBottomView().setRemainingCards(this.clientModel.getHand().getCards().size());
                    this.stage.setWidth(stage.getWidth()-1);
                });

            case 6://DemandSchupfenMsg

                break;
        }
    }

    private void checkValidCombination() {
        // TODO - implement ClientController.checkValidCombination
    }
}
