package Units;

import Game.GAME_CONFIG;

class Peasent extends InternalUnit implements Upgradable {

    public Peasent(int x, int y) {
        super(x, y);
        this.health = GAME_CONFIG.TIER_1_HEALTH;
        this.armor = GAME_CONFIG.TIER_1_ARMOR;
        this.mana = GAME_CONFIG.TIER_1_MANA;
        this.attackRange = GAME_CONFIG.MELEE_RANGE;
        this.physicalAttack = GAME_CONFIG.TIER_1_ATTACK;
    }

    @Override
    public InternalUnit getUpgradedUnit() {
        return new Brute(this);
    }

    @Override
    public String getName() {
        return "peasent";
    }
}
