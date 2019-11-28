package ch.tichuana.tichu.client.model;

import ch.tichuana.tichu.commons.message.*;
import javafx.beans.property.SimpleStringProperty;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientModel {

    private SimpleStringProperty newestMessage = new SimpleStringProperty();
    private SimpleMessageProperty msgCode = new SimpleMessageProperty();
    private volatile boolean grandTichu = false;
    private Socket socket;
    private volatile boolean closed;
    private String playerName;
    private String nextPlayerName;
    private String playerToSchupfCard;
    private Logger logger = Logger.getLogger("");

    private String teamMate;
    private String[] opponents;
    private Hand hand;

    /**
     * connects client to server with JoinMsg and listens for incoming messages
     * @author Philipp
     * @param ipAddress ip 127.0.0.1 from config.properties
     * @param port port number 8080 from config.properties
     * @param playerName receiving from GUI
     * @param password receiving from GUI
     */
    public void connect(String ipAddress, int port, String playerName, String password) {
        logger.info("Connect");
        this.playerName = playerName;
        this.closed = false;
        try {
            socket = new Socket(ipAddress, port);

            Runnable r = () -> {
                while(!closed) {

                    Message msg = Message.receive(socket);

                    if (msg instanceof ConnectedMsg) {
                        if (msg.getStatus()) {
                            this.msgCode.set(1);
                            newestMessage.set("successfully connected to Server");
                        } else
                            newestMessage.set("connection failed: wrong password");
                    }

                    if (msg instanceof GameStartedMsg) {

                        this.teamMate = msg.getTeamMate();
                        this.opponents = msg.getOpponents();
                        this.msgCode.set(2);
                        newestMessage.set("you successfully entered a game");
                    }

                    if (msg instanceof DealMsg) {
                        if (msg.getCards().size() == 8) {
                            this.hand = new Hand(msg.getCards());
                            this.msgCode.set(3);
                            newestMessage.set("your first eight cards, please announce grand tichu");
                        } else {
                            this.hand.addCards(msg.getCards());
                            if (this.grandTichu)
                                newestMessage.set("you already announced, wait till schupfen");
                            else
                                newestMessage.set("your remaining six cards, please announce small tichu");
                            this.msgCode.set(5);
                        }
                    }

                    if (msg instanceof AnnouncedTichuMsg) {
                        this.msgCode.setMessage(msg);
                        this.msgCode.set(4);
                        this.newestMessage.set(msg.getPlayerName()+" announced: "+msg.getTichuType());
                        this.msgCode.set(10);
                    }

                    if (msg instanceof DemandSchupfenMsg) {
                        this.msgCode.setMessage(msg);
                        if (!this.playerName.equals(msg.getPlayerName())) {
                            this.msgCode.set(6);
                            this.playerToSchupfCard = msg.getPlayerName();
                            this.newestMessage.set("please choose card for player: "+msg.getPlayerName());
                        } else {
                            sendMessage(new ReceivedMsg(true));
                            this.msgCode.set(7);
                            this.newestMessage.set("other players are now schupfing for you");
                        }
                    }

                    if (msg instanceof SchupfenMsg) {
                        this.msgCode.setMessage(msg);
                        this.msgCode.set(8);
                        this.newestMessage.set("received card from: "+msg.getPlayerName());
                        this.msgCode.set(10);
                    }

                    if (msg instanceof UpdateMsg) {
                        /*
                        this.msgCode.set(9);
                        if (!this.playerName.equals(msg.getNextPlayer())) {
                            this.nextPlayerName = msg.getNextPlayer();
                            sendMessage(new ReceivedMsg(true));
                        } else {
                            logger.info("it is your turn "+msg.getNextPlayer());
                        }

                         */
                    }
                }
            };
            Thread t = new Thread(r);
            t.start();

            Message msg = new JoinMsg(playerName, password);
            msg.send(socket);

        } catch(IOException e) {
            logger.warning(e.toString());
        }
    }

    /**
     * stops listening and closes socket
     * @author Philipp
     */
    public void disconnect() {
        logger.info("Disconnect");
        this.closed = true;

        if(socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * called from controller to send messages to Player-Object (Server)
     * @author Philipp
     * @param message from a specific type
     */
    public void sendMessage(Message message) { message.send(this.socket); }

    //TODO - needed for broadcasts or not?
    public String receiveMessage() {
        logger.info("Receive Message");
        return newestMessage.get();
    }

    //Getter & Setter
    public String getPlayerToSchupfCard() {
        return playerToSchupfCard;
    }
    public void setMsgCode(int code) {
        this.msgCode.set(code);
    }
    public String getNewestMessage() { return this.newestMessage.get(); }
    public int getMsgCode() {
        return msgCode.get();
    }
    public SimpleMessageProperty getMsgCodeProperty() {
        return msgCode;
    }
    public SimpleStringProperty getNewestMessageProperty() {
        return newestMessage;
    }
    public void setNewestMessage(String message) {
        this.newestMessage.set(message);
    }
    public String getTeamMate() {
        return teamMate;
    }
    public String getOpponent(int i) {
        return opponents[i];
    }
    public String getPlayerName() {
        return playerName;
    }
    public Hand getHand() {
        return hand;
    }
    public boolean announcedGrandTichu() {
        return grandTichu;
    }
    public void setGrandTichu(boolean grandTichu) {
        this.grandTichu = grandTichu;
    }
}