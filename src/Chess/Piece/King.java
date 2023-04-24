package Chess.Piece;

import java.util.ArrayList;

import Chess.ChessBoard;
import Chess.Util.*;


public class King extends ChessPiece{

    public King(PieceColor color) {
        super(color);
        type = PieceType.KING;
    }

    @Override
    public ArrayList<ChessCoor> GetPotentialMoves(ChessBoard CurrentBoard, ChessCoor CurrentCoord) {
        ArrayList<ChessCoor> PotentialCoords = new ArrayList<>();

        ArrayList<ChessCoor> NormMoves = NormalMoves(CurrentBoard, CurrentCoord);

        for(ChessCoor currNormalMoves : NormMoves){
            if(!(currNormalMoves.isContainedIn(getEnemySquares(CurrentBoard)))){
                PotentialCoords.add(currNormalMoves);
            }
        }

        return PotentialCoords;
    }

    @Override
    public boolean AllowedToMoveTo(ChessBoard CurrentBoard, ChessCoor CurrentCoord, ChessCoor newCoor) {
        return newCoor.isContainedIn(GetPotentialMoves(CurrentBoard, CurrentCoord));
    }


    //Allowed moves without accounting if the enemy is controlling the square
    public ArrayList<ChessCoor> NormalMoves(ChessBoard CurrentBoard, ChessCoor CurrentCoord){
        ArrayList<ChessCoor> NormalMoves = new ArrayList<>();

        //EAST
        int testCoordX = CurrentCoord.getX() + 1;
        int testCoordY = CurrentCoord.getY() ;

        if(isValidSquare(CurrentBoard, testCoordX, testCoordY)){
            NormalMoves.add(new ChessCoor(testCoordX, testCoordY));
        }

        //WEST
        testCoordX = CurrentCoord.getX() - 1;
        testCoordY = CurrentCoord.getY() ;

        if(isValidSquare(CurrentBoard, testCoordX, testCoordY)){
            NormalMoves.add(new ChessCoor(testCoordX, testCoordY));
        }
        //NORTH
        testCoordX = CurrentCoord.getX() ;
        testCoordY = CurrentCoord.getY() - 1;
        if(isValidSquare(CurrentBoard, testCoordX, testCoordY)){
            NormalMoves.add(new ChessCoor(testCoordX, testCoordY));
        }

        //SOUTH
        testCoordX = CurrentCoord.getX() ;
        testCoordY = CurrentCoord.getY() + 1;

        if(isValidSquare(CurrentBoard, testCoordX, testCoordY)){
            NormalMoves.add(new ChessCoor(testCoordX, testCoordY));
        }

        //SOUTHEAST
        testCoordX = CurrentCoord.getX() + 1;
        testCoordY = CurrentCoord.getY() + 1;

        if(isValidSquare(CurrentBoard, testCoordX, testCoordY)){
            NormalMoves.add(new ChessCoor(testCoordX, testCoordY));
        }

        //SOUTHWEST
        testCoordX = CurrentCoord.getX() + 1;
        testCoordY = CurrentCoord.getY() - 1;

        if(isValidSquare(CurrentBoard, testCoordX, testCoordY)){
            NormalMoves.add(new ChessCoor(testCoordX, testCoordY));
        }

        //NORTHWEST
        testCoordX = CurrentCoord.getX() - 1;
        testCoordY = CurrentCoord.getY() - 1;

        if(isValidSquare(CurrentBoard, testCoordX, testCoordY)){
            NormalMoves.add(new ChessCoor(testCoordX, testCoordY));
        }

        //NORTHEAST
        testCoordX = CurrentCoord.getX() - 1;
        testCoordY = CurrentCoord.getY() + 1;

        if(isValidSquare(CurrentBoard, testCoordX, testCoordY)){
            NormalMoves.add(new ChessCoor(testCoordX, testCoordY));
        }


        return NormalMoves;
    }

    



