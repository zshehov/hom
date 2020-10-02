package Units;

import Game.DisabledException;
import Game.ExhaustionException;
import Game.Position;
import Game.PositionException;
import Units.Skills.Buff;
import Units.Skills.OutOfManaException;
import Units.Skills.Spell;

import java.util.HashMap;

public interface Unit extends Target, Attacker {

    int getTeamIndex();
    void assignToTeam(int teamIndex);
    void moveTo(Position position) throws PositionException, ExhaustionException, DisabledException;
    void castAtPosition(String spellName, Position position) throws PositionException, DisabledException,
            OutOfManaException, IllegalArgumentException;
    void goToUnitsPosition(Unit unit);
    boolean isDead();
    void addBuff(Buff buff);
    void liveTurn();
    void upgrade();
    String getName();
}
