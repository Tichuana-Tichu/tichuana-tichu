package ch.tichuana.tichu.server.model;

import ch.tichuana.tichu.commons.models.Card;
import ch.tichuana.tichu.commons.models.Combination;

import java.util.ArrayList;

public class Trick {

    private ArrayList<Card> cards;
    private ArrayList<Card> lastMove;
    private Player currentWinner;
    private Player lastPlayer;
    private int passCounter;
    private boolean won;
    private ServerModel serverModel;

    public Trick(ServerModel serverModel){
        this.cards = new ArrayList<>();
        this.lastMove = new ArrayList<>();
        this.serverModel = serverModel;
        passCounter = 0;
        won = false;
    }

    /**
     * If the players move is empty, he passed. When all players have passed the trick is won. If the player has
     * played a move we check if it valid and if so we add the cards to the trick.
     * @param player player who played the move
     * @param move move he played
     */
    public void update(Player player, ArrayList<Card> move){
        // last player is added even if he passed
        lastPlayer = player;

        if (move.isEmpty()){
            passCounter++;
            if (passCounter == serverModel.getGame().getNumberOfRemainingPlayers()){
                this.won = true;
            }
        } else {
            if(Combination.isValidMove(lastMove,move)){
                cards.addAll(move);
                passCounter = 0;
                currentWinner = player;

                for (Card c : move){
                    player.getHand().remove(c);
                }
                // replace last move with current move
                lastMove.clear();
                lastMove.addAll(move);
            } else {
                //TODO: handle if move isn't valid -> shouldn't happen since client checks as well
            }
        }
    }

    /**
     * Returns the total score of this trick, ergo sum of score-value of all cards played
     * @return score
     */
    public int getScore(){
        int totalScore=0;
        for (Card card : cards){
            totalScore += card.getScoreValue();
        }
        return totalScore;
    }

    boolean isWon(){
        return won;
    }

    public void addMove(ArrayList<Card> move){
        this.cards.addAll(move);
    }

    Player getCurrentWinner() {
        return currentWinner;
    }

    public ArrayList<Card> getLastMove() {
        return lastMove;
    }

    public Player getLastPlayer() {
        return lastPlayer;
    }

    String getLastPlayerName(){
        if (this.lastPlayer != null) {
            return this.lastPlayer.getPlayerName();
        } else {
            return "";
        }
    }
}
