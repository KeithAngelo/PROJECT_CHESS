package Chess.Piece;
import Chess.Util.*;


import java.util.ArrayList;

import javax.swing.ImageIcon;

import Chess.*;


abstract public class ChessPiece implements PieceActions{
    final PieceColor color;
    public PieceType type;
    public boolean hasMoved = false;

    public ChessPiece(PieceColor color){
        this.color = color;
    }

    public ChessPiece(ChessPiece newPiece){
        this.color = newPiece.color;
        this.type = newPiece.type;
        this.hasMoved = newPiece.hasMoved;
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

    public boolean move(moveInterface myMove,ChessCoor initialCoor, ChessCoor NewCoor ){
        if(myMove.move(initialCoor, NewCoor)){
            hasMoved = true;
            return true;
        }

        return false;
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

    public static ChessPiece getNewPiece(PieceColor color, PieceType type){
        switch(type){
            case BISHOP:
                return new Bishop(color);
            case KING:
                return new King(color);
            case KNIGHT:
                return new Knight(color);
            case PAWN:
                return new Pawn(color);
            case QUEEN:
                return new Queen(color);
            case ROOK:
                return new Rook(color);
            default:
                throw new IllegalAccessError();
            
        }
    }
}



interface PieceActions {
    public ArrayList<ChessCoor> GetPotentialMoves(ChessBoard CurrentBoard, ChessCoor CurrentCoord);
    public ArrayList<ChessCoor> GetControlledSquares(ChessBoard CurrentBoard, ChessCoor CurrentCoord);
    public boolean AllowedToMoveTo(ChessBoard CurrentBoard, ChessCoor CurrentCoord, ChessCoor newCoor);
}

