package ch.tichuana.tichu.commons.message;

import java.net.Socket;

public abstract class Message {

	/**
	 * 
	 * @param socket
	 */
	public void send(Socket socket) {
		// TODO - implement Message.send
	}

	/**
	 * 
	 * @param socket
	 */
	public static Message receive(Socket socket) {
		// TODO - implement Message.receive
		return null;
	}

}