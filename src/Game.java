import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
public class Game {

    /**
     * Initializing variables in order to perform the play function
     */
    private ArrayList<Player> playersInGame;
    private boolean gameOn = true; //Probably will end up deleting
    private static boolean winner = false;
    private Scanner userInput;
    private String userInputText;
    private Card currentCard;
    private int currentTurn = 0;

    public Game() {
    }

    public void play() {
        mainMenu();
    }

    public void mainMenu() {
        System.out.printf("Main Menu:\n" +
                "(P)lay\n" +
                "(R)ules\n" +
                "(Q)uit\n" +
                "(H)elp\n");
        userInput = new Scanner(System.in);
        System.out.print("Please enter a command: ");
        userInputText = userInput.nextLine();
        if (userInputText.toUpperCase().equals("P")) {
            playMenu();
        } else if (userInputText.toUpperCase().equals("R")) {
            rulesMenu();
        } else if (userInputText.toUpperCase().equals("Q")) {
            quit();
        } else if (userInputText.toUpperCase().equals("H") || userInputText.toUpperCase().equals("HELP")) {
            helpMenu();
        } else {
            System.out.println("Thats not a valid option try again....");
            mainMenu();
        }
    }

    public void playMenu() {
        String playerInput;
        System.out.println("How many players are there?:\n" +
                "(2) Players\n" +
                "(3) Players\n" +
                "(4) Players\n");
        userInput = new Scanner(System.in);
        System.out.print("Enter the amount of players you want to play with: ");
        playerInput = userInput.nextLine();
        if (playerInput.equals("2")) {
            playersInGame = new ArrayList<Player>();
            for (int j = 0; j < 2; j++) {
                playersInGame.add(new Player());
            }
            gameStart();
        } else if (playerInput.equals("3")) {
            playersInGame = new ArrayList<Player>();
            for (int j = 0; j < 3; j++) {
                playersInGame.add(new Player());
            }
            gameStart();
        } else if (playerInput.equals("4")) {
            playersInGame = new ArrayList<Player>();
            for (int j = 0; j < 4; j++) {
                playersInGame.add(new Player());
            }
            gameStart();
        } else {
            System.out.println("Not a valid option please try again");
            playMenu();
        }
    }

    public void rulesMenu() {
        String back;
        System.out.println("Google the rules!\n" +
                "Menu Options:" +
                "(B)ack");
        userInput = new Scanner(System.in);
        System.out.print("Please enter a command: ");
        back = userInput.nextLine();
        if (back.toUpperCase().equals("B")) {
            mainMenu();
        } else {
            System.out.println("Please try again...");
            rulesMenu();
        }
    }

    public void quit() {
        System.out.println("Thank you for playing UNO!");
        System.exit(0);
    }

    public void helpMenu() {
        String back;
        System.out.println("To navigate through the menus enter the letter in brackets for the selection,\n" +
                "you'd like to make, here are your options:\n" +
                "(B)ack");
        userInput = new Scanner(System.in);
        System.out.print("Enter 'B' to go back: ");
        back = userInput.nextLine();
        if (back.toUpperCase().equals("B")) {
            mainMenu();
        } else {
            System.out.println("Please try again");
            helpMenu();
        }
    }


    public void playDisplay(int playerTurn){
        System.out.printf("Currently Player %d playing:\n", playerTurn+1);
        System.out.printf("Cards in hand: ");
        playersInGame.get(playerTurn).viewHand();
    }

    public void playPrompt(int index){
        String input;
        System.out.print("Options for actions: \n" +
                "(P)lace\n" +
                "(D)raw\n" +
                "(Q)uit\n");
        userInput = new Scanner(System.in);
        System.out.print("Please input your option: ");
        input = userInput.nextLine();
        if(input.toUpperCase().equals("P")){
            //Mahad finish this
            System.out.println("Please select the card you want to place: ");
            //place(null);
        }
        else if(input.toUpperCase().equals("D")){
            playersInGame.get(index).drawCard();
        }
        else if(input.toUpperCase().equals("Q")){
            quit();
        }
        else{
            System.out.println("Not a valid input try again");
            playPrompt(index);
        }
    }

    //Do this Mahad
    public boolean winner(){
        return true;
    }

    public void gameStart() {

        while(winner){
            //playDisplay
            playDisplay(currentTurn);
            //Prompt what to play, Draw card or place (Allow player to choose)
            playPrompt(currentTurn);

            //check if playable


            //cardFunction


            //Rotating players
            if (currentTurn + 1 > playersInGame.size()){
                currentTurn = 0;
            }
            else{
                currentTurn += 1;
            }

            //Is winner will be placed here

        }
    }

    public void cardFunctionality(Card playedCard) {
        Card.Value cardValue = playedCard.getValue();
        //Card.Color cardColor = playedCard.getColor();

        switch (cardValue) {

            case REVERSE:
                Collections.reverse(playersInGame);
                break;
            case SKIP:
                if (currentTurn + 1 > playersInGame.size()){
                    currentTurn = 0;
                }
                else{
                    currentTurn += 1;
                }
                break;
            case WILD:
                Scanner wildInput = new Scanner(System.in);
                System.out.println("Choose a color: R, G, B, Y\n");
                String newColor = wildInput.nextLine().toUpperCase();

                // Changing current color based on user input
                if (newColor.equals("R")) {
                    currentCard.setColor(Card.Color.RED);
                }
                else if (newColor.equals("G")) {
                    currentCard.setColor(Card.Color.GREEN);
                }
                else if (newColor.equals("B")) {
                    currentCard.setColor(Card.Color.BLUE);
                }
                else if (newColor.equals("Y")) {
                    currentCard.setColor(Card.Color.YELLOW);
                }
                break;
            //case WILD_DRAW_TWO
        }
    }
}
