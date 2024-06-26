package cs.nyuad.csuh3260.tictactoe;

import java.util.Scanner;

import cs.nyuad.csuh3260.tictactoe.board.Board;
import cs.nyuad.csuh3260.tictactoe.board.exceptions.InvalidMoveException;

// Interface for handling user input
interface UserInput {
    String readLine();
}

// Interface for handling user output
interface UserOutput {
    void println(String message);
}

// Implementation of UserInput using Scanner
class ConsoleInput implements UserInput {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public String readLine() {
        return scanner.nextLine();
    }
}

// Implementation of UserOutput using System.out
class ConsoleOutput implements UserOutput {
    @Override
    public void println(String message) {
        System.out.println(message);
    }
}

// Main TicTacToeGame class
public class TicTacToeGame {
    private Board board;
    private Boolean playGameAgain = true;
    private Scoreboard scoreboard;
    private final UserInput userInput;
    private final UserOutput userOutput;

    public TicTacToeGame() {
        this.userInput = null;
        this.userOutput = null;
        board = new Board();
        scoreboard = new Scoreboard(); // initialize the scoreboard
    }

    // Constructor with dependency injection for Board, UserInput, and UserOutput
    public TicTacToeGame(Board board, Scoreboard scoreboard, UserInput userInput, UserOutput userOutput) {
        this.board = board;
        this.scoreboard = scoreboard;
        this.userInput = userInput;
        this.userOutput = userOutput;
    }

    // If user wants to restart game, re-initialize board
    public void resetGame() {
        board = new Board();
    }

    // Inner class to keep track of player scores
    public static class Scoreboard {
        private int playerXWins;
        private int playerOWins;
        private int ties;

        public int getplayerXWins() {
            return playerXWins;
        }

        public int getplayerOWins() {
            return playerOWins;
        }

        public int getTies() {
            return ties;
        }

        // Initialize scores to zero
        public Scoreboard() {
            playerXWins = 0;
            playerOWins = 0;
            ties = 0;
        }

        // Increment player X's win count
        public void incrementPlayerXWins() {
            playerXWins++;
        }

        // Increment player O's win count
        public void incrementPlayerOWins() {
            playerOWins++;
        }

        // Increment the tie count
        public void incrementTies() {
            ties++;
        }

        // Display the current scores
        public void printScoreboard() {
            System.out.println(" ");
            System.out.println("Scoreboard");
            System.out.println("----------");
            System.out.println("Player X Wins: " + playerXWins);
            System.out.println("Player O Wins: " + playerOWins);
            System.out.println("Ties: " + ties);
            System.out.println(" ");
        }
    }

    //Change the player
    public void promptNextPlayer() {
        userOutput.println("It's player " + board.getSymbol(board.getCurrentPlayer()) + "'s turn. Please enter the coordinates of your next move as x,y: ");
    }

    public void playGame() {
        do {
            while (board.getWinner() == null) {
                board.printBoard();
                promptNextPlayer();
                String line = userInput.readLine();
                String[] input = line.split(",");
                try {
                    board.playMove(Integer.parseInt(input[0]), Integer.parseInt(input[1]));
                } catch (InvalidMoveException e) {
                    userOutput.println("Invalid coordinates. Try again.");
                }
            }

            board.printBoard();
            Board.Player winner = board.getWinner();

            // Update scores and display the result of the game
            if (winner != Board.Player.NONE) { // If winner is not NONE, then display the winner
                userOutput.println("Player " + winner + " has won the game!");
                if (winner == Board.Player.X)
                    scoreboard.incrementPlayerXWins(); // Checks if winner is X, and then increment the score for player X
                else
                    scoreboard.incrementPlayerOWins(); // Checks if winner is O, and then increment the score for player O
            } else {
                userOutput.println("The game is a tie!");
                scoreboard.incrementTies(); // If winner is NONE, increase the number of ties
            }
            // Get play again decision
            playGameAgain = terminateDecision();
            resetGame();
        } while (playGameAgain);
        // Display the current scores
        scoreboard.printScoreboard();

    }

    public boolean terminateDecision() {
        userOutput.println("Would you like to play another game? (yes/no)");
        String response = userInput.readLine().toLowerCase();
        return "yes".equals(response);
    }

    // Main method to run the game
    public static void main(String[] args) {
        Board board = new Board();
        Scoreboard scoreboard = new Scoreboard();
        UserInput consoleInput = new ConsoleInput();
        UserOutput consoleOutput = new ConsoleOutput();
        TicTacToeGame game = new TicTacToeGame(board, scoreboard, consoleInput, consoleOutput);
        game.playGame();
    }
}