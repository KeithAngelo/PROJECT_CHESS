package Chess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import Chess.Piece.ChessPiece;
import Chess.Util.*;

public class ChessBot {
    PieceColor BOTColor;

    ChessBot(PieceColor botColor){
        BOTColor = botColor;
    }

    class MovePackage{
        ChessCoor[] MovePair;
        Double moveEvaluation;


        MovePackage(ChessCoor[] MovePair, Double moveEvaluation){
            this.MovePair = MovePair;
            this.moveEvaluation = moveEvaluation;
        }
    }



    //This will return an array of 2 ChessCoor.
    //Index Zero is initial Coor, index One is new Coor
    public ChessCoor[] GenerateMove(Game currGame){

        //TODO - Complete this

        int RecursionDepth = 3;
        return RecursiveGeneration(currGame, RecursionDepth);

        // return GenerateRandom(currGame);
    }

    private ChessCoor[] RecursiveGeneration(Game currGame, int depth){
        ChessBoard currBoard = currGame.currentBoard;
        PieceColor currentColor = currBoard.TurnColor;

        ArrayList<ChessCoor[]> possiblePairMoves = generatePosPairMoves(currBoard, currentColor);
        HashMap< ChessCoor[], Double> EvaluationMap = new HashMap<>();

        
        for(ChessCoor[] CurrPair : possiblePairMoves){
            Game GameInst = new Game(currGame);
            GameInst.Move(CurrPair[0],CurrPair[1]);

            if(GameInst.currentBoard.isDraw()){
                EvaluationMap.put(CurrPair,0.0);
            }

            if(GameInst.currentBoard.isCheckMated()){
                EvaluationMap.put(CurrPair, EvaluatePosition(GameInst));
            }

            EvaluationMap.put(CurrPair, RecursiveEvaluation(GameInst, depth-1));
        }

        ChessCoor[] HighestScoreCoor = null;
        Double ScoreOfHighest = 0.0;
        for(ChessCoor[] CurrPair : possiblePairMoves){

            if(HighestScoreCoor == null){
                HighestScoreCoor = CurrPair;
                ScoreOfHighest = EvaluationMap.get(CurrPair);
                continue;
            }

            if(EvaluationMap.get(CurrPair) > ScoreOfHighest){
                HighestScoreCoor = CurrPair;
                ScoreOfHighest = EvaluationMap.get(CurrPair);
            }
        }

        return HighestScoreCoor;
    }


    private Double RecursiveEvaluation(Game currGame, int depth){

        ChessBoard currBoard = currGame.currentBoard;
        PieceColor currentColor = currBoard.TurnColor;

        ArrayList<ChessCoor[]> possiblePairMoves = generatePosPairMoves(currBoard, currentColor);

        // BASE CASE
        if(depth <= 0){
            ChessCoor[] HighestScoreCoors = null;
            Double scoreOfHighest = 0.0;


            for(ChessCoor[] currCoors : possiblePairMoves){
                Game testGame = new Game(currGame);
                testGame.Move( currCoors[0], currCoors[1]);
                Double currentEval = EvaluatePosition(currGame);

                if(HighestScoreCoors == null){
                    HighestScoreCoors = currCoors;
                    scoreOfHighest = currentEval;
                }

                if(currentEval > scoreOfHighest){
                    HighestScoreCoors = currCoors;
                    scoreOfHighest = currentEval;
                }
            }

            return scoreOfHighest;

        }

        LinkedList<Double> EvaluationList = new LinkedList<>();

        for(ChessCoor[] CurrPair : possiblePairMoves){
            Game GameInst = new Game(currGame);
            GameInst.Move(CurrPair[0],CurrPair[1]);

            if(GameInst.currentBoard.isDraw()){
                EvaluationList.add(0.0);
            }

            if(GameInst.currentBoard.isCheckMated()){
                EvaluationList.add(EvaluatePosition(GameInst));
            }

            EvaluationList.add(RecursiveEvaluation(GameInst, depth-1));
        }


        Collections.sort(EvaluationList);
        return EvaluationList.getFirst();
    }

    private ArrayList<ChessCoor[]> generatePosPairMoves(ChessBoard currBoard, PieceColor currentColor){
        ArrayList<ChessCoor[]> possiblePairMoves = new ArrayList<>();
        

        for(int initX = 0; initX < 8; initX++){
            for(int initY = 0; initY < 8; initY++){
                ChessPiece currPiece = currBoard.peekPieceAt(initX, initY);
                if( currPiece== null){
                    continue;
                }

                if(currPiece.getColor() != currentColor){
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

        return possiblePairMoves;
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
    private Double EvaluatePosition(Game thisGame){
        ChessBoard thisBoard = thisGame.currentBoard;
        Double Score = 0.0;
        Double sign = 1.0;

        if(thisGame.getCurrentTurn() != BOTColor){
            sign = -1.0;
        }

        if(thisBoard.isDraw()){
            return 0.0;
        }

        if(thisBoard.isCheckMated() && thisBoard.TurnColor != BOTColor){
            return -1000000000.0;
        }


        return Score*sign;
    }

}
