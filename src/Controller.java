import javax.swing.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Controller implements ActionListener{
    Game game = new Game();

    Gui gui;

    public Controller(Game game){
        this.game = game;
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == gui.getDrawCardButton()){
            game.addCardToHand();
            gui.cardToHand();
            for (JButton b: gui.getCardHand()){
                b.setEnabled(false);
            }
            gui.getDrawCardButton().setEnabled(false);
            gui.getNextPlayerButton().setEnabled(true);


            game.getCurrentPlayer().getHand().getCards();

        } else if (e.getSource() == gui.getNextPlayerButton()) {
            game.nextPlayer();
            gui.getDrawCardButton().setEnabled(true);
            for (JButton b: gui.getCardHand()){
                b.setEnabled(true);
            }
            gui.getNextPlayerButton().setEnabled(false);
        }



        else{
            for(JButton button: gui.getCardHand()){
                if(e.getSource() == button){
                    game.cardFunctionality(game.getCurrentCard()); //////FIX THIS
                }
            }
        }
    }
}