package Units.Skills;

import Units.Target;

public class Poison extends Debuff {
    private double amount;

    public Poison(int duration, double amount) {
        super(duration);
        this.amount = amount;
    }

    @Override
    public Types getType() {
        return Types.Poison;
    }

    @Override
    public void affect(Target target) {
        super.affect(target);
        System.out.print("[poison] ");
        target.loseHealth(amount);
    }
}