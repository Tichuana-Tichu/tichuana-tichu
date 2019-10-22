package ch.tichuana.tichu.commons.message;

public class ConnectedMsg extends Message {

	private boolean status;

	public boolean isStatus() {
		return this.status;
	}

	/**
	 * 
	 * @param status
	 */
	public ConnectedMsg(boolean status) {
		this.status = status;
		super.setMsgType(MessageType.ConnectedMsg);
	}

}