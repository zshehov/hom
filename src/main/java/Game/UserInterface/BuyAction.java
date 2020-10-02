package Game.UserInterface;

public class BuyAction extends SrcPositionActionAbstract {
    @Override
    public ActionType getType() {
        return ActionType.BUY;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    private String unitName;
}
