package Game.UserInterface;

public class CastAction extends SrcAndDstPositionActionAbstract {
    @Override
    public ActionType getType() {
        return ActionType.CAST;
    }

    public String getSpellName() {
        return spellName;
    }

    public void setSpellName(String spellName) {
        this.spellName = spellName;
    }

    private String spellName;
}
