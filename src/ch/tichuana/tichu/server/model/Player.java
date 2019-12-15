package ch.tichuana.tichu.server.model;

import ch.tichuana.tichu.commons.message.*;
import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.TichuType;
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
	private volatile SimpleMessageProperty playProperty = new SimpleMessageProperty(false);
	private boolean done;
	private ArrayList<Card> hand;
	private ArrayList<Trick> tricksWon;

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
		this.hand = new ArrayList<>();
		this.tricksWon = new ArrayList<>();
		this.done = false;

		Runnable r = () -> {
			while (!closed) {
				Message msg = Message.receive(socket);

				if (msg instanceof JoinMsg) {
					this.playerName = msg.getPlayerName();
					this.password = msg.getPassword();

					// check if password is correct
					if (verifyPassword() && verifyUserName()){
						sendMessage(new ConnectedMsg(true));
						logger.info("Player: "+msg.getPlayerName()+" logged in");
						serverModel.getPlayers().add(this);
					}
					else {
						sendMessage(new ConnectedMsg(false));
					}
				}

				else if (msg instanceof TichuMsg) {
					logger.info("Player: "+this.playerName+" announced Tichu: " + msg.getTichuType());
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
					logger.info("Player: "+this.playerName+" pushed card to "+msg.getPlayerName());
					this.schupfenProperty.setMessage(msg);
					this.schupfenProperty.setValue(true);
				}

				else if (msg instanceof PlayMsg) {
					this.playProperty.setPlayer(this);
					this.playProperty.setMessage(msg);
					this.playProperty.setValue(true);
				}


				// Does the server ever receive an Update message?
				else if (msg instanceof UpdateMsg) {
					if (this.playerName.equals(msg.getNextPlayer())) {
						this.playProperty.setMessage(msg);
						this.playProperty.setValue(true);
					} else {
						this.playProperty.setValue(false);
					}
				}

				// broadcast incoming chat msg to all players
				else if (msg instanceof ChatMsg) {
					serverModel.broadcast(msg);
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
	protected void stop() {
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
	 * @param message message to send to player
	 */
	protected void sendMessage(Message message){
		message.send(this.socket);
	}

	/**
	 * Checks if password matches password in database belonging to player
	 * @return boolean
	 */
	private boolean verifyPassword(){
		// TODO: Check if password is correct
		return true;
	}

	/**
	 * checks if the username is already taken
	 * @return is valid name
	 */
	private boolean verifyUserName() {
		// make sure no two players have the same name
		for (Player p : serverModel.getPlayers()){
			if (p.getPlayerName().equals(this.playerName)){
				return false;
			}
		}
		return true;
	}

	/**
	 * adds a won trick to this player
	 * @author Christian
	 * @param trick trick this player has won
	 */
	protected void addTrick(Trick trick){
		this.tricksWon.add(trick);
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
	public SimpleMessageProperty getPlayProperty() {
		return this.playProperty;
	}
	public final void setPlayProperty(boolean playProperty) {
		this.playProperty.set(playProperty);
	}
	public ArrayList<Card> getHand() {
		return hand;
	}
	boolean isDone() {
		return done;
	}
    void setDone(boolean done) {
        this.done = done;
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

	ArrayList<Trick> getTricksWon() {
		return tricksWon;
	}
}