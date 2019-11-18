package ch.tichuana.tichu.client.model;

import ch.tichuana.tichu.commons.message.*;
import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.TichuType;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ClientModel {

    private SimpleStringProperty newestMessage = new SimpleStringProperty();
    private Socket socket;
    private volatile boolean closed;
    private String playerName;
    private String nextPlayerName;
    private String playerToSchupfCard;
    private SimpleBooleanProperty connected = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty hisTurn = new SimpleBooleanProperty(false);
    private Logger logger = Logger.getLogger("");

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

                    if (msg instanceof AnnouncedTichuMsg) {
                        newestMessage.set("");
                        newestMessage.set(msg.getPlayers().toString()+" announced: "+msg.getTichuType());
                    }

                    if (msg instanceof ConnectedMsg) {
                        if (msg.getStatus()) {
                            this.connected.set(true);
                            newestMessage.set("successfully connected to Server");
                        } else
                            newestMessage.set("connection failed: wrong password");
                    }

                    if (msg instanceof GameStartedMsg) {
                        sendMessage(MessageType.ReceivedMsg, "true");
                        logger.info("you successfully entered a game");
                    }

                    if (msg instanceof DemandTichuMsg) {
                        logger.info("please announce tichu or pass");
                    }

                    if (msg instanceof DemandSchupfenMsg) {
                        if (!this.playerName.equals(msg.getPlayerName())) {
                            this.playerToSchupfCard = msg.getPlayerName();
                            logger.info("please choose card for player: "+msg.getPlayerName());
                        } else
                            sendMessage(MessageType.ReceivedMsg, "true");
                    }

                    if (msg instanceof UpdateMsg) {
                        if (!this.playerName.equals(msg.getNextPlayer())) {
                            this.hisTurn.set(false);
                            this.nextPlayerName = msg.getNextPlayer();
                            sendMessage(MessageType.ReceivedMsg, "true");
                        } else {
                            this.hisTurn.set(true);
                            logger.info("it is your turn "+msg.getNextPlayer());
                        }

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
     * @param messageType from a specific type
     */
    public void sendMessage(MessageType messageType, String identifier) {
        logger.info("Send message");
        Message msg;

        switch (messageType) {

            case ReceivedMsg:
                msg = new ReceivedMsg(Boolean.parseBoolean(identifier));
                msg.send(socket);
                break;

            case SchupfenMsg:
                msg = new SchupfenMsg(this.nextPlayerName, new Card());
                msg.send(socket);
                break;

            case PlayMsg:
                msg = new PlayMsg(new ArrayList<>());
                msg.send(socket);
                break;
        }
    }

    /**
     * Overloaded method
     * called from controller to send TichuMsg to Player-Object (Server)
     * @author Philipp
     * @param tichuType from type Small- or GrandTichu
     */
    public void sendMessage(TichuType tichuType) {
        logger.info("Send message");
        Message msg = new TichuMsg(this.playerName, tichuType);
        msg.send(this.socket);
    }

    //TODO - needed for broadcasts or not?
    public String receiveMessage() {
        logger.info("Receive Message");
        return newestMessage.get();
    }

    //Getter
    public String getPlayerToSchupfCard() {
        return playerToSchupfCard;
    }
    public boolean isConnected() {
        return connected.get();
    }
    public SimpleBooleanProperty getConnectedProperty() {
        return connected;
    }
    public SimpleBooleanProperty getHisTurnProperty() {
        return hisTurn;
    }
    public SimpleStringProperty getNewestMessageProperty() {
        return newestMessage;
    }
}