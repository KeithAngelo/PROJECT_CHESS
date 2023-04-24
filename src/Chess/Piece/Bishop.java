package Chess.Piece;

import java.util.ArrayList;

import Chess.ChessBoard;
import Chess.Util.*;


public class Bishop extends ChessPiece{

    public Bishop(PieceColor color) {
        super(color);
        type = PieceType.BISHOP;
    }

    @Override
    public ArrayList<ChessCoor> GetPotentialMoves(ChessBoard CurrentBoard, ChessCoor CurrentCoord) {
        ArrayList<ChessCoor> PotentialCoords = new ArrayList<>();
        int CoorX = CurrentCoord.getX();
        int CoorY = CurrentCoord.getY();
        
        boolean blocked = false;
        //Check Upper Left Boxes
        for(int a = 1;  ;a++){
            int testCoorX = CoorX-a;
            int testCoorY = CoorY-a;

            

            if(testCoorX < 0 || testCoorY < 0){
                break;
            }

            if(testCoorX > 7 || testCoorY > 7){
                break;
            }

            if(blocked){
                break;
            }

            if(CurrentBoard.board[testCoorX][testCoorY] == null){
                PotentialCoords.add(new ChessCoor(testCoorX, testCoorY));
                continue;
            }

            if(CurrentBoard.board[testCoorX][testCoorY].getColor() == this.color){
                blocked = true;
            }else{
                blocked = true;
                PotentialCoords.add(new ChessCoor(testCoorX, testCoorY));
                //a square occupied by enemy square can be eaten, so this is added
            }

        }

        

        //Check Lower Right Boxes
        blocked = false;
        for(int a = 1; ;a++){
            int testCoorX = CoorX+a;
            int testCoorY = CoorY+a;

            if(testCoorX < 0 || testCoorY < 0){
                break;
            }

            if(testCoorX > 7 || testCoorY > 7){
                break;
            }

            if(blocked){
                break;
            }

            if(CurrentBoard.board[testCoorX][testCoorY] == null){
                PotentialCoords.add(new ChessCoor(testCoorX, testCoorY));
                continue;
            }

            if(CurrentBoard.board[testCoorX][testCoorY].getColor() == this.color){
                blocked = true;
            }else{
                blocked = true;
                PotentialCoords.add(new ChessCoor(testCoorX, testCoorY));
                //a square occupied by enemy square can be eaten, so this is added
            }

        }

        //Check Upper Right Boxes
        blocked = false;
        for(int a = 1; ;a++){
            int testCoorX = CoorX+a;
            int testCoorY = CoorY-a;

            if(testCoorX < 0 || testCoorY < 0){
                break;
            }

            if(testCoorX > 7 || testCoorY > 7){
                break;
            }

            if(blocked){
                break;
            }

            if(CurrentBoard.board[testCoorX][testCoorY] == null){
                PotentialCoords.add(new ChessCoor(testCoorX, testCoorY));
                continue;
            }

            if(CurrentBoard.board[testCoorX][testCoorY].getColor() == this.color){
                blocked = true;
            }else{
                blocked = true;
                PotentialCoords.add(new ChessCoor(testCoorX, testCoorY));
                //a square occupied by enemy square can be eaten, so this is added
            }

        }

        //Check Lower Right Boxes
        blocked = false;
        for(int a = 1; ;a++){
            int testCoorX = CoorX-a;
            int testCoorY = CoorY+a;

            if(testCoorX < 0 || testCoorY < 0){
                break;
            }

            if(testCoorX > 7 || testCoorY > 7){
                break;
            }

            if(blocked){
                break;
            }

            if(CurrentBoard.board[testCoorX][testCoorY] == null){
                PotentialCoords.add(new ChessCoor(testCoorX, testCoorY));
                continue;
            }

            if(CurrentBoard.board[testCoorX][testCoorY].getColor() == this.color){
                blocked = true;
            }else{
                blocked = true;
                PotentialCoords.add(new ChessCoor(testCoorX, testCoorY));
                //a square occupied by enemy square can be eaten, so this is added
            }

        }

        
        return PotentialCoords;
    }

    @Override
    public ArrayList<ChessCoor> GetControlledSquares(ChessBoard CurrentBoard, ChessCoor CurrentCoord) {
        ArrayList<ChessCoor> PotentialCoords = new ArrayList<>();
        int CoorX = CurrentCoord.getX();
        int CoorY = CurrentCoord.getY();
        
        boolean blocked = false;
        //Check Upper Left Boxes
        for(int a = 1;  ;a++){
            int testCoorX = CoorX-a;
            int testCoorY = CoorY-a;

            

            if(testCoorX < 0 || testCoorY < 0){
                break;
            }

            if(testCoorX > 7 || testCoorY > 7){
                break;
            }

            if(blocked){
                break;
            }

            if(CurrentBoard.board[testCoorX][testCoorY] == null){
                PotentialCoords.add(new ChessCoor(testCoorX, testCoorY));
                continue;
            }

            blocked = true;
            PotentialCoords.add(new ChessCoor(testCoorX, testCoorY));

        }

        

        //Check Lower Right Boxes
        blocked = false;
        for(int a = 1; ;a++){
            int testCoorX = CoorX+a;
            int testCoorY = CoorY+a;

            if(testCoorX < 0 || testCoorY < 0){
                break;
            }

            if(testCoorX > 7 || testCoorY > 7){
                break;
            }

            if(blocked){
                break;
            }

            if(CurrentBoard.board[testCoorX][testCoorY] == null){
                PotentialCoords.add(new ChessCoor(testCoorX, testCoorY));
                continue;
            }

            blocked = true;
            PotentialCoords.add(new ChessCoor(testCoorX, testCoorY));

        }

        //Check Upper Right Boxes
        blocked = false;
        for(int a = 1; ;a++){
            int testCoorX = CoorX+a;
            int testCoorY = CoorY-a;

            if(testCoorX < 0 || testCoorY < 0){
                break;
            }

            if(testCoorX > 7 || testCoorY > 7){
                break;
            }

            if(blocked){
                break;
            }

            if(CurrentBoard.board[testCoorX][testCoorY] == null){
                PotentialCoords.add(new ChessCoor(testCoorX, testCoorY));
                continue;
            }

            blocked = true;
            PotentialCoords.add(new ChessCoor(testCoorX, testCoorY));

        }

        //Check Lower Right Boxes
        blocked = false;
        for(int a = 1; ;a++){
            int testCoorX = CoorX-a;
            int testCoorY = CoorY+a;

            if(testCoorX < 0 || testCoorY < 0){
                break;
            }

            if(testCoorX > 7 || testCoorY > 7){
                break;
            }

            if(blocked){
                break;
            }

            if(CurrentBoard.board[testCoorX][testCoorY] == null){
                PotentialCoords.add(new ChessCoor(testCoorX, testCoorY));
                continue;
            }

            blocked = true;
            PotentialCoords.add(new ChessCoor(testCoorX, testCoorY));

        }

        
        return PotentialCoords;
    }

    @Override
    public boolean AllowedToMoveTo(ChessBoard CurrentBoard, ChessCoor CurrentCoord, ChessCoor newCoor) {
        //TODO : FINISH THIS
        return newCoor.isContainedIn(GetPotentialMoves(CurrentBoard, CurrentCoord));
    }

   
    
}
