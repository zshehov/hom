package Units;

import Game.Environment;
import Units.Skills.Attack;
import Units.Skills.PhysicalDamage;

import java.util.ArrayList;

/**
 * Upgrades Peasent
 */
public class Brute extends InternalUnit {
    static final double BRUTE_SPLASH_PERCENT = 0.25;

    @Override
    protected void attack(Target target) {
        super.attack(target);

        UnitGroup enemies = Environment.getEnvironment().getEnemiesOf(teamIndex);
        ArrayList<Unit> surrounding = enemies.getUnitsAround(target.getPosition());

        for (Target enemy : surrounding) {
            System.out.print("[splash] ");
            new Attack(this, enemy)
                    .addPhysicalDamage(new PhysicalDamage(getPhysicalAttack() * BRUTE_SPLASH_PERCENT))
                    .doAttack();
        }
    }

    Brute(Peasent peasent) {
        copyCurrentStateFrom(peasent);
    }

    @Override
    public String getName() {
        return "brute";
    }
}
