package Chess.Piece;

import java.util.ArrayList;

import Chess.ChessBoard;
import Chess.Util.ChessCoor;
import Chess.Util.PieceColor;

public class Knight extends ChessPiece{

    public Knight(PieceColor color) {
        super(color);
        type = PieceType.KNIGHT;
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
