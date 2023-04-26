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
        // CUSTOM FILEPATH NAME, NEED TO EDIT THE NAME BASED ON THIS ENUMS
        
        // give current path of the file
        String currentPath = System.getProperty("user.dir");
        String PIECE_PATH = currentPath + "\\Media\\" + type + "_" + color + ".png";
        // System.out.println(PIECE_PATH);
        return new ImageIcon(PIECE_PATH);
    }

    public PieceColor getColor(){
        return color;
    }

    public PieceType getType(){
        return type;
    }


    // REUSABLE UTILITY METHODS : 

    //This method only checks if the square is outside the board, or if the square contains piece with same color
    boolean isValidSquare(ChessBoard currentBoard, int CoorX, int CoorY){
        if((CoorX < 0 || CoorY < 0) || (CoorX > 7 || CoorY > 7)){
            return false;
        }

        if(currentBoard.peekPieceAt(CoorX, CoorY) == null){
            return true;
        }

        if(currentBoard.peekPieceAt(CoorX, CoorY).getColor() == this.color){
            return false;
        }

        return true;
    }

    boolean isWithinBounds(ChessBoard currentBoard, int CoorX, int CoorY){
        return !( (CoorX < 0 || CoorY < 0) || (CoorX > 7 || CoorY > 7) );
    }
}



interface PieceActions {
    public ArrayList<ChessCoor> GetPotentialMoves(ChessBoard CurrentBoard, ChessCoor CurrentCoord);
    public ArrayList<ChessCoor> GetControlledSquares(ChessBoard CurrentBoard, ChessCoor CurrentCoord);
    public boolean AllowedToMoveTo(ChessBoard CurrentBoard, ChessCoor CurrentCoord, ChessCoor newCoor);
}

