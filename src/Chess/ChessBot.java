package Chess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
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

        long start = System.nanoTime();

        int RecursionDepth = 3;
        ChessCoor[] output = RecursiveGeneration(currGame, RecursionDepth);

        long duration = (System.nanoTime() - start)/1000000;

        System.out.println("Time : "+duration+"ms");
        return output;

        // return GenerateRandom(currGame);
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

            if(EvaluationMap.get(CurrPair) > ScoreOfHighest){ // Operation of determining which move will be filtered
                HighestScoreCoor = CurrPair;
                ScoreOfHighest = EvaluationMap.get(CurrPair);
            }

            System.out.printf("Score is : %d init: X%d Y%d  fin: X%d Y%d\n",EvaluationMap.get(CurrPair), CurrPair[0].getX(), CurrPair[0].getY() , CurrPair[1].getX(), CurrPair[1].getY());

            possiblePairMoves.remove(RandNum);
        }

        
        System.out.printf("FINAL is : %d init: X%d Y%d \n", ScoreOfHighest, HighestScoreCoor[0].getX(), HighestScoreCoor[0].getY());
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
            Integer scoreOfHighest = 0;


            for(ChessCoor[] currCoors : possiblePairMoves){
                Game testGame = new Game(currGame);
                testGame.Move( currCoors[0], currCoors[1]);
                int currentEval = EvaluatePosition(currGame);

                if(HighestScoreCoors == null){
                    HighestScoreCoors = currCoors;
                    scoreOfHighest = currentEval;
                }

                if(OperationLogic(currentEval, scoreOfHighest, currentColor)){
                    HighestScoreCoors = currCoors;
                    scoreOfHighest = currentEval;
                }
            }

            return scoreOfHighest;

        }

        Integer LowestNum = null;

        for(ChessCoor[] CurrPair : possiblePairMoves){
            Game GameInst = new Game(currGame);
            GameInst.Move(CurrPair[0],CurrPair[1]);

            // PieceColor newColor = GameInst.getCurrentTurn();

            if(LowestNum == null){
                LowestNum = EvaluatePosition(GameInst);
                continue;
            }

            

            if(GameInst.currentBoard.isDraw()){
                if(OperationLogic(0, LowestNum, currentColor)){
                    LowestNum = 0;
                }
                continue;
            }

            if(GameInst.currentBoard.isCheckMated()){
                int currentEval = EvaluatePosition(GameInst);
                if(OperationLogic(currentEval, LowestNum, currentColor)){
                    LowestNum = currentEval;
                }
                continue;
            }


            int currentEval = RecursiveEvaluation(GameInst, depth-1);
            if(OperationLogic(currentEval, LowestNum, currentColor)){
                LowestNum = currentEval;
            }
        }


        return LowestNum;
    }

    private boolean OperationLogic(int num1, int num2, PieceColor currColor){
        if(currColor != BOTColor){
            return num1 < num2;
        }else{
            return num1 > num2;
        }
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

        int PiecesScore = 0;
        int PiecesScoreWeight = 5;


        if(thisBoard.isDraw()){
            return 0;
        }


        if(thisBoard.isCheckMated() && thisGame.getCurrentTurn() != BOTColor){
            return Integer.MAX_VALUE;
        }

        if(thisBoard.isCheckMated() && thisGame.getCurrentTurn() == BOTColor){
            return Integer.MIN_VALUE;
        }
        

        //TODO : Generate weighted scores of pieces on board

        int BoardPiecesBonus = 0;
        int OpponentBoardScore = 0;

        int CaptureScore = 0;
        int CaptureWeight = 15;

        for(Map.Entry<ChessPiece, ChessCoor> entry : thisBoard.PieceMap.entrySet()){

            ChessPiece currPiece = entry.getKey();
            if(currPiece.getColor() == BOTColor){

                //Add to Weighted Score
                BoardPiecesBonus = BoardPiecesBonus + currPiece.getType().getWeight();

                //Capture Score
                ChessPiece PreviousPiece = thisGame.BoardHistory.get(1).peekPieceAt(entry.getValue().getX(),entry.getValue().getY());

                if(PreviousPiece == null){
                    continue;
                }

                if(PreviousPiece.getColor() == BOTColor){
                    continue;
                }

                CaptureScore = 14 + CaptureScore + (PreviousPiece.getType().getWeight() - currPiece.getType().getWeight());
                
            }

            //Check if opponent has eaten bot piece
            if(currPiece.getColor() != BOTColor){

                //Add to weighted Score
                OpponentBoardScore = OpponentBoardScore + currPiece.getType().getWeight();

                //Capture Score
                ChessPiece PreviousPiece = thisGame.BoardHistory.get(1).peekPieceAt(entry.getValue().getX(),entry.getValue().getY());

                if(PreviousPiece == null){
                    continue;
                }

                if(PreviousPiece.getColor() != BOTColor){
                    continue;
                }

                CaptureScore = CaptureScore + (-1 * (14 + PreviousPiece.getType().getWeight() - currPiece.getType().getWeight()));

            }
        }



        //TODO : Generate small punishment for hanging pieces

        

        PiecesScore = BoardPiecesBonus-OpponentBoardScore;
        // System.out.println("PiecesScore is "+PiecesScore);
        

        return (PiecesScore*PiecesScoreWeight) + (CaptureScore*CaptureWeight);
    }

}
