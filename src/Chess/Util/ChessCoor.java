package Chess.Util;

import java.util.ArrayList;

public class ChessCoor {
    int X;
    int Y;

    /* 0,0 is TOP LEFT of the board */

    public ChessCoor(int X, int Y) throws InvalidChessCoordinateException{
        if((X > 7 || X < 0) || (Y > 7 || Y < 0)){
            throw new InvalidChessCoordinateException();
        }
        
        this.X = X;
        this.Y = Y;
    }

    public int getX(){
        return X;
    }

    public int getY(){
        return Y;
    }

    public boolean equals(ChessCoor coor){
        return (coor.getX() == X)  && (coor.getY() == Y);
    }

    public boolean isContainedIn(ArrayList<ChessCoor> listOfCoor){
        for(ChessCoor thisCoor : listOfCoor){
            if(thisCoor.equals(this)){
                return true;
            }
        }
        return false;
    }
}

class InvalidChessCoordinateException extends IllegalArgumentException{
    InvalidChessCoordinateException(){ super(); }
}
