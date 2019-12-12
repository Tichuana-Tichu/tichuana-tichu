package ch.tichuana.tichu.client.chat;

public class ChatController {
    private ChatModel model;
    private ChatView view;

    /**
     * @author Dominik
     * @param model
     * @param view
     */
    public ChatController(ChatModel model, ChatView view){
        this.model = model;
        this.view = view;

        view.btnSend.setOnAction(event -> model.sendMessage(view.txtChatMessage.getText()));

        model.newestMessage.addListener((o, oldValue, newValue) -> {
            if(!newValue.isEmpty())
                view.txtChatMessage.appendText(newValue + "\n");
        });
    }
}
