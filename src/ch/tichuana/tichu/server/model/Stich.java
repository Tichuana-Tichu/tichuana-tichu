package ch.tichuana.tichu.server.model;

import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.Combination;
import ch.tichuana.tichu.server.Server;

import java.util.ArrayList;

public class Stich {

    private ArrayList<Card> cards;
    private Combination combination;
    private Player currentWinner;
    private int passCounter;
    private boolean won;
    private ServerModel serverModel;

    public Stich(ServerModel serverModel){
        this.serverModel = serverModel;
        passCounter = 0;
        won = false;

    }

    /**
     * If the players move is empty, he passed. When all players have passed the "Stich" is won. If the player has
     * played a move we check if it valid and if so we add the cards to the Stich.
     * @param player
     * @param move
     */
    public void update(Player player, ArrayList<Card> move){
        if (move.isEmpty()){
            passCounter++;
            if (passCounter == 4){
                this.won = true;
            }
        } else {
            // TODO check if valid move
            cards.addAll(move);
            passCounter = 0;
            currentWinner = player;
        }
    }

    /**
     * Returns the total score of this "Stich", ergo sum of score-value of all cards played
     * @return score
     */
    public int getScore(){
        int totalScore=0;
        for (Card card : cards){
            totalScore += card.getScoreValue();
        }
        return totalScore;
    }

    public boolean isWon(){
        return won;
    }

    public boolean addMove(){
        return true;
    }
}
