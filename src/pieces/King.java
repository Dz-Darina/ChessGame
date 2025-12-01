package pieces;
import board.Board;
import board.Position;
import moves.Move;
import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    public King(boolean white) {
        super(white);
    }
    @Override
    public char getSymbol() {
        return white ? 'W' : 'B';
    }
    @Override
    public List<Move> validMoves(Board board, Position from) {
        List<Move> moves = new ArrayList<>();
        moves.addAll(collectSimpleMoves(board, from));    //прості ходи
        moves.addAll(collectCaptureMoves(board, from));   //ходи з биттям
        return moves;
    }
    private List<Move> collectSimpleMoves(Board board, Position from) {
        List<Move> moves = new ArrayList<>();
        int[] dirs = {-1, 1};                            //4 діагональні напрямки
        for (int dr : dirs) for (int dc : dirs) {
            int step = 1;
            while (true) {
                Position to = new Position(from.row + step*dr, from.col + step*dc);
                if (!to.isValid()) break;                     //вийшли за дошку
                if (board.getPiece(to) == null) {             //вільна клітинка
                    moves.add(new Move(from, to, false, null));
                } else {                                 //зустріли фігуру
                    break;                              //далі не йдемо
                }
                step++;                                 //рухаємось далі
            }
        }
        return moves;
    }
    private List<Move> collectCaptureMoves(Board board, Position from) {
        List<Move> moves = new ArrayList<>();
        int[] dirs = {-1, 1};
        for (int dr : dirs) for (int dc : dirs) {
            int step = 1;
            boolean foundEnemy = false;
            Position enemyPos = null;                        //де ворог
            while (true) {
                Position current = new Position(from.row + step*dr, from.col + step*dc);
                if (!current.isValid()) break;        //за межами дошки
                var currentPiece = board.getPiece(current);
                if (!foundEnemy) {                        //ще шукаємо ворога
                    if (currentPiece == null) {
                    } else if (currentPiece.white == this.white) {
                        break;                         //якщо своя фігура, то стоп
                    } else {                          //знайшли ворога
                        foundEnemy = true;
                        enemyPos = current;
                    }
                } else {                           //вже знайшли ворога
                    if (currentPiece == null) {         //пусто після ворога
                        moves.add(new Move(from, current, true, enemyPos));
                    } else {
                        break;                       //якщо ще фігура, то стоп
                    }
                }
                step++;
            }
        }
        return moves;
    }
}