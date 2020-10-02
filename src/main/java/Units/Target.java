package Units;

import Game.Position;
import Units.Skills.Debuff;

public interface Target {
    void loseHealth(double amount);

    double getSpellReduction();
    double getPhysicalReduction();
    double getPoisonReduction();

    void addDebuff(Debuff debuff);
    void die();
    Position getPosition();
}
