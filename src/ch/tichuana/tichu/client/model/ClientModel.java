package ch.tichuana.tichu.client.model;

import ch.tichuana.tichu.commons.message.*;
import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.TichuType;
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
                        logger.info("these players announced tichu");
                    }

                    if (msg instanceof ConnectedMsg) {
                        logger.info("successfully connected to Server");
                    }

                    if (msg instanceof GameStartedMsg) {
                        logger.info("you successfully entered a game");
                    }

                    if (msg instanceof DemandTichuMsg) {
                        logger.info("please announce tichu or pass");
                    }
                    if (msg instanceof DemandSchupfenMsg) {
                        logger.info("please choose card for player: "+msg.getPlayerName());
                    }
                    if (msg instanceof UpdateMsg) {
                        logger.info("updating gui with newest information's");
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
    public void sendMessage(MessageType messageType) {
        logger.info("Send message");
        Message msg;

        switch (messageType) {

            case CreatePlayerMsg:
                msg = new CreatePlayerMsg("name", "password");
                msg.send(socket);
                break;

            case ReceivedMsg:
                msg = new ReceivedMsg(true);
                msg.send(socket);
                break;

            case TichuMsg:
                msg = new TichuMsg(this.playerName, TichuType.GrandTichu);
                msg.send(socket);
                break;

            case SchupfenMsg:
                msg = new SchupfenMsg(this.playerName, new Card());
                msg.send(socket);
                break;

            case PlayMsg:
                msg = new PlayMsg(new ArrayList<>());
                msg.send(socket);
                break;
        }
    }

    //TODO - needed for broadcasts or not?
    public String receiveMessage() {
        logger.info("Receive Message");
        return newestMessage.get();
    }
}