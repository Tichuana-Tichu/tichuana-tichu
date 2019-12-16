package ch.tichuana.tichu.client.chat;

import ch.tichuana.tichu.client.model.ClientModel;
import ch.tichuana.tichu.commons.message.ChatMsg;

public class ChatController {

    private ClientModel model;

    /**
     * @author Dominik
     * @param view following MVC pattern
     */
    public ChatController(ChatView view, ClientModel model){
        this.model = model;

        view.getBtnSend().setOnAction(event -> {
            sendMessage(model.getPlayerName(), view.getContent());
            view.clearText();
        });


    }

    public void sendMessage(String name, String content){
        ChatMsg msg = new ChatMsg(name, content);
        model.sendMessage(msg);
    }
}
