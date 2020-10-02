package Units;

import Game.GAME_CONFIG;

class Angel extends InternalUnit implements Upgradable {
    public static double ANGEL_RESSURECT_CHANCE = 0.5;

    public Angel(int x, int y) {
        super(x, y);
        this.health = GAME_CONFIG.TIER_3_HEALTH;
        this.armor = GAME_CONFIG.TIER_3_ARMOR;
        this.mana = GAME_CONFIG.TIER_3_MANA;
        this.attackRange = GAME_CONFIG.MELEE_RANGE;
        this.physicalAttack = GAME_CONFIG.TIER_3_ATTACK;
    }

    @Override
    public void die() {
        if (Math.random() <= ANGEL_RESSURECT_CHANCE) {
            health = GAME_CONFIG.TIER_3_HEALTH;
            System.out.println("Units.Angel just resurrected!");
        } else {
            super.die();
        }
    }

    @Override
    public InternalUnit getUpgradedUnit() {
        return new Archangel(this);
    }

    @Override
    public String getName() {
        return "angel";
    }
}
