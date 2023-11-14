import javax.swing.*;
import java.awt.event.*;

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
    }

    private void handleNextPlayerAction() {
        game.nextPlayer();
        gui.updatePlayerTurnLabel(game.getCurrentTurn());
        gui.updatePlayerHand(game.getCurrentPlayer());
        gui.getDrawCardButton().setEnabled(true);
    }
    private void handleCardAction(ActionEvent e) {
        Card selectedCard = findSelectedCard(e);
        if (selectedCard != null) {
            processCardPlacement(selectedCard);

        }
        gui.disableHand();
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
        if (gui.removeCardFromHand(card)) {
            handleSuccessfulCardPlacement();
        } else {
            //SHOW INVALID MOVE IN STATUS
            gui.getStatusTextArea().setText("Invalid Move!");
        }
    }
    private void handleSuccessfulCardPlacement() {
        gui.getDrawCardButton().setEnabled(false);
        gui.getNextPlayerButton().setEnabled(true);
    }

    private void checkForGameWinner() {
        if (game.checkWinner()) {
            JOptionPane.showMessageDialog(gui.getGameFrame(), "You Won!");
            System.exit(0);
        }
    }
}
