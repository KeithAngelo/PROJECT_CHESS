package Chess.Util;

// Win Event interface : arguements include color of the winner
@FunctionalInterface
public interface WinEvent{
    public void doWinEvent(PieceColor ColorOfWinner);
}
