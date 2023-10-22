import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Game {
    private Deck theDeck;
    private ArrayList<Player> playersInGame;
    private boolean winner = false;
    private Scanner userInput;
    private Card currentCard;
    private int currentTurn = 0;

    public Game() {
        theDeck = new Deck();
        mainMenu();
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
                playersInGame.add(new Player(playerName, theDeck.getCompleteDeck()));
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

    public void playDisplay(int playerTurn) {
        System.out.printf("Currently Player %d playing:\n", playerTurn + 1);
        System.out.print("Cards in hand: ");
        playersInGame.get(playerTurn).viewHand();
    }

    public void playPrompt() {
        Player currentPlayer = playersInGame.get(currentTurn);

        System.out.println("Current Card is: " + currentCard.stringCard());
        playDisplay(currentTurn);

        // Prompt the player to choose an action
        while (true) {
            System.out.println();
            System.out.println(currentPlayer.getName() + ", choose your action:");
            System.out.println("  (D)raw a card");
            System.out.println("  (P)lay a card");

            String playerInput = userInput.nextLine();
            if (playerInput.equalsIgnoreCase("D")) {
                // Draw a card
                currentPlayer.drawCard(theDeck.getCompleteDeck());
                break;
            } else if (playerInput.equalsIgnoreCase("P")) {
                if (currentPlayer.getHand().getCards().isEmpty()) {
                    System.out.println("Your hand is empty. You cannot play a card.");
                } else {
                    System.out.print("Enter the index of the card to play: ");
                    int cardIndex;
                    try {
                        cardIndex = Integer.parseInt(userInput.nextLine());
                        if (cardIndex >= 0 && cardIndex < currentPlayer.getHand().getCards().size()) {
                            Card cardToPlay = currentPlayer.getHand().getCards().get(cardIndex);
                            if (isPlayable(cardToPlay)) {
                                theDeck.place(cardToPlay);
                                currentPlayer.playCard(cardToPlay);
                                currentCard = cardToPlay;
                                break;
                            } else {
                                System.out.println("Invalid move. You cannot play this card.");
                            }
                        } else {
                            System.out.println("Invalid card index. Please try again.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid card index.");
                    }
                }
            } else {
                System.out.println("Invalid input. Please choose (D)raw or (P)lay.");
            }
        }
    }
    public boolean winner() {
        for (Player player : playersInGame) {
            if (player.getHand().getCards().isEmpty()) {
                System.out.println(player.getName() + " has won!");
                return true;
            }
        }
        return false;
    }
    public boolean isPlayable(Card card) {
        if (currentCard == null) {
            // If no card has been played yet, any card is playable.
            return true;
        }

        if (currentCard.getColor() == card.getColor() || currentCard.getValue() == card.getValue()) {
            return true;
        } else if (card.getValue() == Card.Value.WILD || card.getValue() == Card.Value.WILD_DRAW_TWO) {
            return true;
        } else if (card.getValue() == Card.Value.SKIP || card.getValue() == Card.Value.REVERSE) {
            return card.getColor() == currentCard.getColor();
        }

        return false;
    }

    public void gameStart() {
        // Draw the first card to set the current card
        currentCard = theDeck.draw();

        while (!winner) {
            Player currentPlayer = playersInGame.get(currentTurn);

            playPrompt();

            // Rotating players
            currentTurn = (currentTurn + 1) % playersInGame.size();

            winner();
        }

        int winnerScore = 0;
        for (int i = 0; i < playersInGame.size(); i++) {
            int sumOfPlayer = 0;
            for (Card card : playersInGame.get(i).getHand().getCards()) {
                int cardValue = card.getValue().ordinal();
                if (cardValue >= 0 && cardValue < 10) {
                    sumOfPlayer += cardValue;
                } else {
                    switch (cardValue) {
                        case 10:
                            sumOfPlayer += 20;
                            break;
                        case 11:
                            sumOfPlayer += 30;
                            break;
                        case 12:
                            sumOfPlayer += 40;
                            break;
                        case 13:
                            sumOfPlayer += 50;
                            break;
                    }
                }
            }
            winnerScore += sumOfPlayer;
        }
        System.out.println("The Winner has a score of: " + winnerScore);
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
                case WILD_DRAW_TWO:
                    // Getting next player's index in order to make them draw two cards
                    int nextPlayerIndex = (currentTurn + 1) % playersInGame.size();
                    Player nextPlayer = playersInGame.get(nextPlayerIndex);
                    for (int i = 0; i < 2; i++){
                        nextPlayer.drawCard(theDeck.getCompleteDeck());
                    }
                    // Changing current color of cards being played based on user input
                    wildInput = new Scanner(System.in);
                    System.out.println("Choose a color: R, G, B, Y\n");
                    newColor = wildInput.nextLine().toUpperCase();

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
            }
        }
    }