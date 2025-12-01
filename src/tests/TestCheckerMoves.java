package tests;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import board.Board;
import board.Position;
import moves.Move;
import pieces.Checker;
import pieces.Piece;
import java.util.List;
import java.util.stream.Collectors;

public class TestCheckerMoves {
    @Test
    void testWhiteSimpleMove() {
        Board board = new Board();
        board.removePiece(new Position(5, 0));
        board.removePiece(new Position(4, 1));
        Position from = new Position(5, 2);
        Piece whiteChecker = new Checker(true);
        board.setPiece(from, whiteChecker);
        List<Move> moves = whiteChecker.validMoves(board, from);
        Position to1 = new Position(4, 1);
        Position to2 = new Position(4, 3);
        List<Position> finalPositions = moves.stream().map(m -> m.to).collect(Collectors.toList());
        assertTrue(finalPositions.contains(to1), "Має бути хід на (4,1)");
        assertTrue(finalPositions.contains(to2), "Має бути хід на (4,3)");
        assertFalse(moves.stream().anyMatch(m -> m.isCapture), "Не повинно бути захоплень");
    }
    @Test
    void testWhiteCaptureMove() {
        Board board = new Board();
        Position whitePos = new Position(5, 0);
        board.setPiece(whitePos, new Checker(true));
        Position blackPos = new Position(4, 1);
        board.setPiece(blackPos, new Checker(false));
        Position landingPos = new Position(3, 2);
        board.removePiece(landingPos);
        List<Move> moves = board.getPiece(whitePos).validMoves(board, whitePos);
        List<Move> captures = moves.stream().filter(m -> m.isCapture).collect(Collectors.toList());
        assertEquals(1, captures.size(), "Має бути рівно 1 хід захоплення");
        Move captureMove = captures.get(0);
        assertEquals(landingPos, captureMove.to, "Місце приземлення має бути (3,2)");
        assertEquals(blackPos, captureMove.capturedPosition, "Має бути захоплена фігура на (4,1)");
    }
}
