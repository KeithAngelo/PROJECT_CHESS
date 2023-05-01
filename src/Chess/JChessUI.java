package Chess;

/* Please Make Sure that "Media" Folder containing icons 
 * for Chess pieces can be accessed by Java.
 * 
 * Icons should follow the naming convention  
 *  *Piece type(All caps)* + "_" + *Color (All caps)* + ".png"
 * 
 * Example : file containing the king icon should be named:
 * 
 * KING_WHITE.png
 * 
 * 
 * 
 */

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.plaf.synth.SynthSeparatorUI;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import Chess.Piece.ChessPiece;
import Chess.Util.*;


public class JChessUI extends JPanel{


    private ChessCoor SelectedSquare; //Coordinate of selected square. Make this null if no selected square

    final private int Dimension;
    PieceColor PlayerColor = PieceColor.WHITE; //Color of player for easier reading. Pieces of this color Should be at the bottom
    /* Default player color is white unless specified */

    Color WhiteSquares = new Color(0xf3fff0);
    Color DarkSquares = new Color(0x8fbd84);
    Color SelectedBorderColor = new Color(0x008800);
    Color AllowedMoveBorderColor = new Color(0xfcba03);

    private Border SelectedBorder = BorderFactory.createLineBorder(SelectedBorderColor,3);
    private Border AllowedMoveBorder = BorderFactory.createLineBorder(AllowedMoveBorderColor,2);

    boolean GameIsFinished = false;

    boolean AgainstBot = false;
    ChessBot myBot;
    
    //DEPENDENCY INJECTION INTERFACES : 
    
    private WinEvent JCHessWinEvent;
    private MoveEvent myMoveEvent;
    private DrawEvent myDrawEvent;
    

    /* 
    TODO :
    Depending on what color the player chose, 
    render the buttons in the appropriate order, 
    so that if played black, black pieces are on the bottom, and vice versa
    */

    private Game ChessGame = new Game();
    /* Whole Game is encapsulated in ChessGame Object  */


    final private int GridDimension = 8; // DO NOT EDIT THIS, IT WILL BREAK THE GAME
    
    private ChessSquare[][] ChessGrid = new ChessSquare[GridDimension][GridDimension]; 

    class ChessSquare extends JButton{  
        final int XCoor;
        final int YCoor;
   
        ChessSquare(int XCoor, int YCoor){
            //Piece inside the ChessSquare
            ChessPiece currentPiece = ChessGame.peekChessPieceAt(XCoor, YCoor);

            PieceColor CurrentTurn = ChessGame.getCurrentTurn();

            this.XCoor = XCoor;
            this.YCoor = YCoor;


            //Add border if this button is the selected square
            if(SelectedSquare == null){
                this.setBorder(null);
            }else{
                if((XCoor == SelectedSquare.getX()) && (YCoor == SelectedSquare.getY())){
                    this.setBorder(SelectedBorder);
                }
                //Readability is gone hahahahahahahahahahaah
                ArrayList<ChessCoor> possibleMoves = ChessGame.currentBoard.getAllowedSquaresAt(SelectedSquare);
                ChessCoor myCoor = new ChessCoor(XCoor, YCoor);
                if(myCoor.isContainedIn(possibleMoves)){
                    //TODO : Instead Of Border, draw green square in middle
                    this.setBorder(AllowedMoveBorder);
                }

            }

            

            //Generate Squares Checker pattern Coloring
            this.setBackground(WhiteSquares);
            if(XCoor % 2 ==0 ){
                if(!(YCoor % 2 ==0)){
                    this.setBackground(DarkSquares);

                }
            }else{
                if(YCoor % 2 ==0){
                    this.setBackground(DarkSquares);

                }
            }

            //set ChessIcon
            if(!(currentPiece == null)){
                this.setIcon(currentPiece.getImg());
            }


            //Actions when this button is pressed
            this.addActionListener(e -> {
                if(!GameIsFinished){
                    
                if(!AgainstBot || ChessGame.getCurrentTurn() == PlayerColor){

                    boolean makeAmove = false;

                    if(currentPiece == null){
                        makeAmove = SelectedSquare != null;
                    }else{
                        //Cases for when square should be selected 
                        if(currentPiece != null && currentPiece.getColor()==CurrentTurn){
                            SelectedSquare = new ChessCoor(XCoor, YCoor);
                        }

                    
                        // Cases for when move should be executed

                        if(SelectedSquare != null && !(currentPiece.getColor().equals(CurrentTurn)) ){
                            //TODO : add more guard clauses if necessarry
                            makeAmove = true;
                        }
                    }


                    if(makeAmove){
                        int oldX = SelectedSquare.getX();
                        int oldY = SelectedSquare.getY();
                        if(ChessGame.Move(new ChessCoor(oldX,oldY), new ChessCoor(XCoor,YCoor))){
                            if(myMoveEvent != null){
                                myMoveEvent.doMoveEvent(CurrentTurn);
                            }

                            SelectedSquare = null;
                            LoadElements();

                            //Bot Do a move
                            if(AgainstBot && !GameIsFinished){
                                ChessCoor[] botMove = new ChessCoor[2];
                                botMove = myBot.GenerateMove(ChessGame);

                                if(ChessGame.Move(botMove[0], botMove[1])){

                                    // int staticEvaluation = myBot.EvaluatePosition(ChessGame);
                                    // System.out.println("Static Evaluation : "+staticEvaluation);


                                    if(myMoveEvent != null){
                                        myMoveEvent.doMoveEvent(CurrentTurn);
                                    }
                                }
                            }

                        }
                        
                    }
                }
                }
                
                LoadElements();
            }
            );

            
        }
        
    }
    

   
    
    

