package Game.UserInterface;

import Game.Position;

public abstract class SrcPositionActionAbstract implements SrcPositionAction {

    @Override
    public Position getSrcPosition() {
        return srcPosition;
    }

    public void setSrcPosition(Position srcPosition) {
        this.srcPosition = srcPosition;
    }

    protected Position srcPosition;
}
