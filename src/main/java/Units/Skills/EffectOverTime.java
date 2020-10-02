package Units.Skills;

import Units.Target;

public abstract class EffectOverTime {
    protected int duration;
    protected double amountPercent;

    public EffectOverTime(int duration) {
        this(duration, 0);
    }

    public EffectOverTime(int duration, double amountPercent) {
        this.duration = duration;
        this.amountPercent = amountPercent;
    }

    public void expire() {
        --duration;
    }
    public boolean hasExpired() {
        return duration == 0;
    }

    public void affect(Target target) {
        expire();
    }

    public double getAmountPercent() {
        return amountPercent;
    }
}
