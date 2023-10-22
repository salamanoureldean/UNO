import java.util.ArrayList;
import java.util.Stack;
public class Hand {
    private ArrayList<Card> cards;
    private int cardNum;

    /**
     *Hand constructor, will randomly select 7 cards into the players hand from a shuffled deck
     */
    public Hand(Stack<Card> deck){
        cards = new ArrayList<Card>();
        for(int i=0;i < 7; i++){
            cards.add(deck.pop());
            cardNum += 1;
        }
    }

    /**
     * addCard method, adds a card
     */
    public void addCard(Stack<Card> deck){
        cards.add(deck.pop());
        cardNum +=1;
    }

    /**
     * removeCard method, removes a card
     * @param card - removes specified card in the param
     */
    public void removeCard(Card card){
        cards.remove(card);
        cardNum -= 1;
    }

    /**
     * printAll method prints all cards
     */
    public void printAll(){
        for(Card card : cards){
            System.out.print(card.getColor().toString() + " " + card.getValue().toString() + ", ");
        }
    }

    /**
     * getHand will return whatever is in the hand
     * @return - the arraylist
     */
    public ArrayList<Card> getHand() {
        return cards;
    }

    /**
     * gets number of cards in the hand
     * @return - number of cards in hand
     */
    public int getCardNum() {
        return cardNum;
    }
    public ArrayList<Card> getCards(){return cards;}
}


