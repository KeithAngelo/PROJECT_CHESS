package Chess.Piece;

import java.util.ArrayList;

import Chess.ChessBoard;
import Chess.Util.ChessCoor;
import Chess.Util.PieceColor;

public class Rook extends ChessPiece{

    public Rook(PieceColor color) {
        super(color);
        type = PieceType.ROOK;
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
