package pieces;
import board.Board;
import board.Position;

public abstract class Piece {
    public boolean white;         //колір шашки: true = біла, false = чорна
    public Piece(boolean white) {
        this.white = white;     //конструктор — задає колір
    }
    public abstract boolean canMove(Board board, Position from, Position to);
    public abstract char getSymbol();
}
