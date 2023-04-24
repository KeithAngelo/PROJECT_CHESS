package Chess.Piece;

import java.util.ArrayList;

import Chess.ChessBoard;
import Chess.Util.ChessCoor;
import Chess.Util.PieceColor;

public class Pawn extends ChessPiece{

    public Pawn(PieceColor color) {
        super(color);
        type = PieceType.PAWN;
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
    
}
