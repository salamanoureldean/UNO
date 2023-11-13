/**
 * @author: Abody Majeed 101227327, Mahad Mohamed Yonis 101226808, Salama Noureldean 101154365, Pietro Adamvoski 101238885
 * @date: 10/22/2023
 * @version: 1.00
 */
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Game {
    private Gui gui;
    private Deck theDeck;
    private ArrayList<Player> playersInGame;
    private boolean winner = false;
    private Card currentCard;
    private int currentTurn = 0;

    // Game constructor
    public Game() {
        playersInGame = new ArrayList<>();
        theDeck = new Deck();
    }

    // Main game flow
    public void gameStart() {
        currentCard = theDeck.draw();
        cardFunctionality(currentCard);

        while (!winner) {
            Player currentPlayer = getCurrentPlayer();
            // Controller will decide whether to call drawCard() or playCard()

            checkWinner();

            if (!winner) {
                nextPlayer();
            }
        }
        winnerScore();
    }

    // Draw a card
    public boolean addCardToHand() {
        Player currentPlayer = getCurrentPlayer();
        theDeck.isZero(currentCard);
        currentPlayer.drawCard(theDeck);

        Card lastDrawnCard = currentPlayer.getLastCard();

        if (lastDrawnCard != null && isPlayable(lastDrawnCard)) {
            return true;
        } else {
            nextPlayer();
            return false;
        }
    }

    // Play a card
    public boolean removeCardFromHand(Card cardToPlay) {
        Player currentPlayer = getCurrentPlayer();

        if (cardToPlay != null && isPlayable(cardToPlay)) {
            currentCard = cardToPlay;
            cardFunctionality(currentCard);
            theDeck.place(cardToPlay);
            currentPlayer.playCard(cardToPlay);
            return true;
        }
        return false;
    }


    // Check if a player has won
    public void checkWinner() {
        for (Player player : playersInGame) {
            if (player.getHand().getCards().isEmpty()) {
                winner = true;
                break;
            }
        }
    }

    /**
     * checks if the card to be placed is playable
     * @param card you want to place
     * @return true if playable false if not playable
     */
    public boolean isPlayable(Card card) {
        if (currentCard == null) {
            // If no card has been played yet, any card is playable.
            return true;
        }

        if (currentCard.getColor() == card.getColor() || currentCard.getVALUE() == card.getVALUE()) {
            return true;
        } else if (card.getVALUE() == Card.Value.WILD || card.getVALUE() == Card.Value.WILD_DRAW_TWO) {
            return true;
        } else if (card.getVALUE() == Card.Value.SKIP || card.getVALUE() == Card.Value.REVERSE) {
            return card.getColor() == currentCard.getColor();
        }

        return false;
    }

    /**
     * Imeplements the special card functionalities such as REVERSE, WILD, SKIP
     * @param playedCard the special card to be played
     */
    public void cardFunctionality(Card playedCard) {
        Card.Value cardValue = playedCard.getVALUE();
        //Card.Color cardColor = playedCard.getColor();

        switch (cardValue) {
            case REVERSE:
                Collections.reverse(playersInGame);
                //TURN THE PRINT INTO A JOPTIONPANE
                System.out.println("The play has been reversed!");
                break;
            case SKIP:
                if (currentTurn + 1 > playersInGame.size()){
                    currentTurn = 0;
                }
                else{
                    currentTurn += 1;
                }
                //TURN INTO JOPTIONPANE
                System.out.println("Player " + (currentTurn + 1) + "has been skipped!");
                break;
            case WILD:
                Scanner wildInput = new Scanner(System.in);
                //JOPTIONPANE
                System.out.println("Choose a color: R, G, B, Y\n");
                String newColor = wildInput.nextLine().toUpperCase();

                // Changing current color based on user input
                if (newColor.equals("R")) {
                    currentCard.setColor(Card.Color.RED);
                    System.out.println("The color has been changed to red.");
                }
                else if (newColor.equals("G")) {
                    currentCard.setColor(Card.Color.GREEN);
                    System.out.println("The color has been changed to green.");
                }
                else if (newColor.equals("B")) {
                    currentCard.setColor(Card.Color.BLUE);
                    System.out.println("The color has been changed to blue.");
                }
                else if (newColor.equals("Y")) {
                    currentCard.setColor(Card.Color.YELLOW);
                    System.out.println("The color has been changed to yellow.");
                }
                break;
            case WILD_DRAW_TWO:
                // Getting next player's index in order to make them draw two cards
                int nextPlayerIndex = (currentTurn + 1) % playersInGame.size();
                Player nextPlayer = playersInGame.get(nextPlayerIndex);
                for (int i = 0; i < 2; i++){
                    nextPlayer.drawCard(theDeck);
                }
                // Changing current color of cards being played based on user input
                wildInput = new Scanner(System.in);
                System.out.println("Choose a color: R, G, B, Y\n");
                newColor = wildInput.nextLine().toUpperCase();

                if (newColor.equals("R")) {
                    currentCard.setColor(Card.Color.RED);
                    System.out.println("The color has been changed to red.");
                }
                else if (newColor.equals("G")) {
                    currentCard.setColor(Card.Color.GREEN);
                    System.out.println("The color has been changed to green.");
                }
                else if (newColor.equals("B")) {
                    currentCard.setColor(Card.Color.BLUE);
                    System.out.println("The color has been changed to blue.");
                }
                else if (newColor.equals("Y")) {
                    currentCard.setColor(Card.Color.YELLOW);
                    System.out.println("The color has been changed to yellow.");
                }
                break;
        }
    }

    /**
     * Calculates and displays the winners score
     */
    public void winnerScore(){
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
    }


    // Move to the next player
    public void nextPlayer() {
        currentTurn = (currentTurn + 1) % playersInGame.size();
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
}