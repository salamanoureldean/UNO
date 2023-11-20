import java.util.HashMap;

public class AIPlayer extends Player {

    private Hand hand;      // A players hand made up of cards
    private int score;     // Players score

    public AIPlayer(String name, Deck deck) {
        super(name, deck);
        this.score = 0;

    }

    public void makeMove(Card CurrentCard, Game game) {
        //Pietro
    }

    private void playCard(Card card, Game game) {
        //check if the card is wildcard, if it is then set color of wildcard aswell.
        if (card.getVALUE() == Card.Value.WILD || card.getVALUE() == Card.Value.WILDDRAWTWO) {
            //changeWildColor();
        }
        //place the card
        this.getHand().removeCard(card);
        game.setCurrentCard(card);

    }

    public Card.Color changeWildColor() {
        //This method will determines the most common color in the AI's hand, and changes to that.
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
