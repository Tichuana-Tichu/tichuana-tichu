package ch.tichuana.tichu.client.controller;

import ch.tichuana.tichu.client.model.ClientModel;
import ch.tichuana.tichu.client.view.GameView;
import ch.tichuana.tichu.commons.message.MessageType;
import ch.tichuana.tichu.commons.models.TichuType;
import javafx.application.Platform;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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
    public PlayController(ClientModel clientModel, GameView gameView, Stage stage) {
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

        this.gameView.getPlayView().getBottomView().getControlArea().getGrandTichuBtn().setOnAction(event -> {
            this.clientModel.sendMessage(TichuType.GrandTichu);
            this.clientModel.getHisTurnProperty().set(true);
        });

        this.gameView.getPlayView().getBottomView().getControlArea().getSmallTichuBtn().setOnAction(event -> {
            this.clientModel.sendMessage(TichuType.SmallTichu);
        });

        this.gameView.getPlayView().getBottomView().getControlArea().getSchupfenBtn().setOnAction(event -> {
            this.clientModel.sendMessage(MessageType.SchupfenMsg, "");
        });

        this.gameView.getPlayView().getBottomView().getControlArea().getPlayBtn().setOnAction(event -> {
            this.clientModel.sendMessage(MessageType.PlayMsg, "");
        });

        this.clientModel.getHisTurnProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                this.gameView.getPlayView().getPlayArea().updatePlayedColumn();
            }
        });

        this.gameView.getStage().setOnCloseRequest(event -> this.clientModel.disconnect());
    }

    private void checkValidCombination() {
        // TODO - implement ClientController.checkValidCombination
    }
}
