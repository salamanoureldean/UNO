import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui {
    private static JFrame frame1;
    private JFrame gameFrame;
    private int numberOfPlayers;
    private JPanel topCardPanel;
    private JPanel handPanel;
    private JPanel drawPanel;
    private JLabel turnLabel;
    private JLabel cardLabel;
    private JLabel statusLabel;
    private JPanel bottomPanel;
    private ArrayList<JButton> cardHand;
    private ArrayList<Card> playerCards;
    private Game model;

    // Buttons and components that can implement an action
    private JButton nextPlayerButton;
    private JButton drawCardButton;

    public Gui() {
        model = new Game();

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

        gameFrame = new JFrame();
        gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gameFrame.setResizable(true);
        gameFrame.setLayout(new BorderLayout());

        // Create a panel to display label of player's turn label at the top left
        JPanel topPanel = new JPanel(new BorderLayout());
        turnLabel = new JLabel("Player _______");
        topPanel.add(turnLabel, BorderLayout.WEST);
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        topPanel.add(separator, BorderLayout.SOUTH);
        gameFrame.add(topPanel, BorderLayout.NORTH);

        // Create a panel to display the current card
        topCardPanel = new JPanel(new BorderLayout());
        cardLabel = new JLabel(new ImageIcon("unocard.png"));
        topCardPanel.add(cardLabel, BorderLayout.CENTER);

        // Create a panel to display status
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusLabel = new JLabel("Status:");
        statusPanel.add(statusLabel, BorderLayout.NORTH);

        // Add a JTextArea for displaying status
        JTextArea statusTextArea = new JTextArea(10, 10);  // Rows, Columns
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
        Controller controller = new Controller(model);

        // ArrayList of type Card
        playerCards = new ArrayList<>();
        // Add JButtons representing the player's hand
        for (int i = 0; i < 7; i++) {
            JButton cardButton = new JButton();
            cardHand.add(cardButton);
            cardButton.addActionListener(controller);
            handPanel.add(cardHand.get(i));
        }

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
        update();

        gameFrame.setVisible(true);
    }

    public void update() {
        // Set the text and background color on the buttons with random card values and colors
        for (int i = 0; i < cardHand.size(); i++) {
            JButton cardButton = cardHand.get(i);
            Card randomCard = getRandomCard(); // Get a random card
            playerCards.add(randomCard);

            // Set the card text on the button
            cardButton.setText(randomCard.stringCard());

            // Set the icon of the button to the corresponding PNG file
            String filePath = "C:\\Users\\Mahad\\OneDrive\\Documents\\PNGs\\PNGs\\small\\" + getFileNameForCard(randomCard) + ".png";
            ImageIcon icon = new ImageIcon(filePath);
            cardButton.setIcon(icon);
        }
    }

    private Card getRandomCard() {
        // Generate a random card with a random color and value
        Card.Color randomColor = Card.Color.values()[(int) (Math.random() * Card.Color.values().length)];
        Card.Value randomValue = Card.Value.values()[(int) (Math.random() * Card.Value.values().length)];

        return new Card(randomValue, randomColor);
    }

    public void cardToHand(){

        //Salama do this
        JButton tempButton = new JButton();
    }

    private String getFileNameForCard(Card card) {
        // Logic to map card color and value to a corresponding PNG file name
        // This can be customized based on your actual file naming convention
        return card.getColor().toString().toLowerCase() + "_" + card.getVALUE().toString().toLowerCase();
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
    public static void main(String[] args) {
        Gui GUI = new Gui();
    }
}
