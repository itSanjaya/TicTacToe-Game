package cs.nyuad.csuh3260.tictactoe.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import cs.nyuad.csuh3260.tictactoe.board.exceptions.InvalidMoveException;

public class BoardTest {

    private Board board;

    // Setting up a board
    @Before
    public void setUp() {
        board = new Board();
    }

    // Testcase for board initialization
    @Test
    public void testBoardInitialization() {
        assertEquals("Board should be initialized with NONE", Board.Player.NONE, board.getPlayerAtPos(0, 0));
    }

    // Testcases for current player
    @Test
    public void testGetCurrentPlayer() {
        // At the start of the game, the current player should be Player.X
        assertEquals("The initial player should be Player.X", Board.Player.X, board.getCurrentPlayer());
    }

    @Test
    public void testValidMove() throws InvalidMoveException {
        board.playMove(0, 0);
        assertEquals("First move should be by player X at position (0,0)", Board.Player.X, board.getPlayerAtPos(0, 0));
    }

    @Test
    public void testNextValidMove() throws InvalidMoveException {
        board.playMove(0, 0);
        board.playMove(1, 0);
        assertEquals("Second move should be by player O at position (1,0)", Board.Player.O, board.getPlayerAtPos(1, 0));
    }

    // Out of Bounds Cases
    @Test
    public void testInvalidMove() throws InvalidMoveException {
        try {
            board.playMove(3, 3);
            fail("Expected an InvalidMoveException to be thrown");
        } catch (InvalidMoveException e) {
            assertEquals("Input coordinates are outside the board. Try again", e.getMessage());
        }
    }

    @Test
    public void testInvalidMoveExceptionMessageOutOfBoundsNegativeAndPositive() {
        try {
            board.playMove(-1, 3);
            fail("Expected an InvalidMoveException to be thrown");
        } catch (InvalidMoveException e) {
            assertEquals("Input coordinates are outside the board. Try again", e.getMessage());
        }
    }

    @Test
    public void testInvalidMoveExceptionMessageOutOfBoundsPositiveAndNegative() {
        try {
            board.playMove(3, -1);
            fail("Expected an InvalidMoveException to be thrown");
        } catch (InvalidMoveException e) {
            assertEquals("Input coordinates are outside the board. Try again", e.getMessage());
        }
    }

    @Test
    public void testInvalidMoveExceptionMessageOutOfBoundsNegative() {
        try {
            board.playMove(-1, -1);
            fail("Expected an InvalidMoveException to be thrown");
        } catch (InvalidMoveException e) {
            assertEquals("Input coordinates are outside the board. Try again", e.getMessage());
        }
    }

    // Tescase for SquareNotAvailable
    @Test
    public void testInvalidMoveExceptionMessageSquareNotAvailable() throws InvalidMoveException {
        board.playMove(0, 0);
        try {
            board.playMove(0, 0);
            fail("Expected an InvalidMoveException to be thrown");
        } catch (InvalidMoveException e) {
            assertEquals("Invalid Move: Square 0,0 already contains X", e.getMessage());
        }
    }


    // Tescases for getSymbol method
    @Test
    public void testGetSymbolForPlayerX() {
        assertEquals("The symbol for Player.X should be 'X'", "X",
                board.getSymbol(Board.Player.X));
    }

    @Test
    public void testGetSymbolForPlayerO() {
        assertEquals("The symbol for Player.O should be 'O'", "O",
                board.getSymbol(Board.Player.O));
    }

    @Test
    public void testGetSymbolForPlayerNone() {
        assertEquals("The symbol for Player.NONE should be space", " ",
                board.getSymbol(Board.Player.NONE));
    }

    // Tescase for winning move and horizontal case
    @Test
    public void testWinningMove() throws InvalidMoveException {
        board.playMove(0, 0); // X
        board.playMove(1, 0); // O
        board.playMove(0, 1); // X
        board.playMove(1, 1); // O
        board.playMove(0, 2); // X
        assertEquals("Player X should win", Board.Player.X, board.getWinner());
    }

