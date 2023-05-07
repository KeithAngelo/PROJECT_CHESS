package Chess.Piece;

import java.util.ArrayList;

import Chess.ChessBoard;
import Chess.Util.*;


public class Pawn extends ChessPiece{

    public Pawn(PieceColor color) {
        super(color);
        type = PieceType.PAWN;
    }

    public Pawn(ChessPiece newPiece){
        super(newPiece);
    }

    @Override
    public ArrayList<ChessCoor> GetPotentialMoves(ChessBoard CurrentBoard, ChessCoor CurrentCoord) {
        ArrayList<ChessCoor> PotentialCoords = new ArrayList<>();

        int direction = 1;
        if(this.color == PieceColor.WHITE){
            direction = -1;
        }

        int testCoordX = CurrentCoord.getX();
        int testCoordY = CurrentCoord.getY() + (1 * direction);

        boolean canMove = notOutOfBounds(testCoordX, testCoordY) && CurrentBoard.peekPieceAt(testCoordX, testCoordY) == null;
        if(canMove){
            PotentialCoords.add(new ChessCoor(testCoordX, testCoordY));
            //Case when pawn can skip 
            if(maySkip(CurrentCoord) && CurrentBoard.peekPieceAt(testCoordX, testCoordY + (1 * direction)) == null){
                PotentialCoords.add(new ChessCoor(testCoordX, testCoordY + (1 * direction)));
            }
        }

        //EAT THE SIDES
        testCoordX = CurrentCoord.getX() + 1;
        testCoordY = CurrentCoord.getY() + (1 * direction);

        if(canConsume(CurrentBoard, testCoordX, testCoordY)){
            PotentialCoords.add(new ChessCoor(testCoordX, testCoordY));
        }

        testCoordX = CurrentCoord.getX() - 1;
        testCoordY = CurrentCoord.getY() + (1 * direction);

        if(canConsume(CurrentBoard, testCoordX, testCoordY)){
            PotentialCoords.add(new ChessCoor(testCoordX, testCoordY));
        }

        return PotentialCoords;
    }

    private boolean canConsume(ChessBoard currentBoard, int CoorX, int CoorY){
        if(!notOutOfBounds(CoorX, CoorY)){
            return false;
        }

        if(currentBoard.peekPieceAt(CoorX, CoorY) == null){
            return false;
        }
        
        return currentBoard.peekPieceAt(CoorX, CoorY).getColor() != this.color;
    }
    
    private boolean notOutOfBounds(int CoorX, int CoorY){
        return !((CoorX < 0 || CoorY < 0) || (CoorX > 7 || CoorY > 7));
    }
    
    private boolean maySkip(ChessCoor CurrentCoor){
        if(this.color == PieceColor.WHITE){
            return CurrentCoor.getY() == 6;
        }else{
            return CurrentCoor.getY() == 1;
        }
        //Probably not the best method, but you cant stop me
    }

    @Override
    public boolean AllowedToMoveTo(ChessBoard CurrentBoard, ChessCoor CurrentCoord, ChessCoor newCoor) {
        return newCoor.isContainedIn(GetPotentialMoves(CurrentBoard, CurrentCoord));
    }

    @Override
    public ArrayList<ChessCoor> GetControlledSquares(ChessBoard CurrentBoard, ChessCoor CurrentCoord) {
        ArrayList<ChessCoor> PotentialCoords = new ArrayList<>();

        
        int direction = 1;
        if(this.color == PieceColor.WHITE){
            direction = -1;
        }

        //EAT THE SIDES
        int testCoordX = CurrentCoord.getX() + 1;
        int testCoordY = CurrentCoord.getY() + (1 * direction);

        if(isWithinBounds(CurrentBoard, testCoordX, testCoordY)){
            PotentialCoords.add(new ChessCoor(testCoordX, testCoordY));
        }

        testCoordX = CurrentCoord.getX() - 1;
        testCoordY = CurrentCoord.getY() + (1 * direction);

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
            {60, 60, 60, 60, 60, 60, 60, 60},
            {50, 50, 50, 50, 50, 50, 50, 50},
            {10, 10, 20, 30, 30, 20, 10, 10},
            {5,  5, 10, 25, 25, 10,  5,  5},
            {0,  0,  0, 20, 20,  0,  0,  0},
            {5, -5,-10,  0,  0,-10, -5,  5},
            {5, 10, 10,-20,-20, 10, 10,  5},
            {0,  0,  0,  0,  0,  0,  0,  0}

        };
        
        return ValueTable[Y][X];
    }

    
    
}
