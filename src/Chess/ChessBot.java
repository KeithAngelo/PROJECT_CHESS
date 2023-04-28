package Chess;

import java.util.ArrayList;
import java.util.Random;

import Chess.Piece.ChessPiece;
import Chess.Util.*;

public class ChessBot {
    PieceColor BOTColor;

    ChessBot(PieceColor botColor){
        BOTColor = botColor;
    }



    //This will return an array of 2 ChessCoor.
    //Index Zero is initial Coor, index One is new Coor
    public ChessCoor[] GenerateMove(Game currGame){
        //TODO - Complete this

        ChessCoor[] generatedMove = new ChessCoor[2];


        return GenerateRandom(currGame);
    }

    private ChessCoor[] GenerateRandom(Game currGame){
        ChessCoor[] generatedMove = new ChessCoor[2];

        ArrayList<ChessCoor[]> possiblePairMoves = new ArrayList<>();


        ChessBoard currBoard = currGame.currentBoard;
        for(int initX = 0; initX < 8; initX++){
            for(int initY = 0; initY < 8; initY++){
                ChessPiece currPiece = currBoard.peekPieceAt(initX, initY);
                if( currPiece== null){
                    continue;
                }

                if(currPiece.getColor() != BOTColor){
                    continue;
                }

                ArrayList<ChessCoor> possiblePieceMoves = new ArrayList<>();
                possiblePieceMoves = PiecePossibleMoves(currBoard, new ChessCoor(initX, initY));

                if(possiblePieceMoves.isEmpty()){
                    continue;
                }

                for(ChessCoor newCoor : possiblePieceMoves){
                    ChessCoor[] pairOfCoors = new ChessCoor[2];
                    pairOfCoors[0] = new ChessCoor(initX , initY);
                    pairOfCoors[1] = newCoor;
                    possiblePairMoves.add(pairOfCoors);
                }
            }
        }


        //pick random coor in PossiblePairMoves

        Random rand = new Random();
        generatedMove = possiblePairMoves.get(rand.nextInt(0,possiblePairMoves.size()-1));

        return generatedMove;
    }

    //this method is disgusting
    private ArrayList<ChessCoor> PiecePossibleMoves(ChessBoard myBoard, ChessCoor initCoor){
        ArrayList<ChessCoor> possMoves = new ArrayList<>();
        for(int X = 0; X < 8; X++){
            for(int Y = 0; Y < 8; Y++){
                ChessBoard testBoard = new ChessBoard(myBoard);
                if(testBoard.Move(initCoor, new ChessCoor(X, Y))){
                    possMoves.add(new ChessCoor(X, Y));
                }
            }
        }
        return possMoves;
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
