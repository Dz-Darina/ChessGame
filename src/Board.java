public class Board {
    public Piece[][] pieces = new Piece[8][8];   //дошка, де відбуватиметься гра
    public Board() {

        init();
    }
    public void init() {                      //початкове розсташування шашок
        for(int r = 0; r < 8; ++r) {          //се спочатку порожнє
            for(int c = 0; c < 8; ++c) {
                pieces[r][c] = null;
            }
        }
        for(int r = 0; r < 3; ++r) {         //ставляться чорні шашки
            for(int c = 0; c < 8; ++c) {
                if ((r + c) % 2 == 1) {
                    pieces[r][c] = new Piece('b');
                }
            }
        }
        for(int r = 5; r < 8; ++r) {         //ставляться білі шашки
            for(int c = 0; c < 8; ++c) {
                if ((r + c) % 2 == 1) {
                    pieces[r][c] = new Piece( 'w');
                }
            }
        }
    }
    public void printBoard() {           //вивід дошки на екран
        System.out.println("  0 1 2 3 4 5 6 7");
        for(int r = 0; r < 8; ++r) {
            System.out.print(r + " ");
            for(int c = 0; c < 8; ++c) {
                if (pieces[r][c] == null) {
                    System.out.print(". ");
                } else {
                    System.out.print(pieces[r][c].type + " ");
                }
            }
            System.out.println();
        }
    }
}