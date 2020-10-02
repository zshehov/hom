package Units;

import Game.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UnitGroup {
    // TODO: make this map <position -> unit> for quicker acces of getUnitAt
    private Set<Unit> units;

    public UnitGroup() {
        this.units = new HashSet<Unit>();
    }

    public Set<Unit> getUnits() {
        return units;
    }

    public void addUnit(Unit unit) {
        units.add(unit);
    }
    public void removeUnit(Unit unit) {
        units.remove(unit);
    }

    public Optional<Unit> getUnitAt(Position position) {
        return units.stream().filter(unit -> unit.getPosition().equals(position)).findFirst();
    }

    public boolean unitIsPresentAt(Position position) {
        return getUnitAt(position).isPresent();
    }

    public ArrayList<Unit> getUnitsAround(Position position) {
        ArrayList<Unit> unitsAround = new ArrayList<>();
        for (Position pos : position.getPositionsAround()) {
            Optional<Unit> unitAt = getUnitAt(pos);
            if (unitAt.isPresent()) {
                unitsAround.add(unitAt.get());
            }
        }
        return unitsAround;
    }

    public ArrayList<Unit> getUnitsBehind(Position target, Position me) {
        ArrayList<Unit> unitsBehind = new ArrayList<>();
        for (Position pos : target.getPositionsBehind(me)) {
            Optional<Unit> unitAt = getUnitAt(pos);
            if (unitAt.isPresent()) {
                unitsBehind.add(unitAt.get());
            }
        }
        return unitsBehind;
    }

    public void liveTurn() {
        units.forEach(Unit::liveTurn);
    }

    @Override
    public String toString() {
        return "units=" + units;
    }
}