    /* This will get coordinates where the enemy controls the squares.
     * This is needed since the king will note be able to move on these squares
     */
    public ArrayList<ChessCoor> getEnemySquares(ChessBoard CurrentBoard){
        ArrayList<ChessCoor> EnemySquares = new ArrayList<>(); 

        for(int Y = 0; Y < 8; Y++){
            for(int X = 0; X < 8; X ++){

                ChessPiece currentPiece = CurrentBoard.board[X][Y];

                if(currentPiece == null){
                    continue;
                }

                if(currentPiece.getColor() == this.color){
                    continue;
                }

                for(ChessCoor EnemySquareCoor : currentPiece.GetControlledSquares(CurrentBoard, new ChessCoor(X, Y))){
                    EnemySquares.add(EnemySquareCoor);
                }
                
            }
        }

        return EnemySquares;

    }
    
    public boolean isChecked(ChessBoard CurrentBoard, ChessCoor CurrentCoord){
        return CurrentCoord.isContainedIn(getEnemySquares(CurrentBoard));
    }

    public boolean isCheckMated(ChessBoard CurrentBoard, ChessCoor CurrentCoord){
        if(!isChecked(CurrentBoard,CurrentCoord)){
            return false;
        }

        return this.GetPotentialMoves(CurrentBoard, CurrentCoord).isEmpty();
    }

    @Override
    public ArrayList<ChessCoor> GetControlledSquares(ChessBoard CurrentBoard, ChessCoor CurrentCoord) {
        ArrayList<ChessCoor> NormalMoves = new ArrayList<>();

         //EAST
         int testCoordX = CurrentCoord.getX() + 1;
         int testCoordY = CurrentCoord.getY() ;
 
         if(isWithinBounds(CurrentBoard, testCoordX, testCoordY)){
             NormalMoves.add(new ChessCoor(testCoordX, testCoordY));
         }
 
         //WEST
         testCoordX = CurrentCoord.getX() - 1;
         testCoordY = CurrentCoord.getY() ;
 
         if(isWithinBounds(CurrentBoard, testCoordX, testCoordY)){
             NormalMoves.add(new ChessCoor(testCoordX, testCoordY));
         }
         //NORTH
         testCoordX = CurrentCoord.getX() ;
         testCoordY = CurrentCoord.getY() - 1;
         if(isWithinBounds(CurrentBoard, testCoordX, testCoordY)){
             NormalMoves.add(new ChessCoor(testCoordX, testCoordY));
         }
 
         //SOUTH
         testCoordX = CurrentCoord.getX() ;
         testCoordY = CurrentCoord.getY() + 1;
 
         if(isWithinBounds(CurrentBoard, testCoordX, testCoordY)){
             NormalMoves.add(new ChessCoor(testCoordX, testCoordY));
         }
 
         //SOUTHEAST
         testCoordX = CurrentCoord.getX() + 1;
         testCoordY = CurrentCoord.getY() + 1;
 
         if(isWithinBounds(CurrentBoard, testCoordX, testCoordY)){
             NormalMoves.add(new ChessCoor(testCoordX, testCoordY));
         }
 
         //SOUTHWEST
         testCoordX = CurrentCoord.getX() + 1;
         testCoordY = CurrentCoord.getY() - 1;
 
         if(isWithinBounds(CurrentBoard, testCoordX, testCoordY)){
             NormalMoves.add(new ChessCoor(testCoordX, testCoordY));
         }
 
         //NORTHWEST
         testCoordX = CurrentCoord.getX() - 1;
         testCoordY = CurrentCoord.getY() - 1;
 
         if(isWithinBounds(CurrentBoard, testCoordX, testCoordY)){
             NormalMoves.add(new ChessCoor(testCoordX, testCoordY));
         }
 
         //NORTHEAST
         testCoordX = CurrentCoord.getX() - 1;
         testCoordY = CurrentCoord.getY() + 1;
 
         if(isWithinBounds(CurrentBoard, testCoordX, testCoordY)){
            NormalMoves.add(new ChessCoor(testCoordX, testCoordY));
        }

        return NormalMoves;
    }


}
