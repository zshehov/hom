package Game.UserInterface;

public class UpgradeAction extends SrcPositionActionAbstract implements Action {
    @Override
    public ActionType getType() {
        return ActionType.UPGRADE;
    }
}
