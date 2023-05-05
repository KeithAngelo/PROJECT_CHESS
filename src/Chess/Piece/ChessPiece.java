package Chess.Piece;
import Chess.Util.*;


import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.ImageIcon;

import Chess.*;


abstract public class ChessPiece implements PieceActions{
    final PieceColor color;
    public PieceType type;
    public boolean hasMoved = false;

    public ChessPiece(PieceColor color){
        this.color = color;
    }

    public ChessPiece(ChessPiece newPiece){
        this.color = newPiece.color;
        this.type = newPiece.type;
        this.hasMoved = newPiece.hasMoved;
    }

    public ImageIcon getImg(){
        // CUSTOM FILEPATH NAME, NEED TO EDIT THE NAME BASED ON THIS ENUMS
        
        // give current path of the file
        String currentPath = System.getProperty("user.dir");
        String PIECE_PATH = currentPath + "\\Media\\" + type + "_" + color + ".png";
        // System.out.println(PIECE_PATH);
        return new ImageIcon(PIECE_PATH);
    }

    public PieceColor getColor(){
        return color;
    }

    public PieceType getType(){
        return type;
    }

    public boolean move(moveInterface myMove,ChessCoor initialCoor, ChessCoor NewCoor ){
        if(myMove.move(initialCoor, NewCoor)){
            hasMoved = true;
            return true;
        }

        return false;
    }


    // REUSABLE UTILITY METHODS : 

    //This method only checks if the square is outside the board, or if the square contains piece with same color
    boolean isValidSquare(ChessBoard currentBoard, int CoorX, int CoorY){
        if((CoorX < 0 || CoorY < 0) || (CoorX > 7 || CoorY > 7)){
            return false;
        }

        if(currentBoard.peekPieceAt(CoorX, CoorY) == null){
            return true;
        }

        if(currentBoard.peekPieceAt(CoorX, CoorY).getColor() == this.color){
            return false;
        }

        return true;
    }

    boolean isWithinBounds(ChessBoard currentBoard, int CoorX, int CoorY){
        return !( (CoorX < 0 || CoorY < 0) || (CoorX > 7 || CoorY > 7) );
    }

    public static ChessPiece getNewPiece(PieceColor color, PieceType type){
        switch(type){
            case BISHOP:
                return new Bishop(color);
            case KING:
                return new King(color);
            case KNIGHT:
                return new Knight(color);
            case PAWN:
                return new Pawn(color);
            case QUEEN:
                return new Queen(color);
            case ROOK:
                return new Rook(color);
            default:
                throw new IllegalAccessError();
            
        }
    }

    public static ChessPiece copyPiece(ChessPiece oldPiece){
        PieceType type = oldPiece.getType();

        ChessPiece newPiece;
        switch(type){
            case BISHOP:
                newPiece = new Bishop(oldPiece);
            break;
            case KING:
                newPiece = new King(oldPiece);
            break;
            case KNIGHT:
                newPiece = new Knight(oldPiece);
            break;
            case PAWN:
                newPiece = new Pawn(oldPiece);
            break;
            case QUEEN:
                newPiece = new Queen(oldPiece);
            break;
            case ROOK:
                newPiece = new Rook(oldPiece);
            break;
            default:
                throw new IllegalAccessError();
            
        }

        return newPiece;
    }


