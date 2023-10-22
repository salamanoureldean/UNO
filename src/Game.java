import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.util.Stack;

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
                playersInGame.add(new Player(playerName, theDeck));
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

        // Prompt the player to choose an action
        while (true) {
            System.out.println("Current Card is: " + currentCard.stringCard());
            playDisplay(currentTurn);
            System.out.println();
            System.out.println(currentPlayer.getName() + ", choose your action:");
            System.out.println("  (D)raw a card");
            System.out.println("  (P)lay a card");
            System.out.println("  (C)all Uno Against Other Player");


            String playerInput = userInput.nextLine();
            if (playerInput.equalsIgnoreCase("D")) {
                // Draw a card
                theDeck.isZero(currentCard);
                currentPlayer.drawCard(theDeck);
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
                                if (currentPlayer.getHand().getCards().size() == 2) {
                                    System.out.println(currentPlayer.getName() + " has Uno!");
                                    System.out.print("You have one card left! Do you want to call 'Uno'? (Y/N): ");
                                    String unoInput = userInput.nextLine();
                                    if (unoInput.equalsIgnoreCase("Y")) {
                                        currentPlayer.callUno();
                                    }
                                }
                                currentCard = cardToPlay;
                                cardFunctionality(currentCard);
                                theDeck.place(cardToPlay);
                                currentPlayer.playCard(cardToPlay);
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
            } else if (playerInput.equalsIgnoreCase("C")) {
                // Call Uno against another player
                callUnoAgainst(currentPlayer);
            }
        }
    }




    public void callUnoAgainst(Player currentPlayer) {
        System.out.print("Select a player to call Uno against (Enter player number): ");
        int playerNumber;
        try {
            playerNumber = Integer.parseInt(userInput.nextLine()) - 1; // Adjust for 0-based indexing
            if (playerNumber >= 0 && playerNumber < playersInGame.size() && playerNumber != currentTurn) {
                Player targetPlayer = playersInGame.get(playerNumber);

                if (targetPlayer.getHand().getCards().size() == 1 && !targetPlayer.hasUno()) {
                    System.out.println(targetPlayer.getName() + " forgot to call Uno!");
                    System.out.println(currentPlayer.getName() + " calls Uno against " + targetPlayer.getName() + "! " +
                            targetPlayer.getName() + " draws 4 cards.");

                    // Draw 4 cards for the target player
                    for (int i = 0; i < 4; i++) {
                        targetPlayer.drawCard(theDeck);
                    }
                } else {
                    System.out.println(targetPlayer.getName() + " has more than one card or has already called Uno.");
                }
            } else {
                System.out.println("Invalid player number. Please choose a valid player.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid player number.");
        }
    }

    public void winner() {
        for (Player player : playersInGame) {
            if (player.getHand().getCards().isEmpty()) {
                System.out.println(player.getName() + " has won!");
                winner = true;
            }
        }
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
        cardFunctionality(currentCard);

        while (!winner) {
            Player currentPlayer = playersInGame.get(currentTurn);

            playPrompt();

            // Rotating players
            currentTurn = (currentTurn + 1) % playersInGame.size();

            winner();
        }

        winnerScore();


    }

    public void cardFunctionality(Card playedCard) {
            Card.Value cardValue = playedCard.getValue();
            //Card.Color cardColor = playedCard.getColor();

            switch (cardValue) {
                case REVERSE:
                    Collections.reverse(playersInGame);
                    System.out.println("The play has been reversed!");
                    break;
                case SKIP:
                    if (currentTurn + 1 > playersInGame.size()){
                        currentTurn = 0;
                    }
                    else{
                        currentTurn += 1;
                    }
                    System.out.println("Player " + (currentTurn + 1) + "has been skipped!");
                    break;
                case WILD:
                    Scanner wildInput = new Scanner(System.in);
                    System.out.println("Choose a color: R, G, B, Y\n");
                    String newColor = wildInput.nextLine().toUpperCase();

                    // Changing current color based on user input
                    if (newColor.equals("R")) {
                        currentCard.setColor(Card.Color.RED);
                        System.out.println("The color has been changed to red.");
                    }
                    else if (newColor.equals("G")) {
                        currentCard.setColor(Card.Color.GREEN);
                        System.out.println("The color has been changed to green.");
                    }
                    else if (newColor.equals("B")) {
                        currentCard.setColor(Card.Color.BLUE);
                        System.out.println("The color has been changed to blue.");
                    }
                    else if (newColor.equals("Y")) {
                        currentCard.setColor(Card.Color.YELLOW);
                        System.out.println("The color has been changed to yellow.");
                    }
                    break;
                case WILD_DRAW_TWO:
                    // Getting next player's index in order to make them draw two cards
                    int nextPlayerIndex = (currentTurn + 1) % playersInGame.size();
                    Player nextPlayer = playersInGame.get(nextPlayerIndex);
                    for (int i = 0; i < 2; i++){
                        nextPlayer.drawCard(theDeck);
                    }
                    // Changing current color of cards being played based on user input
                    wildInput = new Scanner(System.in);
                    System.out.println("Choose a color: R, G, B, Y\n");
                    newColor = wildInput.nextLine().toUpperCase();

                    if (newColor.equals("R")) {
                        currentCard.setColor(Card.Color.RED);
                        System.out.println("The color has been changed to red.");
                    }
                    else if (newColor.equals("G")) {
                        currentCard.setColor(Card.Color.GREEN);
                        System.out.println("The color has been changed to green.");
                    }
                    else if (newColor.equals("B")) {
                        currentCard.setColor(Card.Color.BLUE);
                        System.out.println("The color has been changed to blue.");
                    }
                    else if (newColor.equals("Y")) {
                        currentCard.setColor(Card.Color.YELLOW);
                        System.out.println("The color has been changed to yellow.");
                    }
                    break;
            }
        }

        public void winnerScore(){
            int winnerScore = 0;
            //Iterating through each player to calculate their hand's score
            for (int i = 0; i < playersInGame.size(); i++) {
                int sumOfPlayer = 0;

                //Iterating through each card in players (i) hand
                for (Card card : playersInGame.get(i).getHand().getCards()) {
                    int cardValue = card.getValue().ordinal();

                    //Checking the value of each card (j)
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
                //Adding total value of player(i) score into the winner's score
                winnerScore += sumOfPlayer;
            }
            System.out.println("The Winner has a score of: " + winnerScore);
        }
    }