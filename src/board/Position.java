package board;

public class Position {
    public final int row;                 //номер рядка
    public final int col;                 //номер стовпця
    public Position(int r, int c) {
        this.row = r;
        this.col = c;
    }
    public boolean isValid() {             //чи в межах дошки
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
    @Override
    public boolean equals(Object o) {             //порівняння позицій
        if (!(o instanceof Position)) return false;
        Position p = (Position) o;
        return p.row == row && p.col == col;
    }
    @Override
    public int hashCode() {
        return java.util.Objects.hash(row, col);
    }
    @Override
    public String toString() {                //для виводу
        return "(" + row + "," + col + ")";
    }
}