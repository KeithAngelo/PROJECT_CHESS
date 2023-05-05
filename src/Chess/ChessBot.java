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



    int MovesEvaluated = 0;
    int MovesTraversed = 0;
    int MovesPruned = 0;
    int FinalEvaluation = 0;
    int SearchCalls = 0;
    //This will return an array of 2 ChessCoor.
    //Index Zero is initial Coor, index One is new Coor
    public ChessCoor[] GenerateMove(Game currGame){
        MovesEvaluated = 0;
        MovesTraversed = 0;
        MovesPruned = 0;
        FinalEvaluation = 0;
        SearchCalls = 0;

        long start = System.nanoTime();

        int RecursionDepth = 3;
        ChessCoor[] output = RecursiveGeneration(currGame, RecursionDepth);

        long duration = (System.nanoTime() - start)/1000000;

        System.out.println("Time : "+duration+"ms");
        System.out.println("Moves Evaluated : "+MovesEvaluated);
        System.out.println("Moves Traversed : "+MovesTraversed);
        System.out.println("Final Evaluation : "+FinalEvaluation);
        System.out.println("Capture Evaluations : "+SearchCalls);
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


            int newEval = RecursiveEvaluation(newGame, depth-1, isMax, Integer.MIN_VALUE, Integer.MAX_VALUE, System.nanoTime());

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
    private int RecursiveEvaluation(Game currGame, int depth, boolean isMaximizing, int alpha, int beta , long TimeStart){

        //TODO : Implement Alpha beta pruning

        // TODO : Refactor by renaming highest to lowest naming

        ChessBoard currBoard = currGame.currentBoard;
        PieceColor currentColor = currBoard.TurnColor;

        if(depth <= 0){
            if(currGame.currentBoard.PreviousIsCapture && (System.nanoTime() - TimeStart)/1000000 < 70000){
                return SearchAllCaptures(currGame, isMaximizing, alpha, beta, System.nanoTime());
            }
            return heavyEvaluation(currGame);
        }

        LinkedList<ChessCoor[]> ListPossibleMoves = generatePosPairMoves(currBoard, currentColor);
        MovesTraversed = MovesTraversed + ListPossibleMoves.size();
        
        if(isMaximizing){
            int MaxValue = Integer.MIN_VALUE;

            for(ChessCoor[] CoorPair : ListPossibleMoves){

                Game newGame = new Game(currGame);
                newGame.Move(CoorPair[0],CoorPair[1]);
    
                if(newGame.currentBoard.isCheckMated()){
                    return heavyEvaluation(newGame);
                }
    
                if(newGame.currentBoard.isDraw()){
                    return 0;
                }
                int ChildScore = RecursiveEvaluation(newGame, depth-1, false, alpha, beta, TimeStart);
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
                    return heavyEvaluation(newGame);
                }
    
                if(newGame.currentBoard.isDraw()){
                    return 0;
                }
                int ChildScore = RecursiveEvaluation(newGame, depth-1, true, alpha, beta, TimeStart);
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

    private int SearchAllCaptures(Game currGame, boolean isMaximizing, int alpha, int beta, long timeStart){
        
        ChessBoard currBoard = currGame.currentBoard;
        PieceColor currentColor = currBoard.TurnColor;

        if(!currGame.currentBoard.PreviousIsCapture){
            SearchCalls++;
            return heavyEvaluation(currGame);
        }

        if((System.nanoTime() - timeStart)/1000000 > 100){
            SearchCalls++;
            System.out.println("SEARCH STOPPED");
            return heavyEvaluation(currGame);
        }

        LinkedList<ChessCoor[]> ListPossibleMoves = generatePosPairMoves(currBoard, currentColor);
        MovesTraversed = MovesTraversed + ListPossibleMoves.size();
        
        if(isMaximizing){
            int MaxValue = Integer.MIN_VALUE;

            for(ChessCoor[] CoorPair : ListPossibleMoves){

                Game newGame = new Game(currGame);
                newGame.Move(CoorPair[0],CoorPair[1]);
    
                if(newGame.currentBoard.isCheckMated()){
                    return heavyEvaluation(newGame);
                }
    
                if(newGame.currentBoard.isDraw()){
                    return 0;
                }
                int ChildScore = SearchAllCaptures(newGame,  false, alpha, beta, timeStart);
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
                    return heavyEvaluation(newGame);
                }
    
                if(newGame.currentBoard.isDraw()){
                    return 0;
                }
                int ChildScore = SearchAllCaptures(newGame, true, alpha, beta, timeStart);
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
    int ControlScore = 0;
    private int heavyEvaluation(Game thisGame){
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
        
        int HangingScore = 0;
        int HangingWeight  = 30;

        int AttackScore = 0;
        int AttackWeight = 30;

        ControlScore = 0;
        int ControlWeight = 1;

        int pieceFactor = 0;
        int pieceFactorWeight = 1;

        for(int square = 0; square < 64 ; square++){
            ChessCoor currCoor = toCoor(square);
            int X = currCoor.getX();
            int Y = currCoor.getY();

            ChessPiece currPiece = thisBoard.board[X][Y];
            if(currPiece == null){
                continue;
            }
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
                CaptureScore = CaptureScore + (8 + PreviousPiece.getType().getWeight() - currPiece.getType().getWeight());
                

                int protectionScore = 0;
                for(PieceType type : PiecesDefending(thisBoard, currCoor)){
                    protectionScore = protectionScore + type.getWeight();
                }

                HangingScore =  protectionScore - currPiece.getType().getWeight();
                
                int threatenScore = 0;
                for(PieceType type : PiecesAttacking(thisBoard, currCoor)){
                    threatenScore = threatenScore + (10 - type.getWeight());
                }

                AttackScore = AttackScore - (currPiece.getType().getWeight()*threatenScore );

                pieceFactor = pieceFactor + currPiece.getMapColorScore(currCoor, thisBoard.numberOfPieces);
                
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
                CaptureScore = CaptureScore + (-1 * (8 + PreviousPiece.getType().getWeight() - currPiece.getType().getWeight()));

                // int protectionScore = 0;
                // for(PieceType type : PiecesDefending(thisBoard, currCoor)){
                //     protectionScore = protectionScore + type.getWeight();
                // }

                // HangingScore =  protectionScore + currPiece.getType().getWeight();
                
                // int threatenScore = 0;
                // for(PieceType type : PiecesAttacking(thisBoard, currCoor)){
                //     threatenScore = threatenScore + (10 - type.getWeight());
                // }

                // AttackScore = AttackScore + (currPiece.getType().getWeight()*threatenScore );

                pieceFactor = pieceFactor - currPiece.getMapColorScore(currCoor, thisBoard.numberOfPieces);

            }



        }
        // System.out.println("-----------------------");


        //TODO : Generate small punishment for hanging pieces
        //Add a multiplier for the hanging piece weight

        

        PiecesScore = BoardPiecesBonus-OpponentBoardScore;
        // System.out.println("PiecesScore is "+PiecesScore);
        

        return (PiecesScore*PiecesScoreWeight) + (CaptureScore*CaptureWeight) + (HangingScore*HangingWeight) + (AttackScore*AttackWeight) + (ControlWeight*ControlScore) + (pieceFactor*pieceFactorWeight);
    }

    private ArrayList<PieceType> PiecesAttacking(ChessBoard curBoard, ChessCoor pieceCoor){
        ArrayList<PieceType> output = new ArrayList<>();
        ChessPiece currPiece = curBoard.board[pieceCoor.getX()][pieceCoor.getY()];

        for(int n = 0; n < 64; n++){
            ChessCoor Checkcoor = toCoor(n);
            ChessPiece newPiece = curBoard.board[Checkcoor.getX()][Checkcoor.getY()];

            if(newPiece == null || (newPiece.getColor() == currPiece.getColor())){
                continue;
            }

            if(pieceCoor.isContainedIn(newPiece.GetControlledSquares(curBoard, Checkcoor))){
                output.add(newPiece.getType());
            }

        }
        return output;

    }

    private ArrayList<PieceType> PiecesDefending(ChessBoard curBoard, ChessCoor pieceCoor){
        ArrayList<PieceType> output = new ArrayList<>();
        ChessPiece currPiece = curBoard.board[pieceCoor.getX()][pieceCoor.getY()];

        for(int n = 0; n < 64; n++){
            ChessCoor Checkcoor = toCoor(n);
            ChessPiece newPiece = curBoard.board[Checkcoor.getX()][Checkcoor.getY()];

            if(newPiece == null || (newPiece.getColor() != currPiece.getColor())){
                continue;
            }

            if(pieceCoor.isContainedIn(newPiece.GetControlledSquares(curBoard, Checkcoor))){
                output.add(newPiece.getType());
            }

        }
        return output;
    }

    private LinkedList<ChessCoor[]> generatePosPairMoves(ChessBoard currBoard, PieceColor currentColor){
        LinkedList<ChessCoor[]> possiblePairMoves = new LinkedList<>();
        

        for(int initX = 0; initX < 8; initX++){
            for(int initY = 0; initY < 8; initY++){
                ChessPiece currPiece = currBoard.peekPieceAt(initX, initY);
                ChessCoor currCoor = new ChessCoor(initX, initY);
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

                //In theory this should be faster, but its way slower, and i dont know why

                // ChessPiece.iterateAllPossibleMoves(currCoor, (X, Y)-> {
                //     ChessCoor NewCoor = new ChessCoor(X, Y);
                //     ChessBoard testBoard = new ChessBoard(currBoard);  
                //     if(testBoard.Move(currCoor, NewCoor)){
                //         ChessCoor[] pairOfCoors = new ChessCoor[2];
                //         pairOfCoors[0] = currCoor;
                //         pairOfCoors[1] = NewCoor;

                //         if(testBoard.PreviousIsCapture || testBoard.isCheckMated()){
                //             possiblePairMoves.push(pairOfCoors);
                //         }else{
                //             possiblePairMoves.add(pairOfCoors);
                //         }
                //     }
                // });

            }
        }

        return possiblePairMoves;
    }

    //this method is disgusting
    private LinkedList<ChessCoor> PiecePossibleMoves(ChessBoard myBoard, ChessCoor initCoor){
        LinkedList<ChessCoor> possMoves = new LinkedList<>();
        ChessPiece currPiece = myBoard.peekPieceAt(initCoor.getX(), initCoor.getY());
        ArrayList<ChessCoor> possibleMoves = currPiece.GetPotentialMoves(myBoard, initCoor);
        for(ChessCoor newCoor : possibleMoves){
            ChessBoard testBoard = new ChessBoard(myBoard);      
                if(testBoard.Move(initCoor, newCoor)){
                    if(testBoard.PreviousIsCapture){
                        possMoves.push(newCoor);
                    }else{
                        possMoves.add(newCoor);
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

    private ChessCoor toCoor(int number){
        int Y = number % 8;
        int X = (number - Y) / 8;

        return new ChessCoor(X, Y);
    }


}
