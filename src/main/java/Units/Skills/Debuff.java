package Units.Skills;

public abstract class Debuff extends EffectOverTime {
    public static enum Types {
        PhysicalAttack,
        SpellAttack,
        PoisonAttack,
        PhysicalDefense,
        SpellDefense,
        PoisonDefense,
        MovementDisable,
        Movement,
        Poison,
        Stun
    }


    public Debuff(int duration, double amountPercent) {
        super(duration, amountPercent);
    }
    public Debuff(int duration) {
        this(duration, 0);
    }

    abstract public Types getType();
}
