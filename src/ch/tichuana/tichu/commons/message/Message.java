package ch.tichuana.tichu.commons.message;

import ch.tichuana.tichu.commons.models.TichuType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public abstract class Message {

	private MessageType msgType;

	/**
	 * Sends the message (msg) of this instace through the socket given as a parameter
	 * @author Christian
	 * @param socket
	 */
	public void send(Socket socket) {
		try{
			OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
			out.write(this.toString());
			out.flush();
			socket.shutdownOutput();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Receives a JSON-String from a specified
	 * @param socket
	 */
	public static Message receive(Socket socket) {
		String response = null;
		try{
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			response = in.readLine();
		} catch (Exception e){
			e.printStackTrace();
		}
		JSONParser parser = new JSONParser();
		JSONObject message = null;
		try {
			message = (JSONObject) parser.parse(response.toString());
		} catch (Exception e){
			e.printStackTrace();
		}


		return parseMessage(message);

	}

	/**
	 * Parses a JSON-Message and returns a Message-object of the corresponding Message class
	 * @author Christian
	 * @param json
	 * @return Message
	 */
	public static Message parseMessage(JSONObject json){
		String playerName;
		String password;

		MessageType messageType = MessageType.valueOf((String) json.get("msg"));
		Message newMessage = null;
		switch (messageType){
			case AnnouncedTichuMsg:
				// TODO: implement AnnouncedTichuMsg
				break;

			case JoinMsg:
				playerName = (String) json.get("playerName");
				password = (String) json.get("password");
				newMessage = new JoinMsg(playerName,password);
				break;

			case CreatePlayerMsg:
				playerName = (String) json.get("playerName");
				password = (String) json.get("password");
				newMessage = new CreatePlayerMsg(playerName,password);
				break;

			case ConnectedMsg:
				String status = (String) json.get("status");
				newMessage = new ConnectedMsg(Boolean.parseBoolean(status));
				break;

			case GameStartedMsg:
				break;
			case DealMsg:
				break;
			case ReceivedMsg:
				break;
			case DemandTichuMsg:
				break;
			case TichuMsg:
				break;
			case DemandSchupfenMsg:
				playerName = (String) json.get("playerName");
				newMessage = new DemandSchupfenMsg(playerName);
				break;

			case SchupfenMsg:
				break;
			case PlayMsg:
				break;
			case UpdateMsg:
				break;
		}
		return newMessage;
	}

	public MessageType getMsgType(){
		return this.msgType;
	}

	public void setMsgType(MessageType msgType) {
		this.msgType = msgType;
	}


	public String getPlayerName(){
		return null;
	}
	public String getPassword(){
		return null;
	}
	public boolean getStatus(){
		return false;
	}

}