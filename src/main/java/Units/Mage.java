package Units;

import Game.*;
import Units.Skills.*;

public class Mage extends Hero {

    public Mage(int x, int y) {
        super(x, y);
        this.health = GAME_CONFIG.MAGE_HEALTH;
        this.armor = GAME_CONFIG.MAGE_ARMOR;
        this.mana = GAME_CONFIG.MAGE_MANA;
        this.attackRange = GAME_CONFIG.RANGED_RANGE;
        this.physicalAttack = GAME_CONFIG.MAGE_ATTACK;

        this.spells.put("fireball", new Fireball(this, 40));
        this.spells.put("frostbolt", new Frostbolt(this, 40));
    }

    public class Fireball extends BasicSpell {
        public Fireball(SpellCaster caster, int manaCost) {
            super(caster, manaCost);
        }

        @Override
        public void cast(Unit target) throws OutOfManaException {
            super.cast(target);

            Attack fireball = new Attack(caster, target);
            fireball.addSpellDamage(new SpellDamage(40));
            fireball.doAttack();
        }
    }

    public class Frostbolt extends BasicSpell {
        public Frostbolt(SpellCaster caster, int manaCost) {
            super(caster, manaCost);
        }

        @Override
        public void cast(Unit target) throws OutOfManaException {
            super.cast(target);
            target.addDebuff(new Freeze());
        }

        public class Freeze extends Debuff {
            public static final int FREEZE_DURATION = 1;

            public Freeze() {
                super(FREEZE_DURATION);
            }

            @Override
            public Types getType() {
                return Types.MovementDisable;
            }
        }
    }

    @Override
    public String getName() {
        return "mage";
    }

}
