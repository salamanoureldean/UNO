/**
 * @author: Salama
 * @date: 10/22/2023
 * @version: 1.00
 */

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class CardTest {
    private static int counter;
    /**
     * Worker: Salama
     * Task: getters, setters: Make sure it doesn't break
     */

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
    public void test_defaultCardConstructor(){
        Card card = new Card(Card.Value.ZERO, Card.Color.BLUE);
        assertEquals(Card.Value.ZERO, card.getValue());
        assertEquals(Card.Color.BLUE, card.getColor());
    }

    @Test
    public void test_wildCardCreation(){
        Card card = new Card(Card.Value.WILD, Card.Color.BLANK);
        assertEquals(Card.Value.WILD, card.getValue());
        assertEquals(Card.Color.BLANK, card.getColor());
    }

    @Test
    public void test_getValue(){
        Card card = new Card(Card.Value.ZERO, Card.Color.BLUE);
        assertEquals(Card.Value.ZERO, card.getValue());
    }

    @Test
    public void test_getColor(){
        Card card = new Card(Card.Value.ZERO, Card.Color.BLUE);
        assertEquals(Card.Color.BLUE, card.getColor());
    }

    @Test
    public void test_setColor(){
        Card card = new Card(Card.Value.ZERO, Card.Color.BLUE);
        card.setColor(Card.Color.RED);
        assertEquals(Card.Color.RED, card.getColor());
    }
}

