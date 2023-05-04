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

    HashMap<ChessPiece, ChessCoor> PieceMap = new HashMap<>();

    //TODO : Implement a way to store what was the previous move
    //InitNewCoor[0] is Initial Coordinate, InitCoor[1] is new Coordinate
    ChessCoor[] InitNewCoor = new ChessCoor[2];
    boolean PreviousIsCapture = false;
    
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

        //create duplicate hashMap
        for(Map.Entry<ChessPiece, ChessCoor> MapEntry : copyChessBoard.PieceMap.entrySet()){
            this.PieceMap.put(MapEntry.getKey(), MapEntry.getValue());
        }

        this.TurnColor = copyChessBoard.TurnColor;
        
    }

    public void SetToDefaultPosition(){
        //TODO : FINISH THIS, remember to make empty squares null

        PieceMap.clear();

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
            PieceMap.put(board[X][1], new ChessCoor(X, 1));
        }

        board[0][0] = new Rook(PieceColor.BLACK);
        PieceMap.put(board[0][0], new ChessCoor(0, 0));
        board[7][0] = new Rook(PieceColor.BLACK);
        PieceMap.put(board[7][0], new ChessCoor(7, 0));

        board[1][0] = new Knight(PieceColor.BLACK);
        PieceMap.put(board[1][0], new ChessCoor(1, 0));
        board[6][0] = new Knight(PieceColor.BLACK);
        PieceMap.put(board[6][0], new ChessCoor(6, 0));

        board[2][0] = new Bishop(PieceColor.BLACK);
        PieceMap.put(board[2][0], new ChessCoor(2, 0));
        board[5][0] = new Bishop(PieceColor.BLACK);
        PieceMap.put(board[5][0], new ChessCoor(5, 0));

        board[3][0] = new Queen(PieceColor.BLACK);
        PieceMap.put(board[3][0], new ChessCoor(3, 0));
        board[4][0] = new King(PieceColor.BLACK);
        PieceMap.put(board[4][0], new ChessCoor(4, 0));


        //White pieces
        for(int X = 0; X < 8; X++){
            board[X][6] = new Pawn(PieceColor.WHITE);
            PieceMap.put(board[X][6], new ChessCoor(X, 6));
        }

        board[0][7] = new Rook(PieceColor.WHITE);
        PieceMap.put(board[0][7], new ChessCoor(0, 7));
        board[7][7] = new Rook(PieceColor.WHITE);
        PieceMap.put(board[7][7], new ChessCoor(7, 7));

        board[1][7] = new Knight(PieceColor.WHITE);
        PieceMap.put(board[1][7], new ChessCoor(1, 7));
        board[6][7] = new Knight(PieceColor.WHITE);
        PieceMap.put(board[6][7], new ChessCoor(6, 7));

        board[2][7] = new Bishop(PieceColor.WHITE);
        PieceMap.put(board[2][7], new ChessCoor(2, 7));
        board[5][7] = new Bishop(PieceColor.WHITE);
        PieceMap.put(board[5][7], new ChessCoor(5, 7));

        board[3][7] = new Queen(PieceColor.WHITE);
        PieceMap.put(board[3][7], new ChessCoor(3, 7));
        board[4][7] = new King(PieceColor.WHITE);
        PieceMap.put(board[4][7], new ChessCoor(4, 7));

        
        
    }

    

    
    public boolean Move(ChessCoor AinitialCoor, ChessCoor ANewCoor){
        //Defenitly a brain dead method, but this uses dependency injection so that there can be a (HasMoved) attribute
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
                //Update piece Map
                PieceMap.put(board[initialCoor.getX()][initialCoor.getY()], NewCoor);
                PieceMap.remove(board[NewCoor.getX()][NewCoor.getY()]);


                board[NewCoor.getX()][NewCoor.getY()] = board[initialCoor.getX()][initialCoor.getY()];
                board[initialCoor.getX()][initialCoor.getY()] = null;

                int YCoor;
                if(TurnColor == PieceColor.WHITE){
                    YCoor = 7;
                }else{
                    YCoor = 0;
                }

                PieceMap.put(board[7][YCoor], new ChessCoor(5, YCoor));

                board[5][YCoor] = board[7][YCoor];
                board[7][YCoor] = null;

                NextTurn();
                return true;
            }

            if(isLongCastleMove(initialCoor, NewCoor)){
                //Update PieceMap
                PieceMap.put(board[initialCoor.getX()][initialCoor.getY()], NewCoor);
                PieceMap.remove(board[NewCoor.getX()][NewCoor.getY()]);


                board[NewCoor.getX()][NewCoor.getY()] = board[initialCoor.getX()][initialCoor.getY()];
                board[initialCoor.getX()][initialCoor.getY()] = null;

                int YCoor;
                if(TurnColor == PieceColor.WHITE){
                    YCoor = 7;
                }else{
                    YCoor = 0;
                }

                PieceMap.put(board[0][YCoor], new ChessCoor(3, YCoor));

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
            
            //Update PieceMap
            PieceMap.put(board[initialCoor.getX()][initialCoor.getY()], NewCoor);
            PieceMap.remove(board[NewCoor.getX()][NewCoor.getY()]);
            
            //Actual Moving
            board[NewCoor.getX()][NewCoor.getY()] = board[initialCoor.getX()][initialCoor.getY()];
            board[initialCoor.getX()][initialCoor.getY()] = null;

            
            

            //In case a pawn must be promoted
            ChessCoor PawnPromotion = pawMustBePromoted();
            if(PawnPromotion != null){
                ChessPiece promotedPiece = myPromotionEvent.doPromotionEvent(TurnColor);

                //Update PieceMap
                PieceMap.remove(board[PawnPromotion.getX()][PawnPromotion.getY()]);
                PieceMap.put(promotedPiece, PawnPromotion);

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

        futureBoard.board[NewCoor.getX()][NewCoor.getY()] = futureBoard.board[initialCoor.getX()][initialCoor.getY()];
        futureBoard.board[initialCoor.getX()][initialCoor.getY()] = null;

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

}




