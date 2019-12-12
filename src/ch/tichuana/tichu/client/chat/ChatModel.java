package ch.tichuana.tichu.client.chat;

import javafx.beans.property.SimpleStringProperty;

import java.net.Socket;
import java.util.logging.Logger;


/**
 * @author Dominik
 */
public class ChatModel {
    protected SimpleStringProperty newestMessage = new SimpleStringProperty();

    private Logger logger = Logger.getLogger("");
    private Socket socket;
    private String name;

    public void connect (String name){
        logger.info("Connect");
        this.name = name;
        try{
            socket = new Socket();


        }
    }
}
