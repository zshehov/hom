package Units;

import Game.Environment;
import Game.GAME_CONFIG;
import Units.Skills.Attack;
import Units.Skills.SpellDamage;

class Devil extends InternalUnit implements Upgradable {

    public static double DEVIL_EXPLODE_DAMAGE = 50;

    public Devil(int x, int y) {
        super(x, y);
        this.health = GAME_CONFIG.TIER_3_HEALTH;
        this.armor = GAME_CONFIG.TIER_3_ARMOR;
        this.mana = GAME_CONFIG.TIER_3_MANA;
        this.attackRange = GAME_CONFIG.MELEE_RANGE;
        this.physicalAttack = GAME_CONFIG.TIER_3_ATTACK;
    }

    @Override
    public void die() {
        UnitGroup enemies = Environment.getEnvironment().getEnemiesOf(teamIndex);
        enemies.getUnitsAround(this.position).forEach(enemy -> new Attack(this, enemy)
                .addSpellDamage(new SpellDamage(DEVIL_EXPLODE_DAMAGE)).doAttack());
    }

    @Override
    public InternalUnit getUpgradedUnit() {
        return new Archdemon(this);
    }

    @Override
    public String getName() {
        return "devil";
    }
}
