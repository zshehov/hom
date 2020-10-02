package Units;

import Game.GAME_CONFIG;

import java.util.HashMap;

public class Shop {
    private static class FatUnit {
        interface Creator {
            InternalUnit create(int x, int y);
        }
        int price;
        int upgradePrice;
        Creator creator;

        public FatUnit(int price, int upgradePrice, Creator creator) {
            this.price = price;
            this.upgradePrice = upgradePrice;
            this.creator = creator;
        }

        FatUnit(int price, Creator creator) {
            this(price, 0, creator);
        }
    }
    private HashMap<String, FatUnit> unitMap = new HashMap<String, FatUnit>();

    public Shop() {
        // Heaven
        unitMap.put("peasent", new FatUnit(GAME_CONFIG.TIER_1_PRICE, GAME_CONFIG.TIER_1_UPGRADE_PRICE, Peasent::new));
        unitMap.put("archer", new FatUnit(GAME_CONFIG.TIER_2_PRICE, GAME_CONFIG.TIER_2_UPGRADE_PRICE, Archer::new));
        unitMap.put("angel", new FatUnit(GAME_CONFIG.TIER_3_PRICE, GAME_CONFIG.TIER_3_UPGRADE_PRICE, Angel::new));

        unitMap.put("brute", new FatUnit(GAME_CONFIG.TIER_1_PRICE + GAME_CONFIG.TIER_1_UPGRADE_PRICE,
                (int x, int y) -> (new Peasent(x, y)).getUpgradedUnit()));
        unitMap.put("crossbowman", new FatUnit(GAME_CONFIG.TIER_2_PRICE + GAME_CONFIG.TIER_2_UPGRADE_PRICE,
                (int x, int y) -> new Archer(x, y).getUpgradedUnit()));
        unitMap.put("archangel", new FatUnit(GAME_CONFIG.TIER_3_PRICE + GAME_CONFIG.TIER_3_UPGRADE_PRICE,
                (int x, int y) -> new Angel(x, y).getUpgradedUnit()));

        // Inferno
        unitMap.put("imp", new FatUnit(GAME_CONFIG.TIER_1_PRICE, GAME_CONFIG.TIER_1_UPGRADE_PRICE, Imp::new));
        unitMap.put("horneddemon", new FatUnit(GAME_CONFIG.TIER_2_PRICE, GAME_CONFIG.TIER_2_UPGRADE_PRICE, HornedDemon::new));
        unitMap.put("devil", new FatUnit(GAME_CONFIG.TIER_3_PRICE, GAME_CONFIG.TIER_3_UPGRADE_PRICE, Devil::new));

        unitMap.put("vermin", new FatUnit(GAME_CONFIG.TIER_1_PRICE + GAME_CONFIG.TIER_1_UPGRADE_PRICE,
                (int x, int y) -> (new Imp(x, y)).getUpgradedUnit()));
        unitMap.put("hornedgrunt", new FatUnit(GAME_CONFIG.TIER_2_PRICE + GAME_CONFIG.TIER_2_UPGRADE_PRICE,
                (int x, int y) -> new HornedDemon(x, y).getUpgradedUnit()));
        unitMap.put("archdemon", new FatUnit(GAME_CONFIG.TIER_3_PRICE + GAME_CONFIG.TIER_3_UPGRADE_PRICE,
                (int x, int y) -> new Devil(x, y).getUpgradedUnit()));

        // Heroes
        unitMap.put("mage", new FatUnit(GAME_CONFIG.HERO_PRICE, Mage::new));
        unitMap.put("warrior", new FatUnit(GAME_CONFIG.HERO_PRICE, Warrior::new));
    }

    public Unit getBasicUnit(String unitType, int x, int y) throws java.lang.IllegalArgumentException {
        if (!unitMap.containsKey(unitType)) {
            throw new java.lang.IllegalArgumentException("No such unit: " + unitType);
        }
        InternalUnit unit = unitMap.get(unitType).creator.create(x, y);
        return new UnitProxy(unit);
    }

    public int getPrice(String unitType) throws java.lang.IllegalArgumentException {
        if (!unitMap.containsKey(unitType)) {
            throw new java.lang.IllegalArgumentException("No such unit: " + unitType);
        }
        return unitMap.get(unitType).price;
    }

    public int getUpgradePrice(String unitType) throws java.lang.IllegalArgumentException {
        if (!unitMap.containsKey(unitType)) {
            throw new java.lang.IllegalArgumentException("No such unit: " + unitType);
        }
        return unitMap.get(unitType).upgradePrice;
    }
}
