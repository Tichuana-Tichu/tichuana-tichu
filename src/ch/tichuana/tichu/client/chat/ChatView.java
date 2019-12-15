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
        box.setPrefHeight(500);
        box.setPrefWidth(500);
        box.getStyleClass().add("chat");

        //ScrollPane for Messages
        ScrollPane pane = new ScrollPane();
        pane.setPrefSize(500, 400);
        pane.getStyleClass().add("chat");



        //Vbox for showing seperate Messages in Scrollpane
        scrollBox = new VBox();
        pane.setContent(scrollBox);


        //HBox for TextArea and Send Button
        HBox bottomBox = new HBox();
        txt = new TextField();
        txt.setPrefSize(400, 90);
        btnSend = new Button("Send");
        btnSend.setPrefSize(100, 90);
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

    public void clearText(){
        txt.clear();
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
