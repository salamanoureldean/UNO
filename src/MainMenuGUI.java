/**
 * @author: Abody Majeed 101227327, Mahad Mohamed Yonis 101226808, Salama Noureldean 101154365, Pietro Adamvoski 101238885
 * @date: 11/14/2023
 * @version: 1.00
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenuGUI extends JFrame {
    private Gui gui;
    private Game game;
    private Controller controller;

    /**
     * Initializes the main menu graphical user interface for the UNO game.
     * It creates a JFrame with buttons for play, rules, quit, and help menu options.
     * The buttons are arranged vertically in a JPanel with a specified layout and styling.
     */
    public MainMenuGUI() {
        // Initialize the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setTitle("UNO Main Menu");

        // Create main menu panel with layout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(121, 121, 234));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        getContentPane().add(panel);

        // Add buttons for menu options
        addButton(panel, "Play", e -> initialize());
        addButton(panel, "Rules", e -> rulesMenu());
        addButton(panel, "Quit", e -> quit());
        addButton(panel, "Help", e -> helpMenu());

        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    /**
     * Adds a button to the specified JPanel with the given text and ActionListener.
     * The button is styled, and rigid space is added after the button for spacing.
     *
     * @param panel           The JPanel to which the button will be added.
     * @param text            The text to be displayed on the button.
     * @param actionListener  The ActionListener to handle button click events.
     */
    private void addButton(JPanel panel, String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(300, 60));
        styleButton(button);
        button.addActionListener(actionListener);
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
    }

    /**
     * Styles the specified JButton by setting its font, text color, background color,
     * removing focus and border painting, making it opaque, and aligning it to the center.
     *
     * @param button The JButton to be styled.
     */
    private void styleButton(JButton button) {
        button.setFont(new Font("Times New Roman", Font.BOLD, 20));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 15, 204));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    /**
     * Displays a simple dialog providing information to encourage the user to search for
     * the rules of the game on the internet. The dialog informs the user to "Google the rules!"
     * and is categorized as an INFORMATION_MESSAGE.
     */
    private void rulesMenu() {
        JOptionPane.showMessageDialog(this, "Google the rules!", "Rules", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Exits the application by invoking the System.exit() method with a status code of 0.
     */
    private void quit() {
        System.exit(0);
    }

    /**
     * Displays a simple dialog providing information about how to navigate through the menus.
     * The dialog advises the user to select the button for the desired action. It is categorized
     * as an INFORMATION_MESSAGE.
     */
    private void helpMenu() {
        JOptionPane.showMessageDialog(this, "To navigate through the menus, select the button for the action you want to perform.", "Help", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Initializes the game by creating a new GUI and controller, associating action listeners
     * with each card button, draw card button, and next player button. It also invokes the
     * card functionality for the current card in the game.
     */
    private void initialize(){
        gui = new Gui();
        game = gui.getModel(); // Initialize the game object with the model from gui
        controller = new Controller(game, gui);

        for(int i=0; i < game.getPlayersInGame().size(); i++){
            for(int j = 0; j< game.getPlayersInGame().get(i).getHand().getCards().size(); j++) {
                game.getPlayersInGame().get(i).getHand().getCards().get(j).getCardButton().addActionListener(controller);
            }
        }
        gui.getDrawCardButton().addActionListener(controller);
        gui.getNextPlayerButton().addActionListener(controller);
        controller.cardFunctionality(game.getCurrentCard());
    }


    /**
     * The main entry point of the UNO game application. Invokes the creation and display
     * of the main menu GUI using SwingUtilities.invokeLater to ensure proper thread handling.
     *
     * @param args The command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenuGUI().setVisible(true));
    }
}
