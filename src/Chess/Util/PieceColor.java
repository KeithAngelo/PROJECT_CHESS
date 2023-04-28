package Chess.Util;

public enum PieceColor {
    BLACK, 
    WHITE;

    public static PieceColor getOther(PieceColor thisColor){
        if(thisColor == PieceColor.WHITE){
            return PieceColor.BLACK;
        }else{
            return PieceColor.WHITE;
        }
    }

}