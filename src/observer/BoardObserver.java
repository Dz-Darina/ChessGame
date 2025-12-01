package observer;
import board.Board;

public interface BoardObserver {
    void update(Board board);
    void gameOver(String winner);
}