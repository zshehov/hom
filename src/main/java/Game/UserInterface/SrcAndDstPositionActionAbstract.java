package Game.UserInterface;

import Game.Position;

abstract class SrcAndDstPositionActionAbstract extends SrcPositionActionAbstract implements SrcAndDstPositionAction {
    @Override
    public Position getDstPosition() {
        return dstPosition;
    }

    public void setDstPosition(Position dstPosition) {
        this.dstPosition = dstPosition;
    }

    protected Position dstPosition;
}
