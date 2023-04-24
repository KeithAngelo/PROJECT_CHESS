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
    public ArrayList<ChessCoor> GetPotentialMoves(ChessBoard CurrentBoard, ChessCoor CurrentCoord) {
        ArrayList<ChessCoor> PotentialCoords = new ArrayList<>();

        //check south squares
        int testCoordX = CurrentCoord.getX() + 1;
        int testCoordY = CurrentCoord.getY() + 2;
        
        if(isValidSquare(CurrentBoard, testCoordX, testCoordY)){
            PotentialCoords.add(new ChessCoor(testCoordX, testCoordY));
        }

        testCoordX = CurrentCoord.getX() - 1;
        testCoordY = CurrentCoord.getY() + 2;

        if(isValidSquare(CurrentBoard, testCoordX, testCoordY)){
            PotentialCoords.add(new ChessCoor(testCoordX, testCoordY));
        }
        
        //check north squares
        testCoordX = CurrentCoord.getX() - 1;
        testCoordY = CurrentCoord.getY() - 2;

        if(isValidSquare(CurrentBoard, testCoordX, testCoordY)){
            PotentialCoords.add(new ChessCoor(testCoordX, testCoordY));
        }

        testCoordX = CurrentCoord.getX() + 1;
        testCoordY = CurrentCoord.getY() - 2;

        if(isValidSquare(CurrentBoard, testCoordX, testCoordY)){
            PotentialCoords.add(new ChessCoor(testCoordX, testCoordY));
        }

        //Check West squares

        testCoordX = CurrentCoord.getX() - 2;
        testCoordY = CurrentCoord.getY() - 1;

        if(isValidSquare(CurrentBoard, testCoordX, testCoordY)){
            PotentialCoords.add(new ChessCoor(testCoordX, testCoordY));
        }

        testCoordX = CurrentCoord.getX() - 2;
        testCoordY = CurrentCoord.getY() + 1;

        if(isValidSquare(CurrentBoard, testCoordX, testCoordY)){
            PotentialCoords.add(new ChessCoor(testCoordX, testCoordY));
        }

        //Check East squares

        testCoordX = CurrentCoord.getX() + 2;
        testCoordY = CurrentCoord.getY() - 1;

        if(isValidSquare(CurrentBoard, testCoordX, testCoordY)){
            PotentialCoords.add(new ChessCoor(testCoordX, testCoordY));
        }

        testCoordX = CurrentCoord.getX() + 2;
        testCoordY = CurrentCoord.getY() + 1;

        if(isValidSquare(CurrentBoard, testCoordX, testCoordY)){
            PotentialCoords.add(new ChessCoor(testCoordX, testCoordY));
        }

        return PotentialCoords;
    }

    //This method only checks if the square is outside the board, or if the square contains piece with same color
    public boolean isValidSquare(ChessBoard currentBoard, int CoorX, int CoorY){
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


    @Override
    public boolean AllowedToMoveTo(ChessBoard CurrentBoard, ChessCoor CurrentCoord, ChessCoor newCoor) {
        return newCoor.isContainedIn(GetPotentialMoves(CurrentBoard, CurrentCoord));
    }

    
    
}