    public JChessUI(int Dimension_XandY){
        Dimension = Dimension_XandY;
        Construct();
    }

    public JChessUI(int Dimension_XandY, PieceColor PlayerColor){ //If user wants to specify color
        this.PlayerColor = PlayerColor;
        Dimension = Dimension_XandY;
        Construct();
    }

    private void Construct(){ //Main body of constructor

        myBot = new ChessBot(PieceColor.getOther(PlayerColor));

        ChessGame.addDrawEvent(() -> {
            GameIsFinished = true;

            if(myDrawEvent != null){
                myDrawEvent.doDrawEvent();
            }
        });

        ChessGame.addWinEvent( ColorOfWinner -> {
            GameIsFinished = true;

            //Inversion of control, win event if ever there should be any actions outside the Chess Package
            if(JCHessWinEvent != null){
                JCHessWinEvent.doWinEvent(ColorOfWinner);
            }

        });

        this.setPreferredSize(new java.awt.Dimension(Dimension,Dimension));
        this.setBounds(0, 0, Dimension, Dimension);
        this.setLayout(new GridLayout(GridDimension, GridDimension));


        

        LoadElements();
    }

    

    private void LoadElements(){
        //Everytime there is a move or action, reload the whole board

        this.removeAll();
        if(PlayerColor == PieceColor.WHITE){
            //YCoor are ascending
            for(int Y = 0; Y < GridDimension; Y++){
                for(int X = 0; X< GridDimension; X++){
                    ChessGrid[X][Y] = new ChessSquare(X, Y);
                    this.add(ChessGrid[X][Y]);
                }
            }

        }

        if(PlayerColor == PieceColor.BLACK){
            //YCoor are descending instead of ascending
            for(int Y = 7; Y >= 0; Y--){
                for(int X = 0; X< GridDimension; X++){
                    ChessGrid[X][Y] = new ChessSquare(X, Y);
                    this.add(ChessGrid[X][Y]);
                }
            }
        }

        this.revalidate();
    }

    public void Revert(){
        GameIsFinished = false;
        
        ChessGame.Revert();
        if(AgainstBot){
            //If playing against bot, you will revert twice
            ChessGame.Revert();
        }
        LoadElements();
    }   

    public void ResetGame(){
        GameIsFinished = false;

        ChessGame.ResetGame();
        SelectedSquare = null;

        if(AgainstBot && (PlayerColor == PieceColor.BLACK)){
            if(AgainstBot && !GameIsFinished){
                ChessCoor[] botMove = new ChessCoor[2];
                botMove = myBot.GenerateMove(ChessGame);

                if(ChessGame.Move(botMove[0], botMove[1])){
                }
            }
        }

        LoadElements();
    }

    public void addWinEvent(WinEvent winEvent){
        this.JCHessWinEvent = winEvent;
    }

    public void addDrawEvent(DrawEvent thisDraw){
        this.myDrawEvent = thisDraw;
    }
    
    //Have not implemented a check event within JChessUI class because I haven't thought of a check event to implement
    //implement this if ever there is a necessary check event
    public void addCheckEvent(CheckEvent checkEvent){
        ChessGame.addCheckEvent(checkEvent);
    }

    public void addCaptureEvent(CaptureEvent thisCapture){
        ChessGame.addCaptureEvent(thisCapture);
    }

    public void addMoveEvent(MoveEvent thisMove){
        this.myMoveEvent = thisMove;
    }

    

    public void playAgainstBot(boolean play){
        this.AgainstBot = play;

        if(AgainstBot && (PlayerColor == PieceColor.BLACK)){
            if(AgainstBot && !GameIsFinished){
                ChessCoor[] botMove = new ChessCoor[2];
                botMove = myBot.GenerateMove(ChessGame);

                if(ChessGame.Move(botMove[0], botMove[1])){
                }
            }
            LoadElements();
        }

    }
}