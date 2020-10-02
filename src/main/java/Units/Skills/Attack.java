package Units.Skills;

import Units.Attacker;
import Units.Target;

import java.util.HashMap;

/*
 Tightly coupled with Units.Attacker and Units.Target - they have to implement the reductions and enhancements
 */
public class Attack {

    private enum DamageType {
        Physical, Spell, Poison;
    }

    private HashMap<DamageType, Damage> damages;
    Attacker source;
    Target target;

    public Attack(Attacker source, Target target) {
        this.source = source;
        this.target = target;
        damages = new HashMap<>();
    }

    // TBD: Handle multiple add of the same kind
    public Attack addPhysicalDamage(PhysicalDamage physicalDamage) {
        damages.put(DamageType.Physical, physicalDamage);
        return this;
    }
    public Attack addSpellDamage(SpellDamage spellDamage) {
        damages.put(DamageType.Spell, spellDamage);
        return this;
    }
    public Attack addPoisonDamage(PoisonDamage poisonDamage) {
        damages.put(DamageType.Poison, poisonDamage);
        return this;
    }

    public void doAttack() {
        applyBonuses();
        applyReductions();

        damages.forEach((k, damage) -> damage.dealDamage(target));
    }


    private void applyBonuses() {
        damages.forEach((k, damage) -> damage.applyEnhancement(source));
    }

    private void applyReductions() {
        damages.forEach((k, v) -> v.applyReduction(target));
    }
}
