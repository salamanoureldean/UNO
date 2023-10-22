/**
 @authors: Salama Noureldean 101154365, Pietro Adamvoski 101238885
 @date: 10/22/2023
 @version: 1.00
 */

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {
    /**
     * Worker: Mahad
     * Tasks: getters, setters (Make sure these don't break), PlayCard (size), Draw Card (size), hasUNO (testing it someway)
     *
     * Worker #2: Pietro
     * Tasks: test any and all updateScore functionality
     */

    private Player player;
    private Deck deck;
    private int startingHandSize = 7;
    private static int counter;

    @BeforeAll
    private static void setUp(){
        counter = 0;
    }

    @AfterEach
    private void summary(){
        counter += 1;
        System.out.println("Number of tests ran " + counter);
    }

    @Test
    public void test_drawCard() {
        deck = new Deck();
        player = new Player("John Doe", deck);
        player.drawCard(deck);
        int handAfterDrawCard = player.getHand().getCardNum();
        assertEquals(startingHandSize + 1, handAfterDrawCard);
    }

    @Test
    public void test_HasUno() {
        // Create a player with one card in hand using the test constructor
        Deck deck = new Deck();
        Player player = new Player("John Doe", deck, 1);

        // Check if the player has Uno
        assertTrue(player.hasUno());
    }

    @Test
    public void test_getUnoCalled(){
        Deck deck = new Deck();
        Player player = new Player("John Doe", deck);

        // Check if the player called uno
        assertFalse(player.getUnoCalled());
    }

    @Test
    public void test_setUnoCalled(){
        Deck deck = new Deck();
        Player player = new Player("John Doe", deck);
        assertFalse(player.getUnoCalled());
        player.setUnoCalled(true);

        // Check if the player called uno after setting it to true
        assertTrue(player.getUnoCalled());
    }

    @Test
    public void test_getHand(){
        Deck deck = new Deck();
        Player player = new Player("John Doe", deck);

        // Check whether the hand has been populated with 7 initial cards
        Hand hand = player.getHand();
        assertTrue(hand.getCardNum() == 7);
    }
    @Test
    public void test_playCard(){
        Deck deck = new Deck();
        Player player = new Player("John Doe", deck);

        // Get the initial number of cards in the player's hand

        Hand hand = player.getHand();

        // Obtain the first card in the set to play it
        Card playedCard = hand.getCards().get(0);
        player.playCard(playedCard);

        // Size of hand after card has been played
        int currentHandSize = player.getHand().getCardNum();

        // Check if the initial hand size is greater than the updated hand size (a card was played)
        assertEquals(currentHandSize, 6);
    }

    @Test
    public void test_callUno() {
        Deck deck = new Deck();
        Player player = new Player("John Doe", deck);

        // Check that uno has been called by the player, updated to true
        player.callUno();
        assertTrue(player.getUnoCalled());
    }


    @Test
    public void test_getName() {
        Deck deck = new Deck();
        Player player = new Player("John Doe", deck);

        // Check that uno has been called by the player, updated to true
        String name = player.getName();
        assertEquals("John Doe", name);
    }

}
