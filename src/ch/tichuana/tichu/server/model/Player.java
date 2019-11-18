package ch.tichuana.tichu.server.model;

import ch.tichuana.tichu.commons.message.*;
import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.TichuType;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Player {

	private Logger logger = Logger.getLogger("");
	private String playerName;
	private String password;
	private Socket socket;
	private volatile boolean closed;
	private ServerModel serverModel;
	//SimpleBooleanProperties control the game flow
	private volatile SimpleMessageProperty announcedTichu = new SimpleMessageProperty(false);
	private volatile SimpleMessageProperty announcedGrandTichu = new SimpleMessageProperty(false);
	private volatile SimpleMessageProperty schupfenProperty = new SimpleMessageProperty(false);
	private TichuType tichuType = TichuType.none;
	private volatile SimpleMessageProperty hisTurn = new SimpleMessageProperty(false);
	private boolean done;
	private ArrayList currentMove;
	private ArrayList<Card> hand;

	/**
	 * New Object when clients connects, has its own socket for communication
	 * listens for Messages that can be answered from this instance
	 * @author Philipp
	 * @param serverModel object of the ServerModel
	 * @param socket own socket for communication with clientModel
	 */
	public Player(ServerModel serverModel, Socket socket) {
		this.serverModel = serverModel;
		this.socket = socket;
		this.closed = false;
		this.hand = new ArrayList<Card>();
		this.done = false;

		Runnable r = () -> {
			while (!closed) {
				Message msg = Message.receive(socket);

				if (msg instanceof JoinMsg) {
					Player.this.playerName = msg.getPlayerName();
					this.password = msg.getPassword();

					// check if password is correct
					if (verifyPassword()){
						sendMessage(new ConnectedMsg(true));
						serverModel.getPlayers().add(this);
						logger.info("Player: "+msg.getPlayerName()+" logged in");
					}
					else {
						sendMessage(new ConnectedMsg(false));
					}
				}

				else if (msg instanceof TichuMsg) {
					logger.info("Player: "+this.playerName+" announced SmallTichu");
					this.tichuType = msg.getTichuType();

					// Important: The boolean value of SimpleMessagePropertys means that a player has already announced
					// Grandtichu/none or smalltichu/none. NOT if he actually announced grand or small tichu.
					// use getTichuType() to find out what the player has announced!
					switch (tichuType){
						case none:
							if (announcedGrandTichu.getValue()){
								announcedTichu.setMessage(msg);
								announcedTichu.setValue(true);
							} else {
								announcedGrandTichu.setMessage(msg);
								announcedGrandTichu.setValue(true);
							}
							break;
						case GrandTichu:
							announcedGrandTichu.setMessage(msg);
							announcedGrandTichu.setValue(true);
							break;
						case SmallTichu:
							announcedTichu.setMessage(msg);
							announcedTichu.setValue(true);
							break;
					}
				}

				else if (msg instanceof SchupfenMsg) {
					logger.info("Player: "+this.playerName+" schupfed card to"+msg.getPlayerName());
					this.schupfenProperty.setMessage(msg);
					this.schupfenProperty.setValue(true);
				}

				else if (msg instanceof PlayMsg) {
					logger.info("Player: "+this.playerName+" played cards");
					this.currentMove = msg.getCards();
				}

				else if (msg instanceof UpdateMsg) {
					if (this.playerName.equals(msg.getNextPlayer())) {
						this.hisTurn.setMessage(msg);
						this.hisTurn.setValue(true);
					} else {
						this.hisTurn.setValue(false);
					}
				}
			}
		};
		Thread t = new Thread(r);
		t.start();
	}

	/**
	 * stops listening and closes socket
	 * @author Philipp
	 */
	public void stop() {
		this.closed = true;
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends a given message to the client of this player instance.
	 * @author Christian
	 * @param message
	 */
	public void sendMessage(Message message){
		message.send(this.socket);
	}

	/**
	 * Checks if password matches password in database belonging to player
	 * @return boolean
	 */
	public boolean verifyPassword(){
		// TODO: Check if password is correct
		// this.password
		// this.playerName
		// get DatabaseConnection from ServiceLocator
		return true;
	}

	//Getter & Setter
	public String getPlayerName() {
		return this.playerName;
	}
	public final void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public SimpleMessageProperty getAnnouncedTichuProperty() {
		return this.announcedTichu;
	}
	public final void setAnnouncedTichu(boolean announcedTichu) {
		this.announcedTichu.set(announcedTichu);
	}
	public SimpleMessageProperty getHisHisTurnProperty() {
		return this.hisTurn;
	}
	public final void setHisTurn(boolean hisTurn) {
		this.hisTurn.set(hisTurn);
	}
	public ArrayList<Card> getCurrentMove() {
		return currentMove;
	}
	public void setCurrentMove(ArrayList<Card> currentMove) {
		this.currentMove = currentMove;
	}
	public ArrayList<Card> getHand() {
		return hand;
	}
	public boolean isDone() {
		return done;
	}
	public SimpleMessageProperty getAnnouncedGrandTichuProperty(){
		return announcedGrandTichu;
	}

	public TichuType getTichuType() {
		return tichuType;
	}
	public SimpleMessageProperty getSchupfenProperty(){
		return schupfenProperty;
	}
}