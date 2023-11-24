import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenuGUI extends JFrame {
    private Gui gui;
    private Game game;
    private Controller controller;

    public MainMenuGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setTitle("UNO Main Menu");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(0, 0, 24)); // Light grey background
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        getContentPane().add(panel);

        addButton(panel, "Play", e -> initialize());
        addButton(panel, "Rules", e -> rulesMenu());
        addButton(panel, "Quit", e -> quit());
        addButton(panel, "Help", e -> helpMenu());

        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

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
                styleButton(button, text); // Reset to original color
            }
        });

        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
    }

    private void styleButton(JButton button, String buttonText) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 20));
        button.setForeground(Color.WHITE);

        switch (buttonText) {
            case "Play":
                button.setBackground(Color.GREEN);
                break;
            case "Rules":
                button.setBackground(Color.BLUE);
                break;
            case "Quit":
                button.setBackground(Color.RED);
                break;
            case "Help":
                button.setBackground(Color.ORANGE);
                break;
            default:
                button.setBackground(Color.GRAY);
                break;
        }

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void rulesMenu() {
        JOptionPane.showMessageDialog(this, "The rules of UNO are simple...\n[add a brief summary here]", "Rules", JOptionPane.INFORMATION_MESSAGE);
    }

    private void quit() {
        System.exit(0);
    }

    private void helpMenu() {
        JOptionPane.showMessageDialog(this, "To navigate through the menus, select the button for the action you want to perform. For more help, visit our FAQ section.", "Help", JOptionPane.INFORMATION_MESSAGE);
    }

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
        controller.cardFunctionality(game.getCurrentCard());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenuGUI().setVisible(true));
    }
}

