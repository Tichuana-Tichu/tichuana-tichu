package ch.tichuana.tichu.client;

import ch.tichuana.tichu.client.view.ServerSelector;
import javafx.application.Application;
import javafx.stage.Stage;

public class ServerSelectorTest extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ServerSelector ss = new ServerSelector();
        ss.show();
    }
}
