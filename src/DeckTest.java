import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DeckTest {
    /**
     * Worker: Abody
     * Task: test ordering based off enum, compare size of the deck to set size, test draw and place functions based off sizes not content
     */
    private static Deck deck;
    private static int count = 0;
    private final static int DECKSIZE = 100;


    public DeckTest(){

    }

    @AfterEach
    public static void increment(){
        count += 1;
    }
    @AfterAll
    private static void testsDone(){
        System.out.print("All deck tests are done");
        System.out.printf("%d tests were ran\n", count);
    }

    @Test
    public void test_DefaultConstructor(){
        Deck deck_test = new Deck();
        deck_test.getCompleteDeck().stream().sorted();
        deck = deck_test;
        assertEquals(deck.getCompleteDeck().size(), DECKSIZE);
        assertNotEquals(deck.draw(), null);
    }

    @Test
    public void test_Shuffle(){
        Deck deck_test = new Deck();
        deck = new Deck();
        assertNotEquals(deck, deck_test);
    }

    @Test
    public void test_Draw(){
        deck = new Deck();
        deck.draw();
        deck.draw();
        assertEquals(deck.getCompleteDeck().size(), 98);
    }
    @Test
    public void test_Place(){
        Card card1 = new Card(Card.Value.ZERO, Card.Color.RED);
        Card card2 = new Card(Card.Value.ZERO, Card.Color.GREEN);
        deck = new Deck();
        deck.place(card1);
        deck.place(card2);
        assertEquals(deck.getDiscardPile().size(), 2);
        assertTrue(deck.getDiscardPile().contains(card1), String.valueOf(true));
        assertFalse(deck.getCompleteDeck().contains(card1), String.valueOf(false));
        assertTrue(deck.getDiscardPile().contains(card2), String.valueOf(true));
        assertFalse(deck.getCompleteDeck().contains(card2), String.valueOf(false));
    }

}
