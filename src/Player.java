/**
 * @author: Abody Majeed 101227327, Mahad Mohamed Yonis 101226808, Salama Noureldean 101154365, Pietro Adamvoski 101238885
 * @date: 11/25/2023
 * @version: 3.00
 */
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
     * For testing, constructs a new player with one card in the hand, a score of 0, and Uno not called.
     */
    public Player(String test_name, Deck test_deck, int i){
        this.hand = new Hand(test_deck, i); // Initialize the player's hand
        this.score = 0;        // Initialize the player's score
        this.unoCalled = false; // Uno is initially not called
        this.name = test_name;
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
     * Getter for the name
     * @return players name
     */
    public String getName() {
        return this.name;
    }

    public Card getLastCard() {
        if (getHand().getCardNum() > 0) {
            return hand.getCards().get(hand.getCards().size() - 1);
        }
        return null;
    }

    /**
     * Checks if the player's hand contains any cards that match the given color.
     *
     * @param color The color to check for in the hand.
     * @return True if there is at least one card of the matching color; otherwise, false.
     */
    public boolean hasMatchingColorCards(Card.Color color) {
        for (Card card : hand.getCards()) {
            if (card.getColor() == color) {
                return true;
            }
        }
        return false;
    }
}