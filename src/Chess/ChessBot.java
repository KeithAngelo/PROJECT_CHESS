package Chess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import Chess.ChessBoard.PieceMappings.PieceNode;
import Chess.Piece.ChessPiece;
import Chess.Util.*;

public class ChessBot {
    PieceColor BOTColor;

    ChessBot(PieceColor botColor){
        BOTColor = botColor;
    }



    int MovesEvaluated = 0;
    int MovesTraversed = 0;
    int MovesPruned = 0;
    int FinalEvaluation = 0;
    //This will return an array of 2 ChessCoor.
    //Index Zero is initial Coor, index One is new Coor
    public ChessCoor[] GenerateMove(Game currGame){
        MovesEvaluated = 0;
        MovesTraversed = 0;
        MovesPruned = 0;
        FinalEvaluation = 0;

        long start = System.nanoTime();

        int RecursionDepth = 3;
        ChessCoor[] output = RecursiveGeneration(currGame, RecursionDepth);

        long duration = (System.nanoTime() - start)/1000000;

        System.out.println("Time : "+duration+"ms");
        System.out.println("Moves Evaluated : "+MovesEvaluated);
        System.out.println("Moves Traversed : "+MovesTraversed);
        System.out.println("Final Evaluation : "+FinalEvaluation);
        System.out.println("Moves Pruned : "+MovesPruned+"\n\n\n");
        return output;

        // return GenerateRandom(currGame);
    }

    private ChessCoor[] RecursiveGeneration(Game currGame, int depth){
        ChessBoard currBoard = currGame.currentBoard;
        PieceColor currentColor = currBoard.TurnColor;

        LinkedList<ChessCoor[]> ListPossibleMoves = generatePosPairMoves(currBoard, currentColor);
        MovesTraversed = MovesTraversed + ListPossibleMoves.size();


        int HighestScore = Integer.MIN_VALUE;
        ChessCoor[] MaxCoors = null;

        boolean isMax = false;
        HashMap<ChessCoor[], Integer> ScoreMap = new HashMap<>();

        for(ChessCoor[] CoorPair : ListPossibleMoves){

            Game newGame = new Game(currGame);
            newGame.Move(CoorPair[0],CoorPair[1]);


            int newEval = RecursiveEvaluation(newGame, depth-1, isMax, Integer.MIN_VALUE, Integer.MAX_VALUE);

            ScoreMap.put(CoorPair, newEval);

            // if(newEval >= HighestScore){
            //     HighestScore = newEval;
            //     MaxCoors = CoorPair;
            // }
        }

        //Randomize order of getting highest number
        while(!ListPossibleMoves.isEmpty()){
            Random rand = new Random();
            int index = rand.nextInt(ListPossibleMoves.size());


            ChessCoor[] CoorPair = ListPossibleMoves.get(index);
            int newEval = ScoreMap.get(CoorPair);

            if(newEval >= HighestScore){
                HighestScore = newEval;
                MaxCoors = CoorPair;
            } 

            ListPossibleMoves.remove(index);
        }
        
        FinalEvaluation = HighestScore;
        return MaxCoors;
    }
 
    // This will return the score of the worst case 
    private int RecursiveEvaluation(Game currGame, int depth, boolean isMaximizing, int alpha, int beta){

        //TODO : Implement Alpha beta pruning

        // TODO : Refactor by renaming highest to lowest naming

        ChessBoard currBoard = currGame.currentBoard;
        PieceColor currentColor = currBoard.TurnColor;

        if(depth <= 0){
            return EvaluatePosition(currGame);
        }

        LinkedList<ChessCoor[]> ListPossibleMoves = generatePosPairMoves(currBoard, currentColor);
        MovesTraversed = MovesTraversed + ListPossibleMoves.size();
        
        if(isMaximizing){
            int MaxValue = Integer.MIN_VALUE;

            for(ChessCoor[] CoorPair : ListPossibleMoves){

                Game newGame = new Game(currGame);
                newGame.Move(CoorPair[0],CoorPair[1]);
    
                if(newGame.currentBoard.isCheckMated()){
                    return EvaluatePosition(newGame);
                }
    
                if(newGame.currentBoard.isDraw()){
                    return 0;
                }
                int ChildScore = RecursiveEvaluation(newGame, depth-1, false, alpha, beta);
                if(ChildScore > MaxValue){
                    MaxValue = ChildScore;
                }

                if(MaxValue > alpha){
                    alpha = MaxValue;
                }

                if(beta <= alpha){
                    MovesPruned++;
                    break;
                }

            }

            return MaxValue;
        }else{
            int minValue = Integer.MAX_VALUE;

            for(ChessCoor[] CoorPair : ListPossibleMoves){

                Game newGame = new Game(currGame);
                newGame.Move(CoorPair[0],CoorPair[1]);
    
                if(newGame.currentBoard.isCheckMated()){
                    return EvaluatePosition(newGame);
                }
    
                if(newGame.currentBoard.isDraw()){
                    return 0;
                }
                int ChildScore = RecursiveEvaluation(newGame, depth-1, true, alpha, beta);
                if(ChildScore < minValue){
                    minValue = ChildScore;
                }

                if(minValue < beta){
                    beta = minValue;
                }

                if(beta <= alpha){
                    MovesPruned++;
                    break;
                }
    
            
            }

            
            return minValue;
            
        }
    }
    
