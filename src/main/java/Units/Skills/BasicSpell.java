package Units.Skills;

import Units.SpellCaster;
import Units.Unit;

public abstract class BasicSpell implements Spell {
    public BasicSpell(SpellCaster caster, int manaCost) {
        this.caster = caster;
        this.manaCost = manaCost;
    }

    @Override
    public void cast(Unit target) throws OutOfManaException {
        caster.prepareCast(manaCost);
    }

    protected SpellCaster caster;
    protected int manaCost;
}
