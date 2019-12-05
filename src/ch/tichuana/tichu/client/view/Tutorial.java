package ch.tichuana.tichu.client.view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Tutorial extends Stage {

    private VBox root;
    private TabPane tabPane;
    private static Tutorial tutorial;

    private Tutorial(){

        // root stage
        root = new VBox();

        //
        tabPane = new TabPane();

        Tab rules = new Tab("Rules");
        Button button = new Button("next");
        rules.setContent(button);
        rules.setClosable(false);

        Tab combinations = new Tab("Valid Moves");
        combinations.setContent(new Label("Rules klasdfj klajsdflkj laksdfjlk asdfjlköas jfölkasj flösakjfölkasj fölasjfklö adsfj asdl fladsö flads fjasdjfö asdf adsf ads f"));
        combinations.setClosable(false);

        SelectionModel<Tab> select = tabPane.getSelectionModel();

        button.setOnAction(e -> {select.select(combinations);});

        tabPane.getTabs().addAll(rules,combinations);




        Scene scene = new Scene(tabPane);
        this.setScene(scene);

    }

    /**
     * factory method to return singleton Tutorial. We only ever need one instance
     * @return
     */
    public static Tutorial getTutorial(){
        if (tutorial == null){
            tutorial = new Tutorial();
        }
        return tutorial;
    }
}
