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

        int RecursionDepth = 6;
        ChessCoor[] output = RecursiveGeneration(currGame, RecursionDepth);

        long duration = (System.nanoTime() - start)/1000000;

        System.out.println("Time : "+duration+"ms");
        return output;

        // return GenerateRandom(currGame);
    }

    private ChessCoor[] RecursiveGeneration(Game currGame, int depth){
        ChessBoard currBoard = currGame.currentBoard;
        PieceColor currentColor = currBoard.TurnColor;

        ArrayList<ChessCoor[]> ListPossibleMoves = generatePosPairMoves(currBoard, currentColor);


        int HighestScore = Integer.MIN_VALUE;
        ChessCoor[] MaxCoors = null;

        boolean isMax = false;

        for(ChessCoor[] CoorPair : ListPossibleMoves){

            Game newGame = new Game(currGame);
            newGame.Move(CoorPair[0],CoorPair[1]);


            int newEval = RecursiveEvaluation(newGame, depth-1, isMax);

            if(newEval > HighestScore){
                HighestScore = newEval;
                MaxCoors = CoorPair;
            }


        }

        
        return MaxCoors;
    }
 
    // This will return the score of the worst case 
    private int RecursiveEvaluation(Game currGame, int depth, boolean isMaximizing){

        //TODO : Implement Alpha beta pruning

        // TODO : Refactor by renaming highest to lowest naming

        ChessBoard currBoard = currGame.currentBoard;
        PieceColor currentColor = currBoard.TurnColor;

        if(depth <= 0){
            return EvaluatePosition(currGame);
        }

        ArrayList<ChessCoor[]> ListPossibleMoves = generatePosPairMoves(currBoard, currentColor);

        for(ChessCoor[] CoorPair : ListPossibleMoves){

            Game newGame = new Game(currGame);
            newGame.Move(CoorPair[0],CoorPair[1]);

            if(newGame.currentBoard.isCheckMated()){
                return EvaluatePosition(newGame);
            }

            if(newGame.currentBoard.isDraw()){
                return 0;
            }

            if(isMaximizing){
                int minValue = Integer.MIN_VALUE;

                int ChildScore = RecursiveEvaluation(newGame, depth-1, false);
                if(ChildScore > minValue){
                    minValue = ChildScore;
                }

                return minValue;


            }else{
                int maxValue = Integer.MAX_VALUE;

                int ChildScore = RecursiveEvaluation(newGame, depth-1, true);

                if(ChildScore < maxValue){
                    maxValue = ChildScore;
                }

                return maxValue;
            }

        }


        // i dont think this part of the code is reachable
        throw new UnknownError();

    }
    
    //If positive num, it is good for the bot color, and vice versa
    public int EvaluatePosition(Game thisGame){
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

}
