public class Piece {
    public char type;
    public int dir;              //напрямок руху(бфлий вверх, чорний вниз)
    public Piece(char t) {

        this.type = t;
        if (t == 'w' || t == 'W') dir = 1;
        else dir = 1;
    }
    public boolean canMove(int fr, int fc, int tr, int tc, Board board) {

        if (Math.abs(fr - tr) == 1 && Math.abs(fc - tc) == 1) {     //рух по діагоналі на 1
            if (dir == -1 && tr < fr) return true;
            if (dir ==  1 && tr > fr) return true;
        }
        if (Math.abs(fr - tr) == 2 && Math.abs(fc - tc) == 2) {    //биття шашки
            int mr = (fr + tr) / 2;
            int mc = (fc + tc) / 2;
            char mid = board.board[mr][mc];     //?
            if (mid != '.') return true;
        }
        return false;
    }
}
