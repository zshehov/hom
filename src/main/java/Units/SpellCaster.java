package Units;

import Units.Skills.OutOfManaException;
import Units.Skills.Spell;

import java.util.HashMap;

public abstract class SpellCaster extends InternalUnit {
    protected HashMap<String, Spell> spells;

    public SpellCaster(int x, int y) {
        super(x, y);
        spells = new HashMap<>();
    }

    public SpellCaster() {
        super();
        spells = new HashMap<>();
    }

    @Override
    protected void cast(String spellName, Unit target) throws OutOfManaException, IllegalArgumentException {
        if (!spells.containsKey(spellName)) {
            throw new IllegalArgumentException("No such spell for " + getName());
        }

        spells.get(spellName).cast(target);
    }

    /**
     * This method must be called in the beginning of any spell that a hero has
     * @param mana Needed mana for the spell
     * @throws OutOfManaException
     */
    public void prepareCast(double mana) throws OutOfManaException {
        if (!hasEnoughManaFor(mana)) {
            throw new OutOfManaException("Not enough mana");
        }
        this.mana -= mana;
    }
}
