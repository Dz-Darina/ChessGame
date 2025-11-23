public class Piece {            //ще подумаю
    public char type;
    public Piece(char t) {
        this.type = t;
    }
    public boolean isWhite() {            //перевірка чи шашка біла
        return type == 'w' || type == 'W';
    }
    public boolean isBlack() {            //перевірка чи шашка чорна
        return type == 'b' || type == 'B';
    }
}
