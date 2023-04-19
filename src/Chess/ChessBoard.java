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


    ChessPiece[][] board = new ChessPiece[8][8];
    
    ChessBoard(){
        SetToDefaultPosition();
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
        if(board[initialCoor.getX()][initialCoor.getY()] == null){
            return false;
        }

        //Only moves when it is the right turn
        if(board[initialCoor.getX()][initialCoor.getY()].getColor() != TurnColor){
            return false;
        }

        //TODO : Add Precaution and stop Illegal Moves

        
        //Actual Moving
        board[NewCoor.getX()][NewCoor.getY()] = board[initialCoor.getX()][initialCoor.getY()];
        board[initialCoor.getX()][initialCoor.getY()] = null;


        NextTurn();

        return true;
    }

    public boolean isCheckMated(){
        // TODO : finish this

        return false;
    }

}
