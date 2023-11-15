import javax.swing.*;
import java.awt.event.*;
import java.util.Collections;

public class Controller implements ActionListener {
    private Game game;
    private Gui gui;

    public Controller(Game game, Gui gui) {
        this.game = game;
        this.gui = gui;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gui.getDrawCardButton()) {
            handleDrawCardAction();
        } else if (e.getSource() == gui.getNextPlayerButton()) {
            handleNextPlayerAction();
        } else {
            handleCardAction(e);
        }
    }

    private void handleDrawCardAction() {
        if (game.addCardToHand()) {
            gui.addLatestCardToHandDisplay();
        }
        gui.getDrawCardButton().setEnabled(false);
        gui.getNextPlayerButton().setEnabled(true);
        gui.updateStatusDraw(game.getCurrentPlayer());
        gui.disableHand();
    }

    private void handleNextPlayerAction() {
        game.nextPlayer();
        gui.updatePlayerTurnLabel(game.getCurrentTurn());
        gui.updatePlayerHand(game.getCurrentPlayer());
        gui.getDrawCardButton().setEnabled(true);
        gui.getNextPlayerButton().setEnabled(false);
        gui.getStatusTextArea().setText("Current Card: " + game.getCurrentCard().stringCard());
    }
    private void handleCardAction(ActionEvent e) {

        Card selectedCard = findSelectedCard(e);
        if (selectedCard != null && selectedCard!=game.getCurrentCard()) {
            processCardPlacement(selectedCard);

        }
        checkForGameWinner();

    }

    private Card findSelectedCard(ActionEvent e) {
        for (Card card : game.getCurrentPlayer().getHand().getCards()) {
            if (e.getSource() == card.getCardButton()) {
                return card;
            }
        }
        return null;
    }

    private void processCardPlacement(Card card) {

        if (card.isWildDrawTwo()) {

            handleWildDrawTwoChallenge(card);
        }
        if (gui.removeCardFromHand(card)) {
            cardFunctionality(card);
            handleSuccessfulCardPlacement();
            gui.disableHand();
        } else {
            //SHOW INVALID MOVE IN STATUS
            gui.getStatusTextArea().setText("Invalid Move!");
        }
    }

    private void handleWildDrawTwoChallenge(Card card) {
        Player currentPlayer = game.getCurrentPlayer();
        Player nextPlayer = game.getNextPlayer();

        boolean challengeAccepted = promptForChallenge(nextPlayer);

        if (challengeAccepted) {
            boolean challengeResult = game.challengeWildDrawTwo(nextPlayer, currentPlayer, card.getColor());

            if (challengeResult) {
                gui.getStatusTextArea().setText(currentPlayer.getName() + " played Wild Draw Two illegally. They draw 2 cards.");
            } else {
                gui.getStatusTextArea().setText(nextPlayer.getName() + " challenged incorrectly. They draw 4 cards.");
            }


            gui.updatePlayerHand(currentPlayer);
            gui.updatePlayerHand(nextPlayer);
        } else {
            //Case where challenge isnt accepted
        }
    }

    private boolean promptForChallenge(Player player) {
        return JOptionPane.showConfirmDialog(gui.getGameFrame(),
                player.getName() + ", do you want to challenge the Wild Draw Two?",
                "Challenge",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    private void handleSuccessfulCardPlacement() {
        gui.getDrawCardButton().setEnabled(false);
        gui.getNextPlayerButton().setEnabled(true);
        gui.disableHand();
    }

    private void checkForGameWinner() {
        if (game.checkWinner()) {
            JOptionPane.showMessageDialog(gui.getGameFrame(), "You Won!");
            System.exit(0);
        }
    }
    public void cardFunctionality(Card playedCard) {
        Card.Value cardValue = playedCard.getVALUE();
        //Card.Color cardColor = playedCard.getColor();
        String newColor;
        JFrame frame = new JFrame();
        String[] choose = {"Red","Green","Blue","Yellow"};

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
                // Getting next player's index in order to make them draw two cards
                int nextPlayerIndex = (game.getCurrentTurn() + 1) % game.getPlayersInGame().size();
                Player nextPlayer = game.getPlayersInGame().get(nextPlayerIndex);
                for (int i = 0; i < 2; i++){
                    nextPlayer.drawCard(game.getTheDeck());
                }



                // Changing current color of cards being played based on user input
                frame.setVisible(true);
                newColor = (String) JOptionPane.showInputDialog(frame,
                        "Choose the color",
                        "Color selection",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        choose,
                        0);

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
                else {
                    game.getCurrentCard().setColor(game.getCurrentCard().getColor());
                    gui.getStatusTextArea().setText("The color has not been changed");
                    gui.getNextPlayerButton().setEnabled(false);
                    cardFunctionality(playedCard);
                    gui.getStatusTextArea().setText("Please select the wild card again");
                }
                break;
        }
    }
}