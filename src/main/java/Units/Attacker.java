package Units;

import Game.DisabledException;
import Game.ExhaustionException;
import Game.Position;
import Game.PositionException;
import Units.Skills.OutOfManaException;

public interface Attacker {

    double getSpellEnhancement();
    double getPhysicalEnhancement();
    double getPoisonEnhancement();

    void attackPosition(Position targetPosition) throws PositionException, ExhaustionException, DisabledException;
    void castAtPosition(String spellName, Position position) throws PositionException, ExhaustionException,
            DisabledException, OutOfManaException, IllegalArgumentException;

    boolean isAttackExhausted();
}
