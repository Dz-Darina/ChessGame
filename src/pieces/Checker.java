package pieces;
import board.Board;
import board.Position;
import moves.Move;
import java.util.ArrayList;
import java.util.List;

public class Checker extends Piece {
    public Checker(boolean white) {
        super(white);
    }
    @Override
    public char getSymbol() {
        return white ? 'w' : 'b';         //w - біла, b - чорна
    }
    @Override
    public List<Move> validMoves(Board board, Position from) {
        List<Move> moves = new ArrayList<>();
        int forward = white ? -1 : 1;          //білі вгору, чорні вниз
        int[] dcs = {-1, 1};                   //вліво та вправо
        for (int dc : dcs) {                 //прості ходи (без биття)
            Position to = new Position(from.row + forward, from.col + dc);
            if (to.isValid() && board.getPiece(to) == null) {                        //якщо клітинка вільна
                moves.add(new Move(from, to, false, null));
            }
        }
        int[] drs = {forward, -forward};           //бити можна вперед і назад
        for (int dr : drs) {
            for (int dc : dcs) {
                Position mid = new Position(from.row + dr, from.col + dc);          //де ворог
                Position land = new Position(from.row + 2*dr, from.col + 2*dc);     //куди стати
                if (mid.isValid() && land.isValid()) {
                    var midPiece = board.getPiece(mid);
                    if (midPiece != null && midPiece.white != this.white && board.getPiece(land) == null) {    //умова: є ворог на mid, land вільний
                        moves.add(new Move(from, land, true, mid));
                    }
                }
            }
        }
        return moves;
    }
}