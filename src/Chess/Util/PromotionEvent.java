package Chess.Util;

import Chess.Piece.ChessPiece;


//TODO : add this interface

// IF there is no added implementation, automatically promote to queen

public interface PromotionEvent {
    public ChessPiece doPromotionEvent();
}
