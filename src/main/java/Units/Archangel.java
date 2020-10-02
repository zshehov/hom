package Units;

import Game.Environment;
import Units.Skills.Attack;
import Units.Skills.PhysicalDamage;

/**
 * Upgrades Angel
 */
class Archangel extends InternalUnit {
    private static final double ARCHANGEL_SPILL_PERCENT = 0.5;

    @Override
    public void attack(Target target) {
        super.attack(target);

        UnitGroup enemies = Environment.getEnvironment().getEnemiesOf(teamIndex);
        for (Unit enemy : enemies.getUnitsBehind(target.getPosition(), this.position)) {
            System.out.print("[spill] ");
            new Attack(this, enemy)
                    .addPhysicalDamage(new PhysicalDamage(getPhysicalAttack() * ARCHANGEL_SPILL_PERCENT))
                    .doAttack();
        }
    }

    Archangel(Angel angel) {
        copyCurrentStateFrom(angel);
    }

    @Override
    public String getName() {
        return "archangel";
    }
}
