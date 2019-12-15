package ch.tichuana.tichu.commons.test;

import ch.tichuana.tichu.client.view.Tutorial;
import javafx.application.Application;
import javafx.stage.Stage;

public class TutorialTest extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        Tutorial tutorial = Tutorial.getTutorial();
        tutorial.show();
    }
}
