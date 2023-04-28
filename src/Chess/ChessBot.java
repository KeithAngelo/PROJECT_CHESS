package Chess;

import Chess.Util.*;

public class ChessBot {
    PieceColor BOTColor;

    ChessBot(PieceColor botColor){
        BOTColor = botColor;
    }



    //This will return an array of 2 ChessCoor.
    //Index Zero is initial Coor, index One is new Coor
    public ChessCoor[] GenerateMove(ChessBoard currBoard){
        //TODO - Complete this

        ChessCoor[] generatedMove = new ChessCoor[2];


        return generatedMove;
    }

    //If positive num, it is good for the bot color, and vice versa
    private Double EvaluatePosition(ChessBoard thisBoard){
        if(thisBoard.isDraw()){
            return 0.0;
        }

        if(thisBoard.isCheckMated() && (thisBoard.TurnColor == BOTColor)){
            return Double.NEGATIVE_INFINITY;
        }

        if(thisBoard.isCheckMated() && (thisBoard.TurnColor != BOTColor)){
            return Double.POSITIVE_INFINITY;
        }

        return 0.0;
    }

}
