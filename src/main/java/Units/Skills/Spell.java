package Units.Skills;

import Units.SpellCaster;
import Units.Unit;

public interface Spell {
    void cast(Unit target) throws OutOfManaException;
}
