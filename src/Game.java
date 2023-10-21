import java.util.ArrayList;
import java.util.Scanner;
public class Game {

    /**
     * Initializing variables in order to perform the play function
     */
    ArrayList<Player> playersInGame;
    boolean gameOn = true;
    Scanner userInput1;
    String menuInput;

    public Game(){}

    public void play(){
        mainMenu();
    }

    public void mainMenu(){
        System.out.printf("Main Menu:\n" +
                "(P)lay\n" +
                "(R)ules\n" +
                "(Q)uit\n" +
                "(H)elp\n");
        userInput1 = new Scanner(System.in);
        System.out.print("Please enter a command: ");
        menuInput = userInput1.nextLine();
        if(menuInput.toUpperCase().equals("P")) {
            playMenu();
        }
        else if(menuInput.toUpperCase().equals("R")){
            rulesMenu();
        }
        else if(menuInput.toUpperCase().equals("Q")){
            quit();
        }
        else if(menuInput.toUpperCase().equals("H") || menuInput.toUpperCase().equals("HELP")){
            helpMenu();
        }
        else{
            System.out.println("Thats not a valid option try again....");
            mainMenu();
        }
    }
    public void playMenu(){
        String playerInput;
        System.out.println("How many players are there?:\n" +
                "(2) Players\n" +
                "(3) Players\n" +
                "(4) Players\n");
        userInput1 = new Scanner(System.in);
        System.out.print("Enter the amount of players you want to play with: ");
        playerInput = userInput1.nextLine();
        if(playerInput.equals("2")){
            playersInGame = new ArrayList<Player>();
            for(int j =0; j < 2; j++){
                playersInGame.add(new Player());
            }
            gameStart();
        }
        else if(playerInput.equals("3")){
            playersInGame = new ArrayList<Player>();
            for(int j =0; j < 3; j++){
                playersInGame.add(new Player());
            }
            gameStart();
        }
        else if(playerInput.equals("4")){
            playersInGame = new ArrayList<Player>();
            for(int j =0; j < 4; j++){
                playersInGame.add(new Player());
            }
            gameStart();
        }
        else{
            System.out.println("Not a valid option please try again");
            playMenu();
        }
    }

    public void rulesMenu(){
        String back;
        System.out.println("Google the rules!\n" +
                "Menu Options:" +
                "(B)ack");
        userInput1 = new Scanner(System.in);
        System.out.print("Please enter a command: ");
        back = userInput1.nextLine();
        if(back.toUpperCase().equals("B")){
            mainMenu();
        }
        else{
            System.out.println("Please try again...");
            rulesMenu();
        }
    }

    public void quit(){
        System.out.println("Thank you for playing UNO!");
        System.exit(0);
    }

    public void helpMenu(){
        String back;
        System.out.println("To navigate through the menus enter the letter in brackets for the selection,\n" +
                "you'd like to make, here are your options:\n" +
                "(B)ack");
        userInput1 = new Scanner(System.in);
        System.out.print("Enter 'B' to go back: ");
        back = userInput1.nextLine();
        if(back.toUpperCase().equals("B")){
            mainMenu();
        }
        else{
            System.out.println("Please try again");
            helpMenu();
        }
    }

    public void gameStart(){

    }

}
