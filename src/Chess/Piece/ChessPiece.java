package Chess.Piece;
import Chess.Util.*;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import Chess.*;


abstract public class ChessPiece implements PieceActions{
    final PieceColor color;
    PieceType type;

    public ChessPiece(PieceColor color){
        this.color = color;
    }

    public ImageIcon getImg(){
        //CUSTOM FILEPATH NAME, NEED TO EDIT THE NAME BASED ON THIS ENUMS
        return new ImageIcon("Media\\"+ type +"_"+color + ".png");
    }

    public PieceColor getColor(){
        return color;
    }
}

enum PieceType{
    PAWN, 
    KING, 
    QUEEN, 
    BISHOP, 
    ROOK, 
    KNIGHT;
}

interface PieceActions {
    public ArrayList<ChessCoor> GetPotentialMoves(ChessBoard CurrentBoard, ChessCoor CurrentCoord);
    public boolean AllowedToMoveTo(ChessBoard CurrentBoard, ChessCoor CurrentCoord, ChessCoor newCoor);
}

