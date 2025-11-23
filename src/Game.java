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
            int fr = sc.nextInt();
            int fc = sc.nextInt();
            int tr = sc.nextInt();
            int tc = sc.nextInt();
            char piece = board.board[fr][fc];   //беремо шашку з початкової клітинки
            if (piece == '.') {            //якщо клітинка пустп
                System.out.println("Тут немає фігури!");
                continue;
            }
            if (whiteTurn && Character.isLowerCase(piece)) {    //перевірка чий і хід і чия шашка
                System.out.println("Це чорна фігура, ви не можете нею ходити");
                continue;
            }
            if (!whiteTurn && Character.isUpperCase(piece)) {
                System.out.println("Це біла фігура, ви не можете нею ходити");
                continue;
            }
            if (Math.abs(fr - tr) == 1 && Math.abs(fc - tc) == 1) {  //рух по діагоналі на клітинку
                board.board[tr][tc] = piece;
                board.board[fr][fc] = '.';
            }
            else if (Math.abs(fr - tr) == 2 && Math.abs(fc - tc) == 2) {   //спроба удару
                int mr = (fr + tr) / 2;         //знаходження шашки, яку хочуть перестрибнути
                int mc = (fc + tc) / 2;
                board.board[tr][tc] = piece;      //видалення цієї шашки
                board.board[fr][fc] = '.';
                board.board[mr][mc] = '.';
            }
            else {
                System.out.println("Невірний хід");
                continue;
            }
            if (piece == 'w' && tr == 0) board.board[tr][tc] = 'W';    //перетворення в дамку
            if (piece == 'b' && tr == 7) board.board[tr][tc] = 'B';
            board.printBoard();
            whiteTurn = !whiteTurn;
        }
    }
}

