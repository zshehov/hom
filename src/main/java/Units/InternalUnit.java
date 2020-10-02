package Units;

import Game.*;
import Units.Skills.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

abstract class InternalUnit implements Unit, Upgradable {
    static final double ARMOR_REDUCTION_PERCENT = 0.1;
    protected double health;
    protected double armor;
    protected double mana;

    protected boolean isDead = false;
    protected double physicalAttack;
    protected int attackRange;

    protected HashMap<Buff.Types, ArrayList<Buff>> buffs = new HashMap<>();
    protected HashMap<Debuff.Types, ArrayList<Debuff>> debuffs = new HashMap<>();

    public InternalUnit(int x, int y) {
        LimitedPosition position = new LimitedPosition(x, y, GAME_CONFIG.FIELD_WIDTH, GAME_CONFIG.FIELD_HEIGHT);
        this.position = position;
    }

    protected Position position;
    // the Unit doesn't have a team prior to being assigned to one
    protected int teamIndex = -1;

    protected boolean moveExhausted = false;
    protected boolean attackExhausted = false;

    @Override
    public int getTeamIndex() {
        if (teamIndex == -1) {
            System.out.println("WARNING: This unit isn't assigned to a team");
        }
        return teamIndex;
    }

    @Override
    public void assignToTeam(int teamIndex) {
        this.teamIndex = teamIndex;
    }

    @Override
    public boolean isAttackExhausted() {
        return attackExhausted;
    }

    public InternalUnit() {
        this(0, 0);
    }

    @Override
    public void moveTo(Position position) throws PositionException, ExhaustionException, DisabledException {
        preMoveCheck();

        UnitGroup allUnits = Environment.getEnvironment().getAllUnits();

        Optional<Unit> unitAt = allUnits.getUnitAt(position);
        if (unitAt.isPresent()) {
            throw new PositionException("Position " + position + " already taken.");
        }

        this.position.setPosition(position);
        moveExhausted = true;
    }

    public void goToUnitsPosition(Unit unit) {
        this.position = unit.getPosition();
    }

    protected Attack generateAttack(Target target) {
        return new Attack(this, target).addPhysicalDamage(new PhysicalDamage(getPhysicalAttack()));
    }

    protected void attack(Target target) {
        generateAttack(target).doAttack();
        attackExhausted = true;
    }

    @Override
    public void attackPosition(Position targetPosition) throws PositionException, ExhaustionException, DisabledException {
        if (position.isCloserThan(targetPosition, attackRange)) {
            UnitGroup enemies = Environment.getEnvironment().getEnemiesOf(teamIndex);

            Optional<Unit> target = enemies.getUnitAt(targetPosition);

            if (!target.isPresent()) {
                throw new PositionException("An enemy unit is not present at position " + targetPosition + ".");
            }

            preAttackCheck();
            attack(target.get());
        } else {
            throw new PositionException("Target " + targetPosition + " is unreachable.");
        }
    }

    @Override
    public void castAtPosition(String spellName, Position position) throws PositionException,
            DisabledException, OutOfManaException, IllegalArgumentException {

        UnitGroup units = Environment.getEnvironment().getAllUnits();

        Optional<Unit> target = units.getUnitAt(position);

        if (!target.isPresent()) {
            throw new PositionException("No unit present at position " + position + ".");
        }

        preCastCheck();
        cast(spellName, target.get());
    }

    protected void cast(String spellName, Unit target) throws OutOfManaException, IllegalArgumentException {
        throw new IllegalArgumentException("Unit has no spells to cast");
    }

    @Override
    public void loseHealth(double amount) {
        System.out.println(this + " just lost " + amount + " health");
        health -= amount;
        if (getHealth() <= 0) {
            die();
        }
    }

    /**
     * death just marks the unit to not be used again
     */
    @Override
    public void die() {
        isDead = true;

        // TODO: postpone environment cleanup to be done every N deaths to get better amortized complexity
        //UnitGroup allies = Environment.getEnvironment().getAlliesOf(this);
        //allies.removeUnit(this);
    }

    @Override
    public boolean isDead() {
        return isDead;
    }

    @Override
    public void addBuff(Buff buff) {
        if (!buffs.containsKey(buff.getType())) {
            buffs.put(buff.getType(), new ArrayList<Buff>());
        }

        buffs.get(buff.getType()).add(buff);
    }

    @Override
    public void addDebuff(Debuff debuff) {
        if (!debuffs.containsKey(debuff.getType())) {
            debuffs.put(debuff.getType(), new ArrayList<Debuff>());
        }

        debuffs.get(debuff.getType()).add(debuff);
    }

