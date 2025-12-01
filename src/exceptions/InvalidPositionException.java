package exceptions;
import board.Position;

public class InvalidPositionException extends IllegalArgumentException {
    public InvalidPositionException(Position p) {
        super("Позиція (" + p.row + "," + p.col + ") недійсна.");
    }
}
