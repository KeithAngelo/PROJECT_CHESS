package Chess.Piece;

import java.util.ArrayList;

import Chess.ChessBoard;
import Chess.Util.*;


public class King extends ChessPiece{

    public King(PieceColor color) {
        super(color);
        type = PieceType.KING;
    }

    public King(ChessPiece newPiece){
        super(newPiece);
    }

    @Override
    public ArrayList<ChessCoor> GetPotentialMoves(ChessBoard CurrentBoard, ChessCoor CurrentCoord) {
        ArrayList<ChessCoor> PotentialCoords = new ArrayList<>();

        ArrayList<ChessCoor> NormMoves = NormalMoves(CurrentBoard, CurrentCoord);

        for(ChessCoor currNormalMoves : NormMoves){
            if(!(currNormalMoves.isContainedIn(getEnemySquares(CurrentBoard, CurrentCoord)))){
                PotentialCoords.add(currNormalMoves);
            }
        }


        int YCoor;
        if(CurrentBoard.TurnColor == PieceColor.WHITE){
            YCoor = 7;
        }else{
            YCoor = 0;
        }

        if(CurrentBoard.canLongCastle()){
            PotentialCoords.add(new ChessCoor(2, YCoor));
            // System.out.println("Longcast");
        }

        if(CurrentBoard.canShortCastle()){
            PotentialCoords.add(new ChessCoor(6, YCoor));
            // System.out.println("Shortcast");
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
    public ArrayList<ChessCoor> getEnemySquares(ChessBoard CurrentBoard, ChessCoor CurentCoord){
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
        //TODO : When checking if the king is in check, you just have to check the straight lines, diagonals, pawns and knights
        return CurrentCoord.isContainedIn(getEnemySquares(CurrentBoard,CurrentCoord));
    }


    // TODO : NEED TO CHECK EVERY POSSIBILITY IF IT REALLY IS CHECKMATE
    public boolean isCheckMated(ChessBoard CurrentBoard, ChessCoor CurrentCoord){
        if(!isChecked(CurrentBoard,CurrentCoord)){
            return false;
        }

        //Check if there are no longer valid moves possible for all pieces on the board :
        for(int Y = 0; Y < 8; Y++){
            for(int X = 0; X < 8; X ++){

                ChessBoard TestBoard = new ChessBoard(CurrentBoard);
                ChessPiece CurrentPiece = TestBoard.peekPieceAt(X, Y);

                if(CurrentPiece == null){
                    continue;
                }

                if(CurrentPiece.getColor() != this.color){
                    continue;
                }

                ArrayList<ChessCoor> PossibleMoves = CurrentPiece.GetPotentialMoves(CurrentBoard, new ChessCoor(X, Y));

                for(ChessCoor testCoor : PossibleMoves){
                    ChessBoard tempBoard = new ChessBoard(TestBoard);
                    if(tempBoard.Move(new ChessCoor(X, Y), testCoor)){
                        return false;
                    }
                }   

            }
        }


        return true;
    }

    public boolean isDraw(ChessBoard CurrentBoard, ChessCoor CurrentCoord){
        if(isChecked(CurrentBoard,CurrentCoord)){
            return false;
        }

        //Check if there are no longer valid moves possible for all pieces on the board :
        for(int Y = 0; Y < 8; Y++){
            for(int X = 0; X < 8; X ++){

                ChessBoard TestBoard = new ChessBoard(CurrentBoard);
                ChessPiece CurrentPiece = TestBoard.peekPieceAt(X, Y);

                if(CurrentPiece == null){
                    continue;
                }

                if(CurrentPiece.getColor() != this.color){
                    continue;
                }

                ArrayList<ChessCoor> PossibleMoves = CurrentPiece.GetPotentialMoves(CurrentBoard, new ChessCoor(X, Y));

                for(ChessCoor testCoor : PossibleMoves){
                    ChessBoard tempBoard = new ChessBoard(TestBoard);
                    if(tempBoard.Move(new ChessCoor(X, Y), testCoor)){
                        return false;
                    }
                }   

            }
        }


        return true;
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
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {30,-40,-40,-50,-50,-40,-40,-30},
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-20,-30,-30,-40,-40,-30,-30,-20},
            {-10,-20,-20,-20,-20,-20,-20,-10},
            {5, 10, 10,-20,-20, 10, 10,  5},
            {20, 20,  0,  0,  0,  0, 20, 20}
        };

        int[][] endGameTable = 
        {
            {-50,-40,-30,-20,-20,-30,-40,-50},
            {-30,-20,-10,  0,  0,-10,-20,-30},
            {-30,-10, 20, 30, 30, 20,-10,-30},
            {-30,-10, 30, 40, 40, 30,-10,-30},
            {-30,-10, 30, 40, 40, 30,-10,-30},
            {-30,-10, 20, 30, 30, 20,-10,-30},
            {-30,-30,  0,  0,  0,  0,-30,-30},
            {-50,-30,-30,-30,-30,-30,-30,-50}
        };
        
        if(endGameFactor > 45){
            return ValueTable[Y][X];
        }

        //Will return a score between the end game and mid game score depending on how little pieces left
        if(endGameFactor <= 45 && endGameFactor > 25){
            int endScore = 64 - endGameFactor;

            int MidValue = ValueTable[Y][X]*endGameFactor;
            int endValue = endGameTable[Y][X]*endScore;

            return (MidValue + endValue) / 64;
        }
        
        return endGameTable[Y][X];
    }


}
