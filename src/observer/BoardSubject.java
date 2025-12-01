package observer;

public interface BoardSubject {
    void attach(BoardObserver observer);
    void detach(BoardObserver observer);
    void notifyObservers();
    void notifyGameOver(String winner);
}