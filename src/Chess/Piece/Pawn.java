package Chess.Piece;

import java.util.ArrayList;

import Chess.ChessBoard;
import Chess.Util.ChessCoor;
import Chess.Util.PieceColor;

public class Pawn extends ChessPiece{

    public Pawn(PieceColor color) {
        super(color);
        type = PieceType.PAWN;
    }

    @Override
    public ArrayList<ChessCoor> GetPotentialMoves(ChessCoor CurrentCoorm, PieceColor color) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'GetPotentialMoves'");
    }

    @Override
    public boolean AllowedToMoveTo(ChessBoard CurrentBoard, ChessCoor newCoor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'AllowedToMoveTo'");
    }
    
}
