package Chess.Piece;

import java.util.ArrayList;

import Chess.ChessBoard;
import Chess.Util.*;


public class Knight extends ChessPiece{

    public Knight(PieceColor color) {
        super(color);
        type = PieceType.KNIGHT;
    }

    public Knight(ChessPiece newPiece){
        super(newPiece);
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


    @Override
    public boolean AllowedToMoveTo(ChessBoard CurrentBoard, ChessCoor CurrentCoord, ChessCoor newCoor) {
        return newCoor.isContainedIn(GetPotentialMoves(CurrentBoard, CurrentCoord));
    }

    @Override
    public ArrayList<ChessCoor> GetControlledSquares(ChessBoard CurrentBoard, ChessCoor CurrentCoord) {
        ArrayList<ChessCoor> PotentialCoords = new ArrayList<>();

        //check south squares
        int testCoordX = CurrentCoord.getX() + 1;
        int testCoordY = CurrentCoord.getY() + 2;
        
        if(isWithinBounds(CurrentBoard, testCoordX, testCoordY)){
            PotentialCoords.add(new ChessCoor(testCoordX, testCoordY));
        }

        testCoordX = CurrentCoord.getX() - 1;
        testCoordY = CurrentCoord.getY() + 2;

        if(isWithinBounds(CurrentBoard, testCoordX, testCoordY)){
            PotentialCoords.add(new ChessCoor(testCoordX, testCoordY));
        }
        
        //check north squares
        testCoordX = CurrentCoord.getX() - 1;
        testCoordY = CurrentCoord.getY() - 2;

        if(isWithinBounds(CurrentBoard, testCoordX, testCoordY)){
            PotentialCoords.add(new ChessCoor(testCoordX, testCoordY));
        }

        testCoordX = CurrentCoord.getX() + 1;
        testCoordY = CurrentCoord.getY() - 2;

        if(isWithinBounds(CurrentBoard, testCoordX, testCoordY)){
            PotentialCoords.add(new ChessCoor(testCoordX, testCoordY));
        }

        //Check West squares

        testCoordX = CurrentCoord.getX() - 2;
        testCoordY = CurrentCoord.getY() - 1;

        if(isWithinBounds(CurrentBoard, testCoordX, testCoordY)){
            PotentialCoords.add(new ChessCoor(testCoordX, testCoordY));
        }

        testCoordX = CurrentCoord.getX() - 2;
        testCoordY = CurrentCoord.getY() + 1;

        if(isWithinBounds(CurrentBoard, testCoordX, testCoordY)){
            PotentialCoords.add(new ChessCoor(testCoordX, testCoordY));
        }

        //Check East squares

        testCoordX = CurrentCoord.getX() + 2;
        testCoordY = CurrentCoord.getY() - 1;

        if(isWithinBounds(CurrentBoard, testCoordX, testCoordY)){
            PotentialCoords.add(new ChessCoor(testCoordX, testCoordY));
        }

        testCoordX = CurrentCoord.getX() + 2;
        testCoordY = CurrentCoord.getY() + 1;

        if(isWithinBounds(CurrentBoard, testCoordX, testCoordY)){
            PotentialCoords.add(new ChessCoor(testCoordX, testCoordY));
        }

        return PotentialCoords;
    }

    @Override
    public int getMapColorScore(ChessCoor coor, int endGameFactor) {
        //X and Y are Flipped, so to access value, it should be ValueTable[Y][X]
        int X = coor.getX();
        int Y = coor.getY();

        if(color == PieceColor.BLACK){
            Y = 7 - coor.getY();
        }

        int[][] ValueTable =  
        {
            {-50,-40,-30,-30,-30,-30,-40,-50},
            {-40,-20,  0,  0,  0,  0,-20,-40},
            {-30,  0, 10, 15, 15, 10,  0,-30},
            {-30,  5, 15, 20, 20, 15,  5,-30},
            {-30,  0, 15, 20, 20, 15,  0,-30},
            {-30,  5, 10, 15, 15, 10,  5,-30},
            {-40,-20,  0,  5,  5,  0,-20,-40},
            {-50,-40,-30,-30,-30,-30,-40,-50}

        };
        
        //This is less important as the game progresses
        return (ValueTable[Y][X]*endGameFactor) / 64;
    }
    
    
}
