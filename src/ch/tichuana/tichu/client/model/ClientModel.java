package ch.tichuana.tichu.client.model;

import ch.tichuana.tichu.commons.message.JoinMsg;
import ch.tichuana.tichu.commons.message.Message;
import ch.tichuana.tichu.commons.message.MessageType;
import javafx.beans.property.SimpleStringProperty;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientModel {

    private SimpleStringProperty newestMessage = new SimpleStringProperty();
    private Socket socket;
    private String playerName;
    private String password;
    private Logger logger = Logger.getLogger("");

    /**
     *
     * @param ipAddress ip 127.0.0.1
     * @param port port number 8080
     * @param playerName receiving from GUI
     * @param password receiving from GUI
     */
    public void connect(String ipAddress, int port, String playerName, String password) {
        logger.info("Connect");
        this.playerName = playerName;
        this.password = password;
        try {
            socket = new Socket(ipAddress, port);

            Runnable r = () -> {
                while(true) {

                    if (Message.receive(socket) != null) {

                        Message msg = Message.receive(socket);
                        switch (msg.getMsgType()) {

                            case AnnouncedTichuMsg:
                                logger.info("these players announced tichu");
                                break;

                            case ConnectedMsg:
                                logger.info("successfully connected to Server");
                                break;

                            case GameStartedMsg:
                                logger.info("you successfully entered a game");
                                break;

                            case DemandTichuMsg:
                                logger.info("please announce tichu or pass");
                                break;

                            case DemandSchupfenMsg:
                                logger.info("please choose card for player: "+msg.getPlayerName());
                                break;

                            case UpdateMsg:
                                logger.info("updating gui with newest information's");
                                break;
                        }
                    }
                }
            };
            Thread t = new Thread(r);
            t.start();

            Message msg = new JoinMsg(playerName, password);
            msg.send(socket);

        } catch(Exception e) {
            logger.warning(e.toString());
        }
    }

    public void disconnect() {
        logger.info("Disconnect");

        if(socket != null)
            try {
                socket.close();
            }catch(IOException e) {

            }
    }

    /**
     * Method to be called from controller
     * @param messageType
     */
    public void sendMessage(MessageType messageType) {
        logger.info("Send message");
        switch (messageType) {

            case CreatePlayerMsg:
                break;

            case ReceivedMsg:
                break;

            case TichuMsg:
                break;

            case SchupfenMsg:
                break;

            case PlayMsg:
                break;
        }
    }

    public String receiveMessage() {
        logger.info("Receive Message");
        return newestMessage.get();
    }
}