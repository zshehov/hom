package Units;

/**
 * Upgrades HornedDemon
 */
public class HornedGrunt extends InternalUnit {

    static final double HORNEDGRUNT_DOUBLEHIT_CHANCE = 0.25;

    @Override
    protected void attack(Target target) {
        super.attack(target);

        if (Math.random() <= HORNEDGRUNT_DOUBLEHIT_CHANCE) {
            System.out.println("Horned grunt just double hit!");
            super.attack(target);
        }
    }

    HornedGrunt(HornedDemon hornedDemon) {
        copyCurrentStateFrom(hornedDemon);
    }

    @Override
    public String getName() {
        return "hornedgrunt";
    }
}
