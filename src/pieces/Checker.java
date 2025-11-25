package pieces;
import board.Board;
import board.Position;

public class Checker extends Piece {
    public Checker(boolean white) {
        super(white);
    }
    @Override            //перевірка, чи може шашка зробити хід на задану клітинку.
    public boolean canMove(Board board, Position from, Position to) {
        int dr = to.row - from.row;      //зміна рядка і стовпця після ходу
        int dc = to.col - from.col;
        if (Math.abs(dr) == 1 && Math.abs(dc) == 1) {  //хід на 1 клітинку
            if (white && dr == -1) return true;
            if (!white && dr == 1) return true;
        }
        if (Math.abs(dr) == 2 && Math.abs(dc) == 2) {    //спроба удару
            int mr = (from.row + to.row) / 2;     //шукаємо клітинку (ту, через яку стрибаємо)
            int mc = (from.col + to.col) / 2;     //беремо шашку, через яку перестрибуємо
            Piece mid = board.pieces[mr][mc];
            if (mid != null && mid.white != this.white) {   //якщо там протилежного кольору, то можна бити
                return true;
            }
        }
        return false;
    }
    @Override
    public char getSymbol() {
        return white ? 'w' : 'b';
    }
}
