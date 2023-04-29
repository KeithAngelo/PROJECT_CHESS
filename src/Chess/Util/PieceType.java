package Chess.Util;

public enum PieceType{
    PAWN(1), 
    KING(12), 
    QUEEN(8), 
    BISHOP(3), 
    ROOK(5), 
    KNIGHT(3);

    int PieceWeight;

    PieceType(int PieceWeight){
        this.PieceWeight = PieceWeight;
    }

    public int getWeight(){
        return PieceWeight;
    }
}