import javax.swing.*;
import java.io.Serializable;

public class Restart implements Serializable {

    private Gui gui;
    private Game game;
    private Controller controller;
    private JButton restartButton;


    /**
     * Constructor for the Restart class.
     */
    public Restart(JButton restartButton){
        this.restartButton = restartButton;
    }

    /**
     * Restarts the game by disposing of the current JFrame, creating a new GUI, and setting up
     * a new game instance with corresponding controllers and action listeners.
     */
    public void restartGame(JFrame frame) {
        frame.dispose();
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
}
