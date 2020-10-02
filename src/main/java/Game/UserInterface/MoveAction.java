package Game.UserInterface;

public class MoveAction extends SrcAndDstPositionActionAbstract implements Action {
    @Override
    public ActionType getType() {
        return ActionType.MOVE;
    }
}
