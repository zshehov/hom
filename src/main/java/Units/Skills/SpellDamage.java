package Units.Skills;

import Units.Attacker;
import Units.Target;

public class SpellDamage extends Damage {
    public SpellDamage(double amount) {
        super(amount);
    }

    public void applyEnhancement(Attacker source) {
        super.applyEnhancement(source.getSpellEnhancement());
    }

    public void applyReduction(Target target) {
        super.applyReduction(target.getSpellReduction());
    }
}
