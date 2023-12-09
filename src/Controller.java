/**
 * @author: Abody Majeed 101227327, Mahad Mohamed Yonis 101226808, Salama Noureldean 101154365, Pietro Adamvoski 101238885
 * @date: 11/25/2023
 * @version: 3.00
 */

import javax.swing.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.Collections;

public class Controller implements ActionListener, Serializable {
    private Game game;
    private Gui gui;
    private boolean tf;
    private boolean gameStart = true;
    private int round =1;
    private boolean flipCheck=false;

    /**
     * Constructs a new Controller object that manages the interaction between the
     * game logic (represented by the Game class) and the graphical user interface
     * (represented by the Gui class).
     *
     * @param game The Game object representing the core logic of the card game.
     * @param gui The Gui object representing the graphical user interface of the game.
     */
    public Controller(Game game, Gui gui) {
        this.game = game;
        this.gui = gui;
        gui.getUndoButton().addActionListener(this);
        gui.getRedoButton().addActionListener(this);
    }

    /**
     * Handles ActionEvents triggered by various GUI components. Depending on the source
     * of the event, it delegates the handling to specific methods such as drawing a card,
     * advancing to the next player's turn, or processing a card action.
     *
     * @param e The ActionEvent triggered by a GUI component.
     */
    public void actionPerformed(ActionEvent e) {

        round += 1;
        if(round > 1){
            gameStart = false;
        }
        if (e.getSource() == gui.getDrawCardButton()) {
            handleDrawCardAction();
        } else if (e.getSource() == gui.getNextPlayerButton()) {
            handleNextPlayerAction();
        } else if(e.getSource() == gui.getUndoButton()){
            handleUndoAction();
        }
        else if(e.getSource() == gui.getSaveButton()){
            game.saveGame();
        }
        else if(e.getSource() == gui.getLoadButton()){
            handleLoadAction();
        }
        else if(e.getSource() == gui.getRedoButton()){
            handleRedoAction();
        }
        else if(e.getSource() == gui.getReplayButton()){
            gui.getRestart().restartGame(gui.getGameFrame());
        }
        else {
            handleCardAction(e);
        }
    }

    /**
     * This method handles what happens when load is pressed.
     */
    private void handleLoadAction() {
        Game loadedGame = Game.loadGame();
        if(loadedGame != null) {
            game = loadedGame;
            gui.updateGameState(loadedGame); //update game state
            gui.refreshGuiComponents(); //refresh gui
            refreshActionListeners(); //refresh action listeners.
        }
    }

    /**
     * Handles the action of a player drawing a card. It adds a card to the current player's hand,
     * updates the GUI to display the latest card in the player's hand, disables the draw card button,
     * enables the next player button, updates the status display, and disables the player's hand.
     */
    private void handleDrawCardAction() {
        if (game.addCardToHand()) {
            gui.addLatestCardToHandDisplay();
        }
        game.getCurrentPlayer().getLastCard().getCardButton().addActionListener(this);

        game.storeGameState();

        gui.getDrawCardButton().setEnabled(false);
        gui.getNextPlayerButton().setEnabled(true);
        gui.getNextPlayerButton().setEnabled(true);
        gui.updateStatusDraw(game.getCurrentPlayer());
        gui.disableHand();
    }

    /**
     * Handles the action to advance the game to the next player's turn. It updates the
     * game state and GUI to reflect the change, enabling the draw card button, disabling
     * the next player button, and updating the player's turn label and current card display.
     */
    private void handleNextPlayerAction() {
        game.nextPlayer();
        processTurn();
        gui.getUndoButton().setEnabled(true);
    }

    /**
     * Handles the redo action in the game, enabling and disabling GUI buttons as needed,
     * flipping cards if specified, redoing the turn, updating GUI components, and refreshing
     * actionListeners.
     */
    private void handleRedoAction(){
        gui.getUndoButton().setEnabled(true);
        gui.getRedoButton().setEnabled(false);

        if (flipCheck == true) {
            game.flipGameState();
            for(Card card: game.getTheDeck().getCompleteDeck()){
                card.flipCard();
            }

            //flip each player's hand
            for(Player player : game.getPlayersInGame()) {
                for (Card card : player.getHand().getCards()) {
                    card.flipCard();
                }
            }
        }

        game.redoTurn();
        Player currentPlayer = game.getCurrentPlayer();
        gui.updateCurrentCard(game.getCurrentCard());
        gui.updatePlayerHand(currentPlayer);

        gui.refreshGuiComponents(); //refresh gui
        refreshActionListeners(); //refresh action listeners.
        gui.getNextPlayerButton().setEnabled(true);
        gui.disableHand();
    }

