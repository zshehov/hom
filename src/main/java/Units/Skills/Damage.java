package Units.Skills;

import Units.Attacker;
import Units.Target;

public abstract class Damage {
    private double amount;

    public Damage(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void dealDamage(Target target) {
        target.loseHealth(Math.max(amount, 0));
    }

    public abstract void applyEnhancement(Attacker source);
    public abstract void applyReduction(Target target);

    protected void applyReduction(double percent) {
        amount -= (amount * percent);
    }

    protected void applyEnhancement(double percent) {
        amount += (amount * percent);
    }
}
