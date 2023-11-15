/**
 * @author: Abody Majeed 101227327, Mahad Mohamed Yonis 101226808, Salama Noureldean 101154365, Pietro Adamvoski 101238885
 * @date: 10/22/2023
 * @version: 1.00
 */
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

public class Game {
    private Deck theDeck;
    private ArrayList<Player> playersInGame;
    private boolean winner = false;
    private Card currentCard;
    private int currentTurn = 0;

    // Game constructor
    public Game(int numPlayers) {
        playersInGame = new ArrayList<>();
        theDeck = new Deck();

        int numberOfPlayers = numPlayers;

        // Initialize players
        for (int i = 0; i < numberOfPlayers; i++) {
            String playerName = "Player " + (i + 1);
            playersInGame.add(new Player(playerName, theDeck));
        }

        currentCard = theDeck.draw();

    }


    // Draw a card
    public boolean addCardToHand() {
        Player currentPlayer = getCurrentPlayer();
        theDeck.isZero(currentCard);
        currentPlayer.drawCard(theDeck);
        return true;
    }

    // Play a card
    public boolean removeCardFromHand(Card cardToPlay) {
        Player currentPlayer = getCurrentPlayer();
        if (cardToPlay != null && isPlayable(cardToPlay)) {
            currentCard = cardToPlay;
            theDeck.place(cardToPlay);
            currentPlayer.playCard(cardToPlay);
            return true;
        }
        return false;
    }

    public boolean challengeWildDrawTwo(Player challengingPlayer, Player challengedPlayer, Card.Color currentColor) {
        if (challengedPlayer.hasMatchingColorCards(currentColor)) {
                challengedPlayer.drawCard(theDeck);
                challengedPlayer.drawCard(theDeck);
            return true;
        } else {
            challengingPlayer.drawCard(theDeck);
            challengingPlayer.drawCard(theDeck);
            challengingPlayer.drawCard(theDeck);
            challengingPlayer.drawCard(theDeck);
            return false;
        }
    }

    // Check if a player has won
    public boolean checkWinner() {
        for (Player player : playersInGame) {
            if (player.getHand().getCards().isEmpty()) {
                winner = true;
                return true;
            }
        }
        return false;
    }

    /**
     * checks if the card to be placed is playable
     * @param card you want to place
     * @return true if playable false if not playable
     */
    public boolean isPlayable(Card card) {
        if (currentCard.getColor() == card.getColor() || currentCard.getVALUE() == card.getVALUE()) {
            return true;
        } else if (card.getVALUE() == Card.Value.WILD || card.getVALUE() == Card.Value.WILDDRAWTWO) {
            return true;
        } else if (card.getVALUE() == Card.Value.SKIP || card.getVALUE() == Card.Value.REVERSE) {
            return card.getColor() == currentCard.getColor();
        }

        return false;
    }

    /**
     * Calculates and displays the winners score
     */
    public int winnerScore(){
        int winnerScore = 0;
        //Iterating through each player to calculate their hand's score
        for (int i = 0; i < playersInGame.size(); i++) {
            int sumOfPlayer = 0;

            //Iterating through each card in players (i) hand
            for (Card card : playersInGame.get(i).getHand().getCards()) {
                int cardValue = card.getVALUE().ordinal();

                //Checking the value of each card (j)
                if (cardValue >= 0 && cardValue < 10) {
                    sumOfPlayer += cardValue;
                } else {
                    switch (cardValue) {
                        case 10:
                            sumOfPlayer += 20;
                            break;
                        case 11:
                            sumOfPlayer += 30;
                            break;
                        case 12:
                            sumOfPlayer += 40;
                            break;
                        case 13:
                            sumOfPlayer += 50;
                            break;
                    }
                }
            }
            //Adding total value of player(i) score into the winner's score
            winnerScore += sumOfPlayer;
        }
        return winnerScore;
    }


    // Move to the next player
    public int nextPlayer() {
       currentTurn = (currentTurn+1) % playersInGame.size();
        return currentTurn;
    }

    // Get the current player
    public Player getCurrentPlayer() {
        return playersInGame.get(currentTurn);
    }

    // Add a new player
    public void addPlayer(Player player) {
        if (player != null) {
            playersInGame.add(player);
        }
    }

    public ArrayList<Player> getPlayersInGame() {
        return playersInGame;
    }

    public Deck getTheDeck() {
        return theDeck;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public void setCurrentCard(Card currentCard) {
        this.currentCard = currentCard;
    }

    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }

    /**
     * Gets the next player in the game.
     *
     * @return The next player.
     */
    public Player getNextPlayer() {
        int nextPlayerIndex = (currentTurn + 1) % playersInGame.size();

        return playersInGame.get(nextPlayerIndex);
    }
}