package Chess;

import java.util.LinkedList;

import Chess.Util.ChessCoor;

public class Game {
    //TODO : Implement a Chess game, where this will store all data related to a occuring chess game
    //THIS WILL ENCAPSULATE A WHOLE GAME. AND WILL ONLY ALLOW MOVES THROUGH THIS CLASS

    LinkedList<ChessBoard> BoardHistory = new LinkedList<>();
    //Use the "Push" and "Pop" functions so that it will act like a stack

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

    public boolean Revert(int steps){

        //TODO : Debug ; Find out why this does not work
        try{
            currentBoard = BoardHistory.get(steps);
            BoardHistory.removeFirst();
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public boolean ResetGame(){
        currentBoard.SetToDefaultPosition();
        BoardHistory.clear();
        BoardHistory.push(currentBoard);
        return true;
    }

}

