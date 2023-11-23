/**
 * @author: Abody Majeed 101227327, Mahad Mohamed Yonis 101226808, Salama Noureldean 101154365, Pietro Adamvoski 101238885
 * @date: 11/14/2023
 * @version: 1.00
 */
import java.util.ArrayList;

public class Game {
    private boolean isLightSide;
    private Deck theDeck;
    private ArrayList<Player> playersInGame;
    private boolean winner = false;
    private Card currentCard;
    private int currentTurn = 0;

    /**
     * Constructs a new Game instance with a specified number of players.
     *
     * @param numPlayers The number of players in the game.
     */
    public Game(int numPlayers, int numAIPlayers) {
        playersInGame = new ArrayList<>();
        theDeck = new Deck();

        int numberOfPlayers = numPlayers;

        // Initialize human players
        for (int i = 0; i < numPlayers; i++) {
            String playerName = "Player " + (i + 1);
            playersInGame.add(new Player(playerName, theDeck));
        }

        // Initialize AI players
        for (int i = 0; i < numAIPlayers; i++) {
            String aiName = "AI " + (i + 1);
            playersInGame.add(new AIPlayer(aiName, theDeck)); // Assuming AIPlayer extends Player
        }


        currentCard = theDeck.draw();

    }


    /**
     * Adds a card to the current player's hand.
     *
     * @return true always, indicating a card has been added.
     */
    public boolean addCardToHand() {
        Player currentPlayer = getCurrentPlayer();
        theDeck.isZero(currentCard);
        currentPlayer.drawCard(theDeck);
        return true;
    }

    /**
     * Attempts to play a card from the current player's hand.
     *
     * @param cardToPlay The card the player wishes to play.
     * @return true if the card is successfully played; false otherwise.
     */
    public boolean removeCardFromHand(Card cardToPlay) {
        Player currentPlayer = getCurrentPlayer();
        if (cardToPlay != null && isPlayable(cardToPlay)) {
            currentCard = cardToPlay;
            theDeck.place(cardToPlay);
            currentPlayer.playCard(cardToPlay);
            return true;
        }
        return false;
    }

    /**
     * Toggles the game state between Light Side and Dark Side.
     */
    public void flipGameState() {
        this.isLightSide = !this.isLightSide;
    }

    /**
     * get the current game state
     * @return boolean
     */
    public boolean getGameState() {
        return isLightSide;
    }

    /**
     * Challenges a player when a Wild Draw Two card is played.
     *
     * @param challengingPlayer The player initiating the challenge.
     * @param challengedPlayer The player being challenged.
     * @param currentColor The current color in play.
     * @return true if the challenged player has matching color cards; false otherwise.
     */
    public boolean challengeWildDrawTwo(Player challengingPlayer, Player challengedPlayer, Card.Color currentColor) {
        if (challengedPlayer.hasMatchingColorCards(currentColor)) {
            challengedPlayer.drawCard(theDeck);
            challengedPlayer.drawCard(theDeck);
            return true;
        } else {
            challengingPlayer.drawCard(theDeck);
            challengingPlayer.drawCard(theDeck);
            challengingPlayer.drawCard(theDeck);
            challengingPlayer.drawCard(theDeck);
            return false;
        }
    }

    /**
     * Checks if any player has won the game.
     *
     * @return true if a player has won; false otherwise.
     */
    public boolean checkWinner() {
        for (Player player : playersInGame) {
            if (player.getHand().getCards().isEmpty()) {
                winner = true;
                return true;
            }
        }
        return false;
    }

    /**
     * checks if the card to be placed is playable
     * @param card you want to place
     * @return true if playable false if not playable
     */
    public boolean isPlayable(Card card) {
        if (currentCard.getColor() == card.getColor() || currentCard.getValue() == card.getValue()) {
            return true;
        } else if (card.getValue() == Card.Value.WILD || card.getValue() == Card.Value.WILDDRAWTWO) {
            return true;
        } else if (card.getValue() == Card.Value.SKIP || card.getValue() == Card.Value.REVERSE) {
            return card.getColor() == currentCard.getColor();
        }

        return false;
    }

    /**
     * Calculates and returns the winner's score.
     *
     * @return The calculated score of the winner.
     */
    public int winnerScore(){
        int winnerScore = 0;
        //Iterating through each player to calculate their hand's score
        for (int i = 0; i < playersInGame.size(); i++) {
            int sumOfPlayer = 0;

            //Iterating through each card in players (i) hand
            for (Card card : playersInGame.get(i).getHand().getCards()) {
                int cardValue = card.getValue().ordinal();

                //Checking the value of each card (j)
                if (cardValue >= 0 && cardValue < 10) {
                    sumOfPlayer += cardValue;
                } else {
                    switch (cardValue) {
                        case 10:
                            sumOfPlayer += 20;
                            break;
                        case 11:
                            sumOfPlayer += 30;
                            break;
                        case 12:
                            sumOfPlayer += 40;
                            break;
                        case 13:
                            sumOfPlayer += 50;
                            break;
                    }
                }
            }
            //Adding total value of player(i) score into the winner's score
            winnerScore += sumOfPlayer;
        }
        return winnerScore;
    }


    /**
     * Moves the turn to the next player in the game.
     *
     * @return The index of the next player.
     */
    public int nextPlayer() {
       currentTurn = (currentTurn+1) % playersInGame.size();
        return currentTurn;
    }

    /**
     * Retrieves the current player in the game.
     *
     * @return The current player.
     */
    public Player getCurrentPlayer() {
        return playersInGame.get(currentTurn);
    }

    /**
     * Adds a new player to the game.
     *
     * @param player The new player to be added.
     */
    public void addPlayer(Player player) {
        if (player != null) {
            playersInGame.add(player);
        }
    }

    /**
     * Gets the list of players currently in the game.
     *
     * @return An ArrayList of players in the game.
     */
    public ArrayList<Player> getPlayersInGame() {
        return playersInGame;
    }

    /**
     * Retrieves the deck used in the game.
     *
     * @return The game's deck.
     */
    public Deck getTheDeck() {
        return theDeck;
    }

    /**
     * Gets the index of the current turn.
     *
     * @return The current turn index.
     */
    public int getCurrentTurn() {
        return currentTurn;
    }

    /**
     * Retrieves the current card in play.
     *
     * @return The current card.
     */
    public Card getCurrentCard() {
        return currentCard;
    }

    /**
     * Sets the current card in play.
     *
     * @param currentCard The new current card.
     */
    public void setCurrentCard(Card currentCard) {
        this.currentCard = currentCard;
    }

    /**
     * Sets the index of the current turn.
     *
     * @param currentTurn The new current turn index.
     */
    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }

    /**
     * Gets the next player in the game.
     *
     * @return The next player.
     */
    public Player getNextPlayer() {
        int nextPlayerIndex = (currentTurn + 1) % playersInGame.size();

        return playersInGame.get(nextPlayerIndex);
    }
}