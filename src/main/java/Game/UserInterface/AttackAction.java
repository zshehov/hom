package Game.UserInterface;

public class AttackAction extends SrcAndDstPositionActionAbstract implements Action {
    @Override
    public ActionType getType() {
        return ActionType.ATTACK;
    }
}
