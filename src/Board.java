public class Board {
    public char[][] board = new char[8][8];   //дошка, де відбуватиметься гра
    public Board() {
        this.init();
    }
    public void init() {                      //початкове розсташування шашок
        for(int r = 0; r < 8; ++r) {          //ставляться крапки, де немає шашок
            for(int c = 0; c < 8; ++c) {
                this.board[r][c] = '.';
            }
        }
        for(int r = 0; r < 3; ++r) {         //ставляться чорні шашки
            for(int c = 0; c < 8; ++c) {
                if ((r + c) % 2 == 1) {
                    this.board[r][c] = 'b';
                }
            }
        }
        for(int r = 5; r < 8; ++r) {         //ставляться білі шашки
            for(int c = 0; c < 8; ++c) {
                if ((r + c) % 2 == 1) {
                    this.board[r][c] = 'w';
                }
            }
        }
    }
    public void printBoard() {           //вивід дошки на екран
        System.out.println("  0 1 2 3 4 5 6 7");
        for(int r = 0; r < 8; ++r) {
            System.out.print(r + " ");
            for(int c = 0; c < 8; ++c) {
                System.out.print(this.board[r][c] + " ");
            }
            System.out.println();
        }
    }
}