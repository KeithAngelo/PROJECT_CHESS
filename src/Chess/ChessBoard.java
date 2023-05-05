package Chess;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Chess.Piece.*;
import Chess.Util.*;



public class ChessBoard {
    //TODO : implement this as where all pieces in a game is stored.

    public PieceColor TurnColor = PieceColor.WHITE;

    public void NextTurn(){
        if(TurnColor == PieceColor.WHITE){
            TurnColor = PieceColor.BLACK;
        }else{
            TurnColor = PieceColor.WHITE;
        }
    }


    public ChessPiece[][] board = new ChessPiece[8][8];

    CaptureEvent myCaptureEvent;
    //Default promotion event. this will be changed when addPromotionEvent() is called
    PromotionEvent myPromotionEvent = CurrentTurn -> new Queen(TurnColor);


    //TODO : Implement a way to store what was the previous move
    //InitNewCoor[0] is Initial Coordinate, InitCoor[1] is new Coordinate
    ChessCoor[] InitNewCoor = new ChessCoor[2];
    boolean PreviousIsCapture = false;
    int numberOfPieces;

    private int getNumberOfPieces(){
        int num = 0;
        for(int X = 0; X < 8; X++){
            for(int Y = 0; Y < 8; Y++){
                if(board[X][Y] != null){
                    num++;
                }
            }
        }
        return num;
    }
    
    ChessBoard(){
        SetToDefaultPosition();
    }


    //Constructor for copying data of another chessBoard
    public ChessBoard(ChessBoard copyChessBoard){
        // make sure that each copy of chess pieces have a unique pointer
        for(int X = 0; X < 8; X++){
            for(int Y = 0; Y < 8; Y++){
                if(copyChessBoard.board[X][Y] == null){
                    this.board[X][Y] = null;
                }else{
                    this.board[X][Y] = ChessPiece.copyPiece(copyChessBoard.board[X][Y]);
                }
                
            }
        }


        this.TurnColor = copyChessBoard.TurnColor;
        this.numberOfPieces = copyChessBoard.numberOfPieces;
        
    }

    public void SetToDefaultPosition(){
        //TODO : FINISH THIS, remember to make empty squares null

        TurnColor = PieceColor.WHITE;

        //Setting empty squares to null
        for(int Y = 2; Y <= 5; Y ++){
            for(int X = 0; X < 8; X++){
                board[X][Y] = null;
            }
        }

        //Custom setting of pieces

        //black pieces
        for(int X = 0; X < 8; X++){
            board[X][1] = new Pawn(PieceColor.BLACK);
        }

        board[0][0] = new Rook(PieceColor.BLACK);
        board[7][0] = new Rook(PieceColor.BLACK);

        board[1][0] = new Knight(PieceColor.BLACK);
        board[6][0] = new Knight(PieceColor.BLACK);

        board[2][0] = new Bishop(PieceColor.BLACK);
        board[5][0] = new Bishop(PieceColor.BLACK);

        board[3][0] = new Queen(PieceColor.BLACK);
        board[4][0] = new King(PieceColor.BLACK);


        //White pieces
        for(int X = 0; X < 8; X++){
            board[X][6] = new Pawn(PieceColor.WHITE);
        }

        board[0][7] = new Rook(PieceColor.WHITE);
        board[7][7] = new Rook(PieceColor.WHITE);

        board[1][7] = new Knight(PieceColor.WHITE);
        board[6][7] = new Knight(PieceColor.WHITE);

        board[2][7] = new Bishop(PieceColor.WHITE);
        board[5][7] = new Bishop(PieceColor.WHITE);

        board[3][7] = new Queen(PieceColor.WHITE);
        board[4][7] = new King(PieceColor.WHITE);

        
        numberOfPieces = getNumberOfPieces();
    }

    private boolean putAtSquare(ChessPiece piece , ChessCoor newCoor){
        int newX = newCoor.getX();
        int newY = newCoor.getY();
        PreviousIsCapture = board[newX][newY] != null;

        if(PreviousIsCapture){
            numberOfPieces--;
        }

        board[newX][newY] = piece;

        return true;
    }


