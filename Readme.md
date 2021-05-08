<h1 align="center">Welcome to Battleship Game 👋</h1>

## What project is it?

> This is a multithreaded socket program of the Battleship game implemented with a server and game client. Both the client and the server have a GUI interface. The server’s GUI is used to display and monitor game traffic; the client’s GUI contains a fleet map, a target map, and any other components required to play the game and chat during gameplay. I created this project in the third semester of college. <a href="https://www.sait.ca/programs-and-courses/diplomas/information-technology" target='_blank'>(SAIT)</a>.

## How does it work?

- The server is multithreaded capable of supporting multiple pairs of Battleship players logged on to chat and play the game concurrently.
- Clients are allowed to register on the server and play the game of Battleship with the next available player logged on to the server. The two players can communications during the game.
- The game server waits until two players have logged on to the server. It pairs these players off under a separate thread of control which, when created, will place battleships randomly on the map. The game will randomly select which player goes first.
- At the end of each game, the players should be given a choice if they wish to continue playing.

## Game Rules

- The map grids are to be set up as a minimum 10 X 10 grid.
- The ships in a fleet and how many squares occupies:
  | Ship | Squares |
  | -- | :--: |
  | Aircraft carrier | 5|
  | Battleship | 4 |
  | Cruiser | 3 |
  | Submarine | 3 |
  | Destroyer | 2 |

  Note: Ships are placed only horizontally or vertically (not diagonally).

  <br/>

## Languages

<p align="left"> <a href="https://www.java.com" target="_blank"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="40" height="40"/> </a> <a href="https://git-scm.com/" target="_blank"> <img src="https://www.vectorlogo.zone/logos/git-scm/git-scm-icon.svg" alt="git" width="40" height="40"/> </a> </p>

- JavaFX, Thread, Socket, Serialization

<br/>

## Screen Shots

> Click the picture to see the demo video!

[![Demo](./screenshot.jpg?raw=true)](https://youtu.be/bTxTnOdCqcQ)

## Author

👤 **Jaeyoung Kim**

- Website: https://www.jaeyoungkim.ca/
- Github: [@jaeyoung-kim-dev](https://github.com/jaeyoung-kim-dev)
- LinkedIn: [@jaeyoung-kim-dev](https://www.linkedin.com/in/jaeyoung-kim-dev/)
- Medium(Blog): [@jaeyoung-kim-dev](https://jaeyoung-kim-dev.medium.com/)
- Email: jaeyong.kim.dev@gmail.com
