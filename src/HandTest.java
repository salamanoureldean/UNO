/**
 * @Author: Abody Majeed 101227327
 * @Date: 10/22/2023
 * @Version: 1.00
 */
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import java.awt.*;
import java.sql.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HandTest {

    public static Deck deck;
    public static Hand hand;
    private static int count;

    public HandTest(){}

    @BeforeAll
    private static void setUp(){
        count = 0;
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
        deck = new Deck();
        hand = new Hand(deck);
        assertEquals(hand.getHand().size(), 7);
        assertNotEquals(hand.getHand().get(0), null);
    }
    @Test
    public void test_addCard(){
        deck = new Deck();
        hand = new Hand(deck);
        hand.addCard(deck);
        assertEquals(hand.getHand().size(), 8);
        assertEquals(hand.getCardNum(), 8);
        assertNotEquals(hand.getHand().get(7), null);
    }
    @Test
    public void test_getCards(){
        deck = new Deck();
        hand = new Hand(deck);
        assertTrue(hand.getCards() instanceof ArrayList<Card>, String.valueOf(true));
    }
}
