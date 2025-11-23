import java.util.Scanner;
public class Game {
    Board board = new Board();   //створюємо дошку
    Scanner sc = new Scanner(System.in);
    boolean whiteTurn = true;    //починають білі
    public void start() {
        board.printBoard();      //показуємо дошку
        while (true) {
            System.out.println(whiteTurn ? "Хід білих" : "Хід чорних");
            System.out.print("Введіть хід: ");
            Move m = new Move(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt());

            Piece p = board.pieces[m.fr][m.fc];
            if (p == null) {            //якщо клітинка пуста
                System.out.println("Тут немає фігури!");
                continue;
            }
            if (whiteTurn && p.type == 'b') {    //перевірка чий і хід і чия шашка
                System.out.println("Це чорна фігура, ви не можете нею ходити");
                continue;
            }
            if (!whiteTurn && p.type == 'w') {
                System.out.println("Це біла фігура, ви не можете нею ходити");
                continue;
            }
            if (!p.canMove(m.fr, m.fc, m.tr, m.tc, board)) {
                System.out.println("Так ходити не можна");
                continue;
            }
            board.pieces[m.tr][m.tc] = p;
            board.pieces[m.fr][m.fc] = null;
            if (p.type == 'w' && m.tr == 0) p.type = 'W';
            if (p.type == 'b' && m.tr == 7) p.type = 'B';
            board.printBoard();
            whiteTurn = !whiteTurn;
        }
    }
}


