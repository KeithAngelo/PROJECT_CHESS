package Chess;

import java.util.Stack;

import Chess.Util.*;


public class Game {
    //TODO : Implement a Chess game, where this will store all data related to a occuring chess game
    //THIS WILL ENCAPSULATE A WHOLE GAME. AND WILL ONLY ALLOW MOVES THROUGH THIS CLASS

    Stack<ChessBoard> BoardHistory = new Stack<>();

    WinEvent currentWinEvent;
    CheckEvent currentCheckEvent;


    ChessBoard currentBoard = new ChessBoard();

    Game(){
        BoardHistory.push(currentBoard);
    }
    
//Implement all public methods of chessboard, but for every update, add to board history

    public boolean Move(ChessCoor initialCoor, ChessCoor NewCoor){
        if(currentBoard.Move(initialCoor, NewCoor)){
            BoardHistory.push(currentBoard);
            return true;
        }
        return false;
    }

    public boolean Revert(){
        return currentBoard.Revert();
    }

    public boolean ResetGame(){
        currentBoard.SetToDefaultPosition();
        BoardHistory.clear();
        BoardHistory.push(currentBoard);
        return true;
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
        currentCheckEvent.doCheckEvent(ColorBeingChecked);
    }

}
