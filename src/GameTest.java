/**
 * @author: Abody Majeed 101227327
 * @date: 11/25/2023
 * @version: 3.00
 */
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class GameTest {

        private static Deck deck;
        private static Game game;
        private static int count;
        private final static int DECKSIZE = 100;


        public GameTest(){}
        @BeforeAll
        private static void setUp(){
            count = 0;
            game = new Game(2,0);
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
        public void testFlip() {
            game = new Game(2,0);
            Card playedCard = new Card(Card.Value.FLIP, Card.Color.BLUE);
            game.flipGameState();
            //boolean currentGameState = game.getGameState();

            //flip the complete deck so that when a card is drawn, it is a dark/light card according to the gameState
            for (Card card : game.getTheDeck().getCompleteDeck()) {
                card.flipCard();
            }

            //flip each player's hand
            for (Player player : game.getPlayersInGame()) {
                for (Card card : player.getHand().getCards()) {
                    card.flipCard();
                }

            //flip the current flip card (that has been played) to display new color
            playedCard.flipCard();
            }
            assertFalse(game.getIsLightSide());
        }


}
