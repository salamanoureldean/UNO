import java.util.Collections;
import java.util.Stack;

public class Deck {

    private Stack<Card> completeDeck = new Stack<Card>();
    private Stack<Card> discardPile = new Stack<Card>();
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
        initialize(4, null, Card.Value.WILD);

        //Wild Draw 2s
        //initialize(4, null, Card.Value.WILD_DRAW_TWO);

        shuffle(completeDeck);
    }

    public Card draw(){
        return completeDeck.pop();
    }

    public void place(Card toPush){
        discardPile.push(toPush);
    }

    public void deckZero(Card currentCard){
        completeDeck = discardPile;
        completeDeck.pop();
        discardPile.clear();
        discardPile.push(currentCard);
    }
    public void shuffle(Stack<Card> toShuffle){
        Collections.shuffle(toShuffle);
    }

    public void initialize(int amount, Card.Color colorOfCard, Card.Value valueOfCard){
        for(int i=0; i < amount; i++){
            completeDeck.push(new Card(valueOfCard, colorOfCard));
        }
    }

    public void isZero(Card currentCard){
        if(completeDeck.size() == 0){
            deckZero(currentCard);
        }
    }
}