    /**
     * Handles the undo action in the game, updating GUI buttons, flipping the game state
     * if necessary, and undoing the last turn.
     */
    private void handleUndoAction() {
        gui.getUndoButton().setEnabled(false);
        gui.getRedoButton().setEnabled(true);
        System.out.println("reached handleUndoAction in controller");
        //game.getLastPlayer().restoreStateBeforeTurn();
        if(game.getLastMode() != game.getIsLightSide()){
            flipCheck = true;
            System.out.println("\nFLIPPED GAME STATE AFTER UNDO");
            game.flipGameState();
            game.undoTurn();
            //flip the complete deck so that when a card is drawn, it is a dark/light card according to the gameState
            for(Card card: game.getTheDeck().getCompleteDeck()){
                card.flipCard();
            }

            //flip each player's hand
            for(Player player : game.getPlayersInGame()) {
                for (Card card : player.getHand().getCards()) {
                    card.flipCard();
                }
            }

            gui.refreshGuiComponents(); //refresh gui
            refreshActionListeners(); //refresh action listeners.

            //cardFunctionality(game.getLastPlayedCard());

            processUndoTurn();
            gui.updateCurrentCard(game.getCurrentCard());
            gui.getUndoButton().setEnabled(false);
            gui.getStatusTextArea().setText("Turn has been Undone.\nCurrent Card: " + game.getCurrentCard().stringCard());
            return;
        }

        game.undoTurn();

        gui.refreshGuiComponents(); //refresh gui
        refreshActionListeners(); //refresh action listeners.

        //cardFunctionality(game.getLastPlayedCard());

        processUndoTurn();
        gui.updateCurrentCard(game.getCurrentCard());
        gui.getUndoButton().setEnabled(false);
        gui.getStatusTextArea().setText("Turn has been Undone.\nCurrent Card: " + game.getCurrentCard().stringCard());
    }


    /**
     * Handles the undo action in the game, updating GUI buttons, flipping the game state
     * if necessary, and undoing the last turn.
     */
    private void processUndoTurn() {
        Player currentPlayer = game.getCurrentPlayer();
        gui.updatePlayerTurnLabel(game.getCurrentTurn());

        if (currentPlayer instanceof AIPlayer) {
            AIPlayer aiPlayer = (AIPlayer) currentPlayer;
            gui.updateAIHand(aiPlayer, game.getIsLightSide());

            boolean shouldDrawCard = aiPlayer.shouldDrawCard(game.getCurrentCard(), game);
            if (shouldDrawCard) {
                game.addCardToHand();
                handleDrawCardAction();
            } else {
                Card playedCard = aiPlayer.makeMove(game.getCurrentCard(), game);
                cardFunctionality(playedCard);
            }

            gui.updateCurrentCard(game.getCurrentCard());
            gui.updateAIHand(aiPlayer, game.getIsLightSide());

        } else {
            // Human player logic
            gui.updatePlayerHand(game.getLastPlayer());
            gui.getDrawCardButton().setEnabled(true);
            gui.getNextPlayerButton().setEnabled(false);
        }

        gui.getStatusTextArea().setText("Current Card: " + game.getCurrentCard().stringCard());
        checkForGameWinner();
    }

    /**
     * Processes the current turn, updating the player's turn label, handling AI player moves,
     * and enabling/disabling relevant GUI components. It also updates the current card in the GUI,
     * displays the current card information in the status text area, and checks for a game winner.
     */
    private void processTurn() {
        Player currentPlayer = game.getCurrentPlayer();
        gui.updatePlayerTurnLabel(game.getCurrentTurn());

        if (currentPlayer instanceof AIPlayer) {
            AIPlayer aiPlayer = (AIPlayer) currentPlayer;
            gui.updateAIHand(aiPlayer, game.getIsLightSide());

            boolean shouldDrawCard = aiPlayer.shouldDrawCard(game.getCurrentCard(), game);
            if (shouldDrawCard) {
                game.addCardToHand();
                handleDrawCardAction();
            } else {
                Card playedCard = aiPlayer.makeMove(game.getCurrentCard(), game);
                cardFunctionality(playedCard);
            }

            gui.updateCurrentCard(game.getCurrentCard());
            gui.updateAIHand(aiPlayer, game.getIsLightSide());

        } else {
            // Human player logic
            gui.updatePlayerHand(game.getCurrentPlayer());
            gui.getDrawCardButton().setEnabled(true);
            gui.getNextPlayerButton().setEnabled(false);
        }

        gui.getStatusTextArea().setText("Current Card: " + game.getCurrentCard().stringCard());
        checkForGameWinner();
    }

