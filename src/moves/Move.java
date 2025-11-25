package moves;
import board.Position;

public class Move {
    public Position from;         //позиція звідки
    public Position to;          //позиція куди
    public Move(Position f, Position t) {
        this.from = f;
        this.to = t;
    }
}
