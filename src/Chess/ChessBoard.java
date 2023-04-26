package Chess;



import Chess.Piece.*;
import Chess.Util.*;



public class ChessBoard {
    //TODO : implement this as where all pieces in a game is stored.

    PieceColor TurnColor = PieceColor.WHITE;

    public void NextTurn(){
        if(TurnColor == PieceColor.WHITE){
            TurnColor = PieceColor.BLACK;
        }else{
            TurnColor = PieceColor.WHITE;
        }
    }


    public ChessPiece[][] board = new ChessPiece[8][8];

    
    ChessBoard(){
        SetToDefaultPosition();
    }


    //Constructor for copying data of another chessBoard
    public ChessBoard(ChessBoard copyChessBoard){

        for(int X = 0; X < 8; X++){
            for(int Y = 0; Y < 8; Y++){
                this.board[X][Y] = copyChessBoard.board[X][Y];
            }
        }

        this.TurnColor = copyChessBoard.TurnColor;
        
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

        
        
    }


    public boolean Move(ChessCoor initialCoor, ChessCoor NewCoor){
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
        
        
        //Actual Moving
        board[NewCoor.getX()][NewCoor.getY()] = board[initialCoor.getX()][initialCoor.getY()];
        board[initialCoor.getX()][initialCoor.getY()] = null;


        NextTurn();

        return true;
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

        futureBoard.board[NewCoor.getX()][NewCoor.getY()] = futureBoard.board[initialCoor.getX()][initialCoor.getY()];
        futureBoard.board[initialCoor.getX()][initialCoor.getY()] = null;

        futureBoard.NextTurn();

        return futureBoard;
    }

    public ChessPiece peekPieceAt(int CoorX, int CoorY){
        return board[CoorX][CoorY];  
    }

    public boolean isCheckMated(){
        // TODO : HANDLE POSSIBLE EXCEPTIONS !!!!!

        ChessCoor KingCoors = KingCoords()[0];

        King CurrentKing = (King) board[KingCoors.getX()][KingCoors.getY()];

        ChessCoor OtherKingCoors = KingCoords()[1];

        King OtherCurrentKing = (King) board[OtherKingCoors.getX()][OtherKingCoors.getY()];

        return CurrentKing.isCheckMated(this, KingCoors) || OtherCurrentKing.isCheckMated(this, OtherKingCoors);
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



    public boolean canShortCastle(){
        return false;
    }

    public boolean canLongCastle(){
        return false;
    }

}