    /**
     * Handles the action triggered by selecting a card button. It identifies the
     * selected card from the ActionEvent, processes its placement if it is a valid move,
     * and checks for a game winner after the card is played.
     *
     * @param e The ActionEvent triggered by selecting a card button.
     */
    private void handleCardAction(ActionEvent e) {
        try {
            Card selectedCard = findSelectedCard(e);
            if (selectedCard != null && selectedCard != game.getCurrentCard()) {
                processCardPlacement(selectedCard);
            }
            checkForGameWinner();
        } catch (Exception ex) {
            ex.printStackTrace(); // Or use logging
            JOptionPane.showMessageDialog(gui.getGameFrame(),
                    "Error occurred in Card Action: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        if (game.getCurrentPlayer() instanceof AIPlayer) {
            handleSuccessfulCardPlacement();
        }
    }

    /**
     * Finds the selected card associated with the ActionEvent in the current player's hand.
     * It iterates through the cards in the player's hand and checks if the source of the
     * ActionEvent matches the button associated with each card.
     *
     * @param e The ActionEvent triggered by selecting a card button.
     * @return The selected card if found, or null if no matching card is found.
     */
    private Card findSelectedCard(ActionEvent e) {
        for (Card card : game.getCurrentPlayer().getHand().getCards()) {
            if (e.getSource() == card.getCardButton()) {
                return card;
            }
        }
        return null;
    }

    /**
     * Processes the placement of a card in the game. Depending on the card type,
     * it may trigger specific actions such as handling Wild Draw Two challenges,
     * executing the functionality of the card, and updating the game state and GUI.
     *
     * @param card The card to be placed in the game.
     */
    private void processCardPlacement(Card card) {
        game.storeGameState();
        if (gui.removeCardFromHand(card)) {
            cardFunctionality(card);
            handleSuccessfulCardPlacement();
        } else {
            //SHOW INVALID MOVE IN STATUS
            gui.getStatusTextArea().setText("Invalid Move!");
        }
    }

    /**
     * Prompts the specified player with a confirmation dialog to determine if they
     * want to challenge the playing of a Wild Draw Two card. Returns true if the player
     * chooses to challenge, and false otherwise.
     *
     * @param player The player to prompt for the challenge.
     * @return true if the player chooses to challenge, false otherwise.
     */
    private boolean promptForChallenge(Player player) {
        return JOptionPane.showConfirmDialog(gui.getGameFrame(),
                player.getName() + ", do you want to challenge the Wild Draw Two?",
                "Challenge",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    /**
     * Handles the actions to be performed after a successful card placement, such as
     * disabling the draw card button, enabling the next player button, and disabling
     * the player's hand in the GUI.
     */
    private void handleSuccessfulCardPlacement() {
        gui.getDrawCardButton().setEnabled(false);
        gui.getNextPlayerButton().setEnabled(true);
        gui.disableHand();
    }

    /**
     * Checks if there is a game winner by invoking the `checkWinner` method of the
     * associated Game object. If a winner is found, displays a congratulatory message
     * through a JOptionPane and exits the program.
     */
    private void checkForGameWinner() {
        if (game.checkWinner()) {
            int playerWon = 0;
            for(int i=0; i < game.getPlayersInGame().size(); i++){
                if(game.getCurrentPlayer().equals(game.getPlayersInGame().get(i))){
                    if(i == game.getPlayersInGame().size()-1){
                        playerWon = 0;
                        break;
                    } else if (i == 0) {
                        playerWon = game.getPlayersInGame().size()-1;
                        break;
                    } else{
                        playerWon = i;
                        break;
                    }
                }
            }
            JOptionPane.showMessageDialog(gui.getGameFrame(), "Congratulations " + game.getPlayersInGame().get(playerWon).getName() + " you WON with a score of: " + game.winnerScore());
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    ExitMenuGUI exitMenu = new ExitMenuGUI();
                    exitMenu.setVisible(true);
                }
            });
        }
    }

