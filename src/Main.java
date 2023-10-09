import java.util.ArrayList;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask the user for the number of players
        System.out.print("Enter the number of players: ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Create an ArrayList to hold the players
        ArrayList<Player> players = new ArrayList<>();

        // Create players
        for (int i = 0; i < numPlayers; i++) {
            Player player = new Player();
            players.add(player);
        }

        // Display each player's hand
        for (int i = 0; i < numPlayers; i++) {
            System.out.println("\nPlayer " + (i + 1) + "'s Hand:");
            players.get(i).viewHand();
        }
    }
}