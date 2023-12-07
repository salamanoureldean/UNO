/**
 * @author: Abody Majeed 101227327, Mahad Mohamed Yonis 101226808, Salama Noureldean 101154365
 * @date: 11/25/2023
 * @version: 3.00
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.*;

public class Gui implements Serializable {
    private static JFrame frame1;
    private JFrame gameFrame;
    private int numberOfPlayers;
    int numberOfAIPlayers;
    private JPanel topCardPanel;
    private JPanel handPanel;
    private JPanel drawPanel;
    private JLabel turnLabel;
    private JLabel statusLabel;
    private JPanel bottomPanel;
    private ArrayList<JButton> cardHand;
    private ArrayList<Card> playerCards;
    private Game model;
    private JButton nextPlayerButton;
    private JButton drawCardButton;
    private JButton undoButton;
    private JButton redoButton;
    private JTextArea statusTextArea;
    private boolean mode;
    private JButton saveGameButton;
    private JButton loadGameButton;

    /**
     * Constructor for the GUI class which sets up the frame.
     */
    public Gui() {
        mode = true;
        frame1 = new JFrame();
        boolean validSelection = false;

        //Player and AI player selection
        while (!validSelection) {
            Object[] possibilities = {0, 1, 2, 3, 4, 5, 6};
            numberOfPlayers = (Integer) JOptionPane.showInputDialog(
                    frame1,
                    "Choose the number of players:",
                    "Number of players selection",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities,
                    0);

            Integer[] aiOptions = {0, 1, 2, 3, 4, 5, 6};
            numberOfAIPlayers = (Integer) JOptionPane.showInputDialog(
                    frame1,
                    "Select the number of AI players:",
                    "AI Players",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    aiOptions,
                    0);

            if (numberOfPlayers != 0 &&
                    numberOfPlayers + numberOfAIPlayers >= 2 && numberOfPlayers > 0) {
                validSelection = true;
                model = new Game(numberOfPlayers, numberOfAIPlayers);
            } else {
                JOptionPane.showMessageDialog(
                        frame1,
                        "There must be at least one human player and a total of at least two players.",
                        "Player Requirement Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }

        frame1.setVisible(false);

        //Initialize frame
        model = new Game(numberOfPlayers, numberOfAIPlayers);
        gameFrame = new JFrame();
        gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gameFrame.setResizable(true);
        gameFrame.setLayout(new BorderLayout());

        // Create a panel to display label of player's turn label at the top left
        JPanel topPanel = new JPanel(new BorderLayout());
        turnLabel = new JLabel((model.getPlayersInGame().get(0).getName()));
        topPanel.add(turnLabel, BorderLayout.WEST);
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        topPanel.add(separator, BorderLayout.SOUTH);
        gameFrame.add(topPanel, BorderLayout.NORTH);

        // Create a panel to contain the status panel and the topCardPanel
        JPanel panelHolder = new JPanel(new BorderLayout());

        // Create a panel to display the current card
        topCardPanel = new JPanel(new BorderLayout());
        topCardPanel.add(model.getCurrentCard().getCardButton(), BorderLayout.CENTER);
        model.getCurrentCard().getCardButton().setEnabled(true);
        panelHolder.add(topCardPanel, BorderLayout.CENTER);


        // Create a panel to display status
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusLabel = new JLabel("Status:");
        statusPanel.add(statusLabel, BorderLayout.NORTH);

        // Add a JTextArea for displaying status
        statusTextArea = new JTextArea(10, 20);  // Rows, Columns
        statusTextArea.setEditable(false);  // Make it read-only
        statusPanel.add(new JScrollPane(statusTextArea), BorderLayout.EAST);
        panelHolder.add(statusPanel, BorderLayout.WEST);

        gameFrame.add(panelHolder, BorderLayout.CENTER);

        // Create a panel to contain buttons
        bottomPanel = new JPanel(new BorderLayout());

        // Add a button to advance to the next player
        nextPlayerButton = new JButton("Next Player");
        JPanel leftPanel = new JPanel();
        leftPanel.add(nextPlayerButton, BorderLayout.SOUTH);
        bottomPanel.add(leftPanel, BorderLayout.WEST);
        nextPlayerButton.setEnabled(false);

        saveGameButton = new JButton("Save Game");

        leftPanel.add(saveGameButton);

        loadGameButton = new JButton("Load Game");

        leftPanel.add(loadGameButton);

        // Add a button to undo a turn
        undoButton = new JButton("Undo");
        leftPanel.add(undoButton);
        undoButton.setEnabled(false);

        //Add a button to redo a turn
        redoButton = new JButton("Redo");
        leftPanel.add(redoButton);
        redoButton.setEnabled(false);

        // Create a panel to display current player's hand using buttons
        handPanel = new JPanel(new GridLayout(1, 0));
        cardHand = new ArrayList<>();

        // ArrayList of type Card
        playerCards = new ArrayList<>();

        Player currentPlayer = model.getCurrentPlayer();
        for (Card card : currentPlayer.getHand().getCards()) {
            handPanel.add(card.getCardButton());
        }
        model.getCurrentCard().getCardButton().setVisible(true);
        updatePlayerHand(model.getCurrentPlayer());


        // Create a JScrollPane and add handPanel to it
        JScrollPane handScrollPane = new JScrollPane(handPanel);
        handScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        bottomPanel.add(handScrollPane, BorderLayout.CENTER);

        // Add a button to draw a card
        drawPanel = new JPanel();
        drawCardButton = new JButton("Draw Card");
        drawPanel.add(drawCardButton);
        bottomPanel.add(drawPanel, BorderLayout.EAST);
        gameFrame.add(bottomPanel, BorderLayout.SOUTH);

        gameFrame.setVisible(true);
    }

    /**
     * Retrieves the game frame.
     *
     * @return The game frame.
     */
    public JFrame getGameFrame() {
        return gameFrame;
    }

    /**
     * Retrieves the "Next Player" button.
     *
     * @return The "Next Player" button.
     */
    public JButton getNextPlayerButton() {
        return nextPlayerButton;
    }

    /**
     * Retrieves the "Draw Card" button.
     *
     * @return The "Draw Card" button.
     */
    public JButton getDrawCardButton() {
        return drawCardButton;
    }

    /**
     * Retrieves the number of players.
     *
     * @return The number of players in the game.
     */
    public int getNumberOfPlayers(){
        return numberOfPlayers;
    }

    /**
     * Retrieves the turn label.
     *
     * @return The turn label.
     */
    public JLabel getTurnLabel() {
        return turnLabel;
    }

    /**
     * Sets the number of players.
     *
     * @param numberOfPlayers The number of players to set.
     */
    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     * Retrieves the status text area.
     *
     * @return The status text area.
     */
    public JTextArea getStatusTextArea() {
        return statusTextArea;
    }


    public Game getModel() {
        return model;
    }

    /**
     * Retrieves the game model.
     *
     * @return The game model.
     */
    public static void main(String[] args) {
        Gui GUI = new Gui();
    }

    /**
     * Updates the visibility and status of the player's hand.
     *
     * @param player The player whose hand is updated.
     */
    public void updatePlayerHand(Player player) {
        handPanel.removeAll();
        for (Card card : player.getHand().getCards()) {
            JButton cardButton = card.getCardButton();
            cardButton.setVisible(true);
            cardButton.setEnabled(true);
            handPanel.add(cardButton);
        }
        handPanel.revalidate();
        handPanel.repaint();
    }


    /**
     * Disables the buttons in the player's hand.
     */
    public void disableHand(){
        for(Component component: handPanel.getComponents()){
            if(component instanceof JButton){
                JButton button = (JButton) component;
                button.setEnabled(false);
            }
        }
    }

    /**
     * Updates the status text area with the information about the drawn card.
     *
     * @param player The player who drew the card.
     */
    public void updateStatusDraw(Player player){
        statusTextArea.setText("Drew: " + player.getLastCard().stringCard());
    }

    /**
     * Updates the display of the current card.
     *
     * @param card The card to be displayed.
     */
    public void updateCurrentCard(Card card) {
        SwingUtilities.invokeLater(() -> {
            topCardPanel.removeAll();

            JButton cardButton = card.getCardButton();
            if (cardButton != null) {
                cardButton.setVisible(true);
                cardButton.setEnabled(true);
                topCardPanel.add(cardButton);

                // Optionally, add padding or alignment for better presentation
                topCardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                topCardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            } else {
                // Handle the case where card button is not available
                System.err.println("Card button is null for card: " + card);
            }

            topCardPanel.revalidate();
            topCardPanel.repaint();
        });
    }



    /**
     * Adds the latest card to the player's hand display.
     */
    public void addLatestCardToHandDisplay() {
        Player currentPlayer = model.getCurrentPlayer();
        ArrayList<Card> cards = currentPlayer.getHand().getCards();

        if (!cards.isEmpty()) {
            // Assuming the latest card is at the end of the list
            Card latestCard = cards.get(cards.size() - 1);

            JButton cardButton = latestCard.getCardButton();
            handPanel.add(cardButton);
            cardButton.setVisible(true);
            cardButton.setEnabled(true);
            handPanel.revalidate();
            handPanel.repaint();
        }
    }

    /**
     * Updates the turn label with the information about the current player's turn.
     *
     * @param currentPlayerIndex The index of the current player.
     */
    public void updatePlayerTurnLabel(int currentPlayerIndex){
        String currentPlayerName = model.getPlayersInGame().get(currentPlayerIndex).getName();
        turnLabel.setText("Current Player: " + currentPlayerName);

        // Refresh the GUI components to reflect these changes
        turnLabel.revalidate();
        turnLabel.repaint();
    }

    /**
     * Removes a card from the player's hand and updates the display.
     *
     * @param cardToPlay The card to be removed.
     * @return True if the card was successfully removed, false otherwise.
     */
    public boolean removeCardFromHand(Card cardToPlay){
        if(model.removeCardFromHand(cardToPlay)) {
            cardToPlay.getCardButton().setVisible(false);
            updateCurrentCard(cardToPlay);
            handPanel.remove(cardToPlay.getCardButton());
            handPanel.revalidate();
            handPanel.repaint();
            return true;
        }
        return false;
    }

    /**
     * Updates the AI player's hand display.
     *
     * @param aiPlayer The AI player whose hand is updated.
     * @param mode The current mode (light or dark).
     */
    public void updateAIHand(AIPlayer aiPlayer, boolean mode) {
        handPanel.removeAll();

        for (Card card : aiPlayer.getHand().getCards()) {
            JButton cardButton = new JButton(card.stringCard());
            if(this.mode != mode){
                this.mode = mode;
            }

            if(mode == true){
                String filePath = "small\\" + card.getFileNameForCard(card.getValue(), card.getColor()) + ".png";
                ImageIcon icon = new ImageIcon(filePath);
                cardButton.setIcon(icon);
                cardButton.setEnabled(false);
                handPanel.add(cardButton);
                handPanel.add(cardButton);
            }
            else{
                String filePath = "dark\\" + card.getFileNameForCard(card.getValue(), card.getColor()) + ".png";
                ImageIcon icon = new ImageIcon(filePath);
                cardButton.setIcon(icon);
                cardButton.setEnabled(false);
                handPanel.add(cardButton);

            }
        }
        handPanel.revalidate();
        handPanel.repaint();
    }

    /**
     * Retrieves the top card panel.
     *
     * @return The top card panel.
     */
    public JPanel getTopCardPanel() {
        return topCardPanel;
    }

    /**
     * Retrieves the hand panel.
     *
     * @return The hand panel.
     */
    public JPanel getHandPanel() {
        return handPanel;
    }

    public JButton getUndoButton() {
        return undoButton;
    }
    public JButton getRedoButton() {
        return redoButton;
    }

    public JButton getSaveButton() {
        return saveGameButton;
    }

    public JButton getLoadButton(){
        return loadGameButton;
    }

    public void refreshGuiComponents() {
        turnLabel.setText("Current Player: " + model.getCurrentPlayer().getName());
        statusTextArea.setText("Current Card: " + model.getCurrentCard().stringCard());
        updatePlayerHand(model.getCurrentPlayer());
        updateCurrentCard(model.getCurrentCard());

        handPanel.revalidate();
        handPanel.repaint();
        topCardPanel.revalidate();
        topCardPanel.repaint();
        bottomPanel.revalidate();
        bottomPanel.repaint();
    }

    public void updateGameState(Game loadedGame) {
        this.numberOfPlayers = loadedGame.getPlayersInGame().size();
        this.model = loadedGame;

        updatePlayerHand(loadedGame.getCurrentPlayer());
        updateCurrentCard(loadedGame.getCurrentCard());
    }


}