    /**
     * Executes the functionality associated with a played card. This method handles
     * various card types such as REVERSE, SKIP, WILD, and WILDDRAWTWO, and the flip values. It interacts
     * with the game state and GUI to perform actions like reversing the order of players,
     * skipping a player's turn, allowing color selection for Wild cards, and making the
     * next player draw two cards for Wild Draw Two cards.
     *
     * @param playedCard The card that has been played and whose functionality is to be executed.
     */
    public void cardFunctionality(Card playedCard) {
        int nextPlayerIndex = (game.getCurrentTurn() + 1) % game.getPlayersInGame().size();
        Player nextPlayer = game.getPlayersInGame().get(nextPlayerIndex);
        Card.Value cardValue = playedCard.getValue();
        //Card.Color cardColor = playedCard.getColor();
        String newColor;
        JFrame frame = new JFrame();
        String[] choose = {"Red","Green","Blue","Yellow"};
        //dark color selection
        String[] chooseDark = {"Pink", "Purple", "Teal", "Orange"};

        switch (cardValue) {
            case REVERSE:
                Collections.reverse(game.getPlayersInGame());
                frame.setVisible(true);
                JOptionPane.showMessageDialog(frame,"Cards have been reversed!");
                frame.setVisible(false);
                break;
            case SKIP:
                frame.setVisible(true);
                if (game.getCurrentTurn() + 1 > game.getPlayersInGame().size()){
                    game.setCurrentTurn(0);
                }
                else{
                    game.setCurrentTurn(game.getCurrentTurn()+1);
                }
                JOptionPane.showMessageDialog(frame, "Player was skipped");
                frame.setVisible(false);
                break;
            case WILD:
                frame.setVisible(true);
                newColor = (String) JOptionPane.showInputDialog(frame,
                        "Choose the color",
                        "Color selection",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        choose,
                        0);
                frame.setVisible(false);
                // Changing current color based on user input
                if (newColor != null && newColor.equals("Red")) {
                    game.getCurrentCard().setColor(Card.Color.RED);
                    gui.getStatusTextArea().setText("The color has been changed to red");
                }
                else if (newColor != null && newColor.equals("Green")) {
                    game.getCurrentCard().setColor(Card.Color.GREEN);
                    gui.getStatusTextArea().setText("The color has changed to green");
                }
                else if (newColor != null && newColor.equals("Blue")) {
                    game.getCurrentCard().setColor(Card.Color.BLUE);
                    gui.getStatusTextArea().setText("The color has changed to blue");
                }
                else if (newColor != null && newColor.equals("Yellow")) {
                    game.getCurrentCard().setColor(Card.Color.YELLOW);
                    gui.getStatusTextArea().setText("The color has been changed to yellow.");
                }
                else{
                    game.getCurrentCard().setColor(game.getCurrentCard().getColor());
                    gui.getStatusTextArea().setText("The color has not been changed");
                    gui.getNextPlayerButton().setEnabled(false);
                    cardFunctionality(playedCard);
                    gui.getStatusTextArea().setText("Please select the wild card again");
                }
                break;
            case WILDDRAWTWO:
                // Allow the player to choose a color
                newColor = (String) JOptionPane.showInputDialog(frame,
                        "Choose the color",
                        "Color selection",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        choose,
                        0);

                if (newColor != null) {
                    // Set the chosen color
                    switch (newColor) {
                        case "Red": game.getCurrentCard().setColor(Card.Color.RED); break;
                        case "Green": game.getCurrentCard().setColor(Card.Color.GREEN); break;
                        case "Blue": game.getCurrentCard().setColor(Card.Color.BLUE); break;
                        case "Yellow": game.getCurrentCard().setColor(Card.Color.YELLOW); break;
                    }
                    gui.getStatusTextArea().setText("The color has been changed to " + newColor.toLowerCase());
                } else {
                    game.getCurrentCard().setColor(game.getCurrentCard().getColor());
                    gui.getStatusTextArea().setText("The color has not been changed");
                }

                Player currentPlayer = game.getCurrentPlayer();

                boolean challengeAccepted = promptForChallenge(nextPlayer);

                if (challengeAccepted) {
                    tf = true;
                    boolean challengeResult = game.challengeWildDrawTwo(nextPlayer, currentPlayer, game.getCurrentCard().getColor());

                    if (challengeResult) {
                        gui.getStatusTextArea().setText(currentPlayer.getName() + " played Wild Draw Two illegally. They draw 2 cards.");
                    } else {
                        gui.getStatusTextArea().setText(nextPlayer.getName() + " challenged incorrectly. They draw 4 cards.");
                    }
                } else {
                    tf = false;
                    // Next player draws two cards if the challenge is not accepted
                    for (int i = 0; i < 2; i++) {
                        nextPlayer.drawCard(game.getTheDeck());
                    }
                }

                gui.updatePlayerHand(currentPlayer);
                gui.updatePlayerHand(nextPlayer);
                gui.updateCurrentCard(game.getCurrentCard());
                break;

            case FLIP:
                if(game.isPlayable(playedCard)){
                    game.flipGameState();
                    //boolean currentGameState = game.getGameState();

                    //flip the complete deck so that when a card is drawn, it is a dark/light card according to the gameState
                    for(Card card: game.getTheDeck().getCompleteDeck()){
                        card.flipCard();
                    }

                    //flip each player's hand
                    for(Player player : game.getPlayersInGame()) {
                        for (Card card : player.getHand().getCards()) {
                            card.flipCard();
                        }
                    }

                    //flip the current flip card (that has been played) to display new color
                    playedCard.flipCard();

                    //gui.updatePlayerHand(currentPlayer);
                    for(Player player: game.getPlayersInGame()){
                        if(player instanceof AIPlayer) {
                            gui.updateAIHand((AIPlayer) player, game.getIsLightSide());
                        }
                        else {
                            gui.updatePlayerHand(player);
                        }
                    }
                    gui.updateCurrentCard(playedCard);
                }
                break;

            case DRAWFIVE:

                nextPlayerIndex = (game.getCurrentTurn() + 1) % game.getPlayersInGame().size();
                nextPlayer = game.getPlayersInGame().get(nextPlayerIndex);

                for (int i = 0; i < 5; i++){
                    nextPlayer.drawCard(game.getTheDeck());
                    nextPlayer.getLastCard().getCardButton().addActionListener(this);
                }
                gui.updatePlayerHand(nextPlayer);

                break;

            case SKIPALL:
                if (game.isPlayable(playedCard)){
                    int currentPlayerIndex = game.getCurrentTurn();
                    if(currentPlayerIndex-1 < 0){
                        currentPlayerIndex = game.getPlayersInGame().size();
                    }
                    game.setCurrentTurn(currentPlayerIndex-1);
                }
                break;

            case WILDDRAWCOLOR:
                newColor = (String) JOptionPane.showInputDialog(frame,
                        "Choose the color",
                        "Color selection",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        chooseDark,
                        0);

                if (newColor != null && newColor.equals("Pink")) {
                    game.getCurrentCard().setColor(Card.Color.PINK);
                    gui.getStatusTextArea().setText("The color has been changed to pink");
                }
                else if (newColor != null && newColor.equals("Purple")) {
                    game.getCurrentCard().setColor(Card.Color.PURPLE);
                    gui.getStatusTextArea().setText("The color has changed to purple");
                }
                else if (newColor != null && newColor.equals("Teal")) {
                    game.getCurrentCard().setColor(Card.Color.TEAL);
                    gui.getStatusTextArea().setText("The color has changed to teal");
                }
                else if (newColor != null && newColor.equals("Orange")) {
                    game.getCurrentCard().setColor(Card.Color.ORANGE);
                    gui.getStatusTextArea().setText("The color has been changed to orange.");
                }
                else {
                    game.getCurrentCard().setColor(game.getCurrentCard().getColor());
                    gui.getStatusTextArea().setText("The color has not been changed");
                    gui.getNextPlayerButton().setEnabled(false);
                    cardFunctionality(playedCard);
                    gui.getStatusTextArea().setText("Please select the wild card again");
                }

                currentPlayer = game.getCurrentPlayer();
                Card.Color chosenColor = convertStringToColor(newColor);

                challengeAccepted = promptForChallenge(nextPlayer);

                if(challengeAccepted){
                    if(currentPlayer.hasMatchingColorCards(chosenColor)){
                        drawUntilColorFound(currentPlayer, chosenColor);
                        break;
                    }else {
                        drawUntilColorFound(nextPlayer, chosenColor);
                        nextPlayer.drawCard(game.getTheDeck());
                        game.getNextPlayer().getLastCard().getCardButton().addActionListener(this);
                        nextPlayer.drawCard(game.getTheDeck());
                        game.getNextPlayer().getLastCard().getCardButton().addActionListener(this);
                        gui.updatePlayerHand(nextPlayer);
                        break;
                    }
                }
                break;

            case DRAWONE:
                if(gameStart == true){
                    nextPlayer = game.getCurrentPlayer();

                    nextPlayer.drawCard(game.getTheDeck());
                    nextPlayer.getLastCard().getCardButton().addActionListener(this);

                    gui.updatePlayerHand(nextPlayer);
                }
                else {
                    nextPlayer = game.getNextPlayer();

                    nextPlayer.drawCard(game.getTheDeck());
                    nextPlayer.getLastCard().getCardButton().addActionListener(this);

                    gui.updatePlayerHand(nextPlayer);
                }
                break;

            case WILDDARK:
                frame.setVisible(true);
                newColor = (String) JOptionPane.showInputDialog(frame,
                        "Choose the color",
                        "Color selection",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        chooseDark,
                        0);
                frame.setVisible(false);
                // Changing current color based on user input
                if (newColor != null && newColor.equals("Pink")) {
                    game.getCurrentCard().setColor(Card.Color.PINK);
                    gui.getStatusTextArea().setText("The color has been changed to pink");
                }
                else if (newColor != null && newColor.equals("Purple")) {
                    game.getCurrentCard().setColor(Card.Color.PURPLE);
                    gui.getStatusTextArea().setText("The color has changed to purple");
                }
                else if (newColor != null && newColor.equals("Teal")) {
                    game.getCurrentCard().setColor(Card.Color.TEAL);
                    gui.getStatusTextArea().setText("The color has changed to teal");
                }
                else if (newColor != null && newColor.equals("Orange")) {
                    game.getCurrentCard().setColor(Card.Color.ORANGE);
                    gui.getStatusTextArea().setText("The color has been changed to orange.");
                }
                else{
                    game.getCurrentCard().setColor(game.getCurrentCard().getColor());
                    gui.getStatusTextArea().setText("The color has not been changed");
                    gui.getNextPlayerButton().setEnabled(false);
                    cardFunctionality(playedCard);
                    gui.getStatusTextArea().setText("Please select the wild card again");
                }
                break;
        }


    }

