public class AIPlayer extends Player{

    public AIPlayer(String name, Deck deck) {
        super(name, deck);
    }

    public void makeMove(Card CurrentCard, Game game){
        //Pietro
    }

    private void playCard(Card card, Game game){
        //check if the card is wildcard, if it is then set color of wildcard aswell.
        if(card.getVALUE() == Card.Value.WILD || card.getVALUE() == Card.Value.WILDDRAWTWO){
            //changeWildColor();
        }
        //place the card
        this.getHand().removeCard(card);
        game.setCurrentCard(card);

    }

    public Card.Color changeWildColor(){
        //This method will determines the most common color in the AI's hand, and changes to that.
        return Card.Color.BLANK; //just for now so no errors
    }
}
