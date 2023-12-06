/**
 * @author: Abody Majeed 101227327, Mahad Mohamed Yonis 101226808, Salama Noureldean 101154365, Pietro Adamvoski 101238885
 * @date: 11/25/2023
 * @version: 3.00
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

public class Game implements Serializable {
    private boolean isLightSide;
    private Deck theDeck;
    private ArrayList<Player> playersInGame;
    private boolean winner = false;
    private Card currentCard;
    private int currentTurn = 0;
    private Stack<Card> discardPile = new Stack<>();
    private Player lastPlayer;
    private Deck lastDeck;
    private int lastTurn;
    private boolean lastMode;
    private Card  lastPlayedCard;
    private String gameState;



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

        isLightSide = true;
        currentCard = theDeck.draw();
        discardPile.add(currentCard);
    }

    public void saveGame() {
        String fileName = "game_state.ser";
        SerializationUtil.saveGame(this, fileName);
    }


    public static Game loadGame() {
        String fileName = "game_state.ser";
        return SerializationUtil.loadGame(fileName);
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
        //storeGameState();
        return true;
    }

    /**
     * Attempts to play a card from the current player's hand.
     *
     * @param cardToPlay The card the player wishes to play.
     * @return true if the card is successfully played; false otherwise.
     */
    public boolean removeCardFromHand(Card cardToPlay) {
        //storeGameState();
        Player currentPlayer = getCurrentPlayer();
        if (cardToPlay != null && isPlayable(cardToPlay)) {
            //add current card before new card is played to card pile
            this.discardPile.add(currentCard);

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
        // Check for color or value match
        if (currentCard.getColor().equals(card.getColor()) || currentCard.getValue().equals(card.getValue())) {
            return true;
        }

        // Check for special cards
        if (card.getValue() == Card.Value.WILD ||
                card.getValue() == Card.Value.WILDDRAWTWO ||
                card.getValue() == Card.Value.WILDDRAWCOLOR) {
            return true;
        }

        if (card.getValue() == Card.Value.FLIP && currentCard.getColor().equals(card.getColor())) {
            return true;
        }

        // draw one
        if (isLightSide && card.getValue() == Card.Value.DRAWONE && card.getColor() == currentCard.getColor()) {
            return true;
        }

        //check dark specific cards
        if (!isLightSide && card.getColor() == currentCard.getColor() && (card.getValue() == Card.Value.DRAWFIVE || card.getValue() == Card.Value.SKIPALL)) {
            return true;
        }

        // wild draw color and wild dark
        if (!isLightSide && (card.getValue() == Card.Value.WILDDRAWCOLOR || card.getValue() == Card.Value.WILDDARK)) {
            return true;
        }

        return false;
    }


    /**
     * Calculates and returns the winner's score.
     *
     * @return The calculated score of the winner.
     */
    public int winnerScore() {
        int winnerScore = 0;

        // Iterating through each player to calculate their hand's score
        for (int i = 0; i < playersInGame.size(); i++) {
            int sumOfPlayer = 0;

            // Iterating through each card in player's (i) hand
            for (Card card : playersInGame.get(i).getHand().getCards()) {
                int cardValue = card.getValue().ordinal();

                if (cardValue >= 0 && cardValue < 10) { // Numbered cards
                    sumOfPlayer += cardValue;
                } else {
                    switch (cardValue) {
                        case 10: // Draw One
                            sumOfPlayer += 10;
                            break;
                        case 11: // Reverse/Skip/Flip
                            sumOfPlayer += 20;
                            break;
                        case 12: // Draw Five
                            sumOfPlayer += 20;
                            break;
                        case 13: // Skip Everyone
                            sumOfPlayer += 30;
                            break;
                        case 14: // Wild
                            sumOfPlayer += 40;
                            break;
                        case 15: // Wild Draw Two
                            sumOfPlayer += 50;
                            break;
                        case 16: // Wild Draw Color
                            sumOfPlayer += 60;
                            break;
                    }
                }
            }
            winnerScore += sumOfPlayer;
        }

        return winnerScore;
    }



    /**
     * Moves the turn to the next player in the game.
     *
     * @return The index of the next player.
     */
    public void nextPlayer() {
        //update current turn for normal playing procedures
        currentTurn = (currentTurn+1) % playersInGame.size();
    }

    public void storeGameState(){
        System.out.println("REACHED STOREGAMESTATE IN GAME CLASS");
        //make the current player the last player
        if(currentTurn == playersInGame.size()){
            lastPlayer = playersInGame.get(currentTurn-1);
        }
        else{
            lastPlayer = getCurrentPlayer();
        }
        //save current hand state to restore to
        lastPlayer.storeHandBeforeTurn();
        //save the current deck
        lastDeck = getTheDeck();
        //save last turn
        lastTurn = getCurrentTurn();
        //save last mode (light or dark)
        lastMode = getIsLightSide();
    }

    public boolean getLastMode(){
        return lastMode;
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

    /**
     * Retrieves the current game state, indicating whether the game is in the Light Side or Dark Side.
     *
     * @return True if the game is in the Light Side; false if it is in the Dark Side.
     */
    public boolean getIsLightSide(){
        return isLightSide;
    }

    public Player getLastPlayer(){
        return lastPlayer;
    }

    public void undoTurn() {
        if (!discardPile.isEmpty()) {
            System.out.print("Reached undoTurn method in game");
            //get the last played card
            lastPlayedCard = discardPile.pop();
            //add played card back to the last player's hand
            lastPlayer.getHand().addPreviousCard(currentCard);

            //restore the current card
            setCurrentCard(lastPlayedCard);

            //reset the deck to the state it was one turn before
            theDeck = lastDeck;

            //reset the turn to one turn back
            currentTurn = lastTurn;

            //reset the side of the game (light or dark)
            isLightSide = getLastMode();
            System.out.println(isLightSide);
        }
    }

    public Card getLastPlayedCard(){
        return lastPlayedCard;
    }
}