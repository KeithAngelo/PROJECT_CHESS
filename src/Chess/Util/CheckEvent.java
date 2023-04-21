package Chess.Util;

@FunctionalInterface
public interface CheckEvent{
    public void doCheckEvent(PieceColor ColorBeingChecked);
}

