# Uno Card Game

Uno Card Game is a Java GUI-based implementation of the classic Uno card game. This updated README provides an overview of the game's GUI version, its features, instructions on how to play, and the technicalities involved in the GUI and game logic.

## Table of Contents
- [Features](#features)
- [Game Rules](#game-rules)
- [Getting Started](#getting-started)
- [How to Play](#how-to-play)
- [Challenging Wild Draw Twos](#challenging-uno-calls)
- [Contributing](#contributing)
- [License](#license)

## Features

- Supports 2 to 4 players with an interactive graphical user interface.
- Implements standard Uno rules with additional GUI-based functionalities.
- Enhanced gameplay experience through mouse interactions in a JFrame environment.
- Includes a "Next Player" button to facilitate controlled game progression.
- User-friendly and visually appealing design for an immersive playing experience.

## Game Rules

Uno is a card game where the objective is to be the first player to get rid of all your cards. The game follows these basic rules:

- Players take turns playing a card that matches the color or value of the top card on the discard pile.
- Special cards (e.g., Skip, Reverse, Wild) have unique effects.
- Players can challenge Wild Draw Twos.

## Getting Started

To run the Uno Card Game on your local machine, follow these steps:

1. Clone this repository:

   ```bash
   git clone https://github.com/salamanoureldean/UNO.git
   ```

2. Compile the Java code:

   ```bash
   javac MainMenuGUI.java
   ```

3. Run the game:

   ```bash
   java MainMenuGUI
   ```

The GUI will open, and you can start playing by following the on-screen instructions.

## How to Play

Uno Card Game can be played by 2 to 4 players. Here's how to play:
1. Launch the game following the "Getting Started" instructions.
2. Select the number of players from the main menu.
3. The game begins with the GUI showing each player's hand and the current card in play.
4. Players use the mouse to interact with the game, choosing to draw or play cards.
5. Use the "Next Player" button to proceed to the next player's turn.
6. The game continues until a player has no cards left.

## Challenging

If a player plays a wild draw two, the next player can challenge it. If the next player wins, the person who initially played the wild draw two picks up 2 cards. If the current player wins, or the next player denies the challenge, the next player picks up 2 cards.

## Contributing

### Contributors
- Mahad Mohamed Yonis
- Abody Majeed - JavaDocs
- Salama Noureldean
- Pietro Adamvoski

## Author
- Mahad Mohamed Yonis

## License
This project is intended strictly for educational and non-commercial purposes.
