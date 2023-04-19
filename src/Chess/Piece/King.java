package Chess.Piece;

import java.util.ArrayList;

import Chess.ChessBoard;
import Chess.Util.ChessCoor;
import Chess.Util.PieceColor;

public class King extends ChessPiece{

    /* This will store coordinates where the enemy controls the squares.
     * This is needed since the king will note be able to move on these squares
     */
    ArrayList<ChessCoor> EnemySquare = new ArrayList<>(); 
    

    public King(PieceColor color) {
        super(color);
        type = PieceType.KING;
    }

    @Override
    public ArrayList<ChessCoor> GetPotentialMoves(ChessBoard CurrentBoard, ChessCoor CurrentCoord) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'GetPotentialMoves'");
    }

    @Override
    public boolean AllowedToMoveTo(ChessBoard CurrentBoard, ChessCoor CurrentCoord, ChessCoor newCoor) {
        return true;
    }
    
    public boolean isChecked(ChessBoard CurrentBoard, ChessCoor CurrentCoord){
        return false;
    }
}
