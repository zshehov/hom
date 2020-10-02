package Units.Skills;

import Units.Attacker;
import Units.Target;

public class PoisonDamage extends Damage {

    private int duration;

    public PoisonDamage(double amount, int duration) {
        super(amount);
        this.duration = duration;
    }

    @Override
    public void dealDamage(Target target) {
        target.addDebuff(new Poison(this.duration, this.getAmount()));
    }

    public void applyEnhancement(Attacker source) {
        super.applyEnhancement(source.getPoisonEnhancement());
    }

    public void applyReduction(Target target) {
        super.applyReduction(target.getPoisonReduction());
    }
}
