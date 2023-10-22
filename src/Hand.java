import java.util.ArrayList;
public class Hand {
    int start = 7;
    private ArrayList<Card> cards;
    private int cardNum;

    /**
     *Hand constructor, will randomly select 7 cards into the players hand
     */
    public Hand(){
        cards = new ArrayList<Card>();
        for(int i=0;i < start; i++){
            cards.add(new Card());
            cardNum += 1;
        }
    }

    /**
     * addCard method, adds a card
     */
    public void addCard(){
        cards.add(new Card());
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


