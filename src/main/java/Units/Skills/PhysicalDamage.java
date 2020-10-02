package Units.Skills;

import Units.Attacker;
import Units.Target;

public class PhysicalDamage extends Damage {
    public PhysicalDamage(double amount) {
        super(amount);
    }

    public void applyEnhancement(Attacker source) {
        super.applyEnhancement(source.getPhysicalEnhancement());
    }

    public void applyReduction(Target target) {
        super.applyReduction(target.getPhysicalReduction());
    }
}
