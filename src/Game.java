import board.Board;
import board.Position;
import moves.Move;
import pieces.Piece;
import pieces.PieceFactory;
import exceptions.InvalidMoveException;
import observer.BoardObserver;
import java.util.Scanner;
import java.util.*;

public class Game implements BoardObserver {
    private static Game instance;            //тільки одна гра
    private Game() {
        board.attach(this);                  //слідкувати за дошкою
    }
    public static Game getInstance() {       //отримати гру
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }
    private final Board board = new Board();  //дошка
    private boolean whiteTurn = true;         //чия черга
    private final Scanner scanner = new Scanner(System.in);
    private int whitePieces;                  //скільки білих
    private int blackPieces;                  //cкільки чорних
    @Override
    public void update(Board board) {         //оновити лічильники
        this.whitePieces = board.countPieces(true);
        this.blackPieces = board.countPieces(false);
        // перевірка перемоги
        if (whitePieces == 0) {               //білих немає
            board.notifyGameOver("Чорні");
        } else if (blackPieces == 0) {        //чорних немає
            board.notifyGameOver("Білі");
        }
    }
    @Override
    public void gameOver(String winner) {     //кінець гри
        System.out.println("              ГРА ЗАВЕРШЕНА!              ");
        System.out.println("              ПЕРЕМІГ " + winner.toUpperCase() + "!               ");
        System.exit(0);                       //вийти
    }
    public void start() {                     //почати гру
        System.out.println("Введіть рух: з рядка з стовпчика до рядка до стовпчика (приклад: 5 0 4 1)");
        update(board);                        //порахувати фігури
        while (true) {                        //основний цикл
            board.printBoard();               //показати дошку
            System.out.println("Білих: " + whitePieces + ", Чорних: " + blackPieces);
            System.out.println((whiteTurn ? "Білих" : "Чорних") + " черга.");

            List<Move> all = collectAllMoves(whiteTurn); // всі ходи гравця

            if (all.isEmpty()) {              //немає ходів
                board.notifyGameOver(whiteTurn ? "Чорні" : "Білі");
            }
            // перевірити, чи є ходи з биттям
            boolean hasCapture = all.stream().anyMatch(m -> m.isCapture);
            if (hasCapture) {                 //є биття
                System.out.println("Є захоплення — дозволені тільки захоплення.");
                all.removeIf(m -> !m.isCapture); //залишити тільки биття
            }
            try {
                Move chosenMove = readMoveFromUser(all); //отримати хід
                executeMoveWithPossibleSequence(chosenMove); //зробити хід
                whiteTurn = !whiteTurn;                   //змінити чергу
            } catch (InvalidMoveException e) {
                System.out.println("Недійсний хід: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Недійсний формат вводу. Будь ласка, введіть 4 числа.");
            }
        }
    }
    private List<Move> collectAllMoves(boolean forWhite) { //всі ходи кольору
        List<Move> allMoves = new ArrayList<>();
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Position p = new Position(r, c);
                Piece piece = board.getPiece(p);
                if (piece != null && piece.white == forWhite) {
                    // додати ходи фігури
                    allMoves.addAll(piece.validMoves(board, p));
                }
            }
        }
        return allMoves;
    }
    private Move readMoveFromUser(List<Move> allowed) throws InvalidMoveException {
        System.out.print("Ваш хід: ");
        String line = scanner.nextLine().trim();
        String[] parts = line.split("\\s+");
        if (parts.length != 4)
            throw new InvalidMoveException("Потрібно ввести 4 числа.");
        // прочитати числа
        int fr = Integer.parseInt(parts[0]);
        int fc = Integer.parseInt(parts[1]);
        int tr = Integer.parseInt(parts[2]);
        int tc = Integer.parseInt(parts[3]);
        Position from = new Position(fr, fc);
        Position to = new Position(tr, tc);
        // знайти серед дозвлених
        for (Move m : allowed) {
            if (m.from.equals(from) && m.to.equals(to))
                return m;
        }
        throw new InvalidMoveException("Цей хід не є дозволеним.");
    }
    private void executeMoveWithPossibleSequence(Move first) {
        first.execute(board);                //зробити перший хід
        handlePromotion(first.to);           //перевірити на короля
        if (!first.isCapture) return;        //якщо не бив, то кінець
        Position current = first.to;         //поточна позиція
        while (true) {                       // продовження биття
            List<Move> captures = getCapturesFrom(current); //можна ще бити?
            if (captures.isEmpty()) break;   //ні
            System.out.println("Потрібно продовжити захоплення з " + current + ". Доступні ходи:");
            for(int i = 0; i < captures.size(); i++)
                System.out.println(i + ": " + captures.get(i));
            System.out.print("Виберіть номер опції: ");
            String ln = scanner.nextLine().trim();
            int idx;
            try {
                idx = Integer.parseInt(ln);  //номер ходу
            } catch (NumberFormatException e) {
                System.out.println("Недійсний вибір, послідовність зупинена.");
                break;
            }
            if (idx < 0 || idx >= captures.size()) {
                System.out.println("Недійсний індекс, послідовність зупинена.");
                break;
            }
            Move pick = captures.get(idx);   //вибраний хід
            pick.execute(board);             //зробити
            handlePromotion(pick.to);        //перевірити на короля
            current = pick.to;               //оновити позицію
        }
    }
    private List<Move> getCapturesFrom(Position from) {
        Piece p = board.getPiece(from);
        if (p == null) return Collections.emptyList();

        List<Move> all = p.validMoves(board, from); //всі ходи
        all.removeIf(m -> !m.isCapture);            //лишити тільки биття
        return all;
    }
    private void handlePromotion(Position position) {
        Piece piece = board.getPiece(position);
        if (piece == null) return;
        Piece promoted = PieceFactory.maybePromote(piece, position); //стати королем?
        if (piece != promoted && promoted != null) { //якщо став королем
            board.setPiece(position, promoted.clone()); //замінити
        }
    }
}