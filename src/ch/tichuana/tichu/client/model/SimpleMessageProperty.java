package ch.tichuana.tichu.client.model;

import ch.tichuana.tichu.commons.message.Message;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SimpleMessageProperty extends SimpleIntegerProperty {

	private SimpleStringProperty newestMsg = new SimpleStringProperty();
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

	//Getter & Setter
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public String getNewestMsg() {
		return newestMsg.get();
	}
	public SimpleStringProperty newestMsgProperty() {
		return newestMsg;
	}
	public void setNewestMsg(String newestMsg) {
		this.newestMsg.set(newestMsg);
	}
}