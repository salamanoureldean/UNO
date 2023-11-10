/**
 * @author: Mahad Mohamed Yonis 101226808
 * @editor: Abody Majeed 101227327
 * @date: 10/22/2023
 * @version: 1.00
 */
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainMenuGUI mainMenu = new MainMenuGUI();
                mainMenu.setVisible(true);
            }
        });
    }
}
