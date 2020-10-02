package Units;

import Game.GAME_CONFIG;
import Units.Skills.*;

public class Warrior extends Hero {

    public Warrior(int x, int y) {
        super(x, y);
        this.health = GAME_CONFIG.WARRIOR_HEALTH;
        this.armor = GAME_CONFIG.WARRIOR_ARMOR;
        this.mana = GAME_CONFIG.WARRIOR_MANA;
        this.attackRange = GAME_CONFIG.RANGED_RANGE;
        this.physicalAttack = GAME_CONFIG.WARRIOR_ATTACK;

        this.spells.put("shieldprotect", new ShieldProtect(this, 40));
        this.spells.put("shieldstun", new ShieldStun(this, 40));
    }

    public class ShieldStun extends BasicSpell {
        public ShieldStun(SpellCaster caster, int manaCost) {
            super(caster, manaCost);
        }

        @Override
        public void cast(Unit target) throws OutOfManaException {
            super.cast(target);

            target.addDebuff(new ShieldStunDebuff());
        }

        public class ShieldStunDebuff extends Debuff {
            public static final int SHIELD_STUN_DURATION = 1;

            public ShieldStunDebuff() {
                super(SHIELD_STUN_DURATION);
            }

            @Override
            public Types getType() {
                return Types.Stun;
            }
        }
    }

    public class ShieldProtect extends BasicSpell {
        public ShieldProtect(SpellCaster caster, int manaCost) {
            super(caster, manaCost);
        }

        @Override
        public void cast(Unit target) throws OutOfManaException {
            super.cast(target);
            target.addBuff(new ShieldProtection());
        }

        public class ShieldProtection extends Buff {
            public static final int SHIELD_PROTECTION_DURATION = 1;
            public static final int SHIELD_PROTECTION_AMOUNT = 1;

            public ShieldProtection() {
                super(SHIELD_PROTECTION_DURATION, SHIELD_PROTECTION_AMOUNT);
            }

            @Override
            public Types getType() {
                return Types.PhysicalDefense;
            }
        }
    }

    @Override
    public String getName() {
        return "warrior";
    }
}
