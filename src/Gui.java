import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui implements ActionListener{
    private static JFrame frame1;
    private JFrame gameFrame;
    private int numberOfPlayers;
    private JPanel topCardPanel;
    private JPanel statusPanel;
    private JPanel handPanel;
    private JPanel drawPanel;
    private JLabel turnLabel;
    private JLabel cardLabel;
    private JLabel statusLabel;
    private JPanel bottomPanel;
    private ArrayList<JButton> cardHand;
    Game model;

    public Gui() {
        model = new  Game();
        //model.addUnoView(this);
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

        // Create the player's turn label at the top left
        turnLabel = new JLabel("Player _______");
        gameFrame.add(turnLabel, BorderLayout.NORTH);

        // Create a panel to display the current card
        topCardPanel = new JPanel(new BorderLayout());
        cardLabel = new JLabel(new ImageIcon("unocard.png"));
        topCardPanel.add(cardLabel, BorderLayout.CENTER);
        gameFrame.add(topCardPanel, BorderLayout.CENTER);

        // Create a panel to display status
        statusPanel = new JPanel();
        statusLabel = new JLabel("Status");
        statusPanel.add(statusLabel);
        gameFrame.add(statusPanel, BorderLayout.SOUTH);

        // Create a panel to contain buttons
        bottomPanel = new JPanel(new BorderLayout());

        // Add a button to advance to the next player
        JButton nextPlayerButton = new JButton("Next Player");
        bottomPanel.add(nextPlayerButton, BorderLayout.WEST);

        // Create a panel to display current player's hand using buttons
        handPanel = new JPanel(new GridLayout(1, 0));
        cardHand = new ArrayList<>();
        Controller controller = new Controller(model);

        // Add JButtons representing the player's hand
        for (int i = 0; i < 7; i++) {
            JButton cardButton = new JButton("Card" + i);
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
        JButton drawCardButton = new JButton("Draw Card");
        drawPanel.add(drawCardButton);
        bottomPanel.add(drawPanel, BorderLayout.EAST);

        gameFrame.add(bottomPanel, BorderLayout.SOUTH);

        gameFrame.setVisible(true);
    }

    public static void main(String[] args) {
        Gui GUI = new Gui();
    }
}
