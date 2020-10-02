package Units;

import Game.DisabledException;
import Game.ExhaustionException;
import Game.Position;
import Game.PositionException;
import Units.Skills.Buff;
import Units.Skills.Debuff;
import Units.Skills.OutOfManaException;

/**
 * A wrapper around an InternalUnit object that enables the upgrade of the InternalUnit
 *
 * Implementing InvocationHandler doesn't delegate method calls to any derived classes from InternalUnit.
 * Instead it just calls the InternalUnit's implementation which is not acceptible
 */
public class UnitProxy implements Unit {
    private InternalUnit internalUnit;

    public UnitProxy(InternalUnit internalUnit) {
        this.internalUnit = internalUnit;
    }

    @Override
    public boolean isAttackExhausted() {
        return internalUnit.isAttackExhausted();
    }

    @Override
    public void attackPosition(Position targetPosition) throws PositionException, ExhaustionException, DisabledException {
        internalUnit.attackPosition(targetPosition);
    }

    @Override
    public void castAtPosition(String spellName, Position position) throws PositionException,
            DisabledException, OutOfManaException, IllegalArgumentException {
        internalUnit.castAtPosition(spellName, position);
    }

    @Override
    public void loseHealth(double amount) {
        internalUnit.loseHealth(amount);
    }

    @Override
    public void die() {
        internalUnit.die();
    }

    @Override
    public void addDebuff(Debuff debuff) {
        internalUnit.addDebuff(debuff);
    }

    @Override
    public double getSpellEnhancement() {
        return internalUnit.getSpellEnhancement();
    }

    @Override
    public double getPhysicalEnhancement() {
        return internalUnit.getPhysicalEnhancement();
    }

    @Override
    public double getPoisonEnhancement() {
        return internalUnit.getPoisonEnhancement();
    }

    @Override
    public double getSpellReduction() {
        return internalUnit.getSpellReduction();
    }

    @Override
    public double getPhysicalReduction() {
        return internalUnit.getPhysicalReduction();
    }

    @Override
    public double getPoisonReduction() {
        return internalUnit.getPoisonReduction();
    }

    @Override
    public Position getPosition() {
        return internalUnit.getPosition();
    }

    @Override
    public String toString() {
        return internalUnit.toString();
    }

    @Override
    public int getTeamIndex() {
        return internalUnit.getTeamIndex();
    }

    @Override
    public void assignToTeam(int teamIndex) {
        internalUnit.assignToTeam(teamIndex);
    }

    @Override
    public void moveTo(Position position) throws PositionException, ExhaustionException, DisabledException {
        internalUnit.moveTo(position);
    }

    public void goToUnitsPosition(Unit unit) {
        internalUnit.goToUnitsPosition(unit);
    }

    @Override
    public boolean isDead() {
        return false;
    }

    @Override
    public void liveTurn() {
        internalUnit.liveTurn();
    }

    @Override
    public String getName() {
        return internalUnit.getName();
    }

    @Override
    public void addBuff(Buff buff) {
        internalUnit.addBuff(buff);
    }

    /**
     * Changes the internal unit we proxy calls to to the upgraded version of it
     */
    public void upgrade() {
        this.internalUnit = this.internalUnit.getUpgradedUnit();
    }

}
