/**
 * @authors: Abody Majeed 101227327
 * @editors: Salama Noureldean 101154365
 * @date: 10/22/2023
 * @version: 1.00
 */

import java.util.Collections;
import java.util.Stack;

public class Deck {

    private Stack<Card> completeDeck = new Stack<Card>();
    private Stack<Card> discardPile = new Stack<Card>();

    /**
     * Constructor for deck class that initializes every that will be used in the game
     */
    public Deck(){
        //Zeros
        initialize(1, Card.Color.RED, Card.Value.ZERO);
        initialize(1, Card.Color.BLUE, Card.Value.ZERO);
        initialize(1, Card.Color.GREEN, Card.Value.ZERO);
        initialize(1, Card.Color.YELLOW, Card.Value.ZERO);

        //Reds
        initialize(2, Card.Color.RED, Card.Value.ONE);
        initialize(2, Card.Color.RED, Card.Value.TWO);
        initialize(2, Card.Color.RED, Card.Value.THREE);
        initialize(2, Card.Color.RED, Card.Value.FOUR);
        initialize(2, Card.Color.RED, Card.Value.FIVE);
        initialize(2, Card.Color.RED, Card.Value.SIX);
        initialize(2, Card.Color.RED, Card.Value.SEVEN);
        initialize(2, Card.Color.RED, Card.Value.EIGHT);
        initialize(2, Card.Color.RED, Card.Value.NINE);

        //Blues
        initialize(2, Card.Color.BLUE, Card.Value.ONE);
        initialize(2, Card.Color.BLUE, Card.Value.TWO);
        initialize(2, Card.Color.BLUE, Card.Value.THREE);
        initialize(2, Card.Color.BLUE, Card.Value.FOUR);
        initialize(2, Card.Color.BLUE, Card.Value.FIVE);
        initialize(2, Card.Color.BLUE, Card.Value.SIX);
        initialize(2, Card.Color.BLUE, Card.Value.SEVEN);
        initialize(2, Card.Color.BLUE, Card.Value.EIGHT);
        initialize(2, Card.Color.BLUE, Card.Value.NINE);

        //Greens
        initialize(2, Card.Color.GREEN, Card.Value.ONE);
        initialize(2, Card.Color.GREEN, Card.Value.TWO);
        initialize(2, Card.Color.GREEN, Card.Value.THREE);
        initialize(2, Card.Color.GREEN, Card.Value.FOUR);
        initialize(2, Card.Color.GREEN, Card.Value.FIVE);
        initialize(2, Card.Color.GREEN, Card.Value.SIX);
        initialize(2, Card.Color.GREEN, Card.Value.SEVEN);
        initialize(2, Card.Color.GREEN, Card.Value.EIGHT);
        initialize(2, Card.Color.GREEN, Card.Value.NINE);

        //Yellows
        initialize(2, Card.Color.YELLOW, Card.Value.ONE);
        initialize(2, Card.Color.YELLOW, Card.Value.TWO);
        initialize(2, Card.Color.YELLOW, Card.Value.THREE);
        initialize(2, Card.Color.YELLOW, Card.Value.FOUR);
        initialize(2, Card.Color.YELLOW, Card.Value.FIVE);
        initialize(2, Card.Color.YELLOW, Card.Value.SIX);
        initialize(2, Card.Color.YELLOW, Card.Value.SEVEN);
        initialize(2, Card.Color.YELLOW, Card.Value.EIGHT);
        initialize(2, Card.Color.YELLOW, Card.Value.NINE);

        //Skips
        initialize(2, Card.Color.RED, Card.Value.SKIP);
        initialize(2, Card.Color.BLUE, Card.Value.SKIP);
        initialize(2, Card.Color.GREEN, Card.Value.SKIP);
        initialize(2, Card.Color.YELLOW, Card.Value.SKIP);

        //Reverse
        initialize(2, Card.Color.RED, Card.Value.REVERSE);
        initialize(2, Card.Color.BLUE, Card.Value.REVERSE);
        initialize(2, Card.Color.GREEN, Card.Value.REVERSE);
        initialize(2, Card.Color.YELLOW, Card.Value.REVERSE);

        //Wilds
        initialize(4, Card.Color.BLANK, Card.Value.WILD);

        //Wild Draw 2s
        initialize(4, Card.Color.BLANK, Card.Value.WILDDRAWTWO);

        //Flips
        initialize(2, Card.Color.BLUE, Card.Value.FLIP);
        initialize(2, Card.Color.RED, Card.Value.FLIP);
        initialize(2, Card.Color.GREEN, Card.Value.FLIP);
        initialize(2, Card.Color.YELLOW, Card.Value.FLIP);

        //Draw ones
        initialize(2, Card.Color.BLUE, Card.Value.DRAWONE);
        initialize(2, Card.Color.RED, Card.Value.DRAWONE);
        initialize(2, Card.Color.GREEN, Card.Value.DRAWONE);
        initialize(2, Card.Color.YELLOW, Card.Value.DRAWONE);

        shuffle(completeDeck);
    }

    /**
     * Draws from the completeDeck Stack
     * @return the card at the top of the stack
     */
    public Card draw(){
        return completeDeck.pop();
    }

    /**
     * places the card on the discard pile
     * @param toPush, this is the card that will be pushed onto the stack
     */
    public void place(Card toPush){
        discardPile.push(toPush);
    }

    /**
     * When deck is zero it the discard pile will become the deck except for the current card
     * @param currentCard this is the card that will remain in the discard pile and should be the current card being viewed in the game
     */
    public void deckZero(Card currentCard){
        completeDeck = discardPile;
        completeDeck.pop();
        discardPile.clear();
        discardPile.push(currentCard);
        shuffle(completeDeck);
    }

    /**
     * Shuffles a deck
     * @param toShuffle the deck that will be shuffled
     */
    public void shuffle(Stack<Card> toShuffle){
        Collections.shuffle(toShuffle);
    }

    /**
     * This will initialize various cards for whatever amount of times are passed through to this variable
     * @param amount amount of cards you want as an int
     * @param colorOfCard the color of the card as a Card.Color
     * @param valueOfCard value of the card as a Card.Value
     */
    public void initialize(int amount, Card.Color colorOfCard, Card.Value valueOfCard){
        for(int i=0; i < amount; i++){
            completeDeck.push(new Card(valueOfCard, colorOfCard));
        }
    }

    /**
     * If the card is zero it will call the isZero function
     * @param currentCard the current card that is being played in the game
     */
    public void isZero(Card currentCard){
        if(completeDeck.size() == 0){
            deckZero(currentCard);
        }
    }

    /**
     * Returns the deck stack
     * @return the deck will be returned as a stack
     */
    public Stack<Card> getCompleteDeck() {
        return completeDeck;
    }

    /**
     * Returns the discard pile stack
     * @return the discard pile will be returned as a stack
     */
    public Stack<Card> getDiscardPile() {
        return discardPile;
    }
}