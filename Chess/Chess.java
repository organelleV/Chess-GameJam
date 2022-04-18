import java.io.*;
import java.util.*;

/*
0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0
*/


/**
* Home of the Chess game
*/
public class Chess {

  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_GREEN = "\u001B[32m";

  static Piece[][] board = new Piece[8][8];

  
  
  // chess AI

  // find available moves
  // returns 2d array of positions
  // int[n][2]
  public ArrayList<int[]> availableMoves(int row,int col){
    ArrayList<int[ ] > validMoves = new ArrayList<int[]>();
    
    for (int i=0;i<board.length;i++){
      for (int j=0;j<board.length;j++){
        if (validMovement(row,col,i,j)){
          // valid move
          int[] pos = {i,j};
          
          validMoves.add(pos);
        }
        }
      }

    return validMoves;

  }
  
  
  // calculatePoints adds up all the points of pieces and subtracts enemy pieces
  public int calculatePoints(boolean color){
    int totalPoints = 0;
    for (int i=0;i<board.length;i++){
      for (int j=0;j<board.length;j++){
        Piece p = board[i][j];
        if (p.color == color){
          totalPoints += p.points;
        }
        else{
          totalPoints -= p.points;
        }
    }
    }
    return totalPoints;
  }

  
  // path checking methods
  // both starting and ending positions are included in checking paths!

  /**
  *
  *Checks if any pieces along a strait path
  */
  public boolean isLineOccupied(int row, int col, int movingRow, int movingCol) {
    // first check if both positions on a strait line

    if (movingRow == row) {// moving left or right
      // if movingRow and row are 3 or less spaces apart return true
      if (Math.abs(movingCol - col) <= 3) {
        return false;
      }

      // check for left or right
      if (movingCol < col) {// moving left
        for (int i = movingCol + 1; i < col - 1; i++) {
          if (board[row][i].isOccupied()) {
            return true;
          }
        }
      } else {// moving right
        for (int i = col + 1; i < movingCol - 1; i++) {
          if (board[row][i].isOccupied()) {
            return true;
          }
        }
      }

      return false;
    } else if (movingCol == col) { // moving up or down
      // if movingCol and col are 3 or less spaces apart return true
      if (Math.abs(movingRow - row) <= 3) {
        return false;
      }

      // check for up or down
      if (movingRow < row) {// moving up
        for (int i = movingRow + 1; i < row - 1; i++) {
          if (board[i][col].isOccupied()) {
            return true;
          }
        }
      } else {// moving down
        for (int i = row + 1; i < movingRow - 1; i++) {
          if (board[i][col].isOccupied()) {
            return true;
          }
        }
      }
      return false;
    } else {
      return true;
    }

  }

  /**
  *Checks if any pieces along a diag
  */
  public static boolean isDiagOccupied(int row, int col, int movingRow, int movingCol) {
     // the slope of a strait diag/bishops diag is 1 or -1 check for that 
     System.out.println("slope: "+Math.abs(slope(row,col,movingRow,movingCol)));
    if (Math.abs(slope(row,col,movingRow,movingCol)) == 1){
     
      
        
       // go from starting position to end position
         // check if any pieces are in the way
            // if so return false
            // we are moving on a strait  diag in 4 directions
       int rowOffset, colOffset;
         if (row < movingRow) {
            rowOffset = 1;
         } else {
            rowOffset = -1;
         }
            if (col < movingCol) {
                colOffset = 1;
            } else {
                colOffset = -1;
            }
            int y = col + colOffset;
            int x = row + rowOffset;
            for (int i = 0; i < Math.abs(row - movingRow)-1; i++) {
                if (board[x][y].isOccupied()){
                  return true;
                }
                y += colOffset;
                x += rowOffset;
            }
          return false;
      }
     return true;
    }
  
    

  

  public static int slope(int x1, int y1, int x2, int y2){
  int p1 = (y2-y1);
  int p2 = (x2-x1);
  if (p2 == 0){
    return 0;
  }
  else{
    return p1/p2;
  }
}

