package Chess.Piece;

import java.util.ArrayList;

import Chess.ChessBoard;
import Chess.Util.*;


public class Queen extends ChessPiece{

    public Queen(PieceColor color) {
        super(color);
        type = PieceType.QUEEN;
    }

    public Queen(ChessPiece newPiece){
        super(newPiece);
    }

    @Override
    public ArrayList<ChessCoor> GetPotentialMoves(ChessBoard CurrentBoard, ChessCoor CurrentCoord) {
        ArrayList<ChessCoor> PotentialCoords = new ArrayList<>();
        int CoorX = CurrentCoord.getX();
        int CoorY = CurrentCoord.getY();

        boolean blocked = false;
        //Check North Boxes
        for(int a = 1;  ;a++){
            int testCoorX = CoorX;
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

        blocked = false;
        //Check South Boxes
        for(int a = 1;  ;a++){
            int testCoorX = CoorX;
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

        blocked = false;
        //Check East Boxes
        for(int a = 1;  ;a++){
            int testCoorX = CoorX+a;
            int testCoorY = CoorY;

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

        blocked = false;
        //Check West Boxes
        for(int a = 1;  ;a++){
            int testCoorX = CoorX-a;
            int testCoorY = CoorY;

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

        
        blocked = false;
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
        //Check North Boxes
        for(int a = 1;  ;a++){
            int testCoorX = CoorX;
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

        blocked = false;
        //Check South Boxes
        for(int a = 1;  ;a++){
            int testCoorX = CoorX;
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

        blocked = false;
        //Check East Boxes
        for(int a = 1;  ;a++){
            int testCoorX = CoorX+a;
            int testCoorY = CoorY;

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

        blocked = false;
        //Check West Boxes
        for(int a = 1;  ;a++){
            int testCoorX = CoorX-a;
            int testCoorY = CoorY;

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

        
        blocked = false;
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
        return newCoor.isContainedIn(GetPotentialMoves(CurrentBoard, CurrentCoord));
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
            {-20,-10,-10, -5, -5,-10,-10,-20},
            {10,  0,  0,  0,  0,  0,  0,-10},
            {-10,  0,  5,  5,  5,  5,  0,-10},
            {-5,  0,  5,  5,  5,  5,  0, -5},
            {0,  0,  5,  5,  5,  5,  0, -5},
            {-10,  5,  5,  5,  5,  5,  0,-10},
            {-10,  0,  5,  0,  0,  0,  0,-10},
            {-20,-10,-10, -5, -5,-10,-10,-20}

        };
        
        //This is less important as the game progresses
        return (ValueTable[Y][X]*endGameFactor) / 64;
    }
}
