# Uno FLip Card Game

The Uno Flip Card Game is an enhanced Java GUI-based implementation of the classic Uno game, integrating the exciting Uno Flip variant. This README provides an overview of the Uno Flip game's GUI version, highlighting its unique features, game rules, instructions on how to play, and the technicalities involved in the GUI and game logic.

## Table of Contents
- [Features](#features)
- [Game Rules](#game-rules)
- [Getting Started](#getting-started)
- [How to Play](#how-to-play)
- [Challenging UNO Flip Rules](#challenging-uno-flip-rules)
- [Save/Load Game](#save-load-game)
- [Redo/Undo Move](#redo-undo-move)
- [Replay Game](#replay-game)
- [Contributing](#contributing)
- [License](#license)

## Features

- Supports 2 to 12 players with an interactive graphical user interface.
- Includes an AI player option, allowing for up to 6 AIs.
- Combines standard Uno with the light and dark sides of Uno Flip, offering a dynamic gameplay experience.
- Enhanced gameplay experience through mouse interactions in a JFrame environment.
- Includes a "Next Player" button to facilitate controlled game progression.
- Flip Card to switch between light and dark sides of the deck.
- User-friendly and visually appealing design for an immersive playing experience.
- Save and load game function.
- Redo/Undo move feature.
- Replay game option.

## Game Rules

Uno Flip extends the classic Uno game with a twist, featuring dual-sided cards with a light side and a dark side, each with distinct rules and action cards. The objective remains to be the first player to get rid of all your cards. Key points include:

- The Light Side features blue/green/yellow/red cards with action cards like Draw One, Wild Draw Two, and Flip.
- The Dark Side introduces pink/teal/purple/orange cards with action cards like Draw Five, Wild Draw Color, and Flip.
- Gameplay starts on the Light Side and switches sides through Flip cards.
Players must match the topmost card on the discard pile by number, color, or symbol, depending on the side currently in play.

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

1. Launch the game following the "Getting Started" instructions.
2. Select the number of players (2-10) from the main menu.
3. Each player is dealt 7 cards, with gameplay beginning on the Light Side.
4. Players match the top card of the discard pile in color, number, or symbol, depending on the current side.
5. Action cards have various effects, such as drawing extra cards, skipping turns, reversing play directions, and flipping sides.
6. If unable to play, draw a card from the draw pile. If it's playable, you may choose to play it or keep it.
7. The round ends when a player has no cards left. Points are scored based on the cards remaining in other players' hands.

## Challenging Uno Flip Rules
Players can challenge Wild Draw Two and Wild Draw Color cards if they suspect the card was played illegally.
In case of a successful challenge, the player who played the action card draws cards instead.
If the challenge is unsuccessful, the challenger draws additional cards as per the rules of the challenged card.

## Save/Load Game
New: Save and load game function allows players to save the current state of the game and resume it later.

## Redo/Undo Move
New: Redo/Undo move feature enables players to undo their last move or redo a previously undone move.

## Replay Game
New: Replay game option lets players restart the game from the beginning for a fresh gaming experience.

## Contributing

### Contributors
- Mahad Mohamed Yonis
- Abody Majeed
- Salama Noureldean
- Pietro Adamvoski

## Author
- Mahad Mohamed Yonis

## License
This project is intended strictly for educational and non-commercial purposes.
