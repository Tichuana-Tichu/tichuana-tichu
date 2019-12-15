package ch.tichuana.tichu.client.chat;

import ch.tichuana.tichu.client.model.ClientModel;
import ch.tichuana.tichu.commons.message.ChatMsg;
import javafx.event.Event;

public class ChatController {

    private ChatView view;
    private ClientModel model;

    /**
     * @author Dominik
     * @param view
     */
    public ChatController(ChatView view, ClientModel model){
        this.view = view;
        this.model = model;

        view.getBtnSend().setOnAction(event -> sendMessage(model.getPlayerName(), view.getContent()));
        view.getBtnSend().setOnMouseClicked(event -> view.clearText());
    }

    public void sendMessage(String name, String content){
        ChatMsg msg = new ChatMsg(name, content);
        model.sendMessage(msg);
    }
}
