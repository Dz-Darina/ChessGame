import board.Board;
import board.Position;
import moves.Move;
import pieces.Checker;
import pieces.Piece;
import pieces.King;
import java.util.Scanner;

public class Game {
    Board board = new Board();     // створюємо дошку
    Scanner sc = new Scanner(System.in);
    boolean whiteTurn = true;      // починають білі
    public void start() {
        board.printBoard();
        while (true) {
            System.out.println(whiteTurn ? "Хід білих" : "Хід чорних");
            System.out.println("Введіть хід у форматі:");
            System.out.println("рядок_звідки  стовпець_звідки  рядок_куди  стовпець_куди");
            System.out.println("Наприклад: 5 0 4 1");
            System.out.print("Ваш хід: ");
            int fr = sc.nextInt();
            int fc = sc.nextInt();
            int tr = sc.nextInt();
            int tc = sc.nextInt();
            Move m = new Move(new Position(fr, fc), new Position(tr, tc));
            Piece p = board.pieces[fr][fc];
            if (p == null) {                                   //якщо клітинка пуста
                System.out.println("Тут немає фігури!");
                continue;
            }
            if (whiteTurn && !p.white) {                       //перевірка чий і хід і чия шашка
                System.out.println("Це чорна фігура, ви не можете нею ходити");
                continue;
            }
            if (!whiteTurn && p.white) {
                System.out.println("Це біла фігура, ви не можете нею ходити");
                continue;
            }
            if (!p.canMove(board, m.from, m.to)) {
                System.out.println("Так ходити не можна");
                continue;
            }
            board.pieces[tr][tc] = p;
            board.pieces[fr][fc] = null;
            if (p instanceof Checker) {
                if (p.white && tr == 0) {
                    board.pieces[tr][tc] = new King(true);
                }
                if (!p.white && tr == 7) {
                    board.pieces[tr][tc] = new King(false);
                }
            }
            board.printBoard();     // показуємо оновлену дошку
            whiteTurn = !whiteTurn; // міняємо хід
        }
    }
}