    // Tescase for draw game
    @Test
    public void testDrawGame() throws InvalidMoveException {
        board.playMove(0, 0);
        board.playMove(1, 0);
        board.playMove(0, 1);
        board.playMove(1, 1);
        board.playMove(1, 2);
        board.playMove(0, 2);
        board.playMove(2, 0);
        board.playMove(2, 1);
        board.playMove(2, 2);
        assertEquals("Game should be a draw", Board.Player.NONE, board.getWinner());
    }
    // Tescase for hasWon method
    // Testcase for vertical win
    @Test
    public void testVerticalWin() throws InvalidMoveException {
        board.playMove(0, 0); // X
        board.playMove(0, 1); // O
        board.playMove(1, 0); // X
        board.playMove(1, 1); // O
        board.playMove(2, 0); // X
        assertEquals("Player X should win", Board.Player.X, board.getWinner());
    }

    // Testcase for right diagonal win
    @Test
    public void testRightDiagonalWin() throws InvalidMoveException {
        board.playMove(0, 0); // X
        board.playMove(0, 1); // O
        board.playMove(1, 1); // X
        board.playMove(1, 0); // O
        board.playMove(2, 2); // X
        assertEquals("Player X should win", Board.Player.X, board.getWinner());
    }

    // Testcase for left diagonal win
    @Test
    public void testLeftDiagonalWin() throws InvalidMoveException {
        board.playMove(0, 2); // X
        board.playMove(0, 1); // O
        board.playMove(1, 1); // X
        board.playMove(1, 0); // O
        board.playMove(2, 0); // X
        assertEquals("Player X should win", Board.Player.X, board.getWinner());
    }

    @Test
    public void testRightDiagonalNotATie() throws InvalidMoveException {
        // Simulate moves to create a right diagonal scenario without a win elsewhere
        board.playMove(0, 0); // X
        board.playMove(0, 1); // O
        board.playMove(2, 2); // X
        board.playMove(1, 0); // O
        board.playMove(1, 1); // X
        // Rest of the board is filled such that there's no win and no tie
        assertFalse(board.isTie());
    }

    @Test
    public void testLeftDiagonalNotATie() throws InvalidMoveException {
        // Simulate moves to create a right diagonal scenario without a win elsewhere
        board.playMove(0, 2);
        board.playMove(1, 0);
        board.playMove(2, 0);
        board.playMove(0, 1);
        board.playMove(1, 1);
        // Rest of the board is filled such that there's no win and no tie
        assertFalse(board.isTie());
    }

    @Test
    public void testVerticalNotATie() throws InvalidMoveException {
        // Simulate moves to create a vertical scenario without a win elsewhere
        board.playMove(0, 0);
        board.playMove(1, 1);
        board.playMove(2, 0);
        board.playMove(1, 2);
        board.playMove(1, 0);
        // Rest of the board is filled such that there's no win and no tie
        assertFalse(board.isTie());
    }

    @Test
    public void testRightDiagonalIsTie() throws InvalidMoveException {
        board.playMove(0, 0); // X
        board.playMove(1, 0); // O
        board.playMove(2, 0); // X
        board.playMove(2, 1); // O
        board.playMove(2, 2); // X
        board.playMove(0, 2); // O
        board.playMove(1, 1); // X
        board.playMove(0, 1); // O

        assertFalse(board.isTie());
    }

    @Test
    public void testLeftDiagonalIsTie() throws InvalidMoveException {
        board.playMove(0, 2); // X
        board.playMove(1, 2); // O
        board.playMove(2, 2); // X
        board.playMove(2, 1); // O
        board.playMove(2, 0); // X
        board.playMove(1, 0); // O
        board.playMove(1, 1); // X
        board.playMove(0, 0); // O

        assertFalse(board.isTie());
    }

    @Test
    public void testblockedDiagonal() throws InvalidMoveException {
        board.playMove(0, 0); // X
        board.playMove(1, 1); // O
        board.playMove(2, 2); // X
        board.playMove(0, 2); // O
        board.playMove(2, 0); // X

        assertFalse(board.isTie());
    }

}
