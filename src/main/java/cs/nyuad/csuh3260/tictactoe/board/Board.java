package cs.nyuad.csuh3260.tictactoe.board;

import cs.nyuad.csuh3260.tictactoe.board.exceptions.InvalidMoveException;

/**
 * Created by snadi on 2018-07-16.
 */
public class Board {

    public enum Player {X, O, NONE};
    private Player currentPlayer;
    private Player winner;
    private Player board[][];

    public Board(){
        board = new Player[3][3];
        initBoard();
        winner = null;
        currentPlayer = Player.X;
    }

    public void initBoard(){
        for (int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                board[i][j] = Player.NONE;

    }

    public void playMove(int row, int col) throws InvalidMoveException {

        if(row < 0 || row >= 3 || col <0 || col >=3)
            throw new InvalidMoveException("Input coordinates are outside the board. Try again");

        if(!isSquareAvailable(row, col)){
            //the given coordinates already contain a played move
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid Move: Square ");
            stringBuilder.append(row);
            stringBuilder.append(",");
            stringBuilder.append(col);
            stringBuilder.append(" already contains ");
            stringBuilder.append(getSymbol(board[row][col]));
            throw new InvalidMoveException(stringBuilder.toString());
        }else{
            board[row][col] = currentPlayer;

            // This if-else statement is called after every move to set a winner of the game
            // The if statement declares either playerX or playerO as the winner
            if (hasWon(row, col))
                winner = currentPlayer;
            // After every move the isTie function is called to check if there are no more possibilities for either player to win the game
            else if(isTie())
                // if the function returns true then Player.NONE is declared as the winner indicating that it is a tie and neither player has won
                winner = Player.NONE;
            else
                // this is to change the player turn after every game
                if (currentPlayer == Player.X)
                    currentPlayer = Player.O;
                else
                    currentPlayer = Player.X;
        }

    }


    private boolean isSquareAvailable(int row, int col){
        return (board[row][col] != Player.X && board[row][col] != Player.O);
    }

    public String getSymbol(Player player) {
        if (player == Player.X) {
            return "X";
        } else if (player == Player.O) {
            return "O";
        } else {
            return " ";
        }
    }

    public boolean hasWon(int lastColPlayed, int lastRowPlayed){

        //check horizontal
        if (board[lastColPlayed][0].equals(board[lastColPlayed][1]) && board[lastColPlayed][1].equals(board[lastColPlayed][2])){
            return true;
        }
        //check vertical
        else if(board[0][lastRowPlayed].equals(board[1][lastRowPlayed]) && board[1][lastRowPlayed].equals(board[2][lastRowPlayed])){
            return true;
        }
        //check diagonal
        else{
            if(isOnRightDiag(lastColPlayed, lastRowPlayed) && board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]))
                return true;
            else if (isOnLeftDiag(lastColPlayed, lastRowPlayed) && board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0]))
                return true;
        }

        return false;
    }

    // Check if the game is a tie 
    public boolean isTie() {
        // Check for any potential win in rows, columns, or diagonals for any player
        for (int i = 0; i < 3; i++) {
            if (isPotentialWinLine(board[i][0], board[i][1], board[i][2]) || isPotentialWinLine(board[0][i], board[1][i], board[2][i])) {
                return false;
            }
        }
        // Check diagonals for potential wins
        if (isPotentialWinLine(board[0][0], board[1][1], board[2][2]) || isPotentialWinLine(board[0][2], board[1][1], board[2][0])) {
            return false;
        }
        // If no potential wins are found, it is a tie
        return true;
    }

    private boolean isPotentialWinLine(Player a, Player b, Player c) {
        // Check if all cells in the line are either the same player or NONE else it's not a potential win situation
        if (a == Player.NONE) a = b == Player.NONE ? c : b;
        return (a == b || b == Player.NONE) && (a == c || c == Player.NONE);
    }
    
    private boolean isOnRightDiag(int col, int row){
        return (col == 0 && row == 0) || (col == 1 && row == 1) || (col == 2 & row == 2);
    }

    private boolean isOnLeftDiag(int col, int row){
        return (col == 0 && row == 2) || (col == 1 && row == 1) || (col == 2 & row == 0);
    }

    public void printBoard(){
        for(int i  = 0; i < 3; i++){
            for(int j = 0 ; j < 3; j++){

               System.out.print(getSymbol(board[i][j]));

                if (j == 2)
                    System.out.println("");
                else
                    System.out.print(" | ");
            }
            System.out.println("----------");
        }
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getWinner() {
        return winner;
    }

    public Player getPlayerAtPos(int row, int col){
        return board[row][col];
    }


}
