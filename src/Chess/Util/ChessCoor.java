package Chess.Util;
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
}

class InvalidChessCoordinateException extends IllegalArgumentException{
    InvalidChessCoordinateException(){ super(); }
}
