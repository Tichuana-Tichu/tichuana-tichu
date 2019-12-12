package ch.tichuana.tichu.client.chat;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Dominik
 */
public class ChatView extends Stage{

    private VBox scrollBox;
    private Button btnSend;
    private TextField txt;
    private static ChatView view;

    public ChatView(){

        //VBox as main overlay
        VBox box = new VBox();

        //ScrollPane for Messages
        ScrollPane pane = new ScrollPane();

        //Vbox for showing seperate Messages in Scrollpane
        scrollBox = new VBox();
        scrollBox.getChildren().add(new Label("test"));
        pane.setContent(scrollBox);


        //HBox for TextArea and Send Button
        HBox bottomBox = new HBox();
        txt = new TextField();
        btnSend = new Button("Send");
        bottomBox.getChildren().addAll(txt, btnSend);

        box.getChildren().addAll(pane,bottomBox);

        Scene scene = new Scene(box);
        this.setScene(scene);

    }

    public void addMessage(String name, String content){
        MessageView view = new MessageView(name, content);
        this.scrollBox.getChildren().add(view);
    }

    public Button getBtnSend() {
        return btnSend;
    }

    public String getContent(){
        return txt.getText();
    }

    public static ChatView getView(){
        if(view == null)
            view = new ChatView();
        return view;
    }
}
