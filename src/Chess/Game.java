package Chess;

import java.util.Stack;

import Chess.Util.ChessCoor;

//TODO : REMOVE THIS MIDDLE MAN CLASS

public class Game {
    //TODO : Implement a Chess game, where this will store all data related to a occuring chess game
    //THIS WILL ENCAPSULATE A WHOLE GAME. AND WILL ONLY ALLOW MOVES THROUGH THIS CLASS

    Stack<ChessBoard> BoardHistory = new Stack<>();


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

}

