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

        int RecursionDepth = 2;
        return RecursiveGeneration(currGame, RecursionDepth);

        // return GenerateRandom(currGame);
    }

    private ChessCoor[] RecursiveGeneration(Game currGame, int depth){
        ChessBoard currBoard = currGame.currentBoard;
        PieceColor currentColor = currBoard.TurnColor;

        ArrayList<ChessCoor[]> possiblePairMoves = generatePosPairMoves(currBoard, currentColor);
        HashMap< ChessCoor[], Integer> EvaluationMap = new HashMap<>();

        
        for(ChessCoor[] CurrPair : possiblePairMoves){
            Game GameInst = new Game(currGame);
            GameInst.Move(CurrPair[0],CurrPair[1]);

            if(GameInst.currentBoard.isDraw()){
                EvaluationMap.put(CurrPair,0);
                continue;
            }

            if(GameInst.currentBoard.isCheckMated()){
                EvaluationMap.put(CurrPair, EvaluatePosition(GameInst));
                continue;
            }

            EvaluationMap.put(CurrPair, RecursiveEvaluation(GameInst, depth-1));
        }

        ChessCoor[] HighestScoreCoor = null;
        int ScoreOfHighest = 0;

        //Use Linear method for optimization later
        // for(ChessCoor[] CurrPair : possiblePairMoves){

        //     if(HighestScoreCoor == null){
        //         HighestScoreCoor = CurrPair;
        //         ScoreOfHighest = EvaluationMap.get(CurrPair);
        //         continue;
        //     }

        //     if(EvaluationMap.get(CurrPair) > ScoreOfHighest){
        //         HighestScoreCoor = CurrPair;
        //         ScoreOfHighest = EvaluationMap.get(CurrPair);
        //     }
        // }


        //This randomizes order of checking thie highest value
        while(!possiblePairMoves.isEmpty()){
            Random rand = new Random();
            int RandNum = rand.nextInt(0,possiblePairMoves.size());

            ChessCoor[] CurrPair = possiblePairMoves.get(RandNum);

            if(HighestScoreCoor == null){
                HighestScoreCoor = CurrPair;
                ScoreOfHighest = EvaluationMap.get(CurrPair);
                continue;
            }

            if(EvaluationMap.get(CurrPair) > ScoreOfHighest){
                HighestScoreCoor = CurrPair;
                ScoreOfHighest = EvaluationMap.get(CurrPair);
            }

            System.out.println(EvaluationMap.get(CurrPair));

            possiblePairMoves.remove(RandNum);
        }

        

        return HighestScoreCoor;
    }

    // This will return the score of the worst case 
    private int RecursiveEvaluation(Game currGame, int depth){

        //TODO : Implement Alpha beta pruning

        // TODO : Refactor by renaming highest to lowest naming

        ChessBoard currBoard = currGame.currentBoard;
        PieceColor currentColor = currBoard.TurnColor;

        ArrayList<ChessCoor[]> possiblePairMoves = generatePosPairMoves(currBoard, currentColor);

        // BASE CASE
        if(depth <= 0){
            ChessCoor[] HighestScoreCoors = null;
            int scoreOfHighest = 0;


            for(ChessCoor[] currCoors : possiblePairMoves){
                Game testGame = new Game(currGame);
                testGame.Move( currCoors[0], currCoors[1]);
                int currentEval = EvaluatePosition(currGame);

                if(HighestScoreCoors == null){
                    HighestScoreCoors = currCoors;
                    scoreOfHighest = currentEval;
                }

                if(currentEval < scoreOfHighest){
                    HighestScoreCoors = currCoors;
                    scoreOfHighest = currentEval;
                }
            }

            return scoreOfHighest;

        }

        int LowestNum = Integer.MAX_VALUE;

        for(ChessCoor[] CurrPair : possiblePairMoves){
            Game GameInst = new Game(currGame);
            GameInst.Move(CurrPair[0],CurrPair[1]);

            

            if(GameInst.currentBoard.isDraw()){
                if(0 < LowestNum){
                    LowestNum = 0;
                }
                continue;
            }

            if(GameInst.currentBoard.isCheckMated()){
                int currentEval = EvaluatePosition(GameInst);
                if(currentEval < LowestNum){
                    LowestNum = currentEval;
                }
                continue;
            }


            int currentEval = RecursiveEvaluation(GameInst, depth-1);
            if(currentEval < LowestNum){
                LowestNum = currentEval;
            }
        }


        return LowestNum;
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
    private int EvaluatePosition(Game thisGame){
        ChessBoard thisBoard = thisGame.currentBoard;
        int Score = 0;
        int sign = 1;

        if(thisGame.getCurrentTurn() == BOTColor){
            sign = -1;
        }

        if(thisBoard.isDraw()){
            return 0;
        }

        if(thisBoard.isCheckMated() && thisBoard.TurnColor == BOTColor){
            return Integer.MIN_VALUE;
        }

        if(thisBoard.isCheckMated() && thisBoard.TurnColor != BOTColor){
            return Integer.MAX_VALUE;
        }

        //TODO : Generate weighted scores of pieces on board

        int BoardPiecesBonus = 0;




        //TODO : Generate bonus for capture moves, and capture moves where captured piece is larger value


        //TODO : Generate small punishment for hanging pieces


        return Score*sign;
    }

}
