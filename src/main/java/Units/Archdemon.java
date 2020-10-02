package Units;

import Game.Position;
import Units.Skills.BasicSpell;
import Units.Skills.OutOfManaException;

/**
 * Upgrades Devil
 */
public class Archdemon extends SpellCaster {

    Archdemon(Devil devil) {
        copyCurrentStateFrom(devil);

        this.spells.put("swap", new Swap(this, 40));
    }

    public class Swap extends BasicSpell {
        public Swap(SpellCaster caster, int manaCost) {
            super(caster, manaCost);
        }

        @Override
        public void cast(Unit target) throws OutOfManaException {
            super.cast(target);
            Position temp = target.getPosition();

            target.goToUnitsPosition(caster);
            caster.position = temp;
        }
    }

    @Override
    public String getName() {
        return "archdemon";
    }
}
