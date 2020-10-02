package Units;

import Units.Skills.Attack;
import Units.Skills.PoisonDamage;

/**
 * Upgrades Imp
 */
public class Vermin extends InternalUnit {

    static final private double VERMIN_POISON_DAMAGE = 5;
    static final private int VERMIN_POISON_DURATION = 2;

    @Override
    protected Attack generateAttack(Target target) {
        return super.generateAttack(target).addPoisonDamage(new PoisonDamage(VERMIN_POISON_DAMAGE, VERMIN_POISON_DURATION));
    }

    Vermin(Imp imp) {
        copyCurrentStateFrom(imp);
    }

    @Override
    public String getName() {
        return "vermin";
    }
}