    private boolean putAtSquare(ChessCoor OldCoor , ChessCoor newCoor){
        int newX = newCoor.getX();
        int newY = newCoor.getY();

        int oldX = OldCoor.getX();
        int oldY = OldCoor.getY();

        PreviousIsCapture = board[newX][newY] != null;

        if(PreviousIsCapture){
            numberOfPieces--;
        }

        board[newX][newY] = board[oldX][oldY];
        board[oldX][oldY] = null;

        return true;
    }

    
    public boolean Move(ChessCoor AinitialCoor, ChessCoor ANewCoor){
        //Defenitly a brain dead method, but this uses dependency injection so that there can be a (HasMoved) attribute
        PreviousIsCapture = false;
        moveInterface BoardMove = (initialCoor, NewCoor) -> {
            //Check if there is no piece at the specified coordinate
            ChessPiece currentPiece = board[initialCoor.getX()][initialCoor.getY()] ;

            if(currentPiece == null){
                return false;
            }

            //Only moves when it is the right turn
            if(currentPiece.getColor() != TurnColor){
                return false;
            }

            //TODO : Add Precaution and stop Illegal Moves

            if(!(currentPiece.AllowedToMoveTo(this, initialCoor, NewCoor))){
                return false;
            }

            //cant move if the next move will be a check to itself
            boolean Checked = peekAtFuture(initialCoor, NewCoor).selfIsChecked();
            if(Checked){
                return false;
            }

            if(isShortCastleMove(initialCoor, NewCoor)){

                board[NewCoor.getX()][NewCoor.getY()] = board[initialCoor.getX()][initialCoor.getY()];
                board[initialCoor.getX()][initialCoor.getY()] = null;

                int YCoor;
                if(TurnColor == PieceColor.WHITE){
                    YCoor = 7;
                }else{
                    YCoor = 0;
                }


                board[5][YCoor] = board[7][YCoor];
                board[7][YCoor] = null;

                NextTurn();
                return true;
            }

            if(isLongCastleMove(initialCoor, NewCoor)){


                board[NewCoor.getX()][NewCoor.getY()] = board[initialCoor.getX()][initialCoor.getY()];
                board[initialCoor.getX()][initialCoor.getY()] = null;

                int YCoor;
                if(TurnColor == PieceColor.WHITE){
                    YCoor = 7;
                }else{
                    YCoor = 0;
                }


                board[3][YCoor] = board[0][YCoor];
                board[0][YCoor] = null;

                NextTurn();
                return true;
            }

            //For capture event dependency injection
            if(board[NewCoor.getX()][NewCoor.getY()] != null){
                if(myCaptureEvent != null){
                    myCaptureEvent.doCaptureEvent(board[NewCoor.getX()][NewCoor.getY()].type);
                }
                
            }
            
            //Actual Moving
            // board[NewCoor.getX()][NewCoor.getY()] = board[initialCoor.getX()][initialCoor.getY()];
            // board[initialCoor.getX()][initialCoor.getY()] = null;

            putAtSquare(AinitialCoor, ANewCoor);

            
            

            //In case a pawn must be promoted
            ChessCoor PawnPromotion = pawMustBePromoted();
            if(PawnPromotion != null){
                ChessPiece promotedPiece = myPromotionEvent.doPromotionEvent(TurnColor);

                //Actual promoting
                board[PawnPromotion.getX()][PawnPromotion.getY()] = promotedPiece;
            }

            NextTurn();
            

            return true;
        };

        return board[AinitialCoor.getX()][AinitialCoor.getY()].move(BoardMove, AinitialCoor, ANewCoor);
        
    }


