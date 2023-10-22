/**
 * @author: Abody Majeed 101227327
 * @editor: Salama Noureldean 101154365
 * @date: 10/22/2023
 * @version: 1.00
 */

import java.util.ArrayList;
import java.util.Stack;
public class Hand {
    private ArrayList<Card> cards;
    private int cardNum;

    /**
     *Hand constructor, will randomly select 7 cards into the players hand from a shuffled deck
     */
    public Hand(Deck deck){
        cards = new ArrayList<Card>();
        for(int i=0;i < 7; i++){
            cards.add(deck.draw());
            cardNum += 1;
        }
    }

    /**
     * Constructor that allows for
     * @param deck
     * @param num
     */
    public Hand(Deck deck, int num){
        cards = new ArrayList<Card>();
        for(int i=0;i < num; i++){
            cards.add(deck.draw());
            cardNum += 1;
        }
    }

    /**
     * addCard method, adds a card
     */
    public void addCard(Deck deck){
        cards.add(deck.draw());
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
            System.out.print(card.stringCard() + ", ");
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

    /**
     * returns the arraylist of cards
     * @return an array of cards
     */
    public ArrayList<Card> getCards(){return cards;}
}


