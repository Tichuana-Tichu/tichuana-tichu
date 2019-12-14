package ch.tichuana.tichu.server.model;

import ch.tichuana.tichu.commons.message.Message;
import javafx.beans.property.SimpleBooleanProperty;

public class SimpleMessageProperty extends SimpleBooleanProperty {

    private Message message;
    private Player player;

    /**
     * SimpleMessageProperty replaces SimpleBooleanProperty in cases where the controller needs
     * to know what the message was or who sent it.
     * Simple concept, big effect
     * @author Christian
     */
    protected SimpleMessageProperty(boolean value){
        super(value);
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
