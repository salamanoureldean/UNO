import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

public class Gui {
    private static JFrame frame1;
    private JFrame gameFrame;
    private int numberOfPlayers = 2;
    private JPanel topCardPanel;
    private JPanel handPanel;
    private JPanel drawPanel;
    private JLabel turnLabel;
    private JLabel statusLabel;
    private JPanel bottomPanel;
    private ArrayList<JButton> cardHand;
    private ArrayList<Card> playerCards;
    private Game model;

    // Buttons and components that can implement an action
    private JButton nextPlayerButton;
    private JButton drawCardButton;
    private JTextArea statusTextArea;

    public Gui() {
        frame1 = new JFrame();
        Object[] possibilities = {2, 3, 4};
        numberOfPlayers = (Integer) JOptionPane.showInputDialog(frame1,
                "Choose the number of players:",
                "Number of players selection",
                JOptionPane.PLAIN_MESSAGE,
                null,
                possibilities,
                2);
        frame1.setVisible(false);

        model = new Game(numberOfPlayers);


        gameFrame = new JFrame();
        gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gameFrame.setResizable(true);
        gameFrame.setLayout(new BorderLayout());

        /**
        for (int i = 0; i < numberOfPlayers; i++) {
            String playerName = JOptionPane.showInputDialog(frame1, "Enter the name of Player " + (i + 1) + ":");
            model.getPlayersInGame().add(new Player(playerName, model.getTheDeck()));
        }
        */

        // Create a panel to display label of player's turn label at the top left
        JPanel topPanel = new JPanel(new BorderLayout());
        turnLabel = new JLabel((model.getPlayersInGame().get(0).getName()));
        topPanel.add(turnLabel, BorderLayout.WEST);
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        topPanel.add(separator, BorderLayout.SOUTH);
        gameFrame.add(topPanel, BorderLayout.NORTH);

        // Create a panel to display the current card
        topCardPanel = new JPanel(new BorderLayout());
        topCardPanel.add(model.getCurrentCard().getCardButton(), BorderLayout.CENTER);

        // Create a panel to display status
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusLabel = new JLabel("Status:");
        statusPanel.add(statusLabel, BorderLayout.NORTH);

        // Add a JTextArea for displaying status
        statusTextArea = new JTextArea(10, 10);  // Rows, Columns
        statusTextArea.setEditable(false);  // Make it read-only
        statusPanel.add(new JScrollPane(statusTextArea), BorderLayout.EAST);
        topCardPanel.add(statusPanel, BorderLayout.WEST);

        gameFrame.add(topCardPanel, BorderLayout.CENTER);

        // Create a panel to display status
        statusPanel = new JPanel();
        statusLabel = new JLabel("Status");
        statusPanel.add(statusLabel);
        gameFrame.add(statusPanel, BorderLayout.SOUTH);

        // Create a panel to contain buttons
        bottomPanel = new JPanel(new BorderLayout());

        // Add a button to advance to the next player
        nextPlayerButton = new JButton("Next Player");
        JPanel leftPanel = new JPanel();
        leftPanel.add(nextPlayerButton, BorderLayout.SOUTH);
        bottomPanel.add(leftPanel, BorderLayout.WEST);
        nextPlayerButton.setEnabled(false);

        // Create a panel to display current player's hand using buttons
        handPanel = new JPanel(new GridLayout(1, 0));
        cardHand = new ArrayList<>();

        // ArrayList of type Card
        playerCards = new ArrayList<>();

        Player currentPlayer = model.getCurrentPlayer();
        for (Card card: currentPlayer.getHand().getCards()){
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
        //update();

        gameFrame.setVisible(true);
    }
    private Card getRandomCard() {
        // Generate a random card with a random color and value
        Card.Color randomColor = Card.Color.values()[(int) (Math.random() * Card.Color.values().length)];
        Card.Value randomValue = Card.Value.values()[(int) (Math.random() * Card.Value.values().length)];

        return new Card(randomValue, randomColor);
    }

    public void cardToHand(Card cardToDraw){
        handPanel.add(cardToDraw.getCardButton());
    }


    public JFrame getGameFrame() {
        return gameFrame;
    }

    public JButton getNextPlayerButton() {
        return nextPlayerButton;
    }

    public ArrayList<Card> getPlayerCards() {
        return playerCards;
    }

    public ArrayList<JButton> getCardHand() {
        return cardHand;
    }

    public JButton getDrawCardButton() {
        return drawCardButton;
    }

    public int getNumberOfPlayers(){
        return numberOfPlayers;
    }

    public JLabel getTurnLabel() {
        return turnLabel;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public JTextArea getStatusTextArea() {
        return statusTextArea;
    }

    public Game getModel() {
        return model;
    }

    public static void main(String[] args) {
        Gui GUI = new Gui();
    }

    // Enable and set the cards in the hand as visible
    public void updatePlayerHand(Player player) {
        handPanel.removeAll();
        for (Card card : player.getHand().getCards()) {
            card.getCardButton().setVisible(true);
            card.getCardButton().setEnabled(true);
            handPanel.add(card.getCardButton());
        }
        handPanel.revalidate();
        handPanel.repaint();

    }

    public void disableHand(){
        for(Component component: handPanel.getComponents()){
            if(component instanceof JButton){
                JButton button = (JButton) component;
                button.setEnabled(false);
            }
        }
    }

    public void updateCurrentCard(Card card){
        topCardPanel.removeAll();
        topCardPanel.add(card.getCardButton());

        card.getCardButton().setVisible(true);
        topCardPanel.revalidate();
        topCardPanel.repaint();

    }

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
    public void updatePlayerTurnLabel(int currentPlayerIndex){
        String currentPlayerName = model.getPlayersInGame().get(currentPlayerIndex).getName();
        turnLabel.setText("Current Player: " + currentPlayerName);

        // Refresh the GUI components to reflect these changes
        turnLabel.revalidate();
        turnLabel.repaint();
    }

    public boolean removeCardFromHand(Card cardToPlay){
        if(model.removeCardFromHand(cardToPlay)) {
            cardToPlay.getCardButton().setVisible(false);
            updateCurrentCard(cardToPlay);
            handPanel.remove(cardToPlay.getCardButton());
            handPanel.revalidate();
            handPanel.repaint();
            return  true;
        }
        return false;
    }

}