    //returns a chessboard to peek at what will happen if the move will occur
    public ChessBoard peekAtFuture(ChessCoor initialCoor, ChessCoor NewCoor){
        ChessBoard futureBoard = new ChessBoard(this);

        ChessPiece currentPiece = futureBoard.board[initialCoor.getX()][initialCoor.getY()] ;

        if(currentPiece == null){
            return futureBoard;
        }

        //Only moves when it is the right turn
        if(currentPiece.getColor() != TurnColor){
            return futureBoard;
        }

        //TODO : Add Precaution and stop Illegal Moves

        // if(!(currentPiece.AllowedToMoveTo(this, initialCoor, NewCoor))){
        //     return futureBoard;
        // }

        if(futureBoard.isShortCastleMove(initialCoor, NewCoor)){
            futureBoard.board[NewCoor.getX()][NewCoor.getY()] = futureBoard.board[initialCoor.getX()][initialCoor.getY()];
            futureBoard.board[initialCoor.getX()][initialCoor.getY()] = null;

            int YCoor;
            if(TurnColor == PieceColor.WHITE){
                YCoor = 7;
            }else{
                YCoor = 0;
            }

            futureBoard.board[5][YCoor] = futureBoard.board[7][YCoor];
            futureBoard.board[7][YCoor] = null;

            futureBoard.NextTurn();
            return futureBoard;
        }

        if(futureBoard.isLongCastleMove(initialCoor, NewCoor)){
            futureBoard.board[NewCoor.getX()][NewCoor.getY()] = futureBoard.board[initialCoor.getX()][initialCoor.getY()];
            futureBoard.board[initialCoor.getX()][initialCoor.getY()] = null;

            int YCoor;
            if(TurnColor == PieceColor.WHITE){
                YCoor = 7;
            }else{
                YCoor = 0;
            }

            futureBoard.board[3][YCoor] = futureBoard.board[0][YCoor];
            futureBoard.board[0][YCoor] = null;

            futureBoard.NextTurn();
            return futureBoard;
        }

        ChessCoor PawnPromotion = futureBoard.pawMustBePromoted();
        if(PawnPromotion != null){
            ChessPiece promotedPiece = futureBoard.myPromotionEvent.doPromotionEvent(futureBoard.TurnColor);

            //Actual promoting
            futureBoard.board[PawnPromotion.getX()][PawnPromotion.getY()] = promotedPiece;
        }

        futureBoard.putAtSquare(initialCoor, NewCoor);

        futureBoard.NextTurn();

        return futureBoard;
    }

    

    public ChessPiece peekPieceAt(int CoorX, int CoorY){
        return board[CoorX][CoorY];  
    }

    public ArrayList<ChessCoor> getAllowedSquaresAt(ChessCoor CoorOfSquare){
        ArrayList<ChessCoor> AllowedSquares = new ArrayList<>();

        //Very efficient yes
        for(int Y = 0; Y < 8 ; Y++){
            for(int X = 0; X < 8; X++){
                ChessBoard TestBoard = new ChessBoard(this);

                if(TestBoard.Move(CoorOfSquare, new ChessCoor( X,Y))){
                    AllowedSquares.add(new ChessCoor(X, Y));
                }

            }
        }

        ChessPiece.iterateAllPossibleMoves(CoorOfSquare, (X, Y) -> {
            ChessBoard TestBoard = new ChessBoard(this);

            if(TestBoard.Move(CoorOfSquare, new ChessCoor( X,Y))){
                AllowedSquares.add(new ChessCoor(X, Y));
            }
        });

        return AllowedSquares;
    }

    public boolean isCheckMated(){
        // TODO : HANDLE POSSIBLE EXCEPTIONS !!!!!

        ChessCoor KingCoors = KingCoords()[0];

        King CurrentKing = (King) board[KingCoors.getX()][KingCoors.getY()];

        ChessCoor OtherKingCoors = KingCoords()[1];

        King OtherCurrentKing = (King) board[OtherKingCoors.getX()][OtherKingCoors.getY()];

        return CurrentKing.isCheckMated(this, KingCoors) || OtherCurrentKing.isCheckMated(this, OtherKingCoors);
    }

    public boolean isDraw(){
        // TODO : HANDLE POSSIBLE EXCEPTIONS !!!!!

        ChessCoor KingCoors = KingCoords()[0];

        King CurrentKing = (King) board[KingCoors.getX()][KingCoors.getY()];

        return CurrentKing.isDraw(this, KingCoors);
    }

    public boolean isChecked(){
        // TODO : HANDLE POSSIBLE EXCEPTIONS !!!!!

        ChessCoor KingCoors = KingCoords()[0];

        King CurrentKing = (King) board[KingCoors.getX()][KingCoors.getY()];

        ChessCoor OtherKingCoors = KingCoords()[1];

        King OtherCurrentKing = (King) board[OtherKingCoors.getX()][OtherKingCoors.getY()];

        return CurrentKing.isChecked(this, KingCoors) || OtherCurrentKing.isChecked(this, OtherKingCoors);
    }

    public boolean selfIsChecked(){
            ChessCoor KingCoors = KingCoords()[1];
        King CurrentKing = (King) board[KingCoors.getX()][KingCoors.getY()];

        return CurrentKing.isChecked(this, KingCoors);
    }

