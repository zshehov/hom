package Units;

import Game.GAME_CONFIG;

class Imp extends InternalUnit implements Upgradable {

    public Imp(int x, int y) {
        super(x, y);
        this.health = GAME_CONFIG.TIER_1_HEALTH;
        this.armor = GAME_CONFIG.TIER_1_ARMOR;
        this.mana = GAME_CONFIG.TIER_1_MANA;
        this.attackRange = GAME_CONFIG.MELEE_RANGE;
        this.physicalAttack = GAME_CONFIG.TIER_1_ATTACK;
    }

    @Override
    public InternalUnit getUpgradedUnit() {
        return new Vermin(this);
    }


    @Override
    public String getName() {
        return "imp";
    }
}
