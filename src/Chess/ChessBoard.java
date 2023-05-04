package Chess;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import Chess.ChessBoard.PieceMappings.PieceNode;
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

    // HashMap<ChessPiece, ChessCoor> PieceMap = new HashMap<>();
    PieceMappings pieceMappings = new PieceMappings();
    
    class PieceMappings{

        public ChessCoor Black_KingCoors;
        public ChessCoor White_KingCoors;

        public ChessCoor getKingCoor(PieceColor color){
            if(color == PieceColor.WHITE){
                return White_KingCoors;
            }else{
                return Black_KingCoors;
            }
        }

        LinkedList<PieceNode> BlackPieces = new LinkedList<>();
        LinkedList<PieceNode> WhitePieces = new LinkedList<>();

        PieceMappings(){}

        PieceMappings(PieceMappings copy){
            this.Black_KingCoors = copy.Black_KingCoors;
            this.White_KingCoors = copy.White_KingCoors;

            for(PieceNode currNode : copy.BlackPieces){
                this.BlackPieces.add(currNode);
            }

            for(PieceNode currNode : copy.WhitePieces){
                this.WhitePieces.add(currNode);
            }
            
        }

        class PieceNode{
            public ChessPiece piece;
            public ChessCoor coor;

            
    
            PieceNode(ChessPiece newPiece, ChessCoor newCoor){
                piece = newPiece;
                coor = newCoor;
            }
    
            public ChessPiece getPiece(){
                return piece;
            }
    
            public ChessCoor getCoor(){
                return coor;
            }
    
            public void setCoor(ChessCoor newCoor){
                coor = newCoor;
            }
    
            public void setPiece(ChessPiece newPiece){
                piece = newPiece;
            }
        }

        

        public void add(ChessPiece newPiece, ChessCoor newCoor){
            //if Checkmate / capture move, push it
            if(newPiece.getColor() == PieceColor.WHITE){
                WhitePieces.add(new PieceNode(newPiece, newCoor));
            }else{
                BlackPieces.add(new PieceNode(newPiece, newCoor));
            }

            if(newPiece.getType() == PieceType.KING){
                if(newPiece.getColor() == PieceColor.WHITE){
                    White_KingCoors = newCoor;
                }else{
                    Black_KingCoors = newCoor;
                }
            }
            
        }

        public void push(ChessPiece newPiece, ChessCoor newCoor){
            //if Checkmate / capture move, push it
            if(newPiece.getColor() == PieceColor.WHITE){
                WhitePieces.push(new PieceNode(newPiece, newCoor));
            }else{
                BlackPieces.push(new PieceNode(newPiece, newCoor));
            }

            if(newPiece.getType() == PieceType.KING){
                if(newPiece.getColor() == PieceColor.WHITE){
                    White_KingCoors = newCoor;
                }else{
                    Black_KingCoors = newCoor;
                }
            }
            
        }

        public boolean ChangeCoor(ChessPiece piece, ChessCoor newCoor){
            //TODO : Use iterator

            Iterator<PieceNode> iterator;

            if(piece.getColor() == PieceColor.WHITE){
                iterator = WhitePieces.iterator();
            }else{
                iterator = BlackPieces.iterator();
            }

            if(piece.getType() == PieceType.KING){
                if(piece.getColor() == PieceColor.WHITE){
                    White_KingCoors = newCoor;
                }else{
                    Black_KingCoors = newCoor;
                }
            }

            while(iterator.hasNext()){
                PieceNode currNode = iterator.next();

                if(currNode.piece == piece){
                    currNode.coor = newCoor;
                    return true;
                }
            }
            
            //reaching this means piece is not within the PieceMappings
            return false;
        }

        public void clear(){
            WhitePieces.clear();
            BlackPieces.clear();
        }

        public int numOfPieces(){
            return WhitePieces.size() + BlackPieces.size();
        }

        public boolean remove(ChessPiece piece){
            Iterator<PieceNode> iterator;

            if(piece.getColor() == PieceColor.WHITE){
                iterator = WhitePieces.iterator();
            }else{
                iterator = BlackPieces.iterator();
            }

            while(iterator.hasNext()){
                
                if(iterator.next().piece == piece){
                    iterator.remove();
                    return true;
                }
            }

            return false;
        }

        public int numOfPieces(PieceColor color){
            if(color == PieceColor.WHITE){
                return WhitePieces.size();
            }else{
                return BlackPieces.size();
            }
        }

        public LinkedList<PieceNode> getBlackPieces(){
            return BlackPieces;
        }

        public LinkedList<PieceNode> getWhitePieces(){
            return BlackPieces;
        }

        public LinkedList<PieceNode> getPieces(PieceColor color){
            if(color == PieceColor.WHITE){
                return WhitePieces;
            }else{
                return BlackPieces;
            }

        }
        

        //Not recommended to use this as it would need to iterate through the whole thing
        public LinkedList<PieceNode> getAllPieces(){
            LinkedList<PieceNode> output = new LinkedList<>();
            output.addAll(WhitePieces);
            output.addAll(BlackPieces);
            
            return output;
        }

        public ChessCoor getKingCoors(PieceColor KingColor){
            if(KingColor == PieceColor.WHITE){
                return White_KingCoors;
            }else{
                return Black_KingCoors;
            }
        }


    }

    

    //TODO : Implement a way to store what was the previous move
    //InitNewCoor[0] is Initial Coordinate, InitCoor[1] is new Coordinate
    ChessCoor[] InitNewCoor = new ChessCoor[2];
    boolean PreviousIsCapture = false;
    
    ChessBoard(){
        SetToDefaultPosition();
    }


    //Constructor for copying data of another chessBoard
    public ChessBoard(ChessBoard copyChessBoard){
        //Setting board Pieces
        for(PieceNode currNode : copyChessBoard.pieceMappings.BlackPieces){
            int X = currNode.coor.getX();
            int Y = currNode.coor.getY();

            this.board[X][Y] = ChessPiece.copyPiece(currNode.piece);
        }

        for(PieceNode currNode : copyChessBoard.pieceMappings.WhitePieces){
            int X = currNode.coor.getX();
            int Y = currNode.coor.getY();

            this.board[X][Y] = ChessPiece.copyPiece(currNode.piece);
        }

        //create duplicate pieceMap
        this.pieceMappings = new PieceMappings(copyChessBoard.pieceMappings);

        this.TurnColor = copyChessBoard.TurnColor;
        this.PreviousIsCapture = copyChessBoard.PreviousIsCapture;
        
    }

    public void SetToDefaultPosition(){
        //TODO : FINISH THIS, remember to make empty squares null

        pieceMappings.clear();

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
            putAtSquare(new Pawn(PieceColor.BLACK), new ChessCoor(X, 1));
        }

        putAtSquare(new Rook(PieceColor.BLACK), new ChessCoor(0, 0));
        putAtSquare(new Rook(PieceColor.BLACK), new ChessCoor(7, 0));

        putAtSquare(new Knight(PieceColor.BLACK), new ChessCoor(1, 0));
        putAtSquare(new Knight(PieceColor.BLACK), new ChessCoor(6, 0));


        putAtSquare(new Bishop(PieceColor.BLACK), new ChessCoor(2, 0));
        putAtSquare(new Bishop(PieceColor.BLACK), new ChessCoor(5, 0));

        putAtSquare(new Queen(PieceColor.BLACK), new ChessCoor(3, 0));
        putAtSquare(new King(PieceColor.BLACK), new ChessCoor(4, 0));

        //White pieces
        for(int X = 0; X < 8; X++){
            putAtSquare(new Pawn(PieceColor.WHITE), new ChessCoor(X, 6));
        }

        putAtSquare(new Rook(PieceColor.WHITE), new ChessCoor(0, 7));
        putAtSquare( new Rook(PieceColor.WHITE), new ChessCoor(7, 7));

        putAtSquare(new Knight(PieceColor.WHITE), new ChessCoor(1, 7));
        putAtSquare(new Knight(PieceColor.WHITE), new ChessCoor(6, 7));

        putAtSquare(new Bishop(PieceColor.WHITE), new ChessCoor(2, 7));
        putAtSquare(new Bishop(PieceColor.WHITE), new ChessCoor(5, 7));

        putAtSquare(new Queen(PieceColor.WHITE), new ChessCoor(3, 7));
        putAtSquare(new King(PieceColor.WHITE), new ChessCoor(4, 7));

    }

    private void putAtSquare(ChessPiece piece, ChessCoor coor){
        //this will be false if the emptry square of the new move is null
        PreviousIsCapture = board[coor.getX()][coor.getY()] != null;

        if(PreviousIsCapture){
            pieceMappings.remove(board[coor.getX()][coor.getY()]);
        }

        //updating pieceMappings
        if(!pieceMappings.ChangeCoor(piece, coor)){
            pieceMappings.add(piece,coor);
        }
        

        //Setting of piece
        board[coor.getX()][coor.getY()] = piece;
        
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
                //Move King
                putAtSquare(board[initialCoor.getX()][initialCoor.getY()], NewCoor);
                board[initialCoor.getX()][initialCoor.getY()] = null;

                int YCoor;
                if(TurnColor == PieceColor.WHITE){
                    YCoor = 7;
                }else{
                    YCoor = 0;
                }

                //Rook move
                putAtSquare(board[7][YCoor], new ChessCoor(5, YCoor));
                board[7][YCoor] = null;

                NextTurn();
                return true;
            }

            if(isLongCastleMove(initialCoor, NewCoor)){
                //Move King
                putAtSquare(board[initialCoor.getX()][initialCoor.getY()], NewCoor);
                board[initialCoor.getX()][initialCoor.getY()] = null;

                int YCoor;
                if(TurnColor == PieceColor.WHITE){
                    YCoor = 7;
                }else{
                    YCoor = 0;
                }

                //Move Rook
                putAtSquare(board[0][YCoor], new ChessCoor(3, YCoor));
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
            putAtSquare(board[initialCoor.getX()][initialCoor.getY()], NewCoor);
            board[initialCoor.getX()][initialCoor.getY()] = null;

            
            

            //In case a pawn must be promoted
            ChessCoor PawnPromotion = pawMustBePromoted();
            if(PawnPromotion != null){
                ChessPiece promotedPiece = myPromotionEvent.doPromotionEvent(TurnColor);

                putAtSquare(promotedPiece, PawnPromotion);
                PreviousIsCapture = false;
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
            futureBoard.putAtSquare(futureBoard.board[initialCoor.getX()][initialCoor.getY()], NewCoor);
            futureBoard.board[initialCoor.getX()][initialCoor.getY()] = null;

            int YCoor;
            if(TurnColor == PieceColor.WHITE){
                YCoor = 7;
            }else{
                YCoor = 0;
            }

            futureBoard.putAtSquare(futureBoard.board[7][YCoor], new ChessCoor(5, YCoor));
            futureBoard.board[7][YCoor] = null;

            futureBoard.NextTurn();
            return futureBoard;
        }

        if(futureBoard.isLongCastleMove(initialCoor, NewCoor)){
            
            futureBoard.putAtSquare(futureBoard.board[initialCoor.getX()][initialCoor.getY()], NewCoor);
            futureBoard.board[initialCoor.getX()][initialCoor.getY()] = null;

            int YCoor;
            if(TurnColor == PieceColor.WHITE){
                YCoor = 7;
            }else{
                YCoor = 0;
            }

            futureBoard.putAtSquare(futureBoard.board[0][YCoor], NewCoor);
            futureBoard.board[0][YCoor] = null;

            futureBoard.NextTurn();
            return futureBoard;
        }

        futureBoard.putAtSquare(futureBoard.board[initialCoor.getX()][initialCoor.getY()], NewCoor);
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

        ChessCoor KingCoors = pieceMappings.getKingCoor(PieceColor.getOther(TurnColor));

        King CurrentKing = (King) board[KingCoors.getX()][KingCoors.getY()];

        ChessCoor OtherKingCoors = pieceMappings.getKingCoor(TurnColor);

        King OtherCurrentKing = (King) board[OtherKingCoors.getX()][OtherKingCoors.getY()];

        return CurrentKing.isCheckMated(this, KingCoors) || OtherCurrentKing.isCheckMated(this, OtherKingCoors);
    }

    public boolean isDraw(){
        // TODO : HANDLE POSSIBLE EXCEPTIONS !!!!!

        ChessCoor KingCoors = pieceMappings.getKingCoor(TurnColor);

        King CurrentKing = (King) board[KingCoors.getX()][KingCoors.getY()];

        return CurrentKing.isDraw(this, KingCoors);
    }

    public boolean isChecked(){
        // TODO : HANDLE POSSIBLE EXCEPTIONS !!!!!

        ChessCoor KingCoors = pieceMappings.getKingCoor(PieceColor.getOther(TurnColor));

        King CurrentKing = (King) board[KingCoors.getX()][KingCoors.getY()];

        ChessCoor OtherKingCoors = pieceMappings.getKingCoor(TurnColor);

        King OtherCurrentKing = (King) board[OtherKingCoors.getX()][OtherKingCoors.getY()];

        return CurrentKing.isChecked(this, KingCoors) || OtherCurrentKing.isChecked(this, OtherKingCoors);
    }

    public boolean selfIsChecked(){
        ChessCoor KingCoors = pieceMappings.getKingCoor(PieceColor.getOther(TurnColor));
        King CurrentKing = (King) board[KingCoors.getX()][KingCoors.getY()];

        return CurrentKing.isChecked(this, KingCoors);
    }

    //returns an array with 2 coordinates of the two kings
    // private ChessCoor[] KingCoords(){
    //     ChessCoor[] pairOfKingCoords = new ChessCoor[2];
    //     for(int X = 0; X < 8; X++){
    //         for(int Y = 0; Y < 8; Y++){
    //             if(board[X][Y] == null){
    //                 continue;
    //             }

    //             if(board[X][Y].getColor() != this.TurnColor){
    //                 continue;
    //             }

    //             if(board[X][Y].getType() == PieceType.KING){
    //                 pairOfKingCoords[0] = new ChessCoor(X, Y);
    //                 X = 8;
    //                 break;
    //             }
    //         }
    //     }

    //     for(int X = 0; X < 8; X++){
    //         for(int Y = 0; Y < 8; Y++){
    //             if(board[X][Y] == null){
    //                 continue;
    //             }

    //             if(board[X][Y].getColor() == this.TurnColor){
    //                 continue;
    //             }

    //             if(board[X][Y].getType() == PieceType.KING){
    //                 pairOfKingCoords[1] = new ChessCoor(X, Y);
    //                 X = 8;
    //                 break;
    //             }
    //         }
    //     }

    //     return pairOfKingCoords;
    // }

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