    /**
     * Converts a color string to the corresponding Card.Color enum.
     *
     * @param colorStr The color string.
     * @return The corresponding Card.Color enum.
     */
    private Card.Color convertStringToColor(String colorStr) {
        switch (colorStr) {
            case "Red": return Card.Color.RED;
            case "Green": return Card.Color.GREEN;
            case "Blue": return Card.Color.BLUE;
            case "Yellow": return Card.Color.YELLOW;
            case "Teal": return Card.Color.TEAL;
            case "Pink": return Card.Color.PINK;
            case "Purple": return Card.Color.PURPLE;
            case "Orange": return Card.Color.ORANGE;
            default: return null;
        }
    }

    /**
     * Draws cards from the deck for the specified player until a card of the chosen color is found.
     *
     * @param player      The player whom's cards are drawn.
     * @param chosenColor The color to be matched.
     */
    private void drawUntilColorFound(Player player, Card.Color chosenColor) {
        boolean found = false;
        while (!found) {
            player.drawCard(game.getTheDeck());
            player.getLastCard().getCardButton().addActionListener(this);
            Card lastDrawnCard = player.getLastCard();
            if (lastDrawnCard.getColor() == chosenColor) {
                found = true;
            }
            gui.updatePlayerHand(player);
        }
    }

    /**
     * Refreshes action listeners for the card buttons in each player's hand.
     * Iterates through all players and their hands, adding action listeners to each card button.
     */
    private void refreshActionListeners(){
        for (int i = 0; i < game.getPlayersInGame().size(); i++) {
            for (int j = 0; j < game.getPlayersInGame().get(i).getHand().getCards().size(); j++) {
                game.getPlayersInGame().get(i).getHand().getCards().get(j).getCardButton().addActionListener(this);
            }
        }
    }



}