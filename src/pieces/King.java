package pieces;
import board.Board;
import board.Position;

public class King extends Piece {
    public King(boolean white) {        //створення дамки з кольором
        super(white);
    }
    @Override                    //метод перевіряє, чи може дамка зробити хід
    public boolean canMove(Board board, Position from, Position to) {
        if (Math.abs(from.row - to.row) == 1 &&       //ще доробити ходи дамки
                Math.abs(from.col - to.col) == 1) return true;
        return false;
    }
    @Override                     //позначення дамки
    public char getSymbol() {
        return white ? 'W' : 'B';
    }
}