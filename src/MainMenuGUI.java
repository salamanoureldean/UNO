import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenuGUI extends JFrame {
    private Gui gui;
    private Game game;
    private Controller controller;

    /**
     * MainMenuGUI constructor
     */
    public MainMenuGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setTitle("UNO Main Menu");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(0, 0, 24));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        getContentPane().add(panel);

        addButton(panel, "Play", e -> initialize());
        addButton(panel, "Rules", e -> rulesMenu());
        addButton(panel, "Quit", e -> quit());
        addButton(panel, "Help", e -> helpMenu());

        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    /**
     * Adds a styled JButton to a specified JPanel with the given text and ActionListener.
     * The button is configured with a maximum size, styling, and event listeners for mouse hover effects.
     *
     * @param panel The JPanel to which the button will be added.
     * @param text The text to be displayed on the button.
     * @param actionListener The ActionListener to be attached to the button.
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
     * Applies a specific style to a JButton based on its text content.
     * The button's font, foreground color, background color, and other visual properties
     * are set according to predefined styles for different button texts.
     *
     * @param button The JButton to be styled.
     * @param buttonText The text content of the button used to determine the styling.
     */
    private void styleButton(JButton button, String buttonText) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 20));
        button.setForeground(Color.WHITE);

        switch (buttonText) {
            case "Play":
                button.setBackground(Color.blue);
                break;
            case "Help":
                button.setBackground(Color.blue);
                break;
            case "Rules":
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
     * Displays a dialog box with information about the rules.
     * The dialog box suggests searching for rules on Google.
     */
     private void rulesMenu() {
            JOptionPane.showMessageDialog(this, "Google the rules!", "Rules", JOptionPane.INFORMATION_MESSAGE);    }

    /**
     * Exits the application gracefully by terminating the Java Virtual Machine (JVM).
     * This method calls System.exit(0), causing the JVM to shut down without performing
     * any additional cleanup or finalization. It is generally used to forcefully exit
     * the application.
     */
     private void quit() {
            System.exit(0);
        }

    /**
     * Displays a dialog box providing basic information on navigating through the application's menus.
     * The information suggests selecting the appropriate button for the desired action.
     */
     private void helpMenu() {
            JOptionPane.showMessageDialog(this, "To navigate through the menus, select the button for the action you want to perform.", "Help", JOptionPane.INFORMATION_MESSAGE);
        }

    /**
     * Initializes the application by creating instances of the GUI, game model, and controller.
     * Additionally, it sets up action listeners for card buttons, draw card button,
     * next player button, save button, load button, and replay button. The card functionality
     * is also initialized based on the current card in the game.
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

