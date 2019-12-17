package ch.tichuana.tichu.client.model;

import ch.tichuana.tichu.client.chat.ChatView;
import ch.tichuana.tichu.client.services.ServiceLocator;
import ch.tichuana.tichu.client.services.Translator;
import ch.tichuana.tichu.commons.message.*;
import javafx.application.Platform;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientModel {

    //variables for communication
    private SimpleMessageProperty msg = new SimpleMessageProperty();
    private Logger logger = Logger.getLogger("");
    private Translator translator;
    private volatile boolean closed;
    private Socket socket;
    //variables for game-flow
    private boolean grandTichu = false;
    private boolean myTurn;
    private boolean firstUpdate;
    private String[] opponents;
    private String playerName;
    private String teamMate;
    private int opponentScore;
    private int ownScore;
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
        this.translator = ServiceLocator.getServiceLocator().getTranslator();
        logger.info(playerName+" connecting...");
        this.playerName = playerName;
        this.closed = false;
        try {
            socket = new Socket(ipAddress, port);

            Runnable r = () -> {
                while(!closed) {

                    Message msg = Message.receive(socket);

                    if (msg instanceof ConnectedMsg) {
                        if (msg.getStatus()) {
                            this.msg.set(1); //triggers handleConnectedMsg in lobbyController
                            this.msg.setNewestMsg(translator.getString("connection"));
                        } else
                            this.msg.setNewestMsg(translator.getString("connectionFailed"));
                    }

                    if (msg instanceof GameStartedMsg) {
                        this.teamMate = msg.getTeamMate();
                        this.opponents = msg.getOpponents();
                        this.msg.set(2); //triggers handleGameStartedMsg in playController
                        this.msg.setNewestMsg(translator.getString("gameStarted"));
                    }

                    if (msg instanceof DealMsg) {

                        this.firstUpdate = true;
                        this.myTurn = false;
                        if (msg.getCards().size() == 8) {
                            this.hand = new Hand(msg.getCards()); //creates hand with first 8 cards
                            this.msg.set(3); //triggers handleFirstDealMsg in playController
                            this.msg.setNewestMsg(translator.getString("firstEightCards"));
                        } else {
                            this.hand.addCards(msg.getCards()); //updates hand with second 6 cards
                            if (this.grandTichu)
                                this.msg.setNewestMsg(translator.getString("grandTichuAnnounced"));
                            else
                                this.msg.setNewestMsg(translator.getString("lastSixCards"));
                            this.msg.set(5); //triggers handleSecondDealMsg in playController
                        }
                    }

                    if (msg instanceof AnnouncedTichuMsg) {
                        this.msg.setMessage(msg);
                        this.msg.set(4); //triggers handleAnnouncedTichuMsg in playController
                        this.msg.set(20); //is only set to trigger an event for the next message
                    }

                    if (msg instanceof DemandSchupfenMsg) {
                        this.grandTichu = false; //resetting variable for the next match
                        this.msg.setMessage(msg);
                        if (!this.playerName.equals(msg.getPlayerName())) { //this player needs to take action
                            this.msg.set(6); //enables the schupfen button in playController
                            this.msg.setNewestMsg(translator.getString("demandPush1")+msg.getPlayerName());
                            this.msg.set(20); //is only set to trigger an event for the next message
                        } else { //this player is being pushed
                            sendMessage(new ReceivedMsg(true));
                            this.msg.set(7); //disables the schupfen button in playController
                            this.msg.setNewestMsg(translator.getString("demandPush2"));
                        }
                    }

                    if (msg instanceof SchupfenMsg) {
                        this.msg.setMessage(msg);
                        this.msg.set(8); //triggers handleSchupfenMsg in playController
                        this.msg.set(20); //is only set to trigger an event for the next message
                    }

                    if (msg instanceof UpdateMsg) {
                        //handing out all cards from pushing to other Players
                        if (this.firstUpdate) {
                            this.msg.set(9); //triggers last handleSchupfenMsg in playController
                            this.firstUpdate = false;
                        }
                        this.msg.setMessage(msg);

                        if (!this.playerName.equals(msg.getNextPlayer())) {
                            this.myTurn = false;
                            this.msg.setNewestMsg(msg.getNextPlayer()+" "+translator.getString("elsesTurn"));
                            sendMessage(new ReceivedMsg(true));
                        } else {
                            this.myTurn = true;
                            this.msg.setNewestMsg(translator.getString("yourTurn"));
                        }

                        this.msg.set(10); //triggers handleUpdateMsg in playController
                        this.msg.set(30);//is only set to trigger an event for the next message
                    }

                    if (msg instanceof GameDoneMsg) {
                        this.ownScore = msg.getOwnScore(); //getting current score
                        this.opponentScore = msg.getOpponentScore(); //getting current score

                        if (!msg.isDone())
                            this.msg.setNewestMsg(translator.getString("matchWon"));
                        else
                            this.msg.setNewestMsg(translator.getString("gameWon"));
                        this.msg.set(11); //triggers handleGameDoneMsg in playController
                        this.msg.set(30);//is only set to trigger an event for the next message
                    }

                    if (msg instanceof ChatMsg) {
                        Platform.runLater(() -> ChatView.getView().addMessage(msg.getPlayerName(), msg.getContent()));
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
        logger.info("Disconnecting...");
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

    //Getter & Setter
    public int getMsgCode() {
        return this.msg.get();
    }
    public SimpleMessageProperty getMsgCodeProperty() {
        return this.msg;
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
    public boolean isMyTurn() {
        return myTurn;
    }
    public int getOpponentScore() {
        return opponentScore;
    }
    public int getOwnScore() {
        return ownScore;
    }
    public void updateTranslator() {
        this.translator = ServiceLocator.getServiceLocator().getTranslator();
    }
}