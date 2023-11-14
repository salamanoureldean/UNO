import javax.swing.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Controller implements ActionListener{
    private Game game;

    private int indexPlayer = 0;

    private Gui gui;

    public Controller(Game game, Gui gui){
        this.game = game;
        this.gui = gui;
    }



    public void actionPerformed(ActionEvent e){
        JFrame frame = new JFrame();
        frame.setVisible(false);
        if(e.getSource() == gui.getDrawCardButton()){
            game.addCardToHand();
            gui.cardToHand();
           //Useless code to be changed
            gui.setHandInvisible(game.getCurrentPlayer());
            gui.getDrawCardButton().setEnabled(false);
            gui.getNextPlayerButton().setEnabled(true);


            game.getCurrentPlayer().getHand().getCards();

        } else if (e.getSource() == gui.getNextPlayerButton()) {
            indexPlayer += 1;
            indexPlayer = indexPlayer % gui.getNumberOfPlayers();
            game.nextPlayer();
            gui.getDrawCardButton().setEnabled(true);
            gui.setHandVisible(game.getCurrentPlayer());
            gui.getNextPlayerButton().setEnabled(false);
            gui.getTurnLabel().setText(game.getPlayersInGame().get(indexPlayer).getName());
        }
        else{
            System.out.println("HIIIIIIIIIIIII");
            for(int i=0; i < game.getCurrentPlayer().getHand().getCards().size(); i++){
                if(e.getSource() == game.getCurrentPlayer().getHand().getCards().get(i).getCardButton()){
                    System.out.println("Hiiiiiiiiiiii");
                    if(game.isPlayable(game.getCurrentPlayer().getHand().getCards().get(i)) == true) {
                        game.removeCardFromHand(game.getCurrentPlayer().getHand().getCards().get(i));
                        gui.placeCard(game.getCurrentPlayer().getHand().getCards().get(i));
                        gui.setHandInvisible(game.getCurrentPlayer());
                        gui.getDrawCardButton().setEnabled(false);
                        gui.getNextPlayerButton().setEnabled(true);
                    }else{
                        gui.getStatusTextArea().setText("Invalid Move!");
                    }
                }
            System.out.println("BYEEEEEEEEEEEEE");
            if(game.checkWinner() == true){
                    JOptionPane.showMessageDialog(frame,"You Won!");
                    System.exit(0);
                }
            }
        }
    }
}