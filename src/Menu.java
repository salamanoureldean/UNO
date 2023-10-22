import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    Scanner userInput;
    public Menu(Scanner userInput){
        this.userInput = userInput;
    }
    public void mainMenu() {
        System.out.printf("Main Menu:\n" +
                "(P)lay\n" +
                "(R)ules\n" +
                "(Q)uit\n" +
                "(H)elp\n");
        userInput = new Scanner(System.in);
        System.out.print("Please enter a command: ");
        String userInputText = userInput.nextLine();
        if (userInputText.equalsIgnoreCase("P")) {
            playMenu();
        } else if (userInputText.equalsIgnoreCase("R")) {
            rulesMenu();
        } else if (userInputText.equalsIgnoreCase("Q")) {
            quit();
        } else if (userInputText.equalsIgnoreCase("H") || userInputText.equalsIgnoreCase("HELP")) {
            helpMenu();
        } else {
            System.out.println("That's not a valid option, please try again.");
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
        System.out.print("Enter the number of players you want to play with: ");
        playerInput = userInput.nextLine();
        int playerCount;
        try {
            playerCount = Integer.parseInt(playerInput);
        } catch (NumberFormatException e) {
            playerCount = 0;
        }

        if (playerCount >= 2 && playerCount <= 4) {
            playersInGame = new ArrayList<Player>();
            for (int j = 0; j < playerCount; j++) {
                String playerName = "Player " + (j + 1);
                playersInGame.add(new Player(playerName));
            }
            gameStart();
        } else {
            System.out.println("Not a valid option, please try again");
            playMenu();
        }
    }
    public void rulesMenu() {
        System.out.println("Google the rules!\n" +
                "Menu Options:\n" +
                "(B)ack");
        userInput = new Scanner(System.in);
        System.out.print("Please enter a command: ");
        String back = userInput.nextLine();
        if (back.equalsIgnoreCase("B")) {
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
        System.out.println("To navigate through the menus, enter the letter in brackets for the selection,\n" +
                "you'd like to make. Here are your options:\n" +
                "(B)ack");
        userInput = new Scanner(System.in);
        System.out.print("Enter 'B' to go back: ");
        String back = userInput.nextLine();
        if (back.equalsIgnoreCase("B")) {
            mainMenu();
        } else {
            System.out.println("Please try again");
            helpMenu();
        }
    }
    public void playDisplay(int playerTurn, ArrayList<Player> playersInGame) {
        System.out.printf("Currently Player %d playing:\n", playerTurn + 1);
        System.out.print("Cards in hand: ");
        playersInGame.get(playerTurn).viewHand();
    }

}
