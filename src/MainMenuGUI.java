import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenuGUI extends JFrame {
    private Gui gui;

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
        addButton(panel, "Play", e -> gui = new Gui());
        addButton(panel, "Rules", e -> rulesMenu());
        addButton(panel, "Quit", e -> quit());
        addButton(panel, "Help", e -> helpMenu());

        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void addButton(JPanel panel, String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(300, 60));
        styleButton(button);
        button.addActionListener(actionListener);
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Times New Roman", Font.BOLD, 20));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 15, 204));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void rulesMenu() {
        JOptionPane.showMessageDialog(this, "Google the rules!", "Rules", JOptionPane.INFORMATION_MESSAGE);
    }

    private void quit() {
        System.exit(0);
    }

    private void helpMenu() {
        JOptionPane.showMessageDialog(this, "To navigate through the menus, select the button for the action you want to perform.", "Help", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenuGUI().setVisible(true));
    }
}
