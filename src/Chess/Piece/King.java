package Chess.Piece;

import java.util.ArrayList;

import Chess.ChessBoard;
import Chess.Util.ChessCoor;
import Chess.Util.PieceColor;

public class King extends ChessPiece{

    public King(PieceColor color) {
        super(color);
        type = PieceType.KING;
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
        
        //TODO : incorporate case of castling
    }
    
}
