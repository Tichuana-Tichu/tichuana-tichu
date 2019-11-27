package ch.tichuana.tichu.client.model;

import ch.tichuana.tichu.commons.message.Message;
import javafx.beans.property.SimpleIntegerProperty;

public class SimpleMessageProperty extends SimpleIntegerProperty {

	private Message message;

	/**
	 * SimpleMessageProperty replaces SimpleIntegerProperty in cases where the controller needs
	 * to know what the message was or who sent it.
	 * Simple concept, big effect
	 * @author Philipp
	 */
	public SimpleMessageProperty(){
		super();
	}

	public SimpleMessageProperty(int value){
		super(value);
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
}