  public boolean canKnightMove(int row, int col, int movingRow, int movingCol) {
    if (movingRow == (row + 2) && (movingCol == (col + 1)||(movingCol == col-1))) {
      return true;
    } else if (movingRow == (row + 2) && (movingCol == (col + 1)||(movingCol == col-1))) {
      return true;
    }else if (movingRow == (row - 2) && (movingCol == (col + 1)||(movingCol == col-1))) {
      return true;
    }else if (movingCol == (col + 2) && (movingRow == (row + 1)||(movingRow == row-1))) {
      return true;
    }
    else if (movingCol == (col - 2) && (movingRow == (row + 1)||(movingRow == row-1))) {
      return true;
    }

    return false;
  }

  /**
  * Checks if the King can move
  */
  public boolean canKingMove(int row, int col, int movingRow, int movingCol) {
    System.out.println("row: "+row+" movingRow: "+movingRow+" row-movingRow: "+(row-movingRow));
    System.out.println("col: "+col+" movingCol: "+movingCol+" col-movingCol: "+(col-movingCol));
    //System.exit(0);
    if (
      ((Math.abs((row-movingRow))==1)||(row==movingRow))
      &&
      ((Math.abs((col-movingCol))==1)||(col==movingCol))
    ){
      return true;
    }
    else{
      return false;
    }

  }

  /*
   * public boolean winCondition(int row, int col, int movingRow, int movingCol,
   * int kingRow, int kingCol){
   * 
   * if(isKnightOccupied(row, col, movingRow, movingCol)){
   * return true;
   * }else {
   * return false;
   * }
   * }
   */

