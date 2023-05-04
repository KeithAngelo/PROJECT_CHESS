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

    //this is for iterating through all colors
    public static PieceColor[] getColors(){
        PieceColor[] colors = new PieceColor[2];
        colors[0] = BLACK;
        colors[1] = WHITE;
        return colors;
    }

}