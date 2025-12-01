package pieces;
import board.Position;

public class PieceFactory {
    public static Checker createChecker(boolean white) {        //створити шашку
        return new Checker(white);
    }
    public static King createKing(boolean white) {             //створити дамку
        return new King(white);
    }
    public static Piece maybePromote(Piece piece, Position pos) {
        if (piece == null || piece instanceof King) return piece;    //дамок не підвищуємо
        if (piece instanceof Checker) {
            if (piece.white && pos.row == 0) {                 //біла шашка стає дамкою на першому ряді
                System.out.println("Біла шашка стала дамкою на позиції " + pos);
                return createKing(true);
            }
            else if (!piece.white && pos.row == 7) {             //чорна шашка стає дамкою на останньому ряді
                System.out.println("Чорна шашка стала дамкою на позиції " + pos);
                return createKing(false);
            }
        }
        return piece;
    }
}