/**
 * @author: Abody Majeed 101227327, Mahad Mohamed Yonis 101226808, Salama Noureldean 101154365, Pietro Adamvoski 101238885
 * @date: 11/14/2023
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