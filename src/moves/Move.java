package moves;
import board.Position;
import board.Board;
import pieces.Piece;

public class Move {
    public final Position from;               //звідки
    public final Position to;                 //куди
    public final boolean isCapture;           //чи б'є
    public final Position capturedPosition;   //кого б'є
    public Move(Position from, Position to, boolean isCapture, Position captured) {
        this.from = from;
        this.to = to;
        this.isCapture = isCapture;
        this.capturedPosition = captured;
    }
    public void execute(Board board) {        //зробити хід
        Piece piece = board.getPiece(from);   //взяти фігуру
        board.removePiece(from);              //звільнити старе місце
        board.setPiece(to, piece);            //поставити на нове
        if (isCapture && capturedPosition != null) { //якщо б'є
            board.removePiece(capturedPosition);     //забрати ворожу
        }
    }
    @Override
    public String toString() {                //показати хід
        return from + " -> " + to + (isCapture ? " x " + capturedPosition : "");
    }
}