    public static LinkedList<ChessCoor> iterateAllPossibleMoves(ChessCoor initCoor, MoveFactor moveFactor){
        LinkedList<ChessCoor> allMoves = new LinkedList<>();

        //LowerRight Moves
        for(int Vector = 1; ; Vector++){
            int VerticalVector = initCoor.getY() + Vector;
            int HorizontalVector = initCoor.getX() + Vector;

            //Add Diagonal Moves
            if(VerticalVector < 8 && HorizontalVector < 8){
                allMoves.add(new ChessCoor(HorizontalVector, VerticalVector));
                if(moveFactor != null){
                    moveFactor.doAction(HorizontalVector, VerticalVector);
                }
            }

            //Add Vertical Moves
            if(VerticalVector < 8){
                allMoves.add(new ChessCoor(initCoor.getX(), VerticalVector));
                if(moveFactor != null){
                    moveFactor.doAction(initCoor.getX(), VerticalVector);
                }
            }

            if(HorizontalVector < 8){
                allMoves.add(new ChessCoor(HorizontalVector, initCoor.getY()));
                if(moveFactor != null){
                    moveFactor.doAction(HorizontalVector, initCoor.getY());
                }
            }

            if(VerticalVector > 7 && HorizontalVector > 7){
                break;
            }
        }

        //UpperLeft Moves
        for(int Vector = 1; ; Vector++){
            int VerticalVector = initCoor.getY() - Vector;
            int HorizontalVector = initCoor.getX() - Vector;

            //Add Diagonal Moves
            if(VerticalVector >= 0 && HorizontalVector >= 0){
                allMoves.add(new ChessCoor(HorizontalVector, VerticalVector));
                if(moveFactor != null){
                    moveFactor.doAction(HorizontalVector, VerticalVector);
                }
            }

            //Add Vertical Moves
            if(VerticalVector >= 0){
                allMoves.add(new ChessCoor(initCoor.getX(), VerticalVector));
                if(moveFactor != null){
                    moveFactor.doAction(initCoor.getX(), VerticalVector);
                }
            }

            if(HorizontalVector >= 0){
                allMoves.add(new ChessCoor(HorizontalVector, initCoor.getY()));
                if(moveFactor != null){
                    moveFactor.doAction(HorizontalVector, initCoor.getY());
                }
            }

            if(VerticalVector < 0 && HorizontalVector < 0){
                break;
            }
        }

        //Knight Moves :

        //East Moves
        if(initCoor.getX() + 2 < 8){
            int XVector = initCoor.getX() + 2;

            if(initCoor.getY() + 1 < 8){
                allMoves.add(new ChessCoor(XVector, initCoor.getY() + 1));
                if(moveFactor != null){
                    moveFactor.doAction(XVector, initCoor.getY() + 1);
                }
            }

            if(initCoor.getY() - 1 >= 0){
                allMoves.add(new ChessCoor(XVector, initCoor.getY() - 1));
                if(moveFactor != null){
                    moveFactor.doAction(XVector, initCoor.getY() - 1);
                }
            }
        }

        //West Moves
        if(initCoor.getX() - 2 >= 0){
            int XVector = initCoor.getX() - 2;

            if(initCoor.getY() + 1 < 8){
                allMoves.add(new ChessCoor(XVector, initCoor.getY() + 1));
                if(moveFactor != null){
                    moveFactor.doAction(XVector, initCoor.getY() + 1);
                }
            }

            if(initCoor.getY() - 1 >= 0){
                allMoves.add(new ChessCoor(XVector, initCoor.getY() - 1));
                if(moveFactor != null){
                    moveFactor.doAction(XVector, initCoor.getY() - 1);
                }
            }
        }

        //North Moves
        if(initCoor.getY() - 2 >= 0){
            int YVector = initCoor.getY() - 2;

            if(initCoor.getX() + 1 < 8){
                allMoves.add(new ChessCoor(initCoor.getX() + 1, YVector));
                if(moveFactor != null){
                    moveFactor.doAction(initCoor.getX() + 1, YVector);
                }
            }

            if(initCoor.getX() - 1 >= 0){
                allMoves.add(new ChessCoor(initCoor.getX() - 1, YVector));
                if(moveFactor != null){
                    moveFactor.doAction(initCoor.getX() - 1, YVector);
                }
            }
        }

        //South Moves
        if(initCoor.getY() + 2 < 8){
            int YVector = initCoor.getY() + 2;

            if(initCoor.getX() + 1 < 8){
                allMoves.add(new ChessCoor(initCoor.getX() + 1, YVector));
                if(moveFactor != null){
                    moveFactor.doAction(initCoor.getX() + 1, YVector);
                }
            }

            if(initCoor.getX() - 1 >= 0){
                allMoves.add(new ChessCoor(initCoor.getX() - 1, YVector));
                if(moveFactor != null){
                    moveFactor.doAction(initCoor.getX() - 1, YVector);
                }
            }
        }

        return allMoves;
    }

    
}



interface PieceActions {
    public ArrayList<ChessCoor> GetPotentialMoves(ChessBoard CurrentBoard, ChessCoor CurrentCoord);
    public ArrayList<ChessCoor> GetControlledSquares(ChessBoard CurrentBoard, ChessCoor CurrentCoord);
    public boolean AllowedToMoveTo(ChessBoard CurrentBoard, ChessCoor CurrentCoord, ChessCoor newCoor);
    public int getMapColorScore(ChessCoor coor, int endGameFactor);
}

