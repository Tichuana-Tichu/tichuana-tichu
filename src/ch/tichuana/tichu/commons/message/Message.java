package ch.tichuana.tichu.commons.message;

import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.TichuType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

public abstract class Message {

	private static Logger logger = Logger.getLogger("");
	private MessageType msgType;

	/**
	 * Sends the message (msg) of this instace through the socket given as a parameter
	 * @author Christian
	 * @param socket socket to send message through
	 */
	public void send(Socket socket) {
		OutputStreamWriter out;
		try {
			out = new OutputStreamWriter(socket.getOutputStream());
			out.write(this.toString()+"\n");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Receives a JSON-String from a specified
	 * @author Christian
	 * @param socket socket to receive message through
	 */
	public static Message receive(Socket socket) {
		BufferedReader in;
		String response = null;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			try {
				response = in.readLine();
			} catch (SocketException e) {
				logger.warning("disconnected");
			}
		} catch (IOException e){
			e.printStackTrace();
		}
		JSONParser parser = new JSONParser();
		JSONObject message = null;

		if (response != null) {
			try {
				message = (JSONObject) parser.parse(response);
			} catch (Exception e){
				e.printStackTrace();
			}
			assert message != null;
			return parseMessage(message);
		}
		return null;
	}

	/**
	 * Parses a JSON-Message and returns a Message-object of the corresponding Message class
	 * @author Christian
	 * @param json json object as received
	 * @return Message
	 */
	public static Message parseMessage(JSONObject json){
		String playerName, password, status;
		TichuType tichuType;
		JSONArray array;
		ArrayList cards;


		MessageType messageType = MessageType.valueOf((String) json.get("msg"));
		Message newMessage = null;

		switch (messageType){

			case AnnouncedTichuMsg:
				tichuType = TichuType.valueOf((String) json.get("tichuType"));
				playerName = (String) json.get("player");
				newMessage = new AnnouncedTichuMsg(playerName, tichuType);
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
				status = (String) json.get("status");
				newMessage = new ConnectedMsg(Boolean.parseBoolean(status));
				break;

			case GameStartedMsg:
				playerName = (String) json.get("teamMate");
				array = (JSONArray) json.get("opponents");
				String[] opponents = new String[2];
				opponents[0] = (String) array.get(0);
				opponents[1] = (String) array.get(1);
				newMessage = new GameStartedMsg(playerName, opponents);
				break;

			case DealMsg:
				cards = new ArrayList();
				array = (JSONArray) json.get("cards");
				for (Object o : array) {
					cards.add(Card.cardFactory((JSONObject) o));
				}
				newMessage = new DealMsg(cards);
				break;

			case ReceivedMsg:
				status = (String) json.get("status");
				newMessage = new ReceivedMsg(Boolean.parseBoolean(status));
				break;

			case DemandTichuMsg:
				tichuType = TichuType.valueOf((String) json.get("tichuType"));
				newMessage = new DemandTichuMsg(tichuType);
				break;

			case TichuMsg:
				playerName = (String) json.get("playerName");
				tichuType = TichuType.valueOf((String) json.get("tichuType"));
				newMessage = new TichuMsg(playerName,tichuType);
				break;

			case DemandSchupfenMsg:
				playerName = (String) json.get("playerName");
				newMessage = new DemandSchupfenMsg(playerName);
				break;

			case SchupfenMsg:
				playerName = (String) json.get("playerName");
				Card card = Card.cardFactory((JSONObject) json.get("card"));
				newMessage = new SchupfenMsg(playerName,card);
				break;

			case PlayMsg:
				cards= new ArrayList();
				array = (JSONArray) json.get("cards");
				for (Object o : array) {
					cards.add(Card.cardFactory((JSONObject) o));
				}
				newMessage = new PlayMsg(cards);
				break;

			case UpdateMsg:
				cards= new ArrayList();
				array = (JSONArray) json.get("lastMove");
				for (Object o : array) {
					cards.add(Card.cardFactory((JSONObject) o));
				}
				int opponentScore = convertToInt(json.get("opponentScore"));
				int ownScore = convertToInt(json.get("ownScore"));
				playerName = (String) json.get("nextPlayer");
				String lastPlayer = (String) json.get("lastPlayer");

				JSONArray playerArray = (JSONArray) json.get("remainingCards");
				int[] remainingCards = new int[4];
				String[] playerNames = new String[4];
				Iterator iterator5 = playerArray.iterator();
				int counter = 0;
				while (iterator5.hasNext()){
					JSONObject player = (JSONObject) iterator5.next();
					playerNames[counter] = (String) player.get("name");
					remainingCards[counter] = convertToInt(player.get("number"));
					counter++;
				}

				newMessage = new UpdateMsg(playerName,lastPlayer, cards,opponentScore,ownScore,playerNames,remainingCards);
				break;

			case GameDoneMsg:
				int opponent = convertToInt(json.get("opponentScore"));
				int own = convertToInt(json.get("ownScore"));
				boolean done = (Boolean) json.get("done");
				newMessage = new GameDoneMsg(own,opponent,done);

			case ChatMsg:
				playerName = (String) json.get("playerName");
				String content = (String) json.get("content");
				newMessage = new ChatMsg(playerName ,content);
				break;

		}
		return newMessage;
	}

	/**
	 * fixes weird issue with SimpleJSON library, on different Java versions it seems to store numbers into the hashmap
	 * as integers or as long. Same code returned int on java 9 and long on java 10.
	 * @param o Object to convert to int
	 * @return int
	 */
	private static int convertToInt(Object o){
		if (o instanceof Integer) {
			return (int) o;
		} else if (o instanceof Long){
			return (int) (long) o;
		} else {
			return 0;
		}
	}

	public MessageType getMsgType(){
		return this.msgType;
	}

	public void setMsgType(MessageType msgType) {
		this.msgType = msgType;
	}

	// Methods only here to be overwritten
	public String getPlayerName(){ return null; }
	public String getPassword(){ return null; }
	public boolean getStatus(){ return false; }
	public TichuType getTichuType() { return null; }
	public ArrayList<String> getPlayers(){ return null; }
	public String[] getOpponents(){ return null; }
	public String getTeamMate(){ return null; }
	public Card getCard() { return null; }
	public ArrayList<Card> getCards(){return null; }
	public String getNextPlayer() { return null; }
	public ArrayList<Card> getLastMove() {
		return null;
	}
	public int getRemainingPlayers(){
		return 0;
	}
	public int getOpponentScore() {
		return 0;
	}
	public int getOwnScore() {
		return 0;
	}
	public int getRemainingCardsByPlayerName(String name){
		return 0;
	}
	public String getLastPlayer(){
		return null;
	}
	public boolean isDone() {
		return false;
	}
	public String getContent() {
		return null;
	}
}