package cs.nyuad.csuh3260.tictactoe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cs.nyuad.csuh3260.tictactoe.TicTacToeGame.Scoreboard;
import cs.nyuad.csuh3260.tictactoe.board.Board;

public class TicTacToeGameTest {

    public TicTacToeGame.Scoreboard scoreboard;
    private Board board;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        scoreboard = new TicTacToeGame.Scoreboard();
        board = new Board();
        board.initBoard();
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void checkIfScoreBoardInitialized() {
        // score board values should be 0
        int expected = 0;
        assertEquals("Player X wins should be 0 upon initialization", expected, scoreboard.getplayerXWins());
        assertEquals("Player O wins should be 0 upon initialization", expected, scoreboard.getplayerOWins());
        assertEquals("Ties should be 0 upon initialization", expected, scoreboard.getTies());
    }

    @Test
    public void checkPlayerOWins() {
        int expected = 1;
        scoreboard.incrementPlayerOWins();
        assertEquals("Player O wins should be 1 after first win", expected, scoreboard.getplayerOWins());
    }

    @Test
    public void checkPlayerXWins() {
        int expected = 1;
        scoreboard.incrementPlayerXWins();
        assertEquals("Player X wins should be 1 after first win", expected, scoreboard.getplayerXWins());
    }

    @Test
    public void checkTies() {
        int expected = 1;
        scoreboard.incrementTies();
        assertEquals("Number of ties should be 1 after first tie", expected, scoreboard.getTies());
    }
    
    @Test
    public void checkPrintScoreBoard() {
        scoreboard.printScoreboard();
        String expectedOutput = "Scoreboard\r\n----------\r\nPlayer X Wins: 0\r\nPlayer O Wins: 0\r\nTies: 0";

        assertEquals("The on-screen output should match the expected scoreboard display.", expectedOutput,
                outContent.toString().trim());
    }

    @Test
    public void testResetGameBoardState() {
        // Test that calling resetGame resets board
        TicTacToeGame game = new TicTacToeGame();
        game.resetGame();
    }
    

    @Test
    public void testPlayGameEndsInTie() {
        UserInput mockUserInput = mock(UserInput.class);
        UserOutput mockUserOutput = mock(UserOutput.class);
        Board mockBoard = mock(Board.class);

        // Assuming the game ends in a tie without specifying all moves
        when(mockUserInput.readLine()).thenReturn("no");

        // Simulate the board state for a tie game
        when(mockBoard.getWinner()).thenReturn(Board.Player.NONE);
        // when(mockBoard.isFull()).thenReturn(true);

        TicTacToeGame game = new TicTacToeGame(mockBoard, new Scoreboard(), mockUserInput, mockUserOutput);
        game.playGame();

        // Verify the tie message is displayed
        verify(mockUserOutput).println("The game is a tie!");
    }


    @Test
    public void testPlayGamePlayerXWins() {
        UserInput mockUserInput = mock(UserInput.class);
        UserOutput mockUserOutput = mock(UserOutput.class);
        Board mockBoard = mock(Board.class);

        when(mockUserInput.readLine()).thenReturn("no"); // User decides not to continue after the game ends
        when(mockBoard.getWinner()).thenReturn(Board.Player.X); // Simulate Player X winning the game

        TicTacToeGame game = new TicTacToeGame(mockBoard, new Scoreboard(), mockUserInput, mockUserOutput);
        game.playGame();

        // Verify the win message for Player X is displayed
        verify(mockUserOutput).println("Player X has won the game!");
    }


    @Test
    public void testPlayGamePlayerOWins() {
        UserInput mockUserInput = mock(UserInput.class);
        UserOutput mockUserOutput = mock(UserOutput.class);
        Board mockBoard = mock(Board.class);

        when(mockUserInput.readLine()).thenReturn("no"); // User decides not to continue after the game ends
        when(mockBoard.getWinner()).thenReturn(Board.Player.O); // Simulate Player O winning the game

        TicTacToeGame game = new TicTacToeGame(mockBoard, new Scoreboard(), mockUserInput, mockUserOutput);
        game.playGame();

        // Verify the win message for Player O is displayed
        verify(mockUserOutput).println("Player O has won the game!");
    }

    @Test
    public void testPrintln() {
        ConsoleOutput consoleOutput = new ConsoleOutput();
        String message = "Test message";
        consoleOutput.println(message);
        
        assertEquals(message + System.lineSeparator(), outContent.toString());
    }

    
    @Test
    public void checkprintBoard(){
        board.printBoard();
        //To identify the correct line separator
        String lineSeparator = System.lineSeparator();
        
        String expectedOutput = "|   |  " + lineSeparator +
        "----------" + lineSeparator + 
        "  |   |  " + lineSeparator +
        "----------" + lineSeparator + 
        "  |   |  " + lineSeparator +
        "----------";  
        expectedOutput.trim();
        assertEquals("The on-screen output should match the expected board display.", expectedOutput, outContent.toString().trim());
    }

    @Test
    public void testPromptNextPlayerX() {
        Board mockBoard = mock(Board.class);
        when(mockBoard.getCurrentPlayer()).thenReturn(Board.Player.X);
        when(mockBoard.getSymbol(Board.Player.X)).thenReturn("X");

        UserInput mockUserInput = mock(UserInput.class);
        UserOutput mockUserOutput = mock(UserOutput.class);

        TicTacToeGame game = new TicTacToeGame(mockBoard, new Scoreboard(), mockUserInput, mockUserOutput);
        game.promptNextPlayer();

        verify(mockUserOutput).println(contains("It's player X's turn."));
    }

    @Test
    public void testPromptNextPlayerO() {
        Board mockBoard = mock(Board.class);
        when(mockBoard.getCurrentPlayer()).thenReturn(Board.Player.O);
        when(mockBoard.getSymbol(Board.Player.O)).thenReturn("O");

        UserInput mockUserInput = mock(UserInput.class);
        UserOutput mockUserOutput = mock(UserOutput.class);

        TicTacToeGame game = new TicTacToeGame(mockBoard, new Scoreboard(), mockUserInput, mockUserOutput);
        game.promptNextPlayer();

        verify(mockUserOutput).println(contains("It's player O's turn."));
    }

    @Test
    public void testTerminateDecisionYes() {
        UserInput mockUserInput = mock(UserInput.class);
        UserOutput mockUserOutput = mock(UserOutput.class);
        when(mockUserInput.readLine()).thenReturn("yes");

        TicTacToeGame game = new TicTacToeGame(null, null, mockUserInput, mockUserOutput);
        assertTrue(game.terminateDecision());
    }

    @Test
    public void testTerminateDecisionNo() {
        UserInput mockUserInput = mock(UserInput.class);
        UserOutput mockUserOutput = mock(UserOutput.class);
        when(mockUserInput.readLine()).thenReturn("no");

        TicTacToeGame game = new TicTacToeGame(null, null, mockUserInput, mockUserOutput);
        assertFalse(game.terminateDecision());
    }
}
