
// class that holds data about a piece
public class Piece {
  char boardChar = '0';
  int points = 0;
  // false -> black
  // true -> white
  boolean color = false;
  boolean hasMoved = false;

  public boolean isOccupied() {
    if (boardChar == ' ' || boardChar == '0') {
      return false;
    } else {
      return true;
    }

  }

}