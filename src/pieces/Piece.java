package pieces;
import board.Board;
import board.Position;
import moves.Move;
import java.util.List;

public abstract class Piece implements Cloneable {
    public final boolean white;                   //біла чи чорна фігура
    public Piece(boolean white) {
        this.white = white;
    }
    public abstract List<Move> validMoves(Board board, Position from);         //які ходи можна зробити
    public abstract char getSymbol();                        //яка буква для друку
    @Override
    public Piece clone() {                           //створює копію фігури
        try {
            return (Piece) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Клонування не підтримується для Piece", e);
        }
    }
}