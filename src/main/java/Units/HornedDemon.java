package Units;

import Game.GAME_CONFIG;

class HornedDemon extends InternalUnit implements Upgradable {

    public HornedDemon(int x, int y) {
        super(x, y);
        this.health = GAME_CONFIG.TIER_2_HEALTH;
        this.armor = GAME_CONFIG.TIER_2_ARMOR;
        this.mana = GAME_CONFIG.TIER_2_MANA;
        this.attackRange = GAME_CONFIG.RANGED_RANGE;
        this.physicalAttack = GAME_CONFIG.TIER_2_ATTACK;
    }

    @Override
    public InternalUnit getUpgradedUnit() {
        return new HornedGrunt(this);
    }

    @Override
    public String getName() {
        return "horneddemon";
    }
}
