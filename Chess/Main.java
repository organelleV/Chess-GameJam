import java.util.Scanner;

/**
*Game Launcher
*/
class Main {
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RESET = "\u001B[0m";

  public static String ANSI_CLS = "\u001b[2J";

  public static String ANSI_HOME = "\u001b[H";

  public static void clearScreen() {
    System.out.print(ANSI_CLS + ANSI_HOME);
    System.out.flush();
  }

  static Chess chess = new Chess();

  public static boolean playerColor;
  
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    boolean islooping = true;
    String playerColorChoice;
    boolean validMove = true;

    System.out.print("What color is player 1 going to be?: ");
    playerColorChoice = input.nextLine();

    if (playerColorChoice.equalsIgnoreCase("White")) {
      playerColor = true;
    } else if (playerColorChoice.equalsIgnoreCase("Black")) {
      playerColor = false;
    }

    boolean currentPlayer = false;
    // false -> player1
    // true -> player2

    while (islooping) { // main loop
      clearScreen();
      // display points
      System.out.println("Points: "+chess.calculatePoints(currentPlayer));
      chess.displayBoard();

      // player1
      if (currentPlayer == false) {

        System.out.println("player 1"+((playerColor==true)?"(white)":"(black)")+": enter a position");
        System.out.print("row: ");
        int row = input.nextInt();
        System.out.print("colum: ");
        int colum = input.nextInt();

        if (row > chess.board.length - 1 || colum > chess.board.length - 1) {
          System.out.println("positions must be less than" + chess.board.length);
          continue;
        }
        if (chess.board[row][colum].boardChar == ' ') {
          System.out.println("no piece at that location!");
          continue;
        }

        System.out.println("Piece " + chess.board[row][colum].boardChar + " Selected");

        if (chess.board[row][colum].color != playerColor){
          System.out.println("cannot select other players pieces!");
          continue;
        }
        System.out.println("enter location to move to: ");
        System.out.print("row: ");
        int rowMove = input.nextInt();
        System.out.print("colum: ");
        int columMove = input.nextInt();

        //check if location has piece of our own color
        // so we dont move to it
        if (chess.board[rowMove][columMove].isOccupied()){
          if (chess.board[rowMove][columMove].color == (playerColor)){
            System.out.println("cannot move onto own piece!");
            continue;
          }
        }

        // check if posistion to move to is valid posistion
        // if so move there and kill any piece there
        if (chess.validMovement(row, colum, rowMove, columMove)) {
          chess.board[row][colum].hasMoved = true;
          if (chess.board[rowMove][columMove].boardChar == 'H'){
            System.out.println("player1 has won!");
            System.exit(0);
          }
          chess.board[rowMove][columMove] = chess.board[row][colum];
          chess.board[row][colum] = new emptyPiece();
        } else {
          System.out.println("invalid move!");
          continue;
        }
      } else {

        // player 2
        clearScreen();
        // display points
      System.out.println("Points: "+chess.calculatePoints(currentPlayer));
        chess.displayBoard();

        System.out.println("player 2"+((!playerColor==true)?"(white)":"(black)")+": enter a position");
        System.out.print("row: ");
        int row = input.nextInt();
        System.out.print("colum: ");
        int colum = input.nextInt();

        if (row > chess.board.length - 1 || colum > chess.board.length - 1) {
          System.out.println("positions must be less than" + chess.board.length);
          continue;
        }
        if (chess.board[row][colum].boardChar == ' ') {
          System.out.println("no piece at that location!");
          continue;
        }

        System.out.println("Piece " + chess.board[row][colum].boardChar + " Selected");

        if (chess.board[row][colum].color != (!playerColor)){
          System.out.println("cannot select other players pieces!");
          continue;
        }
        
        System.out.println("enter location to move to: ");
        System.out.println("row: ");
        int rowMove = input.nextInt();
        System.out.print("colum: ");
        int columMove = input.nextInt();

        //check if location has piece of our own color
        // so we dont move to it
        if (chess.board[rowMove][columMove].isOccupied()){
          if (chess.board[rowMove][columMove].color == (!playerColor)){
            System.out.println("cannot move onto own piece!");
            continue;
          }
        }
        
        // check if posistion to move to is valid posistion
        // if so move there and kill any piece there
        if (chess.validMovement(row, colum, rowMove, columMove)) {
          chess.board[row][colum].hasMoved = true;
          if (chess.board[rowMove][columMove].boardChar == 'H'){
            System.out.println("player1 has won!");
            System.exit(0);
          }
          chess.board[rowMove][columMove] = chess.board[row][colum];
          chess.board[row][colum] = new emptyPiece();
          validMove = true;
        } else {
          System.out.println("invalid move!");
          continue;
        }
      }
      // swap currentPlayer
      currentPlayer = !currentPlayer;
    }
  }
}