  /**
  * Class method
  */
  public Chess() {

    // public boolean is
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[0].length; j++) {
        emptyPiece ep = new emptyPiece();
        board[i][j] = ep;
      }
    }

    for (int i = 1; i < 2; i++) { // put pawns on board
      for (int j = 0; j < board.length; j++) {
        Pawn p = new Pawn();
        board[i][j] = p;
      }
    }

    for (int i = board.length - 2; i > board.length - 3; i += -1) { // put pawns on board
      for (int j = 0; j < board.length; j++) {
        Pawn p = new Pawn();
        p.color = true;
        board[i][j] = p;
      }
    }

    Queen blackQueen = new Queen();
    board[0][4] = blackQueen;
    King blackKing = new King();
    board[0][3] = blackKing;
    Bishop blackBishopL = new Bishop();
    board[0][2] = blackBishopL;
    Bishop blackBishopR = new Bishop();
    board[0][5] = blackBishopR;
    Knight blackKnightL = new Knight();
    board[0][1] = blackKnightL;
    Knight blackKnightR = new Knight();
    board[0][6] = blackKnightR;
    Rook blackRookL = new Rook();
    board[0][0] = blackRookL;
    Rook blackRookR = new Rook();
    board[0][7] = blackRookR;

    Queen whiteQueen = new Queen();
    whiteQueen.color = true;
    board[7][3] = whiteQueen;
    King whiteKing = new King();
    whiteKing.color = true;
    board[7][4] = whiteKing;
    Bishop whiteBishopL = new Bishop();
    whiteBishopL.color = true;
    board[7][2] = whiteBishopL;
    Bishop whiteBishopR = new Bishop();
    whiteBishopR.color = true;
    board[7][5] = whiteBishopR;
    Knight whiteKnightL = new Knight();
    whiteKnightL.color = true;
    board[7][1] = whiteKnightL;
    Knight whiteKnightR = new Knight();
    whiteKnightR.color = true;
    board[7][6] = whiteKnightR;
    Rook whiteRookL = new Rook();
    whiteRookL.color = true;
    board[7][0] = whiteRookL;
    Rook whiteRookR = new Rook();
    whiteRookR.color = true;
    board[7][7] = whiteRookR;
  }

  public char toChessUnicode(char c,boolean color){
    if (color){
      switch (c){
        case 'H':return '♔';
        case 'Q':return '♕';
        case 'R':return '♖';
        case 'B':return '♗'; 
        case 'K':return '♘';
        case 'I':return '♙';
  
        default:return '-';
      }
    }
    else{
       switch (c){
      case 'H':return '♚';
      case 'Q':return '♛';
      case 'R':return '♜';
      case 'B':return '♝'; 
      case 'K':return '♞';
      case 'I':return '♟';

      default:return '-';
    }
  }
}
  /**
  * Displays board
  */
  public void displayBoard() {
    System.out.println(ANSI_GREEN + "  0 1 2 3 4 5 6 7" + ANSI_RESET);
    for (int i = 0; i < board.length; i++) {
      System.out.print(ANSI_GREEN + i + " " + ANSI_RESET);
      for (int j = 0; j < board[0].length; j++) {
        if (board[i][j].color == false) {
         char  Character =  toChessUnicode(board[i][j].boardChar,board[i][j].color);
          if (Character == '-'){
            System.out.print("- ");
          }else{
            System.out.print("\u001B[47m"+ANSI_BLACK+Character + " "+ANSI_RESET);
          }
           
        } else if (board[i][j].color == true) {
          System.out.print(toChessUnicode(board[i][j].boardChar,board[i][j].color) + " " + ANSI_RESET);
        }

      }
      System.out.println("");
    }
  }

  /**
  * Checks if the piece has a valid move
  */
  public boolean validMovement(int myRow, int myCol, int movingRow, int movingCol) {
    Piece piece = board[myRow][myCol]; // players selected piece
    Piece movingPiece = board[movingRow][movingCol];
    System.out.println("here");
    System.out.println("piece color: " + piece.color);

    switch (piece.boardChar) {
      case 'H':
        return (canKingMove(myRow, myCol, movingRow, movingCol));

      case 'I':

        int colorInverter = 1;
        if (piece.color == false) {
          colorInverter = -1;
        }
        // if pawn at starting position,
        // allow moving 2 spaces

        if (piece.hasMoved == false) {
          // check for moving two space up board
          if (myRow + (-2 * colorInverter) == movingRow && movingCol == myCol) {
            // DO NOT MIX UP movingRow AND myRow
            if (movingPiece.isOccupied() ||
                board[myRow + (-1 * colorInverter)][movingCol].isOccupied()) {
              System.out.println("occupied");
              System.exit(0);
              return false;

            } else {
              System.out.println("unoccupied");
              return true;

            }
          }
        }

        // check for moving one space up board
        if (myRow + (-1 * colorInverter) == movingRow && movingCol == myCol) {
          if (movingPiece.isOccupied()) {
            System.out.println("occupied");
            return false;

          } else {
            System.out.println("unoccupied");
            return true;

          }
        }

        // check if moving diagonal
        if (myRow + (-1 * colorInverter) == movingRow && (myCol + 1 == movingCol || myCol - 1 == movingCol)) {
          // check if piece diagonal
          if (movingPiece.isOccupied()) {
            return true;
          }
        }

        break;

      case 'B':
        colorInverter = 1;
        if (piece.color == false) {
          colorInverter = -1;
        }
        if (isDiagOccupied(myRow, myCol, movingRow, movingCol)) {
          return false;
        } else {
          return true;
        }

      case 'K':
        if (canKnightMove(myRow, myCol, movingRow, movingCol)) {
          return true;
        } else {
          return false;
        }

      case 'R':
        // DONT MIX YOUR IMPORTS
        if (isLineOccupied(myRow, myCol, movingRow, movingCol)) {
          return false;
        } else {
          return true;
        }
      case 'Q':
        if (myRow - movingRow == myCol - movingCol) {
          if (isDiagOccupied(myRow, myCol, movingRow, movingCol)) {
            return true;
          } else {
            return false;
          }
        } else {
          if (isLineOccupied(myRow, myCol, movingRow, movingCol)) {
            return false;
          } else {
            return true;
          }
        }

      default:
        return false;
    }

    return false;
  }

}
