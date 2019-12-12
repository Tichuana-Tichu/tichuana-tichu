package ch.tichuana.tichu.client.chat;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Dominik
 */
public class ChatView extends Stage{

    protected Stage stage;
    private Model model;


    VBox box = new VBox();
    ScrollPane pane = new ScrollPane();
    HBox hbox = new HBox();



    Label lblName = new Label("Name");
    TextField txtName = new TextField();

    TextArea txtChatArea  = new TextArea();

    TextField txtChatMessage = new TextField();
    Button btnSend = new Button("Send");

    public View(Stage stage, Model model){
        this.stage = stage;
        this.model = model;


    }
}
