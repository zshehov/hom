package Units;

import Game.Environment;
import Units.Skills.Attack;
import Units.Skills.PhysicalDamage;

import java.util.ArrayList;

/**
 * Upgrades Archer
 */
public class CrossbowMan extends InternalUnit {
    private static final double CROSSBOWMAN_SPLASH_PERCENT = 0.25;

    @Override
    protected void attack(Target target) {
        super.attack(target);

        UnitGroup enemies = Environment.getEnvironment().getEnemiesOf(teamIndex);
        ArrayList<Unit> surrounding = enemies.getUnitsAround(target.getPosition());

        for (Target enemy : surrounding) {
            System.out.print("[splash] ");
            new Attack(this, enemy)
                    .addPhysicalDamage(new PhysicalDamage(getPhysicalAttack() * CROSSBOWMAN_SPLASH_PERCENT))
                    .doAttack();
        }
    }

    CrossbowMan(Archer archer) {
        copyCurrentStateFrom(archer);
    }

    @Override
    public String getName() {
        return "crossbowman";
    }
}
