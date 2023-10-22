/**
 * Represents a player in the Uno card game.
 * @version 1.0
 */
import java.util.ArrayList;
import java.util.Stack;

public class Player {
    private Hand hand;      // A player's hand made up of cards
    private int score;      // Player's score
    private boolean unoCalled;  // Indicates whether Uno has been called by the player

    private String name;
    /**
     * Constructs a new player with an empty hand, a score of 0, and Uno not called.
     */
    public Player(String name, Deck deck) {
        this.hand = new Hand(deck); // Initialize the player's hand
        this.score = 0;        // Initialize the player's score
        this.unoCalled = false; // Uno is initially not called
        this.name = name;
    }

    /**
     * Gets the player's current score.
     *
     * @return The player's score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the player's score.
     *
     * @param score The new score to set.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Checks if Uno has been called by the player.
     *
     * @return True if Uno has been called; otherwise, false.
     */
    public boolean getUnoCalled() {
        return unoCalled;
    }

    /**
     * Sets the Uno call status for the player.
     *
     * @param unoCalled True to indicate Uno has been called; otherwise, false.
     */
    public void setUnoCalled(boolean unoCalled) {
        this.unoCalled = unoCalled;
    }

    /**
     * Gets the player's hand
     * @return Player's hand
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * Draws a card and adds it to the player's hand.
     */
    public void drawCard(Deck deck) {
        hand.addCard(deck);
    }

    /**
     * Plays a card from the player's hand.
     *
     * @param card The card to play.
     */
    public void playCard(Card card) {
        hand.removeCard(card);
    }

    /**
     * Displays all cards in the player's hand.
     */
    public void viewHand() {
        hand.printAll();
    }

    /**
     * Checks if the player has Uno (one card left in hand).
     *
     * @return True if the player has Uno; otherwise, false.
     */
    public boolean hasUno() {
        return hand.getCardNum() == 1;
    }

    /**
     * Calls Uno, indicating that the player has one card left in hand.
     */
    public void callUno() {
        unoCalled = true;
    }

    /**
     * Updates the player's score based on whether they have won the round.
     *
     * @param hasWon True if the player has won the round; otherwise, false.
     */
    public void updateScore(boolean hasWon) {
        if (hasWon) {
            score += 1; // Increase the score by 1 if the player has won.
        }
    }

    public String getName() {
        return this.name;
    }
}
