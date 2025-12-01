package board;
import pieces.Piece;
import pieces.PieceFactory;
import exceptions.InvalidPositionException;
import java.util.ArrayList;
import java.util.List;
import observer.BoardObserver;
import observer.BoardSubject;

public class Board implements BoardSubject {
    private final Piece[][] grid = new Piece[8][8];         //дошка 8 на 8
    private final List<BoardObserver> observers = new ArrayList<>();
    public Board() {
        initializeDefault();
    }
    @Override
    public void attach(BoardObserver observer) {
        observers.add(observer);                  //додати спостерігача
    }
    @Override
    public void detach(BoardObserver observer) {
        observers.remove(observer);                 //видалити спостерігача
    }
    @Override
    public void notifyObservers() {               //сповістити всіх
        for (BoardObserver obs : observers) {
            obs.update(this);
        }
    }
    @Override
    public void notifyGameOver(String winner) {   //кінець гри
        for (BoardObserver obs : observers) {
            obs.gameOver(winner);
        }
    }
    public Piece getPiece(Position p) {
        if (!p.isValid()) return null;          //неправильна позиція
        return grid[p.row][p.col];
    }

    public void setPiece(Position p, Piece piece) {
        if (!p.isValid()) throw new InvalidPositionException(p);
        grid[p.row][p.col] = piece;
        notifyObservers();                //сповістити про зміну
    }
    public void removePiece(Position p) {
        setPiece(p, null);              //видалити фігуру
    }
    public void initializeDefault() {
        for (int r = 0; r < 8; r++)             //очистити дошку
            for (int c = 0; c < 8; c++)
                grid[r][c] = null;
        for (int r = 0; r < 3; r++) {           //чорні шашки
            for (int c = 0; c < 8; c++) {
                if ((r + c) % 2 == 1)                  //тільки темні клітинки
                    grid[r][c] = PieceFactory.createChecker(false);
            }
        }
        for (int r = 5; r < 8; r++) {               //білі шашки
            for (int c = 0; c < 8; c++) {
                if ((r + c) % 2 == 1)
                    grid[r][c] = PieceFactory.createChecker(true);
            }
        }
        notifyObservers();
    }
    public int countPieces(boolean white) {              //скільки фігур кольору
        int count = 0;
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = grid[r][c];
                if (p != null && p.white == white) {
                    count++;
                }
            }
        }
        return count;
    }
    public void printBoard() {                     //друкує дошку
        System.out.println("  0 1 2 3 4 5 6 7");          //номери стовпців
        for (int r = 0; r < 8; r++) {
            System.out.print(r + " ");             //номер рядка
            for (int c = 0; c < 8; c++) {
                Piece p = grid[r][c];
                if (p == null) {              //пусто
                    System.out.print(((r + c) % 2 == 1 ? "  " : ". "));
                } else {
                    System.out.print(p.getSymbol() + " "); //символ фігури
                }
            }
            System.out.println();
        }
    }
}