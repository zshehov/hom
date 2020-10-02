package Game.UserInterface;

import Game.Position;

public interface SrcAndDstPositionAction extends SrcPositionAction, Action {
    Position getDstPosition();
    void setDstPosition(Position dstPosition);
}
