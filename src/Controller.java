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
            //THIS IS DRAW
            game.addCardToHand();
            gui.cardToHand();
            gui.drawTheCard(game.getCurrentPlayer().getLastCard());
            gui.setHandInvisible(game.getCurrentPlayer());
            gui.getDrawCardButton().setEnabled(false);
            gui.getNextPlayerButton().setEnabled(true);


            game.getCurrentPlayer().getHand().getCards();

        } else if (e.getSource() == gui.getNextPlayerButton()) {
            //THIS THE NEXT PLAYER
            indexPlayer += 1;
            indexPlayer = indexPlayer % gui.getNumberOfPlayers();
            game.nextPlayer();
            gui.getDrawCardButton().setEnabled(true);
            gui.setHandVisible(game.getCurrentPlayer());
            gui.getNextPlayerButton().setEnabled(false);
            gui.getTurnLabel().setText(game.getPlayersInGame().get(indexPlayer).getName());
            gui.setHandVisible(game.getPlayersInGame().get(indexPlayer));
        }
        else{
            System.out.println("HIIIIIIIIIIIII");
            //THIS IS PLACING A CARD
            for(int i=0; i < game.getCurrentPlayer().getHand().getCards().size(); i++){
                if(e.getSource() == game.getCurrentPlayer().getHand().getCards().get(i).getCardButton()) {
                    System.out.println("Card registered");
                    //IF IT'S RIGHT
                    if (gui.placeCard(game.getCurrentPlayer().getHand().getCards().get(i), i) == true) {
                        System.out.println("Card is playable");
                        gui.setHandInvisible(game.getCurrentPlayer());
                        gui.getDrawCardButton().setEnabled(false);
                        gui.getNextPlayerButton().setEnabled(true);
                        break;
                        //IF IT'S WRONG
                    } else if(gui.placeCard(game.getCurrentPlayer().getHand().getCards().get(i), i) == false) {
                        gui.getStatusTextArea().setText("Invalid Move!");
                        break;
                    }
                }
                else{
                    //TESTING REMOVE AFTER
                        System.out.println("I hit continue");
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