    /* Implementation of Units.Attacker */
    @Override
    public double getSpellEnhancement() {
        return getAmountFromBuffType(Buff.Types.SpellAttack);
    }

    @Override
    public double getPhysicalEnhancement() {
        return getAmountFromBuffType(Buff.Types.PhysicalAttack);
    }

    @Override
    public double getPoisonEnhancement() {
        return getAmountFromBuffType(Buff.Types.PoisonAttack);
    }

    @Override public double getSpellReduction() {
        return getAmountFromBuffType(Buff.Types.SpellDefense);
    }

    // each armor point reduces physical damage by 0.1 %
    @Override
    public double getPhysicalReduction() {
        double reduction = armor * ARMOR_REDUCTION_PERCENT;
        reduction += getAmountFromBuffType(Buff.Types.PhysicalDefense);
        return reduction;
    }

    @Override
    public double getPoisonReduction() {
        return getAmountFromBuffType(Buff.Types.PoisonDefense);
    }

    private double getAmountFromBuffType(Buff.Types type) {
        double amount = 0;
        if (buffs.containsKey(type)) {
            for (Buff buff : buffs.get(type)) {
                amount += buff.getAmountPercent();
            }
        }
        return amount;
    }
    private double getAmountFromDebuffType(Debuff.Types type) {
        double amount = 0;
        if (debuffs.containsKey(type)) {
            for (Debuff debuff : debuffs.get(type)) {
                amount += debuff.getAmountPercent();
            }
        }
        return amount;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    protected boolean hasEnoughManaFor(double mana) {
        return mana <= this.mana;
    }

    @Override
    public void liveTurn() {
        moveExhausted = false;
        attackExhausted = false;
        // put the cleanup before affects because this is fired at the beginning of each turn
        buffs.forEach((k, v) -> v.removeIf(EffectOverTime::hasExpired));
        debuffs.forEach((k, v) -> v.removeIf(EffectOverTime::hasExpired));

        // remove the newly empty types of effects
        buffs.entrySet().removeIf(entry -> entry.getValue().isEmpty());
        debuffs.entrySet().removeIf(entry -> entry.getValue().isEmpty());

        buffs.forEach((k, v) -> v.forEach(buff -> buff.affect(this)));
        debuffs.forEach((k, v) -> v.forEach(debuff -> debuff.affect(this)));
    }

    /**
     * Anything is its upgraded version if there are no more upgrades. It's reached perfection basically
     * @return The upgraded version of the unit
     */
    @Override
    public InternalUnit getUpgradedUnit() {
        return this;
    }

    @Override
    public void upgrade() {
        // TODO: Make this throw
        System.out.println("No upgrades");
    }

    @Override
    public String toString() {
        return getName() + " at " + position;
    }

    void refreshMoveExhaustion() {
        moveExhausted = false;
    }

    void refreshAttackExhaustion() {
        attackExhausted = false;
    }

    void preMoveCheck() throws ExhaustionException, DisabledException {
        if (moveExhausted) {
            throw new ExhaustionException(this + " already moved this turn");
        }

        if (debuffs.containsKey(Debuff.Types.Stun)) {
            throw new DisabledException(this + " is stunned");
        }

        if (debuffs.containsKey(Debuff.Types.MovementDisable)) {
            throw new DisabledException(this + " can't move");
        }
    }

    void preAttackCheck() throws ExhaustionException, DisabledException {
        if (isAttackExhausted()) {
            throw new ExhaustionException(this + " already moved this turn");
        }

        if (debuffs.containsKey(Debuff.Types.Stun)) {
            throw new DisabledException(this + " is stunned");
        }
    }

    void preCastCheck() throws DisabledException {
        if (debuffs.containsKey(Debuff.Types.Stun)) {
            throw new DisabledException(this + " is stunned");
        }
    }

    void copyCurrentStateFrom(InternalUnit source) {
        this.health = source.health;
        this.armor = source.armor;
        this.mana = source.mana;
        this.isDead = source.isDead;
        this.physicalAttack = source.physicalAttack;
        this.attackRange = source.attackRange;
        this.position = source.position;
        this.attackExhausted = source.attackExhausted;
        this.moveExhausted = source.moveExhausted;
        this.teamIndex = source.teamIndex;

        this.buffs = source.buffs;
        this.debuffs = source.debuffs;
    }

    public double getHealth() {
        return health;
    }

    public double getPhysicalAttack() {
        return physicalAttack;
    }
}