    //returns an array with 2 coordinates of the two kings
    private ChessCoor[] KingCoords(){
        ChessCoor[] pairOfKingCoords = new ChessCoor[2];
        for(int X = 0; X < 8; X++){
            for(int Y = 0; Y < 8; Y++){
                if(board[X][Y] == null){
                    continue;
                }

                if(board[X][Y].getColor() != this.TurnColor){
                    continue;
                }

                if(board[X][Y].getType() == PieceType.KING){
                    pairOfKingCoords[0] = new ChessCoor(X, Y);
                    X = 8;
                    break;
                }
            }
        }

        for(int X = 0; X < 8; X++){
            for(int Y = 0; Y < 8; Y++){
                if(board[X][Y] == null){
                    continue;
                }

                if(board[X][Y].getColor() == this.TurnColor){
                    continue;
                }

                if(board[X][Y].getType() == PieceType.KING){
                    pairOfKingCoords[1] = new ChessCoor(X, Y);
                    X = 8;
                    break;
                }
            }
        }

        return pairOfKingCoords;
    }

    public ChessCoor pawMustBePromoted(){
        int YCoorPromote;
        if(TurnColor == PieceColor.WHITE){
            YCoorPromote = 0;
        }else{
            YCoorPromote = 7;
        }

        for(int X = 0; X < 8; X++){
            if(board[X][YCoorPromote] == null){
                continue;
            }

            if(board[X][YCoorPromote].getType() == PieceType.PAWN){
                return new ChessCoor(X, YCoorPromote);
            }
        }

        return null;
    }


    public boolean canShortCastle(){
        int YCoor;

        if(TurnColor == PieceColor.WHITE){
            YCoor = 7;
        }else{
            YCoor = 0;
        }

        //Check if rook or king spots are empty
        if(board[4][YCoor] == null || board[7][YCoor] == null){
            return false;
        }

        //Check if king has moved
        if(board[4][YCoor].hasMoved){
            return false;
        }

        //Check if right Rook has moved
        if(board[7][YCoor].hasMoved){
            return false;
        }

        //Check Square between king and rook are empty
        if(board[5][YCoor] != null || board[6][YCoor] != null){
            return false;
        }
        

        return true;
    }
    
    private boolean isShortCastleMove(ChessCoor initCoor, ChessCoor NewCoor){
        int initX = initCoor.getX();
        int initY = initCoor.getY();
        if(board[initX][initY].type != PieceType.KING){
            return false;
        }

        if(!canShortCastle()){
            return false;
        }

        int YCoor;
        if(TurnColor == PieceColor.WHITE){
            YCoor = 7;
        }else{
            YCoor = 0;
        }

        if(!NewCoor.equals(new ChessCoor(6, YCoor))){
            return false;
        }

        return true;
    }

    private boolean isLongCastleMove(ChessCoor initCoor, ChessCoor NewCoor){
        int initX = initCoor.getX();
        int initY = initCoor.getY();
        if(board[initX][initY].type != PieceType.KING){
            return false;
        }

        if(!canLongCastle()){
            return false;
        }

        int YCoor;
        if(TurnColor == PieceColor.WHITE){
            YCoor = 7;
        }else{
            YCoor = 0;
        }

        if(!NewCoor.equals(new ChessCoor(2, YCoor))){
            return false;
        }

        return true;
    }

    public boolean canLongCastle(){
        int YCoor;

        if(TurnColor == PieceColor.WHITE){
            YCoor = 7;
        }else{
            YCoor = 0;
        }

        //Check if rook or king spots are empty
        if(board[4][YCoor] == null || board[0][YCoor] == null){
            return false;
        }

        //Check if king has moved
        if(board[4][YCoor].hasMoved){
            return false;
        }

        //Check if right Rook has moved
        if(board[0][YCoor].hasMoved){
            return false;
        }

        //Check Square between king and rook are empty
        if(board[1][YCoor] != null || board[2][YCoor] != null || board[3][YCoor] != null){
            return false;
        }
        

        return true;
    }

    public void addCaptureEvent(CaptureEvent thisCapture){
        this.myCaptureEvent = thisCapture;
    }

    public void addPromotionEvent(PromotionEvent thisPromotion){
        this.myPromotionEvent = thisPromotion;
    }

    private ChessCoor toCoor(int number){
        int Y = number % 8;
        int X = (number - Y) / 8;

        return new ChessCoor(X, Y);
    }

}




