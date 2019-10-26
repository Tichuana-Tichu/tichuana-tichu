package ch.tichuana.tichu.client.model;

import ch.tichuana.tichu.commons.message.JoinMsg;
import ch.tichuana.tichu.commons.message.Message;
import javafx.beans.property.SimpleStringProperty;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientModel {

    protected SimpleStringProperty newestMessage = new SimpleStringProperty();
    private Socket socket;
    private String playerName;
    private String password;
    private Logger logger = Logger.getLogger("");

    /**
     *
     * @param ipAddress
     * @param port
     * @param playerName
     * @param password
     */
    public void connect(String ipAddress, int port, String playerName, String password) {
        logger.info("Connect");
        this.playerName = playerName;
        this.password = password;
        try {
            socket = new Socket(ipAddress, port);

            Runnable r = new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        Message msg = Message.receive(socket);
                        newestMessage.set("");
                        //newestMessage.set(msg.getName()+": "+msg.getContent());
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
     *
     * @param message
     */
    public void sendMessage(String message) {
        logger.info("Send message");
        //Message msg = new Message(name, message);
        //msg.send(socket);
    }

    public String receiveMessage() {
        logger.info("Receive Message");
        return newestMessage.get();
    }
}