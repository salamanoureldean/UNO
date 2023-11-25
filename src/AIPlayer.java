public class AIPlayer extends Player {

    private Hand hand;      // A players hand made up of cards
    private int score;     // Players score

    public AIPlayer(String name, Deck deck) {
        super(name, deck);
        this.score = 0;

    }

    public void makeMove(Card currentCard, Game game) {

        //Check if AIPlayer has one card left, if so c
        if (this.getHand().getCards().size() == 1 && this.getHand().getCards().get(0).getColor() == currentCard.getColor() && this.getHand().getCards().get(0).getValue() == currentCard.getValue()) {
            aiHasOneCardLeft(currentCard, game);
        }

        else if (game.getNextPlayer().getHand().getCards().size()<=2){
            nextPlayerHasTwoOrLessCards(currentCard, game);
        }


        else if (game.getNextPlayer().getHand().getCards().size()>2){
            playFirstPlayableCard(currentCard, game);
        }

        else {
            game.addCardToHand();
        }
    }

    private void playCard(Card card, Game game) {
        //check if the card is wildcard, if it is then set color of wildcard aswell.
        if (card.getValue() == Card.Value.WILD || card.getValue() == Card.Value.WILDDRAWTWO) {
            card.setColor(changeWildColor());
        }

        game.removeCardFromHand(card);
        game.setCurrentCard(card);
    }

    private void aiHasOneCardLeft(Card card, Game game){
        playCard(this.getHand().getCards().get(0), game);
    }

    private void nextPlayerHasTwoOrLessCards(Card currentCard, Game game){
        //If next player has 2 or fewer cards, play a card that will make it harder for them to win



        for(int i = 0; i<this.getHand().getCards().size(); i++){

            //Play WildDrawTwo as First priority
            if (this.getHand().getCards().get(i).getValue() == Card.Value.WILDDRAWTWO){
                playCard(this.getHand().getCards().get(i), game);
                break;
            }

            //Play Draw One as Second Priority
            /** Waiting for Implementation
             else if (this.getHand().getCards().get(i).getVALUE() == Card.Value.DRAWONE && this.getHand().getCards().get(i).getColor() == currentCard.getColor()){
             playCard(this.getHand().getCards().get(i), game);
             break;
             }
             */

            //Play SKIP as Third Priority
            else if (this.getHand().getCards().get(i).getValue() == Card.Value.SKIP && this.getHand().getCards().get(i).getColor() == currentCard.getColor()){
                playCard(this.getHand().getCards().get(i), game);
                break;
            }

            //Play REVERSE as Fourth Priority
            else if (this.getHand().getCards().get(i).getValue() == Card.Value.REVERSE && this.getHand().getCards().get(i).getColor() == currentCard.getColor()){
                playCard(this.getHand().getCards().get(i), game);
                break;
            }

            //Play FLIP as Fifth Priority
            /** Waiting for Implementation
             else if (this.getHand().getCards().get(i).getVALUE() == Card.Value.FLIP && this.getHand().getCards().get(i).getColor() == currentCard.getColor()){
             playCard(this.getHand().getCards().get(i), game);
             break;
             }
             */
            //Play WILD as 6th Priority
            else if (this.getHand().getCards().get(i).getValue() == Card.Value.WILD){
                playCard(this.getHand().getCards().get(i), game);
                break;
            }

            //If not Wild Card, Play first Playable Card

            else {
                playFirstPlayableCard(currentCard, game);
                break;
            }

        }


    }


    public void playFirstPlayableCard(Card currentCard, Game game){
        for (int i = 0; i < this.getHand().getCards().size(); i++) {
            if (this.getHand().getCards().get(i).getValue() == currentCard.getValue() || this.getHand().getCards().get(i).getColor() == currentCard.getColor()) {
                playCard(this.getHand().getCards().get(i), game);
                break;
         }
    }
    }

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