    //If positive num, it is good for the bot color, and vice versa
    public int EvaluatePosition(Game thisGame){
        MovesEvaluated++;

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
        // System.out.println("-----------------------");

        PieceColor colorCheck = PieceColor.WHITE;
        //Switches black to white, kinda brain dead but i hate you
        for(int x = 0; x < 2; x++){
            if(x == 1){
                colorCheck = PieceColor.BLACK;
            }
            
            for(PieceNode currNode : thisBoard.pieceMappings.getPieces(colorCheck)){
                
                ChessPiece currPiece = currNode.piece;
                int X = currNode.coor.getX();
                int Y = currNode.coor.getY();
                if(currPiece.getColor() == BOTColor){

                    //Add to Weighted Score
                    BoardPiecesBonus = BoardPiecesBonus + currPiece.getType().getWeight();

                    //Capture Score
                    ChessPiece PreviousPiece = thisGame.BoardHistory.get(1).peekPieceAt(X,Y);

                    if(PreviousPiece == null){
                        continue;
                    }

                    if(PreviousPiece.getColor() == BOTColor){
                        continue;
                    }
                    CaptureScore = 14 + CaptureScore + (PreviousPiece.getType().getWeight() - currPiece.getType().getWeight());
                    
                }

                //Check if opponent has eaten bot piece
                else{

                    //Add to weighted Score
                    OpponentBoardScore = OpponentBoardScore + currPiece.getType().getWeight();

                    //Capture Score
                    ChessPiece PreviousPiece = thisGame.BoardHistory.get(1).peekPieceAt(X,Y);

                    if(PreviousPiece == null){
                        continue;
                    }

                    if(PreviousPiece.getColor() != BOTColor){
                        continue;
                    }
                    CaptureScore = CaptureScore + (-1 * (14 + PreviousPiece.getType().getWeight() - currPiece.getType().getWeight()));

                }
            }
        }



        //TODO : Generate small punishment for hanging pieces
        //Add a multiplier for the hanging piece weight

        

        PiecesScore = BoardPiecesBonus-OpponentBoardScore;
        // System.out.println("PiecesScore is "+PiecesScore);
        

        return (PiecesScore*PiecesScoreWeight) + (CaptureScore*CaptureWeight);
    }

    private LinkedList<ChessCoor[]> generatePosPairMoves(ChessBoard currBoard, PieceColor currentColor){
        LinkedList<ChessCoor[]> possiblePairMoves = new LinkedList<>();
        

        for(int initX = 0; initX < 8; initX++){
            for(int initY = 0; initY < 8; initY++){
                ChessPiece currPiece = currBoard.peekPieceAt(initX, initY);
                if( currPiece== null){
                    continue;
                }

                if(currPiece.getColor() != currentColor){
                    continue;
                }

                LinkedList<ChessCoor> possiblePieceMoves = PiecePossibleMoves(currBoard, new ChessCoor(initX, initY));

                if(possiblePieceMoves.isEmpty()){
                    continue;
                }

                for(ChessCoor newCoor : possiblePieceMoves){
                    ChessCoor[] pairOfCoors = new ChessCoor[2];
                    pairOfCoors[0] = new ChessCoor(initX , initY);
                    pairOfCoors[1] = newCoor;
                    possiblePairMoves.push(pairOfCoors);
                }
            }
        }

        return possiblePairMoves;
    }

    //this method is disgusting
    private LinkedList<ChessCoor> PiecePossibleMoves(ChessBoard myBoard, ChessCoor initCoor){
        LinkedList<ChessCoor> possMoves = new LinkedList<>();
        for(int X = 0; X < 8; X++){
            for(int Y = 0; Y < 8; Y++){
                ChessBoard testBoard = new ChessBoard(myBoard);
                if(testBoard.Move(initCoor, new ChessCoor(X, Y))){
                    possMoves.push(new ChessCoor(X, Y));
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

                LinkedList<ChessCoor> possiblePieceMoves = PiecePossibleMoves(currBoard, new ChessCoor(initX, initY));

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
