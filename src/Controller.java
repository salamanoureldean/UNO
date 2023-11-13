import javax.swing.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Controller implements ActionListener{
    Game game = new Game();

    Gui gui = new Gui();

    public Controller(Game game){
        this.game = game;
    }

    public void actionPerformed(ActionEvent e){
        Player currPlayer;
        if(e.getSource() == gui.drawCardButton){
            game.addCardToHand();
            for (JButton b: gui.cardHand){
                b.setEnabled(false);
            }
            gui.drawCardButton.setEnabled(false);
            gui.nextPlayerButton.setEnabled(true);

        } else if (e.getSource() == gui.nextPlayerButton) {

        }
    }
}