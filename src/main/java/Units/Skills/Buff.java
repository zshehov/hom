package Units.Skills;

public abstract class Buff extends EffectOverTime {

    public static enum Types {
        PhysicalAttack,
        SpellAttack,
        PoisonAttack,
        PhysicalDefense,
        SpellDefense,
        PoisonDefense,
        Movement
    }

    public Buff(int duration, double amountPercent) {
        super(duration, amountPercent);
    }
    public Buff(int duration) {
        this(duration, 0);
    }

    abstract public Types getType();
}
