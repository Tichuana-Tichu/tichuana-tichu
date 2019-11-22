package ch.tichuana.tichu.client.controller;

import ch.tichuana.tichu.client.model.ClientModel;
import ch.tichuana.tichu.client.view.CardArea;
import ch.tichuana.tichu.client.view.GameView;
import ch.tichuana.tichu.commons.message.SchupfenMsg;
import ch.tichuana.tichu.commons.message.TichuMsg;
import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.Rank;
import ch.tichuana.tichu.commons.models.TichuType;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

class PlayController {

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
        this.clientModel.getNewestMessageProperty().addListener((observable, oldVal, newVal) ->
                Platform.runLater(() -> this.gameView.getPlayView().getBottomView().setConsole(newVal)));

        /*
        handling of all messageTypes via handleMsg
         */
        this.clientModel.getMsgCodeProperty().addListener(this::handleMsg);

        /*
        event-handler of the GrandTichu Button
         */
        this.gameView.getPlayView().getBottomView().getControlArea().getGrandTichuBtn().setOnAction(event -> {
            //if (!this.clientModel.announcedSmallTichu() || !this.clientModel.announcedGrandTichu()) {
            //    this.clientModel.setGrandTichu(true);
                this.clientModel.sendMessage(new TichuMsg(clientModel.getPlayerName(), TichuType.GrandTichu));
            //}
        });

        /*
        event-handler of the SmallTichu Button
         */
        this.gameView.getPlayView().getBottomView().getControlArea().getSmallTichuBtn().setOnAction(event -> {
            //if (!this.clientModel.announcedSmallTichu() || !this.clientModel.announcedGrandTichu()) {
            //    this.clientModel.setSmallTichu(true);
                this.clientModel.sendMessage(new TichuMsg(clientModel.getPlayerName(), TichuType.SmallTichu));
            //}
        });

        /*
        event-handler of the Schupfen Button
         */
        this.gameView.getPlayView().getBottomView().getControlArea().getSchupfenBtn().setOnAction(event -> {
            //TODO - change to the real card from the game flow!
            //if (this.clientModel.getMsgCode() == 6)
                this.clientModel.sendMessage(new SchupfenMsg(clientModel.getPlayerName(), new Card(Rank.dog)));
        });

        /*
        event-handler of the Play Button
         */
        this.gameView.getPlayView().getBottomView().getControlArea().getPlayBtn().setOnAction(event -> {

            //if (this.clientModel.getMsgCode() == 3 || this.clientModel.getMsgCode() == 4) {
            //if (!this.clientModel.announcedSmallTichu() || !this.clientModel.announcedGrandTichu())
                this.clientModel.sendMessage(new TichuMsg(clientModel.getPlayerName(), TichuType.none));
            //} else {
                //TODO - change to the real cards from the game flow!
               // this.clientModel.sendMessage(new PlayMsg(new ArrayList<Card>()));
            //}
        });

        /*
        disconnects client if stage is closed
         */
        this.gameView.getStage().setOnCloseRequest(event -> this.clientModel.disconnect());
    }

    /**
     *
     * @param obs
     * @param oldVal
     * @param newVal
     */
    private void handleMsg(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {

        switch (newVal.intValue()) {

            case 2://GameStartedMsg

                Platform.runLater(() -> this.gameView.getPlayView().getPlayArea().updateNameColumn());
                break;

            case 3://DealMsg (first 8 cards)

                Platform.runLater(() -> {
                    this.gameView.getPlayView().getBottomView().setCardArea(CardArea.CardAreaType.Cards, 8);
                    this.stage.setWidth(stage.getWidth()-1);
                    this.gameView.getPlayView().getBottomView().getControlArea().getPlayBtn().setText("Passen");
                    this.gameView.getPlayView().getBottomView().getControlArea().getGrandTichuBtn().setDisable(false);
                    this.gameView.getPlayView().getBottomView().getControlArea().getPlayBtn().setDisable(false);
                });
                break;

            case 4://AnnouncedTichuMsg

                String playerName = this.clientModel.getMsgCodeProperty().getMessage().getPlayerName();
                TichuType tichuType = this.clientModel.getMsgCodeProperty().getMessage().getTichuType();

                /*
                if (this.clientModel.getPlayerName().equals(playerName)) {
                    Platform.runLater(() -> {
                        this.gameView.getPlayView().getPlayArea().updateTichuColumn(playerName, tichuType);
                        this.gameView.getPlayView().getBottomView().getControlArea().getGrandTichuBtn().setDisable(true);
                        this.gameView.getPlayView().getBottomView().getControlArea().getSmallTichuBtn().setDisable(true);
                        this.gameView.getPlayView().getBottomView().getControlArea().getPlayBtn().setDisable(true);
                    });
                } else {

                 */
                    Platform.runLater(() -> {
                        this.gameView.getPlayView().getPlayArea().updateTichuColumn(playerName, tichuType);
                    });
                //}
                break;

            case 5://DealMsg (remaining 6 cards)

                int size = this.clientModel.getHand().getCards().size();
                //sets the remaining 6 cards
                Platform.runLater(() -> {
                    this.gameView.getPlayView().getBottomView().setRemainingCards(size);
                    this.stage.setWidth(stage.getWidth()-1);

                    this.gameView.getPlayView().getBottomView().getControlArea().getSmallTichuBtn().setDisable(false);
                    this.gameView.getPlayView().getBottomView().getControlArea().getPlayBtn().setDisable(false);
                });

                /*
                //automatically sends GrandTichu msg
                if (this.clientModel.announcedGrandTichu()) {
                    this.clientModel.sendMessage(new TichuMsg(clientModel.getPlayerName(), TichuType.GrandTichu));
                } else {//or lets the user choose between SmallTichu and none
                    Platform.runLater(() -> {
                        this.gameView.getPlayView().getBottomView().getControlArea().getSmallTichuBtn().setDisable(false);
                        this.gameView.getPlayView().getBottomView().getControlArea().getPlayBtn().setDisable(false);
                    });
                }
                 */
                break;

            case 6://DemandSchupfenMsg

                this.gameView.getPlayView().getBottomView().getControlArea().getSchupfenBtn().setDisable(false);
                break;
        }
    }

    private void checkValidCombination() {
        // TODO - implement ClientController.checkValidCombination
    }
}
