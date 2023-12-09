import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ExitMenuGUI extends JFrame {
    private Gui gui;
    private Game game;
    private Controller controller;

    /**
     * Constructor for the ExitMenuGUI.
     */
    public ExitMenuGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setTitle("UNO Exit Menu");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(0, 0, 24));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        getContentPane().add(panel);

        addButton(panel, "Play Again", e -> initialize());
        addButton(panel, "Quit", e -> quit());

        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
    }


    /**
     * Adds a styled JButton to a JPanel with the specified text, ActionListener, and mouse hover effects.
     */
    private void addButton(JPanel panel, String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(300, 60));
        styleButton(button, text);
        button.addActionListener(actionListener);

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(button.getBackground().darker());
            }

            public void mouseExited(MouseEvent evt) {
                styleButton(button, text);
            }
        });

        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
    }

    /**
     * Styles the specified JButton with a specified font, text color, background color, and alignment.
     */
    private void styleButton(JButton button, String buttonText) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 20));
        button.setForeground(Color.WHITE);

        switch (buttonText) {
            case "Play Again":
                button.setBackground(Color.blue);
                break;
            case "Quit":
                button.setBackground(Color.blue);
                break;
            default:
                button.setBackground(Color.blue);
                break;
        }

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    /**
     * Exits the application by terminating the Java Virtual Machine with a status code of 0.
     */
    private void quit() {
        System.exit(0);
    }

    /**
     * Initializes the game by creating a new GUI, game model, and controller.
     * Adds action listeners to relevant components and invokes card functionality for the
     * current card in the game.
     */
    private void initialize() {
        gui = new Gui();
        game = gui.getModel();
        controller = new Controller(game, gui);

        for (int i = 0; i < game.getPlayersInGame().size(); i++) {
            for (int j = 0; j < game.getPlayersInGame().get(i).getHand().getCards().size(); j++) {
                game.getPlayersInGame().get(i).getHand().getCards().get(j).getCardButton().addActionListener(controller);
            }
        }
        gui.getDrawCardButton().addActionListener(controller);
        gui.getNextPlayerButton().addActionListener(controller);
        gui.getSaveButton().addActionListener(controller);
        gui.getLoadButton().addActionListener(controller);
        gui.getReplayButton().addActionListener(controller);
        controller.cardFunctionality(game.getCurrentCard());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenuGUI().setVisible(true));
    }
}
