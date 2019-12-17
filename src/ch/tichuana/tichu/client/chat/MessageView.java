package ch.tichuana.tichu.client.chat;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class MessageView extends HBox {

    public MessageView(String name, String content){
        String punctuation = name+":";
        Label name1 = new Label(punctuation);
        Label text = new Label(content);
        text.setWrapText(true);
        text.setPrefWidth(400);
        name1.setPrefWidth(75);
        name1.getStyleClass().add("green");

        this.getChildren().addAll(name1, text);
    }
}
