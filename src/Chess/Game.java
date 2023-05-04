package Chess;

import java.util.Stack;

import Chess.Piece.ChessPiece;
import Chess.Util.*;


public class Game {
    //TODO : Implement a Chess game, where this will store all data related to a occuring chess game
    //THIS WILL ENCAPSULATE A WHOLE GAME. AND WILL ONLY ALLOW MOVES THROUGH THIS CLASS

    Stack<ChessBoard> BoardHistory = new Stack<>();

    WinEvent currentWinEvent;
    DrawEvent currentDrawEvent;
    CheckEvent currentCheckEvent;


    ChessBoard currentBoard = new ChessBoard();

    Game(){
        BoardHistory.push(new ChessBoard(currentBoard));
    }

    Game(Game newGame){
        this.currentBoard = new ChessBoard(newGame.currentBoard);
        for(ChessBoard histBoard : newGame.BoardHistory){
            this.BoardHistory.add(histBoard);
        }
    }
    
    //Implement all public methods of chessboard, but for every update, add to board history

    public boolean Move(ChessCoor initialCoor, ChessCoor NewCoor){
        if(currentBoard.Move(initialCoor, NewCoor)){
            BoardHistory.push(new ChessBoard(currentBoard));

                if(currentBoard.isCheckMated()){
                    win(getCurrentTurn());
                    return true;
                }

                if(currentBoard.isDraw()){
                    if(currentDrawEvent !=null){
                        currentDrawEvent.doDrawEvent();
                    }
                }

                if(currentBoard.isChecked()){
                    checkEvent(getCurrentTurn());
                    return true;
                }

            
            return true;
        }
        return false;
    }

    public boolean Revert(){
        //Still might have bugs, keep out for this
        if(BoardHistory.size() < 2){
            return false;
        }
        BoardHistory.pop();
        currentBoard = new ChessBoard(BoardHistory.peek());

        return true;
    }

    public boolean ResetGame(){
        currentBoard.pieceMappings.clear();
        currentBoard.SetToDefaultPosition();
        BoardHistory.clear();
        BoardHistory.push(new ChessBoard(currentBoard));
        return true;
    }

    public ChessPiece peekChessPieceAt(int x, int y){
        return currentBoard.board[x][y];
    }

    public PieceColor getCurrentTurn(){
        return currentBoard.TurnColor;
    }

    public void addWinEvent(WinEvent winEvent){
        this.currentWinEvent = winEvent;
    }

    public void addCheckEvent(CheckEvent checkEvent){
        this.currentCheckEvent = checkEvent;
    }

    //TODO : Implement when win method should be called in this class.

    public void win(PieceColor winner){
        if(currentWinEvent != null){
            currentWinEvent.doWinEvent(winner);
        }
    }

    public void checkEvent(PieceColor ColorBeingChecked){
        if(currentCheckEvent != null){
            currentCheckEvent.doCheckEvent(ColorBeingChecked);
        }
        
    }

    public void addCaptureEvent(CaptureEvent myCapt){
        currentBoard.addCaptureEvent(myCapt);
    }

    public void addDrawEvent(DrawEvent myDraw){
        this.currentDrawEvent = myDraw;
    }

}
