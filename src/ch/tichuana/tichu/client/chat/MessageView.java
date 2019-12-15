package ch.tichuana.tichu.client.chat;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;



public class MessageView extends HBox {
   private Label name;
   private Label text;

    public MessageView(String name, String content){
        this.name = new Label(name+":");
        this.text = new Label(content);
        this.text.setWrapText(true);
        this.text.setPrefWidth(400);
        this.name.setPrefWidth(100);
        this.name.getStyleClass().add("green");

        this.getChildren().addAll(this.name, this.text);
    }
}
