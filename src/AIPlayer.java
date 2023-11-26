/**
 * @author: Mahad Mohamed Yonis 101226808, Pietro Adamvoski 101238885
 * @date: 11/25/2023
 * @version: 3.00
 */
public class AIPlayer extends Player {

    private Hand hand;      // A players hand made up of cards
    private int score;     // Players score

    /**
     * Constructs a new AIPlayer with the specified name and associated deck.
     *
     * @param name The name of the AIPlayer.
     * @param deck The deck associated with the AIPlayer.
     */
    public AIPlayer(String name, Deck deck) {
        super(name, deck);
        this.score = 0;
    }

    /**
     * Makes a move based on the current card and game state. Determines the best move
     * based on different scenarios, such as having one card left, the next player having
     * two or fewer cards, or the next player having more than two cards.
     *
     * @param currentCard The current card in play.
     * @param game        The Game object representing the state of the card game.
     * @return The card to be played by the AIPlayer.
     */
    public Card makeMove(Card currentCard, Game game) {
        // Check if AIPlayer has one card left
        if (this.getHand().getCards().size() == 1 && this.getHand().getCards().get(0).getColor() == currentCard.getColor() && this.getHand().getCards().get(0).getValue() == currentCard.getValue()) {
            return aiHasOneCardLeft(currentCard, game);
        }
        // If next player has 2 or fewer cards
        else if (game.getNextPlayer().getHand().getCards().size() <= 2) {
            return nextPlayerHasTwoOrLessCards(currentCard, game);
        }
        // If next player has more than 2 cards
        else if (game.getNextPlayer().getHand().getCards().size() > 2) {
            return playFirstPlayableCard(currentCard, game);
        }
        // Return null if no move was made
        return null;
    }

    /**
     * Determines whether the AIPlayer should draw a card based on the current card and game state.
     * The AIPlayer draws a card if there are no playable cards in its hand.
     *
     * @param currentCard The current card in play.
     * @param game        The Game object representing the state of the card game.
     * @return true if the AIPlayer should draw a card, false otherwise.
     */
    public boolean shouldDrawCard(Card currentCard, Game game) {
        // Check if there's a playable card
        for (Card card : this.getHand().getCards()) {
            if (isPlayable(card, currentCard)) {
                return false; // Don't draw
            }
        }


        return true; // Draw a card if no playable card
    }

    /**
     * Checks if a given card is playable on the current card.
     *
     * @param cardToPlay  The card to check for playability.
     * @param currentCard The current card in play.
     * @return true if the card is playable, false otherwise.
     */
    private boolean isPlayable(Card cardToPlay, Card currentCard) {
        // Check if the card can be played on the current card
        return cardToPlay.getColor() == currentCard.getColor() || cardToPlay.getValue() == currentCard.getValue() || cardToPlay.isWild();
    }

    /**
     * Plays the specified card, updating the game state accordingly.
     *
     * @param card The card to be played.
     * @param game The Game object representing the state of the card game.
     */
    private void playCard(Card card, Game game) {
        //check if the card is wildcard, if it is then set color of wildcard aswell.
        if (card.getValue() == Card.Value.WILD || card.getValue() == Card.Value.WILDDRAWTWO) {
            card.setColor(changeWildColor());
        }

        game.removeCardFromHand(card);
        game.setCurrentCard(card);
    }

    /**
     * Plays a card when the AIPlayer has only one card left. Chooses the only available card
     * and updates the game state.
     *
     * @param card The card to be played.
     * @param game The Game object representing the state of the card game.
     * @return The card played by the AIPlayer.
     */
    private Card aiHasOneCardLeft(Card card, Game game){
        Card cardToPlay = this.getHand().getCards().get(0);
        playCard(cardToPlay, game);
        return cardToPlay;
    }

    /**
     * Plays a card when the next player has two or fewer cards. Chooses the best card based
     * on specific conditions such as Wild Draw Two, Draw One, Skip, Reverse, Flip, or Wild.
     *
     * @param currentCard The current card in play.
     * @param game        The Game object representing the state of the card game.
     * @return The card played by the AIPlayer.
     */
    private Card nextPlayerHasTwoOrLessCards(Card currentCard, Game game) {
        for (int i = 0; i < this.getHand().getCards().size(); i++) {
            Card cardToPlay = this.getHand().getCards().get(i);

            if (cardToPlay.getValue() == Card.Value.WILDDRAWTWO) {
                playCard(cardToPlay, game);
                return cardToPlay;
            }
            else if (cardToPlay.getValue() == Card.Value.DRAWONE && cardToPlay.getColor() == currentCard.getColor()) {
                playCard(cardToPlay, game);
                return cardToPlay;
            }
            else if (cardToPlay.getValue() == Card.Value.SKIP && cardToPlay.getColor() == currentCard.getColor()) {
                playCard(cardToPlay, game);
                return cardToPlay;
            }
            else if (cardToPlay.getValue() == Card.Value.REVERSE && cardToPlay.getColor() == currentCard.getColor()) {
                playCard(cardToPlay, game);
                return cardToPlay;
            }
            else if (cardToPlay.getValue() == Card.Value.FLIP && cardToPlay.getColor() == currentCard.getColor()) {
                playCard(cardToPlay, game);
                return cardToPlay;
            }
            else if (cardToPlay.getValue() == Card.Value.WILD) {
                playCard(cardToPlay, game);
                return cardToPlay;
            }
        }

        return playFirstPlayableCard(currentCard, game);
    }

    /**
     * Plays the first playable card in the AIPlayer's hand based on the current card.
     *
     * @param currentCard The current card in play.
     * @param game        The Game object representing the state of the card game.
     * @return The card played by the AIPlayer.
     */
    public Card playFirstPlayableCard(Card currentCard, Game game){
        for (int i = 0; i < this.getHand().getCards().size(); i++) {
            Card cardToPlay = this.getHand().getCards().get(i);
            if (cardToPlay.getValue() == currentCard.getValue() || cardToPlay.getColor() == currentCard.getColor()) {
                playCard(cardToPlay, game);
                return cardToPlay;
            }
        }
        return null;
    }

    /**
     * Changes the color of a Wild card to the most common color in the AIPlayer's hand.
     *
     * @return The new color for the Wild card.
     */
    public Card.Color changeWildColor() {
        //This method will determine the most common color in the AI's hand, and changes to that.
        int redCount = 0;
        int blueCount = 0;
        int greenCount = 0;
        int yellowCount = 0;
        int max = 0;
        for (int i = 0; i < this.getHand().getCards().size(); i++) {

            if (this.getHand().getCards().get(i).getColor() == Card.Color.RED) {
                redCount++;

            }

            if (this.getHand().getCards().get(i).getColor() == Card.Color.BLUE) {
                blueCount++;
            }

            if (this.getHand().getCards().get(i).getColor() == Card.Color.GREEN) {
                greenCount++;
            }

            if (this.getHand().getCards().get(i).getColor() == Card.Color.YELLOW) {
                yellowCount++;
            }

            max = Math.max(redCount, blueCount);
            max = Math.max(max, greenCount);
            max = Math.max(max, yellowCount);

        }
        if (max == redCount) {
            return Card.Color.RED;
        }
        if (max == blueCount) {
            return Card.Color.BLUE;
        }
        if (max == greenCount) {
            return Card.Color.GREEN;
        }
        if (max == yellowCount) {
            return Card.Color.YELLOW;
        }
        return Card.Color.BLANK;
    }
}