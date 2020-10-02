package Game.UserInterface;

import Game.Position;

public interface SrcPositionAction extends Action {
    Position getSrcPosition();
    void setSrcPosition(Position srcPosition);
}
