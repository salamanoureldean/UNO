import javax.swing.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Controller implements ActionListener{
    private Game game;

    private int index = 0;

    private Gui gui;

    public Controller(Game game, Gui gui){
        this.game = game;
        this.gui = gui;
    }



    public void actionPerformed(ActionEvent e){
        JFrame frame = new JFrame();
        frame.setVisible(false);
        Card tempCard = game.getCurrentPlayer().getHand().getCards().get(index);
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
            index = (index + 1) % 4;
            game.nextPlayer();
            gui.getDrawCardButton().setEnabled(true);
            for (JButton b: gui.getCardHand()){
                b.setEnabled(true);
            }
            gui.getNextPlayerButton().setEnabled(false);
            gui.getTurnLabel().setText(game.getPlayersInGame().get(index).getName());
        }
        else{
            for(int i=0; i < game.getCurrentPlayer().getHand().getCards().size(); i++){
                if(e.getSource() == tempCard.getCardButton()){
                    if(game.isPlayable(tempCard) == true) {
                        game.removeCardFromHand(tempCard);
                        gui.placeCard(tempCard);
                        gui.setHandInvisible(game.getCurrentPlayer());
                    }else{
                        gui.getStatusTextArea().setText("Invalid Move!");
                    }
                }
            if(game.checkWinner() == true){
                    JOptionPane.showMessageDialog(frame,"You Won!");
                    System.exit(0);
                }
            }
        }
    }
}